package net.mst.dcpi.discord.app;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.request.RequestManager;
import net.mst.gateway.Shard;

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

}
