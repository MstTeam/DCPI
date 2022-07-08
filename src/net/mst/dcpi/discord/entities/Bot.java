package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.app.ApplicationManager;
import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.discord.app.gateway.Shard;
import net.mst.json.JsonObject;
import net.mst.json.Parser;

public class Bot extends Application {
	
	public Bot(String Token) {
		
		createInstance(null, Token, null, null);
		
	}
	
	public Bot(String Name, String Token) {
		
		createInstance(Name, Token, null, null);
		
	}
	
	public Bot(String Name, String Token, Shard Shard) {
		
		createInstance(Name, Token, null, Shard);
		
	}
	
	public Bot(String Name, String Token, ApiVersion ApiVersion) {
		
		createInstance(Name, Token, ApiVersion, null);
		
	}
	
	public Bot(String Name, String Token, Shard Shard, ApiVersion ApiVersion) {
		
		createInstance(Name, Token, ApiVersion, Shard);
		
	}
	
	private void createInstance(String Name, String Token, ApiVersion ApiVersion, Shard Shard) {
		
		this.token = Token;
		
		if(ApiVersion != null) {
			
			this.customApiVersion = ApiVersion;
			
		}
		
		if(Shard != null) {
			
			this.shard = Shard;
			
		}else if(Name != null) {
			
			this.shard = new Shard().setBrowser(Name).setDevice(Name).setSystem("linux");
			
		}
		
		if(Name != null) {
			
			this.name = Name;
			ApplicationManager.registerApplication(Name, this);
			
		}

		cache();
		
	}
	
	// Methods
	
	public User getSelfUser() {
		
		JsonObject jo = new Parser().parse(sendGetRequest("/users/@me").body());
		
		System.out.println(jo.hash());
		
		return new User(jo);
		
	}
	
	public User getUserOfId(String ID) {
		
		return new User(ID, this);
		
	}

}
