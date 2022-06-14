package net.mst.gateway.enums;

public enum GatewayApiVersion {
	
	VERSION_9 (9, "available"),
	VERSION_8 (8, "available"),
	VERSION_7 (7, "unknown"),
	VERSION_6 (6, "deprecated"),
	VERSION_5 (5, "discontinued"),
	VERSION_4 (4, "discontinued");
	
	private String status;
	private Integer version = 0;
	
	private GatewayApiVersion(int version, String status) {
		
		this.version = version;
		
	}
	
	public int getVersion() {
		
		return version;
		
	}
	
	public String getStatus() {
		
		return status;
		
	}
	
	public boolean isDefault() {
		
		if(version == 6) {
			
			return true;
			
		}
		
		return false;
		
	}
	
	public GatewayApiVersion ofID(int ID) {
		
		for(GatewayApiVersion s : GatewayApiVersion.values()) {
			
			if(s.version.equals(ID)) {
				
				return s;
				
			}
			
		}
		
		return null;
		
	}

}
