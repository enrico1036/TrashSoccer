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
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

import com.trashgames.trashsoccer.GameManager;
import com.trashgames.trashsoccer.ui.UIButton;
import com.trashgames.trashsoccer.ui.UILabel;


public class MenuScreen extends GameScreen{
	private Texture btup;
	private Texture btdown;
	private Vector<UIButton> buttons;
	private Vector<UILabel> labels;
 	
	public MenuScreen(GameManager gm) {
		super(gm);
		Gdx.input.setInputProcessor(this);
		
		sb = new SpriteBatch();
		camera = new OrthographicCamera(30 , 30*(GameManager.WND_HEIGHT/GameManager.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);
		
		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);
		
		buttons = new Vector<UIButton>();
		labels = new Vector<UILabel>();
		
		// Button creation
		btup = new Texture(Gdx.files.internal("btup.jpg"));
		btdown = new Texture(Gdx.files.internal("btdown.jpg"));
		UIButton bt = new UIButton("Bottone di prova", font, new Rectangle(0,100,250,80), new Sprite(btup), new Sprite(btdown));
		bt.setAction(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("CIAO");
			}
		});
		buttons.add(bt);
		
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
		btdown.dispose();
		btup.dispose();
	}
	
	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for(UIButton bt : buttons)
			if(bt.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
				bt.setPressed(true);
				bt.execAction();
				break;
			}
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for(UIButton bt : buttons)
			bt.setPressed(false);
		return true;
	}
	
}
