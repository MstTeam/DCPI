package net.mst.dcpi.discord.app;

public class Bot extends Application {
	
	public Bot(String Token) {
		
		token = Token;
		
	}
	
	public Bot(String Name, String Token) {
		
		this.token = Token;
		this.name = Name;
		
		ApplicationManager.registerApplication(Name, this);
		
	}

}
