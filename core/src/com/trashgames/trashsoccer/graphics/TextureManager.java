package com.trashgames.trashsoccer.graphics;

import java.util.HashMap;
import java.util.Map.Entry;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public class TextureManager {
	private HashMap<String, Texture> map;
	
	public TextureManager(){
		map = new HashMap<String, Texture>();
	}
	
	public Texture get(String id){
		return map.get(id);
	}
	
	public void put(String id, Texture tex){
		if(map.containsKey(id))
			map.get(id).dispose();
		map.put(id, tex);
	}
	
	public boolean loadTexture(String path, String id){
		try{
			Texture tex = new Texture(Gdx.files.internal(path));
			this.put(id, tex);
		}
		catch(Exception e){
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean loadLevel(String path){
		// TODO
		return false;
	}
	
	public void remove(String id){
		map.get(id).dispose();
		map.remove(id);
	}
	
	public void dispose(){
		for(Entry<String,Texture> entry : map.entrySet()){
			entry.getValue().dispose();
		}
	}

}
