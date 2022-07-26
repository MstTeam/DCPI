package net.mst.dcpi.discord.entities;

import java.awt.Color;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import net.mst.dcpi.discord.EntityData;
import net.mst.dcpi.discord.ImageManager;
import net.mst.dcpi.discord.RouteType;
import net.mst.dcpi.discord.entities.enums.user.ImageSize;
import net.mst.dcpi.discord.entities.enums.user.ImageType;
import net.mst.dcpi.discord.entities.enums.user.Locale;
import net.mst.dcpi.discord.entities.enums.user.UserFlag;
import net.mst.json.JsonObject;
import net.mst.json.Parser;
import net.mst.requests.Request;
import net.mst.requests.RequestAction;

public class User {
	
	String id = "0";
	EntityData data;
	
	ClientInstance client;
	
	public User(String ID, ClientInstance ClientInstance) {
		
		this.id = ID;
		this.client = ClientInstance;
		
		createEntityData(null);
		
	}
	
	User(JsonObject Object, ClientInstance ClientInstance, RouteType RouteType) {
		
		this.client = ClientInstance;
		this.id = Object.getString("id");
		
		createEntityData(RouteType);
		
	}
	
	@SuppressWarnings("static-access")
	private void createEntityData(RouteType RouteType) {
		
		if(RouteType != null) {
			
			switch(RouteType) {
			
			case GET_CURRENT_USER: this.data = new EntityData(null, client, id).setRoute(RouteType); break;
			default: this.data = new EntityData(null, client, id).setRoute(RouteType); break;
			
			}
			
		}else {
			
			this.data = new EntityData(null, client, id).setRoute(RouteType.GET_USER);
			
		}
		
	}
	 
	public String getId() {
		
		return this.id;
		
	}
	
	public void update() {
		
		data.cache();
		
	}
	
	// Methods
	
	public String getName() {
		
		return data.get("username");
		
	}
	
	public String getAvatarUrl() {
		
		String avatarHash = data.get("avatar");
		
		if(avatarHash != null) {
			
			return ImageManager.receiveImageUrl(ImageType.USER_AVATAR, null, getId(), avatarHash);
			
		}
		
		return getDefaultAvatarUrl();
		
	}
	
	public String getAvatarUrl(ImageSize ImageSize) {
		
		String avatarHash = data.get("avatar");
		
		if(avatarHash != null) {
			
			return ImageManager.receiveImageUrl(ImageType.USER_AVATAR, ImageSize, getId(), avatarHash);
			
		}
		
		return getDefaultAvatarUrl(ImageSize);
		
	}
	
	public String getDefaultAvatarUrl() {
		
		return ImageManager.receiveImageUrl(ImageType.DEFAULT_USER_AVATAR, null, getDiscriminator() % 5);
		
	}
	
	public String getDefaultAvatarUrl(ImageSize ImageSize) {
		
		return ImageManager.receiveImageUrl(ImageType.DEFAULT_USER_AVATAR, ImageSize, getDiscriminator() % 5);
		
	}
	
	public String getBannerUrl() {
		
		String bannerHash = data.get("banner");
		
		if(bannerHash != null) {
			
			return ImageManager.receiveImageUrl(ImageType.USER_AVATAR, null, getId(), bannerHash);
			
		}
		
		return null;
		
	}
	
	public String getBannerUrl(ImageSize ImageSize) {
		
		String bannerHash = data.get("banner");
		
		if(bannerHash != null) {
			
			return ImageManager.receiveImageUrl(ImageType.USER_BANNER, ImageSize, getId(), bannerHash);
			
		}
		
		return null;
		
	}
	
	public String getAvatarDecoration() {
		
		String decorationHash = data.get("avatar_decoration");
		
		if(decorationHash != null) {
				
			return ImageManager.receiveImageUrl(ImageType.AVATAR_DECORATIONS, null, getId(), decorationHash);
			
		}
		
		return null;
		
	}
	
	public String getAvatarDecoration(ImageSize ImageSize) {
		
		String decorationHash = data.get("avatar_decoration");
		
		if(decorationHash != null) {
				
			return ImageManager.receiveImageUrl(ImageType.AVATAR_DECORATIONS, ImageSize, getId(), decorationHash);
			
		}
		
		return null;
		
	}
	
	public Color getBannerColor() {
		
		if(data.get("banner_color") != null) {
			
			return Color.decode((data.get("banner_color")));
			
		}
		
		if(data.get("accent_color") != null) {
			
			return Color.decode("#" + Integer.toHexString(data.get("accent_color")));
			
		}
		
		return null;
		
	}
	
	public String getMention() {
		
		return String.format("<@%s>", this.id);
		
	}
	
	public Integer getDiscriminator() {
		
		return Integer.valueOf(data.get("discriminator"));
		
	}
	
	public String getNametag() {
		
		return String.format("%s#%s", getName(), getDiscriminator());
		
	}
	
	public List<UserFlag> getPublicFlags() {
		
		List<UserFlag> tlist = new ArrayList<UserFlag>();
		
		if(data.get("public_flags") == null) {
			
			return tlist;
			
		}
		
		String t = Long.toBinaryString((Integer) data.get("public_flags"));
		
		for(int i = 0; i < t.length(); i++) {
			
			String s = String.valueOf(t.charAt(t.length() - 1 - i));
			
			if(s.equals("1")) {
				
				tlist.add(UserFlag.ofValue(i));
				
			}
			
		}
		
		return tlist;
		
	}
	
	public List<UserFlag> getFlags() {
		
		List<UserFlag> tlist = new ArrayList<UserFlag>();
		
		if(data.get("flags") == null) {
			
			return tlist;
			
		}
		
		String t = Long.toBinaryString((Integer) data.get("flags"));
		
		for(int i = 0; i < t.length(); i++) {
			
			String s = String.valueOf(t.charAt(t.length() - 1 - i));
			
			if(s.equals("1")) {
				
				tlist.add(UserFlag.ofValue(i));
				
			}
			
		}
		
		return tlist;
		
	}
	
	public Boolean isBot() {
			
		return data.get("bot");
		
	}
	
	public Boolean hasVerifiedEmail() {
		
		return data.get("verified");
		
	}
	
	public Boolean has2FA() {
		
		return data.get("mfa_enabled");
		
	}
	
	public String getEmail() {
		
		return data.get("email");
		
	}
	
	public String getPronouns() {
		
		return data.get("pronouns");
		
	}
	
	public String getBiography() {
		
		return data.get("bio");
		
	}
	
	public Locale getLocale() {
		
		return Locale.ofValue(data.get("locale"));
		
	}
	
	public Instant getTimeCreated() {
		
		return Instant.ofEpochMilli((Long.valueOf(data.get("id")) >> 22) + 1420070400000L);
		
	}
	
	public LocalDateTime getTimeCreated(ZoneId ZoneId) {
		
		return Instant.ofEpochMilli((Long.valueOf(data.get("id")) >> 22) + 1420070400000L).atZone(ZoneId).toLocalDateTime();
		
	}
	
	// Post
	
	public <T> RequestAction<PrivateChannel> createPrivateChannel() {
		
		JsonObject params = new JsonObject().addValue("recipient_id", this.id);
		
		Request<PrivateChannel> response = new Request<PrivateChannel>() {

			@Override
			public PrivateChannel setAction() {
				HttpResponse<String> httpResponse = client.sendPostRequest("/users/@me/channels", params).get();
				
				System.out.println(httpResponse.body());
				JsonObject object = new Parser().parse(httpResponse.body());
				
				return new PrivateChannel(client.channelCache.handle(object.getString("id"), object, RouteType.GET_CHANNEL));
			}
			
		};
		
		return new RequestAction<PrivateChannel>(client.manager, response);
		
	}

}
