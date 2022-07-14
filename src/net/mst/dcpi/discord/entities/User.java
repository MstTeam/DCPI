package net.mst.dcpi.discord.entities;

import java.awt.Color;
import java.net.http.HttpResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;

import net.mst.dcpi.discord.EntityData;
import net.mst.dcpi.discord.entities.enums.user.AvatarDecorationSize;
import net.mst.dcpi.discord.entities.enums.user.AvatarSize;
import net.mst.dcpi.discord.entities.enums.user.BannerSize;
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
		this.data = new EntityData(null, ClientInstance, ID);
		
	}
	
	User(JsonObject Object, ClientInstance ClientInstance) {
		
		this.client = ClientInstance;
		this.id = Object.getString("id");
		
		
	}
	 
	public String getId() {
		
		return this.id;
		
	}
	
	void cache() {
		
		HttpResponse<String> response = client.sendGetRequest("/users/" + this.id);
		
		data.updateJson(new Parser().parse(response.body()));
		
		System.out.println(response.body());
		
	}
	
	public void update() {
		
		cache();
		
	}
	
	// Methods
	
	public String getName() {
		
		return data.get("username");
		
	}
	
	public String getAvatarUrl() {
		
		String avatarHash = data.get("avatar");
		
		if(avatarHash != null) {
			
			if(avatarHash.startsWith("a_")) {
				
				return String.format("https://cdn.discordapp.com/avatars/%s/%s.gif", this.id, avatarHash);
				
			}else {
				
				return String.format("https://cdn.discordapp.com/avatars/%s/%s.png", this.id, avatarHash);
				
			}
			
		}
		
		return getDefaultAvatarUrl();
		
	}
	
	public String getAvatarUrl(AvatarSize ImageSize) {
		
		String avatarHash = data.get("avatar");
		
		if(avatarHash != null) {
			
			if(avatarHash.startsWith("a_")) {
				
				return String.format("https://cdn.discordapp.com/avatars/%s/%s.gif?size=%s", this.id, avatarHash, ImageSize.getSize());
				
			}else {
				
				return String.format("https://cdn.discordapp.com/avatars/%s/%s.png?size=%s", this.id, avatarHash, ImageSize.getSize());
				
			}
			
		}
		
		return getDefaultAvatarUrl(ImageSize);
		
	}
	
	public String getDefaultAvatarUrl() {
		
		return String.format("https://cdn.discordapp.com/embed/avatars/%s.png", getDiscriminator() % 5);
		
	}
	
	public String getDefaultAvatarUrl(AvatarSize ImageSize) {
		
		return String.format("https://cdn.discordapp.com/embed/avatars/%s.png?size=%s", getDiscriminator() % 5, ImageSize.getSize());
		
	}
	
	public String getBannerUrl() {
		
		String bannerHash = data.get("banner");
		
		if(bannerHash != null) {
			
			if(bannerHash.startsWith("a_")) {
				
				return String.format("https://cdn.discordapp.com/banners/%s/%s.gif", this.id, bannerHash);
				
			}else {
				
				return String.format("https://cdn.discordapp.com/banners/%s/%s.png", this.id, bannerHash);
				
			}
			
		}
		
		return null;
		
	}
	
	public String getBannerUrl(BannerSize BannerSize) {
		
		String bannerHash = data.get("banner");
		
		if(bannerHash != null) {
			
			if(bannerHash.startsWith("a_")) {
				
				return String.format("https://cdn.discordapp.com/banners/%s/%s.gif?size=%s", this.id, bannerHash, BannerSize.getSize());
				
			}else {
				
				return String.format("https://cdn.discordapp.com/banners/%s/%s.png?size=%s", this.id, bannerHash, BannerSize.getSize());
				
			}
			
		}
		
		return null;
		
	}
	
	public String getAvatarDecoration() {
		
		String decorationHash = data.get("avatar_decoration");
		
		if(decorationHash != null) {
				
			return String.format("https://cdn.discordapp.com/avatar-decorations/%s/%s.png", this.id, decorationHash);
			
		}
		
		return null;
		
	}
	
	public String getAvatarDecoration(AvatarDecorationSize AvatarDecorationSize) {
		
		String decorationHash = data.get("avatar_decoration");
		
		if(decorationHash != null) {
				
			return String.format("https://cdn.discordapp.com/avatar-decorations/%s/%s.png?size=%s", this.id, decorationHash, AvatarDecorationSize.getSize());
			
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
				
				return new PrivateChannel(client.channelCache.handle(id, object));
			}
			
		};
		
		return new RequestAction<PrivateChannel>(client.manager, response);
		
	}

}
