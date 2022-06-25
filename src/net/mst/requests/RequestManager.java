package net.mst.requests;

import java.net.http.HttpClient;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.gateway.enums.GatewayApiVersion;
import net.mst.utilities.timer.Task;
import net.mst.utilities.timer.Timer;

public class RequestManager {
	
	private int actionsPerTime = 120;
	private long timeCycle = 60*1000;
	
	private int actionsDone = 0;
	private long timePassed = 0;
	
	private long startTime = 0;
	
	private Requests requests = new Requests();
	private Timer rateLimiter;
	
	protected HttpClient $getterClient = HttpClient.newHttpClient();
	
	public static ApiVersion restApiVersion = ApiVersion.VERSION_10;
	public static GatewayApiVersion gatewayApiVersion = GatewayApiVersion.VERSION_9;
	
	public RequestManager() {
		
		startCycle();
		
	}
	
	private void startCycle() {
		
		rateLimiter = new Timer();
		
		startTime = System.currentTimeMillis();
		
		rateLimiter.schedule(new Task() {

			@Override
			public void execute() {
				
				timePassed = System.currentTimeMillis() - startTime;
				
				if(timePassed > timeCycle) {
					
					startTime = System.currentTimeMillis();
					timePassed = 0;
					actionsDone = 0;
					
				}
				
				if(!requests.isEmpty()) {
					
					if(executeRequest(requests.getLast())) {
						
						requests.removeLast();
						
					}
					
					setInterval((timeCycle - timePassed) / (actionsPerTime - actionsDone));
					
				}
				
			}
			
		}, (timeCycle - timePassed) / (actionsPerTime - actionsDone));
		
	}
	
	private boolean executeRequest(Request Request) {
		
		if(actionsDone < actionsPerTime) {
			
			addActionsRate(1);
			Request.perform();
			
			return true;
			
		}
		
		return false;
		
	}
	
	public void stop() {
		
		rateLimiter.cancel();
		
	}
	
	private void addActionsRate(Integer Rate) {
		
		this.actionsDone = this.actionsDone + Rate;
		
	}
	
	public RequestManager queueRequest(Request Request) {
		
		requests.addRequest(Request);
		
		return this;
		
	}
	
	public RequestManager instantRequest(Request Request) {
		
		if(!executeRequest(Request)) {
			
			requests.addRequest(Request);
			
		}
		
		return this;
		
	}
	
	public RequestManager setActionsPerTime(Integer Actions, long Milliseconds) {
		
		if(Actions > 0) {
			
			this.actionsPerTime = Actions;

			if(Milliseconds > 0) {
				
				this.timeCycle = Milliseconds;
				
			}
			
		}
		
		return this;
		
	}

}
