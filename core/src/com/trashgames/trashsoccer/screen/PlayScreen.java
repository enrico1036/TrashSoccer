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
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.trashgames.trashsoccer.Game;

import static com.trashgames.trashsoccer.Game.PPM;

import com.trashgames.trashsoccer.entities.Player;
import com.trashgames.trashsoccer.graphics.TextureManager;

public class PlayScreen extends GameScreen {
	
	private AssetManager am;
	private Sprite sprite;
	private World world;
	private Box2DDebugRenderer renderer;
	Player player1;
	
	public PlayScreen(Game gm) {
		super(gm);
		
		renderer = new Box2DDebugRenderer();
		
		// World initialization
		world = new World(new Vector2(0f, -9.81f), true);
		
		// Body creation
		BodyDef bdef = new BodyDef();
		bdef.position.set(320 / PPM, 50 / PPM);
		bdef.type  = BodyType.StaticBody;
		Body body = world.createBody(bdef);
		
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(320 / PPM, 30 / PPM);
		
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		Filter filter = new Filter();
		filter.categoryBits = 8;
		filter.maskBits = 1;
		body.createFixture(fdef).setFilterData(filter);
		
		bdef.position.set(0, Gdx.graphics.getHeight()/(2*PPM));
		Body bodyL = world.createBody(bdef);
		shape.setAsBox(30/PPM, 240/PPM);
		fdef.shape = shape;
		bodyL.createFixture(fdef).setFilterData(filter);
		
		bdef.position.set(Gdx.graphics.getWidth()/PPM, Gdx.graphics.getHeight()/(2*PPM));
		Body bodyR = world.createBody(bdef);
		bodyR.createFixture(fdef).setFilterData(filter);
		
		gm.assetManager.load("data/MenuBackground.jpg", Texture.class);
		gm.assetManager.load("data/character/leg.png", Texture.class);
		gm.assetManager.load("data/rosario-muniz.jpg", Texture.class);
		gm.assetManager.load("data/paolo-brosio.jpg", Texture.class);
		gm.assetManager.finishLoading();
		
		Rectangle rect = new Rectangle(300 / PPM, 300 / PPM, 30 / PPM, 60 / PPM);
		player1 = new Player(world, rect, filter, gm.assetManager);
		
		
		
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
		sprite.draw(sb);
		player1.render(sb);
		sb.end();
		
		renderer.render(world, camera.combined);
	}

	@Override
	public void update(float delta) {
		camera.update();
		world.step(delta, 6, 2);
		player1.update(delta);
	}

	@Override
	public void dispose() {
		super.dispose();
		world.dispose();
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		player1.jump();
		return true;
	}
	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.ESCAPE:
			gm.screenManager.pop();
			break;
			
		case Keys.A:
			player1.createBodies(new Filter());
			break;
		default:
			break;
		}
		return true;
	}
}
