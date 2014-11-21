package com.trashgames.trashsoccer.entities;

import static com.trashgames.trashsoccer.Game.PPM;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.Dimension;

public class Ball extends Entity {

	public Ball(World world, Rectangle bounds, Filter filter, AssetManager assetManager) {
		this.world = world;
		this.bounds = bounds;
		setFilter(filter);
		
		bodies = new Body[1];
		sprites = new Sprite[1];
		dims = new Dimension[1];
		
		dims[0] = new Dimension(bounds.width / 2, bounds.height / 2, 0);
		createBodies();
		
		sprites[0] = new Sprite(assetManager.get(Asset.TEX_BALL, Texture.class));
		sprites[0].setOriginCenter();
	}
	
	@Override
	protected void createBodies(){
		BodyDef bdef = new BodyDef();
		bdef.position.set(bounds.x, bounds.y);
		bdef.type  = BodyType.DynamicBody;
		bdef.allowSleep = false;
		bdef.angularDamping = 0.6f;
		MassData md = new MassData();
		md.mass = .1f;
		md.I = 0.005f;
		bodies[0] = world.createBody(bdef);
		bodies[0].setMassData(md);
		
		FixtureDef fdef = new FixtureDef();
		CircleShape cshape = new CircleShape();
		cshape.setRadius(dims[0].width);
		fdef.shape = cshape;
		fdef.restitution = 0.8f;
		fdef.friction = 0.7f;
		Fixture fixture = bodies[0].createFixture(fdef);
		fixture.setFilterData(filter);
	}
	
	public float getRadius(){
		return dims[0].width;
	}

}
