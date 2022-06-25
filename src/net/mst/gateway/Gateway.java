package net.mst.gateway;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.net.http.WebSocket;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.ExecutionException;

import net.mst.dcpi.discord.app.AccountInstance;
import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.requests.Request;
import net.mst.requests.RequestManager;
import net.mst.dcpi.Object;
import net.mst.gateway.enums.GatewayApiVersion;
import net.mst.gateway.enums.Status;

public class Gateway extends Object {
	
	protected AccountInstance AccountInstance;
	protected WebSocket websocket;
	
	protected GatewayApiVersion GatewayApiVersion;
	
	protected final HttpClient webClient = HttpClient.newHttpClient();
	
	protected RequestManager reqManager = new RequestManager().setActionsPerTime(120, 60*1000);
	
	public Gateway(AccountInstance AccountInstance) {
		
		this.GatewayApiVersion = RequestManager.gatewayApiVersion;
		this.AccountInstance = AccountInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public Gateway(AccountInstance AccountInstance, GatewayApiVersion GatewayApiVersion) {
		
		this.GatewayApiVersion = GatewayApiVersion;
		this.AccountInstance = AccountInstance;
		
		try {
			connect();
		} catch (InterruptedException | ExecutionException | URISyntaxException e) {
			e.printStackTrace();
		}
		
	}
	
	public String connect() throws InterruptedException, ExecutionException, URISyntaxException {
		
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create("https://discord.com/api/v" + this.AccountInstance.getApiVersion().getVersion() + "/gateway/bot")).header("Authorization", "Bot " + this.AccountInstance.getToken()).build();
		
		try {
			
			HttpResponse<String> response = $getterClient.send(request, BodyHandlers.ofString());
			
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
			
			return null;
			
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

}

class GatewayManager implements Listener {
	
	protected String tempString = "";
	protected WebSocket websocket;
    
    protected String sequenceNumber = null;
    protected int heartbeat_interval = 0;
    protected Heartbeat hb;
    
    protected int ratelimit = 0;
    protected Timer ratelimiter = new Timer();
    
    // Ping
    
    protected long ping_start = 0;
    protected boolean awaiting_ack = false;
    protected long ping = 1000;
    
    protected Status status;
    private Gateway gw;
    
    public GatewayManager(Gateway Gateway) {
    	
    	this.gw = Gateway;
    	this.websocket = gw.websocket;
    	
    	ratelimiter.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				
				ratelimit = 0;
				
			}
    		
    	}, 60*1000, 60*1000);
    	
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
			
			String s = new Parser().parse(this.gw.AccountInstance.getShard().receivePayloadObject(this.gw.AccountInstance.getToken()));
			
			System.out.println(s);
			
			// Identify Payload
			sendCommand(new Parser().parse(this.gw.AccountInstance.getShard().receivePayloadObject(this.gw.AccountInstance.getToken())), false);
			
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
			
			this.gw.reqManager.instantRequest(new Request() {

				@Override
				public void perform() {
					
					sendWebsocketText(command);
					
				}
				
			});
			
		}else {
			
			this.gw.reqManager.queueRequest(new Request() {

				@Override
				public void perform() {
					
					sendWebsocketText(command);
					
				}
				
			});
			
		}
		
	}
	
	private void sendWebsocketText(String command) {
		
		this.websocket.sendText(command, true);
		
	}
	
}