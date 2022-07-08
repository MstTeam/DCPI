package net.mst.dcpi.discord;

import net.mst.dcpi.discord.enums.Error;

public class ApiException extends RuntimeException {
	
	private Error error;
	
	public ApiException(Error Error, String Details) {
		
		super(Details);
		this.error = Error;
		
	}
	
	public Error getError() {
		
		return this.error;
		
	}

}
