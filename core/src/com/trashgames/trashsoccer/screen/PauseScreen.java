package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.ScreenUtils;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.Game;
import com.trashgames.trashsoccer.ui.UIButton;

public class PauseScreen extends GameScreen{
	private UIButton toMenuButton;
	private UIButton resumeButton;
	private UIButton pauseButton;
	private Sprite darkFilter;
	private TextureRegion lastFrameTex;
	private FrameBuffer lastFrame;
	
	public PauseScreen(Game gm) {
		super(gm);
		
		Rectangle bounds = new Rectangle(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 7);
		toMenuButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get(Asset.UI_TO_MENU_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_TO_MENU_DOWN, Texture.class));
		
		bounds.setPosition(Gdx.graphics.getWidth() * (13f / 24f), Gdx.graphics.getHeight() / 2);
		resumeButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get(Asset.UI_RESUME_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_RESUME_DOWN, Texture.class));
		
		bounds.setSize(Gdx.graphics.getHeight() / 10, Gdx.graphics.getHeight() / 10);
		bounds.setPosition(Gdx.graphics.getWidth() - bounds.width - 10, Gdx.graphics.getHeight() - bounds.height - 10);
		pauseButton = new UIButton(null, gm.mainFont, new Rectangle(bounds), 
				gm.assetManager.get(Asset.UI_PAUSE_DOWN, Texture.class), 
				gm.assetManager.get(Asset.UI_PAUSE_DOWN, Texture.class));
		
		darkFilter = new Sprite(gm.assetManager.get(Asset.UI_PAUSE_BACKGROUND, Texture.class));
		
		sb = new SpriteBatch();
		
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		Gdx.gl.glClearColor(0f, 0f, 0f, 0.5f);
	}
	
	public void setLastFrame(FrameBuffer fb){
		lastFrameTex = new TextureRegion(fb.getColorBufferTexture());
		lastFrameTex.flip(false, true);
	}
	
	void assetTree(FileHandle handle, String spaces){
		for(FileHandle h : handle.list()){
			System.out.println(spaces + h.name());
			assetTree(h, spaces + "  ");
		}
	}
	
	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		sb.setProjectionMatrix(uiCamera.combined);
		sb.begin();
			sb.draw(lastFrameTex, 0, 0);
			sb.draw(darkFilter.getTexture(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
			toMenuButton.render(sb);
			resumeButton.render(sb);
			pauseButton.render(sb);
		sb.end();
	}
	
	@Override
	public void update(float delta) {
		super.update(delta);
		uiCamera.update();
		
	}
	
	
	
	
	
}
