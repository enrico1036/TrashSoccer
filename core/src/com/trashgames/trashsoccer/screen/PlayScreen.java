package com.trashgames.trashsoccer.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Game;
import com.trashgames.trashsoccer.MyContactListener;

import static com.trashgames.trashsoccer.Game.PPM;

import com.trashgames.trashsoccer.entities.Ball;
import com.trashgames.trashsoccer.entities.Entity;
import com.trashgames.trashsoccer.entities.Goal;
import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.entities.Score;
import com.trashgames.trashsoccer.entities.Terrain;
import com.trashgames.trashsoccer.graphics.TextureManager;
import com.trashgames.trashsoccer.ui.UIButton;
import com.trashgames.trashsoccer.ui.UILabel;

public class PlayScreen extends GameScreen {
	
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer renderer;
	private MyContactListener cl;
	private Score scores[];
	private final int MAX_SCORE = 5;
	private ArrayList<Entity> entities;
	private UIButton kickButton;
	private UIButton jumpButton;
	private UIButton pauseButton;
	private UILabel scoreLabel;
	private ArrayList<String> loadedAssets;
	private boolean paused;
	
	public PlayScreen(Game gm) {
		super(gm);
		entities = new ArrayList<Entity>();
		
		renderer = new Box2DDebugRenderer();
		renderer.setDrawContacts(true);
		renderer.setDrawJoints(true);
		
		scores = new Score[2];
		// Left score
		scores[0] = new Score();
		// Right score
		scores[1] = new Score();
		// World initialization
		cl = new MyContactListener(scores[0], scores[1]);
		world = new World(new Vector2(0f, -9.81f), true);
		world.setContactListener(cl);
		
		// Asset loading
		Asset.loadSounds(gm.assetManager);
		loadedAssets = Asset.loadRandomWorld(gm.assetManager);
		gm.assetManager.finishLoading();
		
		
		// Usefull instances
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Filter filter = new Filter();
		
		// Terrain creation
		filter.categoryBits = B2DFilter.TERRAIN;
		filter.maskBits = B2DFilter.PLAYER | B2DFilter.BALL | B2DFilter.FOOT_SENSOR;
		Terrain terrain = new Terrain(world, (Gdx.graphics.getHeight() / 5 + 20) / PPM, 0.3f, filter, gm.assetManager);
		entities.add(terrain);
		
		// Left wall
		bdef.position.set(0, Gdx.graphics.getHeight()/(2*PPM));
		Body bodyL = world.createBody(bdef);
		shape.setAsBox(1/PPM, Gdx.graphics.getHeight()/PPM);
		fdef.shape = shape;
		bodyL.createFixture(fdef).setFilterData(filter);
		
		// Right wall
		bdef.position.set(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/(2*PPM));
		Body bodyR = world.createBody(bdef);
		bodyR.createFixture(fdef).setFilterData(filter);
		
		
		// #### BALL ####
		filter = new Filter();
		filter.categoryBits = B2DFilter.BALL;
		filter.maskBits = B2DFilter.ALL;
		Ball ball = new Ball(world, new Rectangle (Gdx.graphics.getWidth() / (2*PPM), Gdx.graphics.getHeight() / (2*PPM), Gdx.graphics.getHeight() / (24*PPM), Gdx.graphics.getHeight() / (24*PPM)), filter, gm.assetManager);
		entities.add(ball);
		
		// #### PLAYERS ####
		Rectangle rect = new Rectangle(
				0,
				0,
				Gdx.graphics.getWidth() * 0.037f/ PPM, //0.47f
				Gdx.graphics.getHeight() * 0.17f/ PPM); // 0.21f
		filter = new Filter();
		filter.categoryBits = B2DFilter.PLAYER;
		filter.maskBits = B2DFilter.ALL;

		float offset = 1/7f;
		for(int i = 0; i < 4; i++){
			if(i%2 != 0)
			{
				rect.setPosition(Gdx.graphics.getWidth() * offset * 2 / PPM - rect.width / 2, Gdx.graphics.getHeight() / (2 * PPM));
				entities.add(new Player(world, new Rectangle(rect), filter, gm.assetManager, false ^ (i > 1), terrain.getSurfaceY()));
				offset *= 2;
			}else{
				rect.setPosition(Gdx.graphics.getWidth() * (1 - offset * 2) / PPM - rect.width / 2, Gdx.graphics.getHeight() / (2 * PPM));
				entities.add(new Player(world, new Rectangle(rect), filter, gm.assetManager, true ^ (i > 1), terrain.getSurfaceY()));
			}
		}
	
		// #### GOAL ####
		filter = new Filter();
		filter.categoryBits = B2DFilter.GOAL;
		filter.maskBits = B2DFilter.ALL;
		// Right goal
		Rectangle bounds = new Rectangle(Gdx.graphics.getWidth() * 7.53f / (8.53f * PPM), terrain.getSurfaceY(), Gdx.graphics.getWidth() / (8.53f*PPM), Gdx.graphics.getHeight() / (2.4f*PPM));
		entities.add(new Goal(world, new Rectangle(bounds), filter, gm.assetManager, true, ball.getRadius()));
		// Left goal
		bounds.setPosition(0, terrain.getSurfaceY());
		entities.add(new Goal(world, new Rectangle(bounds), filter, gm.assetManager, false, ball.getRadius()));
		
		
		// #### UI ####
		Rectangle bound = new Rectangle(10, 10, Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
		kickButton = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_KICK_BLUE_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_KICK_BLUE_DOWN, Texture.class));
		kickButton.setAction(new Runnable() {
			@Override
			public void run() {
				for (Entity entity : entities)
					try {
						((Player)entity).toggleKick();
					} catch (Exception e) {
						// Do nothing
					}
			}
		});
		
		bound.setPosition(Gdx.graphics.getWidth() - bound.width - 10, bound.y);
		jumpButton = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_JUMP_BLUE_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_JUMP_BLUE_DOWN, Texture.class));
		jumpButton.setAction(new Runnable() {
			@Override
			public void run() {
				for (Entity entity : entities)
					try {
						((Player)entity).jump();
					} catch (Exception e) {
						// Do nothing
					}
			}
		});
		
		bound.setSize(Gdx.graphics.getHeight() / 10, Gdx.graphics.getHeight() / 10);
		bound.setPosition(Gdx.graphics.getWidth() - bound.width - 10, Gdx.graphics.getHeight() - bound.height - 10);
		pauseButton = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get(Asset.UI_PAUSE_UP, Texture.class), 
				gm.assetManager.get(Asset.UI_PAUSE_DOWN, Texture.class));
		pauseButton.setAction(new Runnable() {
			@Override
			public void run() {
				paused = !paused;
			}
		});
		
		scoreLabel = new UILabel("0 : 0", new Rectangle(Gdx.graphics.getWidth() / 2 - 50, Gdx.graphics.getHeight() - 50, 100, 50), gm.mainFont);
		
		// Camera
		sb = new SpriteBatch();
		worldCamera = new OrthographicCamera();
		worldCamera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		uiCamera = new OrthographicCamera();
		uiCamera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		
		// OpenGL initialization
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1f);
		
		paused = false;
	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		// World rendering
		sb.setProjectionMatrix(worldCamera.combined);
		sb.begin();
			for (Entity entity : entities)
				entity.render(sb);
		sb.end();
		
		renderer.render(world, worldCamera.combined);
		
		// Ui rendering
		sb.setProjectionMatrix(uiCamera.combined);
		sb.begin();
			kickButton.render(sb);
			jumpButton.render(sb);
			pauseButton.render(sb);
			scoreLabel.render(sb);
		sb.end();
	}

	@Override
	public void update(float delta) {

		if(paused) return;
		
		worldCamera.update();
		uiCamera.update();
		int stepsPerformed = 0;
		int MAX_STEPS = 25; 
		while ( (delta > 0.0) && (stepsPerformed < MAX_STEPS) ){
		float deltaTime = Math.min(delta, Game.TIME_STEP); //deltaTime becomes 20
		   delta -= deltaTime; //frameTime is now zero
		   if (delta < Game.TIME_STEP) { // this if will succeed because frameTime is zero
		      deltaTime += delta; // deltaTime is unchanged because frameTime is zero
		      delta = 0.0f; // nothing changes
		   }
		   world.step(Game.TIME_STEP , 6, 2);
		   stepsPerformed++;
		}
		
		String scoreText = scores[0].getScore() + " : " + scores[1].getScore();
		scoreLabel.setText(scoreText);
		
		for(int i = 0; i < scores.length; i++)
		{
			if(scores[i].hasWon(MAX_SCORE))	{
				System.out.println(i + "won");
				reset(true);
			}
			if(scores[i].isIncremented())
				reset(false);
		}
		
		
		
		for (Entity entity : entities)
			entity.update(delta);
	}
	
	public void reset(boolean newGame){
		for (Entity entity : entities){
			entity.regenerateBodies();
		}
		if(newGame)
			for(int i = 0; i < scores.length; i++)
				scores[i].reset();
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
		// Unload assets randomly loaded
		for(String asset : loadedAssets)
			gm.assetManager.unload(asset);
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if(kickButton.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			kickButton.setPressed(true);
			kickButton.execAction();
		}
		
		if(jumpButton.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			jumpButton.setPressed(true);
			jumpButton.execAction();
		}
		
		
		if(pauseButton.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			pauseButton.setPressed(!pauseButton.isPressed());
			pauseButton.execAction();
		}
		
		return true;
	}
	
	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		if(kickButton.checkBound(new Vector2(screenX, Gdx.graphics.getHeight() - screenY))){
			kickButton.execAction();
			kickButton.setPressed(false);
		}
		jumpButton.setPressed(false);
		return true;
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			kickButton.setPressed(true);
			kickButton.execAction();
			break;
		default:
			break;
		}
		return true;
	}
	
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.ESCAPE:
			gm.screenManager.pop();
			break;
		case Keys.SPACE:
			kickButton.execAction();
			kickButton.setPressed(false);
			break;
		case Keys.A:
			reset(false);
			break;
			
		case Keys.R:
			for (Entity entity : entities)
				entity.destroy();
			break;
		case Keys.P:
			pauseButton.setPressed(!pauseButton.isPressed());
			pauseButton.execAction();
			break;
		default:
			break;
		}
		return true;
	}
}
