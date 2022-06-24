package net.mst.gateway;

import java.util.ArrayList;
import java.util.List;

import net.mst.gateway.enums.GatewayIntent;
import net.mst.json.JsonObject;

public class Shard {
	
	private String os = "unknown";
	private String browser = "dcpi";
	private String device = "dcpi";
	
	private boolean compress = false;
	
	private int threshold = 50;
	private List<GatewayIntent> intents = new ArrayList<GatewayIntent>();
	
	public Shard(GatewayIntent... GatewayIntents) {
		
		if(GatewayIntents != null) {
			
			for(GatewayIntent gi : GatewayIntents) {
				
				intents.add(gi);
				
			}

			
		}
		
	}
	
	public Shard setThreshold(int Threshold) {
		
		if(Threshold >= 50 && Threshold <= 250) {
			
			this.threshold = Threshold;
			
		}
		
		return this;
		
	}
	
	public Shard setSystem(String OperatingSystem) {
		
		this.os = OperatingSystem;
		
		return this;
		
	}
	
	public Shard setBrowser(String Browser) {
		
		this.browser = Browser;
		
		return this;
		
	}
	
	public Shard setDevice(String Device) {
		
		this.device = Device;
		
		return this;
		
	}
	
	public Shard enablePackageCompression() {
		
		this.compress = true;
		
		return this;
		
	}
	
	public JsonObject receiveObject(String Token) {
		
		JsonObject object = new JsonObject();
		
		object.addValue("token", Token);
		object.addValue("compress", compress);
		object.addValue("large_threshold", threshold);
		
		JsonObject properties = new JsonObject();
		
		properties.addValue("os", os);
		properties.addValue("browser", browser);
		properties.addValue("device", device);
		
		object.addValue("properties", properties);
		object.addValue("intents", GatewayIntent.getIntentInteger(intents));
		
		return object;
		
	}

}
