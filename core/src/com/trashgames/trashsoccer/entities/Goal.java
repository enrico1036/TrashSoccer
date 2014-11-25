package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Dimension;

import static com.trashgames.trashsoccer.Game.PPM;

public class Goal extends Entity{
	
	// Positions of bodies into body array
	private static final int POLES = 0;
	
	// To make sure the Goal will fit in a defined dimension
	private Rectangle bounds;
	
	private boolean leftfacing;
	private float sensorOffset;
	
	public Goal(World world, Rectangle bounds, Filter filter, AssetManager assetManager, boolean leftfacing, float sensorOffset){
		this.world = world;
		this.bounds = bounds;
		this.leftfacing = leftfacing;
		this.sensorOffset = sensorOffset;
		setFilter(filter);
		
		bodies = new Body[1];
		sprites = new Sprite[1];
		dims = new Dimension[1];
		
		sprites[POLES] = new Sprite(assetManager.get(Asset.TEX_GOAL, Texture.class));
		if(leftfacing)
			sprites[POLES].setFlip(true, false);
		
		dims[POLES] = new Dimension(bounds.width / 2, bounds.height / 2, 0);
		
			
		createBodies();
	}
	
	@Override
	protected void createBodies(){
		// Sets parts dimension proportional to bound rect (half dimension)
		dims[POLES] = new Dimension(bounds.width / 2, bounds.height / 2, 0);
		
		BodyDef bdef = new BodyDef();

		// ##### POLES #####
 		bdef.type = BodyType.KinematicBody;
		bodies[POLES] = world.createBody(bdef);
		
		ChainShape shape = new ChainShape();
		Vector2[] vertices = new Vector2[6];
		if(leftfacing)
		{
			vertices[0] = new Vector2(bounds.x + dims[POLES].width * 2, bounds.y);
			vertices[1] = new Vector2(bounds.x + dims[POLES].width, bounds.y + dims[POLES].height * 2);
			vertices[2] = new Vector2(bounds.x, bounds.y + dims[POLES].height * 2);
			vertices[3] = new Vector2(bounds.x, bounds.y + dims[POLES].height * 1.9f);
			vertices[4] = new Vector2(bounds.x + dims[POLES].width * 0.9f, bounds.y + dims[POLES].height * 1.9f);
			vertices[5] = new Vector2(bounds.x + dims[POLES].width * 1.9f, bounds.y);
		}else{
			vertices[0] = new Vector2(bounds.x, bounds.y);
			vertices[1] = new Vector2(bounds.x + dims[POLES].width, bounds.y + dims[POLES].height * 2);
			vertices[2] = new Vector2(bounds.x + dims[POLES].width * 2, bounds.y + dims[POLES].height * 2);
			vertices[3] = new Vector2(bounds.x + dims[POLES].width * 2, bounds.y + dims[POLES].height * 1.9f);
			vertices[4] = new Vector2(bounds.x + dims[POLES].width * 1.1f, bounds.y + dims[POLES].height * 1.9f);
			vertices[5] = new Vector2(bounds.x + dims[POLES].width * 0.1f, bounds.y);
		}
		
		shape.createLoop(vertices);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		bodies[POLES].createFixture(fdef).setFilterData(filter);
		
		EdgeShape eshape = new EdgeShape();
		if(leftfacing){
			eshape.set(vertices[2].add(sensorOffset, 0), new Vector2(bounds.x + sensorOffset, bounds.y));
			fdef.filter.categoryBits = B2DFilter.SENSORR;
		}else{
			eshape.set(vertices[2].sub(sensorOffset, 0), new Vector2(bounds.x + dims[POLES].width * 2 - sensorOffset, bounds.y));
			fdef.filter.categoryBits = B2DFilter.SENSORL;
		}
		fdef.shape = eshape;
		fdef.isSensor = true;
		fdef.filter.maskBits = B2DFilter.BALL;
		bodies[POLES].createFixture(fdef).setUserData(this);
		
	}
	
	@Override
	public void update(float delta) {
		// Update sprites position
		sprites[POLES].setPosition(bounds.x, bounds.y);
		sprites[POLES].setSize(dims[POLES].width * 2, dims[POLES].height * 2);
		sprites[POLES].setOriginCenter();
		sprites[POLES].setRotation(bodies[POLES].getAngle() * MathUtils.radiansToDegrees);
	
	}
	
	
}
