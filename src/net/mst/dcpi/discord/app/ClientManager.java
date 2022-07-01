package net.mst.dcpi.discord.app;

import java.net.http.HttpClient;

public class ClientManager {
	
	public static HttpClient getHttpClient(ClientInstance ClientInstance) {
		
		return ClientInstance.client;
		
	}

}
