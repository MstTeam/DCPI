package net.mst.dcpi.discord.app;

import net.mst.dcpi.discord.app.enums.ApiVersion;
import net.mst.dcpi.request.RequestManager;

public class Bot extends Application {
	
	public Bot(String Token) {
		
		token = Token;
		this.customApiVersion = RequestManager.restApiVersion;
		
		cache();
		
	}
	
	public Bot(String Name, String Token) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = RequestManager.restApiVersion;
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}
	
	public Bot(String Name, String Token, ApiVersion ApiVersion) {
		
		this.token = Token;
		this.name = Name;
		this.customApiVersion = ApiVersion;
		
		cache();
		
		ApplicationManager.registerApplication(Name, this);
		
	}

}
