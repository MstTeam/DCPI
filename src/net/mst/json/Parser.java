package net.mst.json;

public class Parser {
	
	private String JsonString = null;
	private JsonObject JsonObject = null;
	
	public Parser() {
		
	}
	
	public JsonObject parse(String JsonString) {
		
		this.JsonString = JsonString;
		
		for (int current = 0; current < JsonString.length(); current++) {
	          
			String character = String.valueOf(JsonString.charAt(current));
			
			if(character.equals("{")) {
				
				JsonObject = analyseObject(current);
				
			}
			
		}
		
	}
	
	public JsonObject parse(JsonObject JsonObject) {
		
		this.JsonObject = JsonObject;
		
	}
	
	private JsonObject analyseObject(Integer current) {
		
		JsonObject object = new JsonObject();
		
		
		
	}

}
