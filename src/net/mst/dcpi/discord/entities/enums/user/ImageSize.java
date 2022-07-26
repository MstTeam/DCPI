package net.mst.dcpi.discord.entities.enums.user;

public enum ImageSize {

	MINI (16),
	TINY (32),
	VERY_SMALL (64),
	SMALL (128),
	MIDDLE (256),
	LARGE (512),
	VERY_LARGE (1024),
	HUGE (2096),
	GIANTIC (4096);
	
	private int size;
	
	private ImageSize(int Size) {
		
		this.size = Size;
		
	}
	
	public Integer getSize() {
		
		return this.size;
		
	}
	
	ImageSize ofValue(int Size) {
		
		for(ImageSize is : ImageSize.values()) {
			
			if(Size == is.size) {
				
				return is;
				
			}
			
		}
		
		return ImageSize.MIDDLE;
		
	}
	
}
