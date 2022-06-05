package net.mst.dcpi.discord.app;

import java.util.HashMap;
import java.util.Map;

public class ApplicationManager {
	
	private static Map<String, ApplicationInstance> applications = new HashMap<>();
	
	public static void registerApplication(String Name, ApplicationInstance ApplicationInstance) {
		
		if(applications.containsKey(Name)) {
			
			return;
			
		}
		
		applications.put(Name, ApplicationInstance);
		
		return;
		
	}

}
