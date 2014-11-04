package com.trashgames.trashsoccer.screen;

import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
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

import static com.trashgames.trashsoccer.Game.PPM;

import com.trashgames.trashsoccer.entities.Ball;
import com.trashgames.trashsoccer.entities.Entity;
import com.trashgames.trashsoccer.entities.Goal;
import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.graphics.TextureManager;

public class PlayScreen extends GameScreen {
	
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer renderer;
	
	Goal goalR;
	Goal goalL;
	
	ArrayList<Entity> entities;
	
	public PlayScreen(Game gm) {
		super(gm);
		entities = new ArrayList<Entity>();
		
		renderer = new Box2DDebugRenderer();
		renderer.setDrawContacts(true);
		renderer.setDrawJoints(true);
		
		// World initialization
		world = new World(new Vector2(0f, -9.81f), true);
		
		// Terrain creation
		BodyDef bdef = new BodyDef();
		bdef.position.set(320 / PPM, 50 / PPM);
		bdef.type  = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		// Ground
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(Gdx.graphics.getWidth() / PPM, 30 / PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = 0.3f;
		Filter filter = new Filter();
		filter.categoryBits = B2DFilter.TERRAIN;
		filter.maskBits = B2DFilter.PLAYER | B2DFilter.BALL;
		body.createFixture(fdef).setFilterData(filter);
		
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
		
		
		gm.assetManager.load("data/MenuBackground.jpg", Texture.class);
		gm.assetManager.load("data/character/head.png", Texture.class);
		gm.assetManager.load("data/character/body.png", Texture.class);
		gm.assetManager.load("data/character/arm_lx.png", Texture.class);
		gm.assetManager.load("data/character/arm_rx.png", Texture.class);
		gm.assetManager.load("data/character/leg.png", Texture.class);
		gm.assetManager.load("data/balls/ballstd.png", Texture.class);
		gm.assetManager.finishLoading();
		
		// #### BALL ####
		filter.categoryBits = B2DFilter.BALL;
		filter.maskBits = B2DFilter.ALL;
		entities.add(new Ball(world, new Rectangle (200 / PPM, 200 / PPM, 30 / PPM, 30 / PPM), filter, gm.assetManager));
		
		// #### PLAYER1 ####
		Rectangle rect = new Rectangle(300 / PPM, 300 / PPM, 60 / PPM, 150 / PPM);
		filter.categoryBits = B2DFilter.PLAYER;
		filter.maskBits = B2DFilter.ALL;
		for(int i = 0; i < 1; i++)
			entities.add(new Player(world, rect, filter, gm.assetManager, true));
	
		// #### GOAL ####
		rect = new Rectangle((Gdx.graphics.getWidth()-180)/PPM, 66/PPM, 150/PPM, 300/PPM);
		filter = new Filter();
		filter.categoryBits = B2DFilter.GOAL;
		filter.maskBits = B2DFilter.ALL;
		goalR = new Goal(world, rect, filter, gm.assetManager, true);
		rect.x = 30 / PPM;
		goalL = new Goal(world, rect, filter, gm.assetManager, false);
		
		
		
		// Texture loading
		sprite = new Sprite(gm.assetManager.get("data/MenuBackground.jpg", Texture.class));
		sprite.setPosition(0, 0);
		sprite.setSize(Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		sb = new SpriteBatch();
		
		// Camera
		camera = new OrthographicCamera();
		camera.setToOrtho(false, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);

		// OpenGL initialization
		Gdx.gl.glClearColor(.5f, .5f, .5f, 1f);

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		//sprite.draw(sb);

		for (Entity entity : entities)
			entity.render(sb);
		
		sb.end();
		
		renderer.render(world, camera.combined);
	}

	@Override
	public void update(float delta) {
		camera.update();
		world.step(delta , 6, 2);
		for (Entity entity : entities)
			entity.update(delta);
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		for (Entity entity : entities)
			try {
				((Player)entity).jump();
			} catch (Exception e) {
				// TODO Auto-generated catch block
			}
		return true;
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			for (Entity entity : entities)
				try {
					((Player)entity).toggleKick();
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
					((Player)entity).toggleKick();
				} catch (Exception e) {
					// TODO Auto-generated catch block
				}
			break;
		case Keys.A:
			for (Entity entity : entities)
				entity.regenerateBodies();
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
