package net.mst.dcpi.discord.enums;

public enum Error {
	
	INVALID_AUTHORIZATION_TOKEN ("1.0.0", 401, ResponseType.HTTP_ERROR),
	
	DUPLICATED_NAME ("1.1.0", null, ResponseType.BASE_ERROR);
	
	private String errorCode;
	private Integer responseCode;
	private ResponseType responseType;
	
	private Error(String Errorcode, Integer ResponseCode, ResponseType ResponseType) {
		
		this.errorCode = Errorcode;
		this.responseCode = ResponseCode;
		this.responseType = ResponseType;
		
	}
	
	public String getCode() {
		
		return this.errorCode;
		
	}
	
	public ResponseType getResponseType() {
		
		return this.responseType;
		
	}
	
	public Integer getResponseCode() {
		
		return this.responseCode;
		
	}
	
	public Error ofCode(String Code) {
		
		for(Error error : Error.values()) {
			
			if(error.errorCode.equals(Code)) {
				
				return error;
				
			}
			
		}
		
		return null;
		
	}
	
	public static Error ofMessage(String Message, ResponseType ResponseType) {
		
		Integer integer = Integer.valueOf(Message.replace(" ", "").split(":")[0]);
		
		return ofData(integer, ResponseType);
		
	}
	
	public static Error ofData(Integer ResponseCode, ResponseType ResponseType) {
		
		for(Error error : Error.values()) {
			
			if(error.getResponseType().equals(ResponseType)) {
				
				if(error.getResponseCode().equals(ResponseCode)) {
					
					return error;
					
				}
				
			}
			
		}
		
		return null;
		
	}

}
