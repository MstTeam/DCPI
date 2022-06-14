package net.mst.dcpi.discord.app;

import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
	
	private static Map<String, Application> applications = new HashMap<>();
	
	public static void registerApplication(String Name, Application Application) {
		
		if(applications.containsKey(Name)) {
			
			return;
			
		}
		
		applications.put(Name, Application);
		
		return;
		
	}

}
