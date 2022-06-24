package net.mst.gateway;

import java.net.http.WebSocket;
import java.net.http.WebSocket.Listener;
import java.nio.ByteBuffer;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CompletionStage;

import net.mst.gateway.enums.Status;
import net.mst.json.JsonObject;
import net.mst.json.Parser;


public class GatewayManager implements Listener {
	
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
    	System.out.println("[Text] " + data.toString());
    	tempString = tempString + data.toString();
    	
    	if(!last) {
    		
    		return null;
    		
    	}
    	
    	JsonObject jo = new Parser().parse(data.toString());
    	
    	if(jo.contains("s")) {
    		
    		sequenceNumber = jo.getString("s");
    		
    	}
    	
    	if(jo.get("op").toString().equals("1")) {
			
    		sendCommand("{\"op\":1,\"d\":" + sequenceNumber + "}");
    		
    	}
    	
    	if(jo.get("op").toString().equals("10")) {
			
			this.heartbeat_interval = jo.getObject("d").getInteger("heartbeat_interval");
			
			hb = new Heartbeat(this);
			
			// Identify Payload
			sendCommand(new Parser().parse(this.gw.AccountInstance.getShard().receiveObject(this.gw.AccountInstance.getToken())));
			
		}
    	
    	if(jo.get("op").toString().equals("11")) {
    		
    		if(awaiting_ack) {
    			
    			awaiting_ack = false;
    			ping = System.currentTimeMillis() - ping_start;
    			hb.ping.cancel();
    			
    			System.out.println("Ping: " + ping);
    			
    		}
    		
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
    	
    	System.out.println("[Error] " + error.getMessage());
    	
    }
	
	protected void sendCommand(String command) {
		
		if(ratelimit >= 120) {
			
			return;
			
		}
		
		ratelimit = ratelimit + 1;
		
		websocket.sendText(command, true);
		
	}
}
