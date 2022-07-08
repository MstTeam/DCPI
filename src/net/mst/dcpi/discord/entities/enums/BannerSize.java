package net.mst.dcpi.discord.entities.enums;

public enum BannerSize {

	SMALL (300),
	LARGE (600);
	
	private int size;
	
	private BannerSize(int Size) {
		
		this.size = Size;
		
	}
	
	public Integer getSize() {
		
		return this.size;
		
	}
	
	BannerSize ofValue(int Size) {
		
		for(BannerSize is : BannerSize.values()) {
			
			if(Size == is.size) {
				
				return is;
				
			}
			
		}
		
		return BannerSize.LARGE;
		
	}
	
}
