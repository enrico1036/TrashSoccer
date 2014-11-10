package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Dimension;

import static com.trashgames.trashsoccer.Game.PPM;

public class Goal extends Entity{
	
	// Positions of bodies into body array
	private static final int POLES = 0;
	// Dimension of bodies
	private Dimension[] dims;
	
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
		dims = new Dimension[1];
		
		createBodies();
	}
	
	@Override
	protected void createBodies(){
		// Sets parts dimension proportional to bound rect (half dimension)
		dims[POLES] = new Dimension(bounds.width / 2, bounds.height / 2, 0);
		
		BodyDef bdef = new BodyDef();

		// ##### POLES #####
 		bdef.type = BodyType.StaticBody;
		bodies[POLES] = world.createBody(bdef);
		
		ChainShape shape = new ChainShape();
		Vector2[] vertices = new Vector2[8];
		if(leftfacing)
		{
			vertices[0] = new Vector2(bounds.x, bounds.y);
			vertices[1] = new Vector2(vertices[0].x + dims[POLES].width * 2, vertices[0].y);
			vertices[2] = new Vector2(vertices[0].x + dims[POLES].width * 0.95f, vertices[1].y + dims[POLES].height * 2);
			vertices[3] = new Vector2(vertices[0].x, vertices[2].y);
			vertices[4] = new Vector2(vertices[3].x, vertices[0].y + dims[POLES].height * 1.9f);
			vertices[5] = new Vector2(vertices[0].x + dims[POLES].width * 0.8f, vertices[4].y);
			vertices[6] = new Vector2(vertices[0].x + dims[POLES].width * 1.75f, vertices[0].y + dims[POLES].height * 0.095f);
			vertices[7] = new Vector2(vertices[0].x, vertices[6].y);
		}else{
			vertices[0] = new Vector2(bounds.x + bounds.width, bounds.y);
			vertices[1] = new Vector2(vertices[0].x - dims[POLES].width * 2, vertices[0].y);
			vertices[2] = new Vector2(vertices[0].x - dims[POLES].width * 0.95f, vertices[1].y + dims[POLES].height * 2);
			vertices[3] = new Vector2(vertices[0].x, vertices[2].y);
			vertices[4] = new Vector2(vertices[3].x, vertices[0].y + dims[POLES].height * 1.9f);
			vertices[5] = new Vector2(vertices[0].x - dims[POLES].width * 0.8f, vertices[4].y);
			vertices[6] = new Vector2(vertices[0].x - dims[POLES].width * 1.75f, vertices[0].y + dims[POLES].height * 0.095f);
			vertices[7] = new Vector2(vertices[0].x, vertices[6].y);
		}
		
		shape.createLoop(vertices);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		bodies[POLES].createFixture(fdef).setFilterData(filter);
		
		EdgeShape eshape = new EdgeShape();
		if(leftfacing){
			eshape.set(vertices[0].add(sensorOffset, 0), vertices[3].add(sensorOffset, 0));
			fdef.filter.categoryBits = B2DFilter.SENSORR;
		}else{
			eshape.set(vertices[0].sub(sensorOffset, 0), vertices[3].sub(sensorOffset, 0));
			fdef.filter.categoryBits = B2DFilter.SENSORL;
		}
		fdef.shape = eshape;
		fdef.isSensor = true;
		fdef.filter.maskBits = B2DFilter.BALL;
		bodies[POLES].createFixture(fdef).setUserData(this);
		
	}
	
	public void update(float delta){
		
	}
	
	public void render(SpriteBatch batch){
		
	}
	
}
