package net.mst.dcpi.discord.entities.enums;

public enum AvatarSize {

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
	
	private AvatarSize(int Size) {
		
		this.size = Size;
		
	}
	
	public Integer getSize() {
		
		return this.size;
		
	}
	
	AvatarSize ofValue(int Size) {
		
		for(AvatarSize is : AvatarSize.values()) {
			
			if(Size == is.size) {
				
				return is;
				
			}
			
		}
		
		return AvatarSize.MIDDLE;
		
	}
	
}
