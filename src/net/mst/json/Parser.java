package net.mst.json;

public class Parser {
	
	private String JsonString = null;
	private JsonObject JsonObject = null;
	
	private int currentPos = 0;
	
	public Parser() {
		
	}
	
	public JsonObject parse(String JsonString) {
		
		this.JsonString = JsonString;
		
		for (currentPos = 0; currentPos < JsonString.length(); currentPos++) {
	          
			String character = String.valueOf(JsonString.charAt(currentPos));
			
			if(character.equals("{")) {
				
				JsonObject = analyseObject(currentPos);
				
			}
			
		}
		
		return this.JsonObject;
		
	}
	
	public String parse(JsonObject JsonObject) {
		
		this.JsonObject = JsonObject;
		
		return getObject(JsonObject);
		
	}
	
	private JsonObject analyseObject(Integer current) {
		
		JsonObject object = new JsonObject();
		int structure = 0;
		
		String name = "";
		Object tObject;
		
		for (int i = current; i < JsonString.length(); i++) {
			
			String character = String.valueOf(JsonString.charAt(i));
			
			if(character.equals("\"")) {
				
				if(!getLast(i).equals("\\")) {
					
					if(structure == 0) {
						
						structure = 1;
						
						continue;
						
					}
					
					if(structure == 1) {
						
						structure = 0;
						
						tObject = analyseValue(i, false);
						
						object.addValue(name, tObject);
						
						i = currentPos;
						
						name = "";
						
						continue;
						
					}
					
				}
				
			}
			
			if(structure == 1) {
				
				name = name + character;
				
				continue;
				
			}
			
			if(character.equals("}")) {
				
				currentPos = i;
				
				return object;
				
			}
			
		}
		
		return object;
		
	}
	
	private Object analyseValue(Integer cur, Boolean skipReady) {
		
		Boolean ready = skipReady;
		
		String tString = "";
		int structure = 0;
		
		Integer start = cur;
		
		for (int i = cur; i < JsonString.length(); i++) {
			
			String character = String.valueOf(JsonString.charAt(i));
			
			if(ready) {
				
				// Strings
				
				if(character.equals("\"")) {
					
					if(!getLast(i).equals("\\")) {
						
						if(structure == 0) {
							
							structure = 1;
							
							continue;
							
						}
						
						if(structure == 1) {
							
							currentPos = i;
							
							return tString;
							
						}
						
					}
					
				}
				
				if(structure == 1) {
					
					tString = tString + character;
					
					continue;
					
				}
				
				// Objects and Arrays
				
				if(character.equals("{")) {
					
					currentPos = i;
					
					return analyseObject(currentPos);
					
				}
				
				if(character.equals("[")) {
					
					currentPos = i;
					
					return analyseArray(currentPos);
					
				}
				
				// Other
				
				if(character.equals(",") | character.equals("}") | character.equals("]")) {
					
					String value = JsonString.substring(start, i).replace(" ", "");
					
					if(value.equals("false")) {
						
						currentPos = i - 1;
						
						return Boolean.FALSE;
						
					}
					
					if(value.equals("true")) {
						
						currentPos = i - 1;
						
						return Boolean.TRUE;
						
					}
					
					if(value.equals("null")) {
						
						currentPos = i - 1;
						
						return null;
						
					}
					
					if(isNumber(value)) {
						
						currentPos = i - 1;
						
						return Integer.parseInt(value);
						
					}
					
					currentPos = i - 1;
					
					return null;
					
				}
				
			}
			
			if(character.equals(":")) {
				
				ready = true;
				
				start = i + 1;
				
				continue;
				
			}
			
		}
		
		return null;
		
	}
	
	private JsonArray analyseArray(Integer cur) {
		
		JsonArray array = new JsonArray();
		
		for (int i = cur; i < JsonString.length(); i++) {
			
			String character = String.valueOf(JsonString.charAt(i));
				
			if(character.equals("[")) {
				
				Object object = analyseValue(i + 1, true);
				
				if(object == null) {
					
					return array;
					
				}else {
					
					array.addValue(object);
					
				}
				
				i = currentPos;
				
				continue;
				
			}
			
			if(character.equals(",")) {
				
				array.addValue(analyseValue(i + 1, true));
				
				i = currentPos;
				
				continue;
				
			}
			
			if(character.equals("]")) {
				
				currentPos = i;
				
				return array;
				
			}
			
		}
		
		return array;
		
	}
	
	private String getLast(Integer current) {
		
		if(current.equals(0)) {
			
			return "";
			
		}
		
		return String.valueOf(JsonString.charAt(current - 1));
		
	}
	
	private boolean isNumber(String s) {
		
		try {
		
		    Integer.parseInt(s);
		    
		    return true;
		    
		} catch (NumberFormatException e) {
		    
			return false;
			
		}
		
	}
	
	private String getObject(JsonObject JsonObject) {
		
		String main = "{";
		int i = 0;
		
		for(String key : JsonObject.getKeys()) {
			
			i++;
			
			if(i > 1) {
				
				main = main + ", ";
				
			}
			
			if(JsonObject.get(key) instanceof String) {
				
				main = main + "\"" + key + "\":\"" + String.valueOf(JsonObject.get(key)) + "\"";
				
				continue;
				
			}
			
			if(JsonObject.get(key) instanceof Integer) {
				
				main = main + "\"" + key + "\":" + (Integer) JsonObject.get(key);
				
				continue;
				
			}
			
			if(JsonObject.get(key) instanceof Boolean) {
				
				main = main + "\"" + key + "\":" + (Boolean) JsonObject.get(key);
				
				continue;
				
			}
			
			if(JsonObject.get(key) instanceof JsonObject) {
				
				main = main + "\"" + key + "\":" + getObject(JsonObject.getObject(key));
				
			}
			
			if(JsonObject.get(key) instanceof JsonArray) {
				
				main = main + "\"" + key + "\":" + getArray(JsonObject.getArray(key));
				
			}
			
		}
		
		main = main + "}";
		
		return main;
		
	}
	
	private String getArray(JsonArray JsonArray) {
		
		String sub = "[";
		int i = 0;
		
		for(Object element : JsonArray.get()) {
			
			i++;
			
			if(i > 1) {
				
				sub = sub + ", ";
				
			}
			
			if(element instanceof String) {
				
				sub = sub + "\"" + String.valueOf(element) + "\"";
				
			}
			
			if(element instanceof Integer) {
				
				sub = sub + (Integer) element;
				
			}
			
			if(element instanceof Boolean) {
				
				sub = sub + (Boolean) element;
				
			}
			
			if(element instanceof JsonObject) {
				
				sub = sub + getObject((JsonObject) element);
				
			}
			
			if(element instanceof JsonArray) {
				
				sub = sub + getArray((JsonArray) element);
				
			}
			
		}
		
		sub = sub + "]";
		
		return sub;
		
	}

}
