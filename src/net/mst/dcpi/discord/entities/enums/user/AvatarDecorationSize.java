package net.mst.dcpi.discord.entities.enums.user;

public enum AvatarDecorationSize implements ImageSize {

	SMALL (96),
	LARGE (160);
	
	private int size;
	
	private AvatarDecorationSize(int Size) {
		
		this.size = Size;
		
	}
	
	public Integer getSize() {
		
		return this.size;
		
	}
	
	AvatarDecorationSize ofValue(int Size) {
		
		for(AvatarDecorationSize is : AvatarDecorationSize.values()) {
			
			if(Size == is.size) {
				
				return is;
				
			}
			
		}
		
		return AvatarDecorationSize.LARGE;
		
	}
	
}
