package net.mst.gateway;

import java.util.Timer;
import java.util.TimerTask;

import net.mst.gateway.enums.Status;

public class Heartbeat {
	
	private GatewayManager gw;
	private Timer heartbeat = new Timer();
	
	protected Timer ping = new Timer();
	
	public Heartbeat(GatewayManager GatewayListener) {
		
		this.gw = GatewayListener;
		gw.status = Status.AWAITING_IDENTITY;
		
		long l = (long) (Math.random() * gw.heartbeat_interval);
		
		Timer t = new Timer();
		t.schedule(new TimerTask() {

			@Override
			public void run() {
				
				System.err.println("Heartbeating after " + l);
				
				gw.ping_start = System.currentTimeMillis();
				gw.awaiting_ack = true;
	    		gw.sendCommand("{\"op\":1,\"d\":" + gw.sequenceNumber + "}");
				
				t.cancel();
				
				startHeartbeat();
				
			}
			
		}, l);
		
	}
	
	private void startHeartbeat() {
		
		heartbeat.scheduleAtFixedRate(new TimerTask() {

			@Override
			public void run() {
				
				System.err.println("Heartbeating after " + gw.heartbeat_interval);
				
	    		gw.sendCommand("{\"op\":1,\"d\":" + gw.sequenceNumber + "}");
				
				gw.ping_start = System.currentTimeMillis();
				gw.awaiting_ack = true;
				checkZombied();
				
			}
			
		}, gw.heartbeat_interval, gw.heartbeat_interval);
		
	}
	
	private void checkZombied() {
		
		ping = new Timer();
		
		ping.schedule(new TimerTask() {

			@Override
			public void run() {
				
				System.out.println("CONNECTION ZOMBIED");
				
				ping.cancel();
				
				// End process
				
			}
			
		}, gw.ping + 1000);
		
	}

}
