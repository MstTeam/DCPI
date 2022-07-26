package net.mst.dcpi.discord;

public enum RouteType {
	
	GET_CURRENT_USER ("/users/@me"),
	GET_USER ("/users/%s"),
	
	GET_CHANNEL ("/channels/%s"),
	GET_CHANNEL_MESSAGE ("/channels/%s/messages/%s");
	
	private String route;
	
	private RouteType(String Route) {
		
		this.route = Route;
		
	}
	
	public String getRoute(Object... Parameters) {
		
		return String.format(this.route, Parameters);
		
	}

}
