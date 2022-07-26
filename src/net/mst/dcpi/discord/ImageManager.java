package net.mst.dcpi.discord;

import net.mst.dcpi.discord.entities.enums.user.ImageSize;
import net.mst.dcpi.discord.entities.enums.user.ImageType;

public class ImageManager {
	
	private final static String BASE_URL = "https://cdn.discordapp.com/";
	
	public static String receiveImageUrl(ImageType ImageType, ImageSize ImageSize, Object... Parameters) {
		
		String url = BASE_URL + ImageType.getPath(Parameters);
		
		if(Parameters[Parameters.length - 1].toString().startsWith("a_")) {
			
			url = url + ".gif";
			
		}else {
			
			url = url + ".png";
			
		}
		
		if(ImageSize != null) {
			
			url = url + "?size=" + ImageSize.getSize();
			
		}
		
		return url;
		
	}

}
