package net.mst.dcpi.discord.app.enums;

public enum ApiVersion {
	
	VERSION_10 (10, "available"),
	VERSION_9 (9, "available"),
	VERSION_8 (8, "deprecated"),
	VERSION_7 (7, "deprecated"),
	VERSION_6 (6, "deprecated"),
	VERSION_5 (5, "discontinued"),
	VERSION_4 (4, "discontinued"),
	VERSION_3 (3, "discontinued");
	
	private String status;
	private Integer version = 0;
	
	private ApiVersion(int version, String status) {
		
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
	
	public static ApiVersion getDefault() {
		
		for(ApiVersion s : ApiVersion.values()) {
			
			if(s.isDefault()) {
				
				return s;
				
			}
			
		}
		
		return ApiVersion.values()[0];
		
	}
	
	public ApiVersion ofID(int ID) {
		
		for(ApiVersion s : ApiVersion.values()) {
			
			if(s.version.equals(ID)) {
				
				return s;
				
			}
			
		}
		
		return null;
		
	}

}
