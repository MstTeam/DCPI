package net.mst.dcpi.discord.app.gateway;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.net.http.WebSocket;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import net.mst.dcpi.discord.app.gateway.enums.GatewayApiVersion;
import net.mst.dcpi.discord.app.gateway.enums.Status;
import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.requests.Request;
import net.mst.requests.RequestManager;

public class Gateway {
	
	ClientInstance ClientInstance;
	WebSocket websocket;
	
	GatewayApiVersion GatewayApiVersion;
	
	final HttpClient webClient = HttpClient.newHttpClient();
	
	RequestManager reqManager = new RequestManager().setActionsPerTime(120, 60*1000);
	
	
	public Gateway(ClientInstance ClientInstance) {
		
		this.GatewayApiVersion = RequestManager.gatewayApiVersion;
		this.ClientInstance = ClientInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public Gateway(ClientInstance ClientInstance, GatewayApiVersion GatewayApiVersion) {
		
		this.GatewayApiVersion = GatewayApiVersion;
		this.ClientInstance = ClientInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public void connect() throws InterruptedException, ExecutionException, URISyntaxException {
		
		HttpResponse<String> response = ClientInstance.sendGetRequest("/gateway/bot");
		
		System.out.println(response.body());
		
		JsonObject jo = new Parser().parse(response.body());
		
		if(jo.contains("session_start_limit")) {
			
			JsonObject subjo = jo.getObject("session_start_limit");
			
			if(subjo.contains("remaining")) {
				
				if(subjo.getInteger("remaining") > 0) {
					
					websocket =  webClient.newWebSocketBuilder().buildAsync(new URI("wss://gateway.discord.gg/?v=" + this.GatewayApiVersion.getVersion() + "&encoding=json"), new GatewayManager(this)).get();
					
				}
				
			}
			
		}
		
	}

}

class GatewayManager implements Listener {
	
	protected String tempString = "";
	protected WebSocket websocket;
    
    protected String sequenceNumber = null;
    protected int heartbeat_interval = 0;
    protected Heartbeat hb;
    
    // Ping
    
    protected long ping_start = 0;
    protected boolean awaiting_ack = false;
    protected long ping = 1000;
    
    protected Status status;
    private Gateway gw;
    
    public GatewayManager(Gateway Gateway) {
    	
    	this.gw = Gateway;
    	this.websocket = gw.websocket;
    	
    }
    
    @Override
    public CompletionStage<?> onText(WebSocket webSocket, CharSequence data, boolean last) {
    	
    	this.websocket = webSocket;
    	
    	websocket.request(1);
    	tempString = tempString + data.toString();
    	
    	if(!last) {
    		
    		return null;
    		
    	}
    	
    	System.out.println("[Text] " + tempString);
    	
    	JsonObject jo = new Parser().parse(tempString);
    	
    	if(jo.contains("s")) {
    		
    		sequenceNumber = jo.getString("s");
    		
    	}
    	
    	if(jo.get("op").toString().equals("1")) {
			
    		sendCommand("{\"op\":1,\"d\":" + sequenceNumber + "}", true);
    		
    	}
    	
    	if(jo.get("op").toString().equals("10")) {
			
			this.heartbeat_interval = jo.getObject("d").getInteger("heartbeat_interval");
			
			hb = new Heartbeat(this);
			
			String s = new Parser().parse(this.gw.ClientInstance.getShard().receivePayloadObject(this.gw.ClientInstance.getToken()));
			
			System.out.println(s);
			
			// Identify Payload
			sendCommand(new Parser().parse(this.gw.ClientInstance.getShard().receivePayloadObject(this.gw.ClientInstance.getToken())), false);
			
		}
    	
    	if(jo.get("op").toString().equals("11")) {
    		
    		if(awaiting_ack) {
    			
    			awaiting_ack = false;
    			ping = System.currentTimeMillis() - ping_start;
    			hb.ping.cancel();
    			
    			System.out.println("Ping: " + ping);
    			
    		}
    		
    	}
    	
    	if(jo.get("op").toString().equals("0")) {
    		
    		System.out.println("received");
    		
    	}
    	
    	tempString = "";
    	
    	return null;
    	
    }
    
    @Override
    public CompletionStage<?> onBinary(WebSocket webSocket, ByteBuffer data, boolean last) {
    	
    	this.websocket = webSocket;
    	
    	System.out.println("[Binary] " + data.toString());
    	
    	return null;
    	
    }
    
    @Override
    public CompletionStage<?> onClose(WebSocket webSocket, int statusCode, String reason) {
    	
    	this.websocket = webSocket;
    	
    	System.out.println("[Closed] Status: " + statusCode + " / Reason: " + reason);
    	
    	return null;
    	
    }
    
    @Override
    public CompletionStage<?> onPing(WebSocket webSocket, ByteBuffer message) {
    	
    	this.websocket = webSocket;
    	
    	System.out.println("[Ping] " + message.toString());
    	
    	return null;
    	
    }
    
    @Override
	public CompletionStage<?> onPong(WebSocket webSocket, ByteBuffer message) {
		
		this.websocket = webSocket;
    	
    	System.out.println("[Pong] " + message.toString());
    	
    	return null;
    	
    }
	
	
	@Override
	public void onError(WebSocket webSocket, Throwable error) {
		
		this.websocket = webSocket;
    	
    	System.err.println("[Error] " + error.getMessage());
    	
    	error.printStackTrace();
    	
    }
	
	protected void sendCommand(String command, boolean instant) {
		
		if(instant) {
			
			this.gw.reqManager.instantRequest(new Request<Object>() {

				@Override
				public Object setAction() {
					sendWebsocketText(command);
					return null;
				}
				
			});
			
		}else {
			
			this.gw.reqManager.queueRequest(new Request<Object>() {

				@Override
				public Object setAction() {
					
					sendWebsocketText(command);
					return null;
					
				}
				
			});
			
		}
		
	}
	
	private void sendWebsocketText(String command) {
		
		this.websocket.sendText(command, true);
		
	}
	
}