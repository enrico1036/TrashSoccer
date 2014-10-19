package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

import java.util.Vector;

import com.trashgames.trashsoccer.GameManager;
import com.trashgames.trashsoccer.ui.UIButton;
import com.trashgames.trashsoccer.ui.UILabel;


public class MenuScreen extends GameScreen implements InputProcessor{

	private Vector<UIButton> buttons;
	private Vector<UILabel> labels;
 	
	public MenuScreen(GameManager gm) {
		super(gm);
		sb = new SpriteBatch();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		buttons = new Vector<UIButton>();
		labels = new Vector<UILabel>();

		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		
		// UI rendering
		for(UIButton bt : buttons)
			bt.render(sb);
		for(UILabel lb : labels)
			lb.render(sb);
		
		sb.end();
	}

	@Override
	public void update(float delta) {
		super.update(delta);
		camera.update();
		
		// Check button press
	}
	
	@Override
	public void show() {
		super.show();
	}
	
	@Override
	public void dispose() {
		super.dispose();
		
	}
	
	/*
	 *	INPUT EVENTS
	 */
	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int arg0) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int arg0, int arg1, int arg2) {
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}
	

}
