package com.trashgames.trashsoccer.entities;

import static com.trashgames.trashsoccer.Game.PPM;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Dimension;

public class Terrain extends Entity{
	
	// Positions of bodies into body array
	private static final int TERRAIN = 0;
	private static final int BACKGROUND = 1;
	
	private float friction;
	private AssetManager assetManager;

	public Terrain(World world, float height, float friction, Filter filter, AssetManager assetManager){
		this.friction = friction;
		this.world = world;
		this.assetManager = assetManager;
		setFilter(filter);
		
		bodies = new Body[1];
		dims = new Dimension[1];
		sprites = new Sprite[2];

		dims[0] = new Dimension(Gdx.graphics.getWidth() / (2*PPM), height / 2, 0);
		setSprites();
		
		createBodies();
		super.update(1);
	}
	
	@Override
	protected void createBodies() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(dims[0].width, dims[0].height);
		bdef.type  = BodyType.StaticBody;
		bodies[0] = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dims[0].width, dims[0].height);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = friction;
		bodies[0].createFixture(fdef).setFilterData(filter);
	}

	@Override
	protected void setSprites() {
		sprites[BACKGROUND] = new Sprite(assetManager.get(Asset.TEX_BACKGROUND, Texture.class));
		sprites[BACKGROUND].setBounds(0, 0, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);		
		sprites[TERRAIN] = new Sprite(assetManager.get(Asset.TEX_TERRAIN, Texture.class));
	}
	public float getSurfaceY(){
		return dims[0].height * 2;
	}
	
	@Override
	public void regenerateBodies() {
		setSprites();
		super.update(1);
	}
	

	@Override
	public void update(float delta) {
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprites[BACKGROUND].draw(batch);
		super.render(batch);
	}
}
