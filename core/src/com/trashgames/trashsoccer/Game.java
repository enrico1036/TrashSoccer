package com.trashgames.trashsoccer;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.trashgames.trashsoccer.screen.MenuScreen;
import com.trashgames.trashsoccer.screen.SinglePlayerScreen;
import com.trashgames.trashsoccer.screen.ScreenManager;

public class Game extends ApplicationAdapter {
	// Static variables
	public static final float PPM = 100;
	public static final float TIME_STEP = 1f/60f;
	public static final String WND_TITLE = "Platformer game";
	public static final int WND_WIDTH = 640;
	public static final int WND_HEIGHT = 480;
	public static float VOLUME = .1f;
	public BitmapFont mainFont;
	
	// Class memebers
	public ScreenManager screenManager;
	public AssetManager assetManager;
	@Override
	public void create() {
		assetManager = new AssetManager();
		// Load font
		mainFont = new BitmapFont(Gdx.files.internal("data/font/kongtext.fnt"), Gdx.files.internal("data/font/kongtext.png"), false);
		mainFont.setScale(1f);
		Texture.setAssetManager(assetManager);
		screenManager = new ScreenManager(this);
		screenManager.push(new MenuScreen(this));
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);

	}

	@Override
	public void render() {
		Gdx.graphics.setTitle(WND_TITLE + " FPS:" + Gdx.graphics.getFramesPerSecond());
		if (!screenManager.renderCurrent(Gdx.graphics.getDeltaTime())) {
			Gdx.app.log("ERROR", "Nothing to render. Exiting");
			Gdx.app.exit();
		}
	}
	

	@Override
	public void dispose() {
		super.dispose();
		screenManager.dispose();
		assetManager.dispose();
	}
}
