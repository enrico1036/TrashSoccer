package com.trashgames.trashsoccer.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Buttons;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

import com.trashgames.trashsoccer.Asset;
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
		worldCamera = new OrthographicCamera(30, 30 * (Game.WND_HEIGHT / Game.WND_WIDTH));
		worldCamera.setToOrtho(false);
		worldCamera.position.set(worldCamera.viewportWidth / 2f, worldCamera.viewportHeight / 2f, 0);

		BitmapFont font = new BitmapFont();
		font.setColor(Color.WHITE);

		buttons = new Vector<UIButton>();
		labels = new Vector<UILabel>();

		// Load texture manager
		Asset.loadMenu(gm.assetManager);
		Asset.loadUI(gm.assetManager);
		gm.assetManager.finishLoading();

		// Background
		bgSprite = new Sprite(gm.assetManager.get(Asset.TEX_MENU_BACKGOUND, Texture.class));
		bgSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		// Terrain
		terSprite = new Sprite(gm.assetManager.get(Asset.TEX_MENU_TERRAIN, Texture.class));
		terSprite.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 4);

		// Rosario muniz
//		rosSprite = new Sprite(gm.assetManager.get("data/rosario-muniz.png", Texture.class));
//		rosSprite.setSize(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
//		rosSprite.setPosition(Gdx.graphics.getWidth() * (2f / 3f), 0);

		// Button creation
		UIButton bt = new UIButton(null, gm.mainFont, 
				new Rectangle(Gdx.graphics.getWidth() / 8, Gdx.graphics.getHeight() / 2, 
						Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 7), 
				gm.assetManager.get(Asset.UI_SINGLE_PLAYER_UP, Texture.class), gm.assetManager.get(Asset.UI_SINGLE_PLAYER_DOWN, Texture.class));
		bt.setAction(new Runnable() {

			@Override
			public void run() {
				gm.screenManager.push(new PlayScreen(gm));
			}
		});
		buttons.add(bt);

		// 2 players button creation
		UIButton bt2 = new UIButton(null, gm.mainFont, 
				new Rectangle(Gdx.graphics.getWidth() * (13f / 24f), Gdx.graphics.getHeight() / 2, Gdx.graphics.getWidth() / 3, Gdx.graphics.getHeight() / 7), 
				gm.assetManager.get(Asset.UI_MULTI_PLAYER_UP, Texture.class), gm.assetManager.get(Asset.UI_MULTI_PLAYER_DOWN, Texture.class));
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
//		rosSprite.draw(sb);
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
		worldCamera.update();

	}

	@Override
	public void dispose() {
		super.dispose();
		//gm.assetManager.dispose();	//Unneeded call because already called in super.dispose()
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
