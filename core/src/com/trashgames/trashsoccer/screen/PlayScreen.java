package com.trashgames.trashsoccer.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.Quaternion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
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

public class PlayScreen extends GameScreen {
	
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer renderer;
	private MyContactListener cl;
	private Score scores[];
	private Goal goalR;
	private Goal goalL;
	private final int MAX_SCORE = 5;
	private ArrayList<Entity> entities;
	private UIButton kickButton;
	private UIButton jumpButton;
	private UIButton pauseButton;
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
		cl = new MyContactListener();
		cl.setScores(scores[0], scores[1]);
		world = new World(new Vector2(0f, -9.81f), true);
		world.setContactListener(cl);
		
		
		
		gm.assetManager.load("data/StandardTerrain.png", Texture.class);
		gm.assetManager.load("data/StandardBackground.png", Texture.class);
		gm.assetManager.load("data/character/head.png", Texture.class);
		gm.assetManager.load("data/character/body.png", Texture.class);
		gm.assetManager.load("data/character/arm_lx.png", Texture.class);
		gm.assetManager.load("data/character/arm_rx.png", Texture.class);
		gm.assetManager.load("data/character/leg.png", Texture.class);
		gm.assetManager.load("data/balls/ballstd.png", Texture.class);
		gm.assetManager.load("data/ui/kick_blue_up.png", Texture.class);
		gm.assetManager.load("data/ui/kick_blue_down.png", Texture.class);
		gm.assetManager.load("data/ui/jump_blue_up.png", Texture.class);
		gm.assetManager.load("data/ui/jump_blue_down.png", Texture.class);
		gm.assetManager.load("data/ui/pause_up.png", Texture.class);
		gm.assetManager.load("data/ui/pause_down.png", Texture.class);
		gm.assetManager.load("data/sound/s2.mp3", Sound.class);
		gm.assetManager.load("data/sound/s4-1.mp3", Sound.class);
		gm.assetManager.load("data/sound/jump.mp3", Sound.class);
		gm.assetManager.load("data/sound/kick.mp3", Sound.class);
		gm.assetManager.finishLoading();
		
		
		// Usefull instances
		BodyDef bdef = new BodyDef();
		PolygonShape shape = new PolygonShape();
		FixtureDef fdef = new FixtureDef();
		Filter filter = new Filter();
		
		// Terrain creation
		filter.categoryBits = B2DFilter.TERRAIN;
		filter.maskBits = B2DFilter.PLAYER | B2DFilter.BALL | B2DFilter.FOOT_SENSOR;
		Terrain terrain = new Terrain(world, 55 / PPM, 0.3f, filter, gm.assetManager);
		entities.add(terrain);
		
		// Left wall
		bdef.position.set(0, Gdx.graphics.getHeight()/(2*PPM));
		Body bodyL = world.createBody(bdef);
		shape.setAsBox(30/PPM, Gdx.graphics.getHeight()/PPM);
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
		Ball ball = new Ball(world, new Rectangle (800 / PPM, 200 / PPM, 30 / PPM, 30 / PPM), filter, gm.assetManager);
		entities.add(ball);
		
		// #### PLAYERS ####
		Rectangle rect = new Rectangle(
				0,
				0,
				Gdx.graphics.getWidth() * 0.047f / PPM,
				Gdx.graphics.getHeight() * 0.21f / PPM);
		filter = new Filter();
		filter.categoryBits = B2DFilter.PLAYER;
		filter.maskBits = B2DFilter.ALL ;

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
		rect = new Rectangle((Gdx.graphics.getWidth()-180)/PPM, 66/PPM, 150/PPM, 300/PPM);
		filter = new Filter();
		filter.categoryBits = B2DFilter.GOAL;
		filter.maskBits = B2DFilter.ALL;
		goalR = new Goal(world, rect, filter, gm.assetManager, true, ball.getRadius() * 2);
		rect.x = 30 / PPM;
		goalL = new Goal(world, rect, filter, gm.assetManager, false, ball.getRadius() * 2);
		
		
		// #### UI ####
		Rectangle bound = new Rectangle(10, 10, Gdx.graphics.getHeight() / 5, Gdx.graphics.getHeight() / 5);
		kickButton = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get("data/ui/kick_blue_up.png", Texture.class), 
				gm.assetManager.get("data/ui/kick_blue_down.png", Texture.class));
		kickButton.setAction(new Runnable() {
			@Override
			public void run() {
				for (Entity entity : entities)
					try {
						((Player)entity).toggleKick(true);
					} catch (Exception e) {
						// Do nothing
					}
			}
		});
		
		bound.setPosition(Gdx.graphics.getWidth() - bound.width - 10, bound.y);
		jumpButton = new UIButton(null, 
				gm.mainFont, 
				new Rectangle(bound), 
				gm.assetManager.get("data/ui/jump_blue_up.png", Texture.class), 
				gm.assetManager.get("data/ui/jump_blue_down.png", Texture.class));
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
				gm.assetManager.get("data/ui/pause_up.png", Texture.class), 
				gm.assetManager.get("data/ui/pause_down.png", Texture.class));
		pauseButton.setAction(new Runnable() {
			@Override
			public void run() {
				paused = !paused;
			}
		});
		
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
		sb.end();
		
		
	}

	@Override
	public void update(float delta) {
		if(paused) return;
		
		worldCamera.update();
		uiCamera.update();
		world.step(delta , 6, 2);
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
		kickButton.setPressed(false);
		jumpButton.setPressed(false);
		return true;
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			for (Entity entity : entities)
				try {
					((Player)entity).toggleKick(true);
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
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
			for (Entity entity : entities)
				try {
					((Player)entity).toggleKick(false);
				} catch (Exception e) {
				}
			break;
		case Keys.A:
			reset(false);
			break;
			
		case Keys.R:
			for (Entity entity : entities)
				entity.destroy();
			break;
		default:
			break;
		}
		return true;
	}
}
