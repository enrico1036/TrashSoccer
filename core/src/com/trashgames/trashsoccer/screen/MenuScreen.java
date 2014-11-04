package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
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
import com.trashgames.trashsoccer.Game;
import com.trashgames.trashsoccer.graphics.TextureManager;
import com.trashgames.trashsoccer.ui.UIButton;
import com.trashgames.trashsoccer.ui.UILabel;

public class MenuScreen extends GameScreen {

	private Sprite bgSprite;
	private Sprite terSprite;
	private Sprite rosSprite;

	private Vector<UIButton> buttons;
	private Vector<UILabel> labels;

	public MenuScreen(final Game gm) {
		super(gm);

		sb = new SpriteBatch();
		camera = new OrthographicCamera(30, 30 * (Game.WND_HEIGHT / Game.WND_WIDTH));
		camera.setToOrtho(false);
		camera.position.set(camera.viewportWidth / 2f, camera.viewportHeight / 2f, 0);

		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);

		buttons = new Vector<UIButton>();
		labels = new Vector<UILabel>();

		// Load texture manager

		gm.assetManager.load("data/StandardBackground.png", Texture.class);
		gm.assetManager.load("data/StandardTerrain.png", Texture.class);
		gm.assetManager.load("data/rosario-muniz.png", Texture.class);
		gm.assetManager.load("data/btup.jpg", Texture.class);
		gm.assetManager.load("data/btdown.jpg", Texture.class);
		gm.assetManager.finishLoading();

		// Background
		bgSprite = new Sprite(gm.assetManager.get("data/StandardBackground.png", Texture.class));
		bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Terrain
		terSprite = new Sprite(gm.assetManager.get("data/StandardTerrain.png", Texture.class));
		terSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 4);

		// Rosario muniz
		rosSprite = new Sprite(gm.assetManager.get("data/rosario-muniz.png", Texture.class));
		rosSprite.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
		rosSprite.setPosition(Gdx.graphics.getWidth() * (2f / 3f), 0);

		// Button creation
		UIButton bt = new UIButton("1 Player", font, 
				new Rectangle(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2, 
						Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 7), 
				gm.assetManager.get("data/btup.jpg", Texture.class), gm.assetManager.get("data/btdown.jpg", Texture.class));
		bt.setAction(new Runnable() {

			@Override
			public void run() {
				gm.screenManager.push(new PlayScreen(gm));
			}
		});
		buttons.add(bt);

		// 2 players button creation
		UIButton bt2 = new UIButton("2 Players", font, 
				new Rectangle(Gdx.graphics.getWidth() * (13f / 24f), Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 7), 
				gm.assetManager.get("data/btup.jpg", Texture.class), gm.assetManager.get("data/btdown.jpg", Texture.class));
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
		for (UIButton bt : buttons)
			bt.render(sb);
		for (UILabel lb : labels)
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
		gm.assetManager.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (UIButton bt : buttons)
			if (bt.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))) {
				bt.setPressed(true);
				bt.execAction();
				break;
			}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		for (UIButton bt : buttons)
			bt.setPressed(false);
		return true;
	}

}
