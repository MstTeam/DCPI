package net.mst.dcpi.discord.app;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.Shard;
import net.mst.requests.RequestManager;

public class Bot extends Application {
	
	public Bot(String Token) {
		
		token = Token;
		this.customApiVersion = RequestManager.restApiVersion;
		this.shard = new Shard();
		
		cache();
		
	}
	
	public Bot(String Name, String Token) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = RequestManager.restApiVersion;
		this.shard = new Shard().setBrowser(Name).setDevice(Name);
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}
	
	public Bot(String Name, String Token, Shard Shard) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = RequestManager.restApiVersion;
		this.shard = Shard;
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}
	
	public Bot(String Name, String Token, Shard Shard, ApiVersion ApiVersion) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = ApiVersion;
		this.shard = Shard;
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}
	
	public Bot(String Name, String Token, ApiVersion ApiVersion) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = ApiVersion;
		this.shard = new Shard().setBrowser(Name).setDevice(Name);
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}
	
	private void createInstance(String Name, String Token, ApiVersion ApiVersion, Shard Shard) {
		
		this.token = Token;
		
		if(ApiVersion == null) {
			
			
			
		}
		
		if(Name != null) {
			
			this.name = Name;
			ApplicationManager.registerApplication(Name, this);
			
		}
		
	}

}
