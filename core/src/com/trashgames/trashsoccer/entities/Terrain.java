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
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Dimension;

public class Terrain extends Entity{
	float friction;
	public Terrain(World world, float height, float friction, Filter filter, AssetManager assetManager){
		this.friction = friction;
		this.world = world;
		setFilter(filter);
		
		bodies = new Body[1];
		dims = new Dimension[1];
		sprites = new Sprite[2];

		dims[0] = new Dimension(Gdx.graphics.getWidth() / PPM, height, 0);
		sprites[0] = new Sprite(assetManager.get("data/StandardTerrain.png", Texture.class));
		sprites[1] = new Sprite(assetManager.get("data/StandardBackground.png", Texture.class));
		sprites[1].setBounds(0, 0, Gdx.graphics.getWidth() / PPM, Gdx.graphics.getHeight() / PPM);
		
		createBodies();
		super.update(1);
	}
	
	@Override
	protected void createBodies() {
		BodyDef bdef = new BodyDef();
		bdef.position.set(Gdx.graphics.getWidth() / 2 / PPM, dims[0].height / 2);
		bdef.type  = BodyType.StaticBody;
		bodies[0] = world.createBody(bdef);
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dims[0].width, dims[0].height);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.friction = friction;
		bodies[0].createFixture(fdef).setFilterData(filter);
	}

	public float getSurfaceY(){
		return dims[0].height;
	}
	
	@Override
	public void regenerateBodies() {
	}
	

	@Override
	public void update(float delta) {
	}
	
	@Override
	public void render(SpriteBatch batch) {
		sprites[1].draw(batch);
		super.render(batch);
	}
}
