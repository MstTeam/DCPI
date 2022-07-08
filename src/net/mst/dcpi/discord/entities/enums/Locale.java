package net.mst.dcpi.discord.entities.enums;

public enum Locale {
	
	DANISH ("da"),
	GERMAN ("de"),
	ENGLISH_UK ("en-GB"),
	ENGLISH_US ("en-US"),
	SPANISH ("es-ES"),
	FRENCH ("fr"),
	CROATIAN ("hr"),
	ITALIAN ("it"),
	LITHUANIAN ("lt"),
	HUNGARIAN ("hu"),
	DUTCH ("nl"),
	NORWEGIAN ("no"),
	POLISH ("pl"),
	PURTUGUESE_BRAZILIAN ("pt-BR"),
	ROMANIAN_ROMANIA ("ro"),
	FINNISH ("fi"),
	SWEDISH ("sv-SE"),
	VIETNAMESE ("vi"),
	TURKISH ("tr"),
	CZECH ("cs"),
	GREEK ("el"),
	BULGARIAN ("bg"),
	RUSSIAN ("ru"),
	UKRAINIAN ("uk"),
	HINDI ("hi"),
	THAI ("th"),
	CHINESE_CHINA ("zh-CN"),
	JAPANESE ("ja"),
	CHINESE_TAIWAN ("zh-TW"),
	KOREAN ("ko");
	
	private String id;
	
	private Locale(String ID) {
		
		this.id = ID;
		
	}
	
	public static Locale ofValue(String Value) {
		
		for(Locale p : Locale.values()) {
			
			if(p.id.equals(Value)) {
				
				return p;
				
			}
			
		}
		
		return null;
		
	}

}
