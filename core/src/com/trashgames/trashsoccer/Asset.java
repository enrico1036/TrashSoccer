package com.trashgames.trashsoccer;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Application.ApplicationType;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.XmlReader;

public class Asset {
	// Textures
	public static String TEX_HEAD_A;
	public static String TEX_ARM_A;
	public static String TEX_TORSO_A;
	public static String TEX_LEG_A;
	public static String TEX_HEAD_B;
	public static String TEX_ARM_B;
	public static String TEX_TORSO_B;
	public static String TEX_LEG_B;
	public static String TEX_BALL;
	public static String TEX_TERRAIN;
	public static String TEX_BACKGROUND;
	public static String TEX_GOAL;
	public static String TEX_MENU_BACKGOUND;
	public static String TEX_MENU_TERRAIN;
	// UI textures
	public static String UI_KICK_BLUE_UP;
	public static String UI_KICK_RED_UP;
	public static String UI_JUMP_BLUE_UP;
	public static String UI_JUMP_RED_UP;
	public static String UI_SINGLE_PLAYER_UP;
	public static String UI_MULTI_PLAYER_UP;
	public static String UI_PAUSE_UP;
	public static String UI_TO_MENU_UP;
	public static String UI_RESUME_UP;
	public static String UI_KICK_BLUE_DOWN;
	public static String UI_KICK_RED_DOWN;
	public static String UI_JUMP_BLUE_DOWN;
	public static String UI_JUMP_RED_DOWN;
	public static String UI_SINGLE_PLAYER_DOWN;
	public static String UI_MULTI_PLAYER_DOWN;
	public static String UI_PAUSE_DOWN;
	public static String UI_TO_MENU_DOWN;
	public static String UI_RESUME_DOWN;
	public static String UI_PAUSE_BACKGROUND;
	public static String UI_SOUND_UP;
	public static String UI_SOUND_DOWN;
	// Sounds
	public static String SND_KICK;
	public static String SND_JUMP;
	public static String SND_MUSIC;
	// Description files
	public static String XML_WORLD;
	
	
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
		UI_TO_MENU_UP = uiPath + "to_menu_up.png";
		manager.load(UI_TO_MENU_UP, Texture.class);
		UI_TO_MENU_DOWN = uiPath + "to_menu_down.png";
		manager.load(UI_TO_MENU_DOWN, Texture.class);
		UI_RESUME_UP = uiPath + "resume_up.png";
		manager.load(UI_RESUME_UP, Texture.class);
		UI_RESUME_DOWN = uiPath + "resume_down.png";
		manager.load(UI_RESUME_DOWN, Texture.class);
		UI_PAUSE_BACKGROUND = uiPath + "pause_background.png";
		manager.load(UI_PAUSE_BACKGROUND, Texture.class);
		UI_SOUND_UP = uiPath + "sound_up.png";
		manager.load(UI_SOUND_UP, Texture.class);
		UI_SOUND_DOWN = uiPath + "sound_down.png";
		manager.load(UI_SOUND_DOWN, Texture.class);
	}
	
	public static void loadSounds(AssetManager manager){
		String soundPath = "data/sound/";
		//	Load sounds
		SND_KICK = soundPath + "kick.mp3";
		manager.load(SND_KICK, Sound.class);
		SND_JUMP = soundPath + "jump.mp3";
		manager.load(SND_JUMP, Sound.class);
		SND_MUSIC = soundPath + "music.mp3";
		manager.load(SND_MUSIC, Sound.class);
	}
	
	public static void loadMenu(AssetManager manager){
		String dataPath = "data/textures/world/default/";
		//	Load menu textures
		TEX_MENU_BACKGOUND = dataPath + "background.png";
		manager.load(TEX_MENU_BACKGOUND, Texture.class);
		TEX_MENU_TERRAIN = dataPath + "terrain.png";
		manager.load(TEX_MENU_TERRAIN, Texture.class);
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
		if(Gdx.app.getType() == ApplicationType.Desktop)
			dataPath = "./bin/";
		if(Gdx.app.getType() == ApplicationType.iOS)
			dataPath = "assets/";
		
		// Load head	
		String dirPath = dataPath + "data/textures/player/";
		FileHandle[] children = Gdx.files.internal(dirPath + "head/").list();
		
		// Load player
		dirPath = dataPath + "data/textures/player/";
		children = Gdx.files.internal(dirPath).list();
		FileHandle randomDir = children[rand.nextInt(children.length)], randomDir2 = randomDir;
		// Choose two different folders for cpu and user Players
		while(!randomDir.isDirectory())
			randomDir = children[rand.nextInt(children.length)];
		while(randomDir.equals(randomDir2))
			randomDir2 = children[rand.nextInt(children.length)];
		String worldDirPath = randomDir.path().substring(dataPath.length());
		TEX_HEAD_A = worldDirPath + "/head.png";
		TEX_TORSO_A = worldDirPath + "/body.png";
		TEX_ARM_A = worldDirPath + "/arm.png";
		TEX_LEG_A = worldDirPath + "/leg.png";
		worldDirPath = randomDir2.path().substring(dataPath.length());
		TEX_HEAD_B = worldDirPath + "/head.png";
		TEX_TORSO_B = worldDirPath + "/body.png";
		TEX_ARM_B = worldDirPath + "/arm.png";
		TEX_LEG_B = worldDirPath + "/leg.png";
		manager.load(TEX_TORSO_A, Texture.class);
		manager.load(TEX_HEAD_A, Texture.class);
		manager.load(TEX_ARM_A, Texture.class);
		manager.load(TEX_LEG_A, Texture.class);
		manager.load(TEX_TORSO_B, Texture.class);
		manager.load(TEX_HEAD_B, Texture.class);
		manager.load(TEX_ARM_B, Texture.class);
		manager.load(TEX_LEG_B, Texture.class);
		loaded.add(TEX_TORSO_A);
		loaded.add(TEX_HEAD_A);
		loaded.add(TEX_ARM_A);
		loaded.add(TEX_LEG_A);
		loaded.add(TEX_TORSO_B);
		loaded.add(TEX_HEAD_B);
		loaded.add(TEX_ARM_B);
		loaded.add(TEX_LEG_B);
		
		// Load ball
		dirPath =  dataPath + "data/textures/ball/";
		children = Gdx.files.internal(dirPath).list();
		TEX_BALL = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_BALL, Texture.class);
		loaded.add(TEX_BALL);
		
		// Load goal 
		dirPath =  dataPath + "data/textures/goal/";
		children = Gdx.files.internal(dirPath).list();
		TEX_GOAL = children[rand.nextInt(children.length)].path().substring(dataPath.length());
		manager.load(TEX_GOAL, Texture.class);
		loaded.add(TEX_GOAL);
		
		// Load world
		dirPath = dataPath + "data/textures/world/";
		children = Gdx.files.internal(dirPath).list();
		randomDir = children[rand.nextInt(children.length)];
		while(!randomDir.isDirectory())			// Avoid single files
			randomDir = children[rand.nextInt(children.length)];
		worldDirPath = randomDir.path().substring(dataPath.length());
		TEX_BACKGROUND = worldDirPath + "/background.png";
		TEX_TERRAIN = worldDirPath + "/terrain.png";
		XML_WORLD = worldDirPath + "/properties.xml";
		manager.load(TEX_BACKGROUND, Texture.class);
		manager.load(TEX_TERRAIN, Texture.class);
		loaded.add(TEX_BACKGROUND);
		loaded.add(TEX_TERRAIN);
		
		return loaded;
	}
}
