package net.mst.dcpi.request;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

import net.mst.dcpi.discord.app.enums.ApiVersion;

public class RequestManager {
	
	private Map<String, HttpClient> webClients = new HashMap<String, HttpClient>();
	
	protected HttpClient $getterClient = HttpClient.newHttpClient();
	
	public static ApiVersion restApiVersion = ApiVersion.VERSION_10;
	public static String gatewayApiVersion = "v9";

}
