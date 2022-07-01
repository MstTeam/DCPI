package net.mst.dcpi.discord.entities;

import net.mst.dcpi.discord.app.ClientInstance;

public class User {
	
	private String id;
	
	private ClientInstance client;
	
	public User(String ID, ClientInstance ClientInstance) {
		
		this.id = ID;
		this.client = ClientInstance;
		
	}
	
	public String getId() {
		
		return this.id;
		
	}

}
