package com.trashgames.trashsoccer;

import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;

public class Asset {
	// Textures
	public static String TEX_HEAD;
	public static String TEX_ARM;
	public static String TEX_TORSO;
	public static String TEX_LEG;
	public static String TEX_BALL;
	public static String TEX_TERRAIN;
	public static String TEX_BACKGROUND;
	public static String TEX_GOAL;
	// UI textures
	public static String UI_KICK_BLUE_UP;
	public static String UI_KICK_RED_UP;
	public static String UI_JUMP_BLUE_UP;
	public static String UI_JUMP_RED_UP;
	public static String UI_SINGLE_PLAYER_UP;
	public static String UI_MULTI_PLAYER_UP;
	public static String UI_PAUSE_UP;
	public static String UI_KICK_BLUE_DOWN;
	public static String UI_KICK_RED_DOWN;
	public static String UI_JUMP_BLUE_DOWN;
	public static String UI_JUMP_RED_DOWN;
	public static String UI_SINGLE_PLAYER_DOWN;
	public static String UI_MULTI_PLAYER_DOWN;
	public static String UI_PAUSE_DOWN;
	
	
	public static void loadUI(AssetManager manager){
		String uiPath = "data/ui/";
		// Load UI (fixed)
		UI_KICK_BLUE_UP = uiPath + "kick_blue_up.png";
		manager.load(UI_KICK_BLUE_UP, Texture.class);
		UI_KICK_BLUE_DOWN = uiPath + "kick_blue_down.png";
		manager.load(UI_KICK_BLUE_DOWN, Texture.class);
		UI_KICK_RED_UP = uiPath + "kick_red_up.png";
		manager.load(UI_KICK_RED_UP, Texture.class);
		UI_KICK_RED_DOWN = uiPath + "kick_red_down.png";
		manager.load(UI_KICK_RED_DOWN, Texture.class);
		UI_JUMP_BLUE_UP = uiPath + "jump_blue_up.png";
		manager.load(UI_JUMP_BLUE_UP, Texture.class);
		UI_JUMP_BLUE_DOWN = uiPath + "jump_blue_down.png";
		manager.load(UI_JUMP_BLUE_DOWN, Texture.class);
		UI_JUMP_RED_UP = uiPath + "jump_red_up.png";
		manager.load(UI_JUMP_RED_UP, Texture.class);
		UI_JUMP_RED_DOWN = uiPath + "jump_red_down.png";
		manager.load(UI_JUMP_RED_DOWN, Texture.class);
		UI_PAUSE_UP = uiPath + "pause_up.png";
		manager.load(UI_PAUSE_UP, Texture.class);
		UI_PAUSE_DOWN = uiPath + "pause_down.png";
		manager.load(UI_PAUSE_DOWN, Texture.class);
		UI_SINGLE_PLAYER_UP = uiPath + "single_up.png";
		manager.load(UI_SINGLE_PLAYER_UP, Texture.class);
		UI_SINGLE_PLAYER_DOWN = uiPath + "single_down.png";
		manager.load(UI_SINGLE_PLAYER_DOWN, Texture.class);
		UI_MULTI_PLAYER_UP = uiPath + "multi_up.png";
		manager.load(UI_MULTI_PLAYER_UP, Texture.class);
		UI_MULTI_PLAYER_DOWN = uiPath + "multi_down.png";
		manager.load(UI_MULTI_PLAYER_DOWN, Texture.class);
	}
	
	public static ArrayList<String> loadRandomWorld(AssetManager manager){
		/*
		 * For every texture that needs to be loaded, 
		 * this function chooses a random file between 
		 * those in data/textures
		 */
		Random rand = new Random(TimeUtils.nanoTime());
		// Keep track of loaded assets in order to destroy the later
		ArrayList<String> loaded = new ArrayList<String>();

		// This is a workaround for the FileHandle desktop partial incompatibility
		String dataPath = "";
		if(Gdx.app.getType() != ApplicationType.Android)
			dataPath = "./bin/";
		
		// Load head	
		String dirPath = dataPath + "data/textures/player/";
		FileHandle[] children = Gdx.files.internal(dirPath + "head/").list();
		TEX_HEAD = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_HEAD, Texture.class);
		loaded.add(TEX_HEAD);
		// Load torso
		children = Gdx.files.internal(dirPath + "torso/").list();
		TEX_TORSO = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_TORSO, Texture.class);
		loaded.add(TEX_TORSO);
		// Load torso
		children = Gdx.files.internal(dirPath + "arm/").list();
		TEX_ARM = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_ARM, Texture.class);
		loaded.add(TEX_ARM);
		// Load torso
		children = Gdx.files.internal(dirPath + "leg/").list();
		TEX_LEG = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_LEG, Texture.class);
		loaded.add(TEX_LEG);
		
		// Load terrain
		dirPath = dataPath + "data/textures/terrain/";
		children = Gdx.files.internal(dirPath).list();
		TEX_TERRAIN = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_TERRAIN, Texture.class);
		loaded.add(TEX_TERRAIN);
		
		// Load ball
		dirPath =  dataPath + "data/textures/ball/";
		children = Gdx.files.internal(dirPath).list();
		TEX_BALL = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_BALL, Texture.class);
		loaded.add(TEX_BALL);
		
		// Load background
		dirPath = dataPath + "data/textures/background/";
		children = Gdx.files.internal(dirPath).list();
		TEX_BACKGROUND = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_BACKGROUND, Texture.class);
		loaded.add(TEX_BACKGROUND);
		
		return loaded;
	}
}
