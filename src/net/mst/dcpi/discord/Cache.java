package net.mst.dcpi.discord;

import java.util.Collection;
import java.util.HashMap;

import net.mst.dcpi.discord.entities.ClientInstance;
import net.mst.json.JsonObject;

public class Cache<T extends EntityData> {
	
	private HashMap<String, T> collection = null;
	private ClientInstance client;
	
	public Cache(ClientInstance ClientInstance) {
		
		this.collection = new HashMap<String, T>();
		this.client = ClientInstance;
		
	}
	
	public Collection<T> getAll() {
		
		return this.collection.values();
		
	}
	
	public Collection<String> getAllIds() {
		
		return this.collection.keySet();
		
	}
	
	public T getEntityById(String Id) {
		
		if(!collection.containsKey(Id)) {
			
			return null;
			
		}
		
		return collection.get(Id);
		
	}
	
	public HashMap<String, T> getRaw() {
		
		return this.collection;
		
	}
	
	@SuppressWarnings("unchecked")
	public EntityData handle(String Id, JsonObject Object) {
		
		if(collection.containsKey(Id)) {
			
			collection.get(Id).updateJson(Object);
			return collection.get(Id);
			
		}
		
		EntityData data = new EntityData(Object, this.client, Id);
		
		collection.put(Id, (T) data);
		return data;
		
	}

}
