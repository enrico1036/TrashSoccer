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
	private Texture bg;
	private Texture ter;
	private Texture ros;
	private Sprite bgSprite;
	private Sprite terSprite;
	private Sprite rosSprite;
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
		
		// Background
		bg = new Texture("StandardBackground.png");
		bgSprite = new Sprite(bg);
		bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// Terrain
		ter = new Texture("StandardTerrain.png");
		terSprite = new Sprite(ter);
		terSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/4);
		
		// Rosario muniz
		ros = new Texture("rosario-muniz.png");
		rosSprite = new Sprite(ros);
		rosSprite.setSize(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
		rosSprite.setPosition(Gdx.graphics.getWidth()*(2f/3f), 0);
		
		btup = new Texture(Gdx.files.internal("btup.jpg"));
		btdown = new Texture(Gdx.files.internal("btdown.jpg"));
		// Button creation
		UIButton bt = new UIButton("1 Player", font, new Rectangle(Gdx.graphics.getWidth()/8,Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/7), new Sprite(btup), new Sprite(btdown));
		bt.setAction(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("1player");
			}
		});
		buttons.add(bt);
		
		// 2 players button creation
		UIButton bt2 = new UIButton("2 Players", font, new Rectangle(Gdx.graphics.getWidth()*(13f/24f),Gdx.graphics.getHeight()/2,Gdx.graphics.getWidth()/3,Gdx.graphics.getHeight()/7), new Sprite(btup), new Sprite(btdown));
		bt2.setAction(new Runnable() {
			
			@Override
			public void run() {
				System.out.println("2players");
			}
		});
		buttons.add(bt2);
		
		
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1f);
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		sb.begin();
		// Background rendering
		bgSprite.draw(sb);
		// Terrain rendering
		terSprite.draw(sb);
		// Rosario muniz rendering
		rosSprite.draw(sb);
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
		
	}

	@Override
	public void dispose() {
		super.dispose();
		
		bg.dispose();
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
