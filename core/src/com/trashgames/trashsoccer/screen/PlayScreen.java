package com.trashgames.trashsoccer.screen;

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
import com.trashgames.trashsoccer.Game;

import static com.trashgames.trashsoccer.Game.PPM;

import com.trashgames.trashsoccer.entities.Goal;
import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.graphics.TextureManager;

public class PlayScreen extends GameScreen {
	
	private AssetManager am;
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer renderer;
	
	Player player1;
	Player player2;
	Player player3;
	Player player4;
	Goal goalR;
	Goal goalL;
	
	public PlayScreen(Game gm) {
		super(gm);
		
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
		filter.categoryBits = 8;
		filter.maskBits = 1;
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
		
		// Ball
		bdef = new BodyDef();
		bdef.position.set(200 / PPM, 200/PPM);
		bdef.type  = BodyType.DynamicBody;
		bdef.allowSleep = false;
		bdef.angularDamping = 0.7f;
		MassData md = new MassData();
		md.mass = .5f;
		md.I = 0.05f;
		Body ball = world.createBody(bdef);
		ball.setMassData(md);
		
		
		CircleShape cshape = new CircleShape();
		cshape.setRadius(15f / PPM);
		fdef.shape = cshape;
		fdef.restitution = 0.7f;
		fdef.friction = 0.5f;
		fdef.filter.categoryBits = 8;
		fdef.filter.categoryBits = 255;
		ball.createFixture(fdef);
		
		
		
		gm.assetManager.load("data/MenuBackground.jpg", Texture.class);
		gm.assetManager.load("data/character/leg.png", Texture.class);
		gm.assetManager.load("data/rosario-muniz.jpg", Texture.class);
		gm.assetManager.load("data/paolo-brosio.jpg", Texture.class);
		gm.assetManager.finishLoading();
		
		// #### PLAYER1 ####
		Rectangle rect = new Rectangle(300 / PPM, 300 / PPM, 60 / PPM, 150 / PPM);
		filter.categoryBits = 1;
		filter.maskBits = 8;
		player1 = new Player(world, rect, filter, gm.assetManager, true);
		
		// #### PLAYER2 ####
//		rect = new Rectangle(100 / PPM, 300 / PPM, 40 / PPM, 90 / PPM);
		player2 = new Player(world, rect, filter, gm.assetManager, true);
		
		// #### PLAYER3 ####
		player3 = new Player(world, rect, filter, gm.assetManager, false);
		
		// #### PLAYER4 ####
		player4 = new Player(world, rect, filter, gm.assetManager, false);
		
		// #### GOAL ####
		rect = new Rectangle((Gdx.graphics.getWidth()-180)/PPM, 66/PPM, 150/PPM, 300/PPM);
		filter = new Filter();
		filter.categoryBits = 8;
		filter.maskBits = 1;
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
		Gdx.gl.glClearColor(.0f, .0f, .0f, 1f);

	}

	@Override
	public void render(float delta) {
		super.render(delta);
		Gdx.gl.glClear(Gdx.gl.GL_COLOR_BUFFER_BIT);
		
		sb.setProjectionMatrix(camera.combined);
		sb.begin();
		//sprite.draw(sb);
		//player1.render(sb);
		//player2.render(sb);
		sb.end();
		
		renderer.render(world, camera.combined);
	}

	@Override
	public void update(float delta) {
		camera.update();
		world.step(delta , 6, 2);
		player1.update(delta);
		player2.update(delta);
		player3.update(delta);
		player4.update(delta);
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		player1.jump();
		player2.jump();
		player3.jump();
		player4.jump();
		return true;
	}
	
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.SPACE:
			player1.toggleKick();
			player2.toggleKick();
			player3.toggleKick();
			player4.toggleKick();
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
			player1.toggleKick();
			player2.toggleKick();
			player3.toggleKick();
			player4.toggleKick();
			break;
		case Keys.A:
			player1.createBodies();
			player2.createBodies();
			player3.createBodies();
			player4.createBodies();
			break;
			
		case Keys.R:
			player1.destroy();
			player2.destroy();
			player3.destroy();
			player4.destroy();
			break;
		default:
			break;
		}
		return true;
	}
}
