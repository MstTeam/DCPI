package net.mst.dcpi.request;

import java.net.http.HttpClient;
import java.util.HashMap;
import java.util.Map;

public class RequestManager {
	
	private Map<String, HttpClient> webClients = new HashMap<String, HttpClient>();
	
	protected HttpClient $getterClient = HttpClient.newHttpClient();
	
	public static String restApiVersion = "v10";
	public static String gatewayApiVersion = "v9";

}
