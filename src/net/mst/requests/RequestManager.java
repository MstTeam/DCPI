package net.mst.requests;

import java.net.http.HttpClient;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.enums.GatewayApiVersion;
import net.mst.utilities.timer.Task;
import net.mst.utilities.timer.Timer;
import net.mst.utilities.timer.TimerUnit;

public class RequestManager {
	
	private int actionsPerTime = 120;
	private long timeCycle = 60*1000;
	
	private int actionsDone = 0;
	private long timePassed = 0;
	
	private long startTime = 0;
	
	private Requests requests = new Requests();
	private Timer rateLimiter;
	
	protected HttpClient getterClient = HttpClient.newHttpClient();
	
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
					
					if(executeRequest(requests.getLast()) != null) {
						
						requests.removeLast();
						
					}
					
					setInterval((timeCycle - timePassed) / (actionsPerTime - actionsDone));
					
				}
				
			}
			
		}, (timeCycle - timePassed) / (actionsPerTime - actionsDone));
		
	}
	
	@SuppressWarnings("unchecked")
	private <T extends Object> Response<T> executeRequest(Request<? extends Object> Request) {
		
		if(actionsDone < actionsPerTime) {
			
			addActionsRate(1);
			return (Response<T>) Request.perform();
			
		}
		
		return null;
		
	}
	
	public void stop() {
		
		rateLimiter.cancel();
		
	}
	
	private void addActionsRate(Integer Rate) {
		
		this.actionsDone = this.actionsDone + Rate;
		
	}
	
	public RequestManager queueRequest(Request<?> Request) {
		
		requests.addRequest(Request);
		
		return this;
		
	}
	
	public <T extends Object> Response<T> instantRequest(Request<T> Request) {
		
		Response<T> object = executeRequest(Request);
		
		if(object == null) {
			
			requests.addRequest(Request);
			
			
		}
		
		return object;
		
	}
	
	public RequestManager setActionsPerTime(Integer Actions, long Time, TimerUnit TimerUnit) {
		
		setActionsPerTime(Actions, TimerUnit.getMilliseconds(Time));
		
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
