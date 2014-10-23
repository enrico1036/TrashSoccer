package com.trashgames.trashsoccer.entities;



import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.trashgames.trashsoccer.Dimension;

import static com.trashgames.trashsoccer.Game.PPM;

public class Player extends Entity{
	// Positions of bodies into body array
	private static final int HEAD = 0;
	private static final int TORSO = 1;
	private static final int RIGHT_ARM = 2;
	private static final int LEFT_ARM = 3;
	private static final int RIGHT_LEG = 4;
	private static final int LEFT_LEG = 5;
	private static final int RIGHT_FOOT = 6;
	private static final int LEFT_FOOT = 7;
	private static final int PIVOT = 8;
	private static final int JUNCTION = 9;
	
	// Dimension of bodies
	private Dimension[] dims;
	
	// To make sure the Player will fit in a defined dimension
	private Rectangle bounds;
	
	public Player(World world, Rectangle bounds, Filter filter, AssetManager assetManager){
		this.world = world;
		this.bounds = bounds;

		bodies = new Body[10];
		sprites = new Sprite[10];
		dims = new Dimension[10];
		
		for(int i = 0; i < dims.length; i++){
			dims[i] = new Dimension(0, 0, 0);
		}
		
		createBodies(filter);
		
		// Creating Sprites
		sprites[HEAD] = new Sprite(assetManager.get("data/paolo-brosio.jpg", Texture.class));
		sprites[TORSO] = new Sprite(assetManager.get("data/rosario-muniz.jpg", Texture.class));
		sprites[RIGHT_ARM] = new Sprite(assetManager.get("data/rosario-muniz.jpg", Texture.class));
		sprites[LEFT_ARM] = new Sprite(assetManager.get("data/character/leg.png", Texture.class));
		sprites[RIGHT_LEG] = new Sprite(assetManager.get("data/character/leg.png", Texture.class));
		sprites[LEFT_LEG] = new Sprite(assetManager.get("data/character/leg.png", Texture.class));
		for(Sprite sprite : sprites)
			if(sprite != null)
				sprite.setOriginCenter();
	}
	
	public void createBodies(Filter filter){
		// Destroy first
		destroy();
		
		// Set parts dimension proportional to bound rect (half dimension)
		dims[TORSO].width = (float)(bounds.width / 2 * 0.6);
		dims[RIGHT_ARM].width = (float)(bounds.width / 2 * 0.2);
		dims[RIGHT_LEG].width = (float)(bounds.width / 2 * 0.2);
		dims[HEAD].height = dims[HEAD].width = (float)(bounds.height / 2 * 0.2);
		dims[TORSO].height =  (float)(bounds.height / 2 * 0.6);
		dims[RIGHT_LEG].height = (float)(bounds.height / 2 * 0.4);
		dims[RIGHT_ARM].height = (float)(bounds.height / 2 * 0.3);
		
		dims[LEFT_ARM] = dims[RIGHT_ARM];
		dims[LEFT_LEG] = dims[RIGHT_LEG];
		
		// Get box2d torso position
		Vector2 pos = new Vector2(
				bounds.x + (2*dims[RIGHT_ARM].width + dims[TORSO].width),
				bounds.y + (2*dims[RIGHT_LEG].height + dims[TORSO].height));
		
		// ##### TORSO #####
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		bdef.angle = 0.1f * (float)Math.PI;
		bodies[TORSO] = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dims[TORSO].width, dims[TORSO].height);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.restitution = 0.5f;
		bodies[TORSO].createFixture(fdef);
		
		// ##### HEAD #####
		CircleShape cshape = new CircleShape();
		cshape.setRadius(dims[HEAD].width);
		cshape.setPosition(new Vector2(0, dims[HEAD].width + dims[TORSO].height));
		fdef.shape = cshape;
		bodies[TORSO].createFixture(fdef).setFilterData(filter);
		MassData md = new MassData();
		md.mass = 10f;
		md.I = 0.5f;
		bodies[TORSO].setMassData(md);
				
		// #### RIGHT ARM ####
		bodies[RIGHT_ARM] = world.createBody(bdef);
		md.I = 0.1f;
		md.mass = 0.2f;
		bodies[RIGHT_ARM].setMassData(md);
		shape.setAsBox(dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);
		
		fdef.shape = shape;
		fdef.restitution = 0.2f;
		bodies[RIGHT_ARM].createFixture(fdef);
		
		// #### LEFT ARM ####
		bodies[LEFT_ARM] = world.createBody(bdef);
		bodies[LEFT_ARM].setMassData(md);
		bodies[LEFT_ARM].createFixture(fdef);
		
		// #### RIGHT ARM JOINT ####
		RevoluteJointDef jdef = new RevoluteJointDef();
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[RIGHT_ARM];
		jdef.localAnchorA.set(dims[TORSO].width, dims[TORSO].height);
		jdef.localAnchorB.set(-dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);
		jdef.collideConnected = true;
		world.createJoint(jdef);
		
		// #### LEFT ARM JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_ARM];
		jdef.localAnchorA.set(-dims[TORSO].width, dims[TORSO].height);
		jdef.localAnchorB.set(dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);
		world.createJoint(jdef);
				
		
		// #### RIGHT LEG ####
		filter.categoryBits = 1;
		filter.maskBits = 8;
		shape.setAsBox(dims[RIGHT_LEG].width, dims[RIGHT_LEG].height);
		fdef.restitution = 0.2f;
		fdef.friction = 0.8f;
		md.mass = 30f;
		md.I = 0.7f;
		bodies[RIGHT_LEG] = world.createBody(bdef);
		bodies[RIGHT_LEG].setMassData(md);
		bodies[RIGHT_LEG].createFixture(fdef).setFilterData(filter);
		
		// #### LEFT LEG ####
		bodies[LEFT_LEG] = world.createBody(bdef);
		bodies[LEFT_LEG].setMassData(md);
		bodies[LEFT_LEG].createFixture(fdef).setFilterData(filter);
		
		// #### RIGHT LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[RIGHT_LEG];
		jdef.localAnchorA.set(dims[TORSO].width/2, -dims[TORSO].height+10/PPM);
		jdef.localAnchorB.set(0, dims[RIGHT_LEG].height);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
		// #### LEFT LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_LEG];
		jdef.localAnchorA.set(-dims[TORSO].width/2, -dims[TORSO].height+10/PPM);
		jdef.localAnchorB.set(0, dims[RIGHT_LEG].height);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);

		bdef.angle = 0;
		cshape.setPosition(new Vector2(0, -dims[RIGHT_LEG].height));
		
		// #### PIVOT ####
		bdef.type = BodyType.KinematicBody;
		bdef.position.set(bounds.x + bounds.width / 2, bounds.y + bounds.height / 2);
		bodies[PIVOT] = world.createBody(bdef);
		
		// #### JUNCTION ####
		bdef.type = BodyType.DynamicBody;
		bdef.linearDamping = 0.9f;
		bdef.angularDamping = 0.9f;
		bodies[JUNCTION] = world.createBody(bdef);
		md.mass = 0;
		bodies[JUNCTION].setMassData(md);
		shape.setAsBox(1/PPM, 1/PPM);
		fdef.shape = shape;
		bodies[JUNCTION].createFixture(fdef).setFilterData(filter);

		// #### SPRING ####
		DistanceJointDef djdef = new DistanceJointDef();
		djdef.bodyA = bodies[JUNCTION];
		djdef.bodyB = bodies[TORSO];
		djdef.localAnchorA.setZero();
		djdef.localAnchorB.set(0, dims[TORSO].height);
		djdef.length = dims[TORSO].height-dims[TORSO].width;
		djdef.dampingRatio = 0.2f;
		djdef.frequencyHz = 2.15f;
		world.createJoint(djdef);
		
		// #### ROPE ####
		RopeJointDef rdef = new RopeJointDef();
		rdef.bodyA = bodies[PIVOT];
		rdef.bodyB = bodies[JUNCTION];
		rdef.localAnchorA.setZero();
		rdef.localAnchorB.setZero();
		rdef.maxLength = dims[TORSO].height*2;
		world.createJoint(rdef);
				
	}
	
	public void jump(){
		bodies[TORSO].applyForceToCenter(-30000*(float)Math.sin(bodies[TORSO].getAngle()), 
				30000*(float)Math.cos(bodies[TORSO].getAngle()), 
				true);
	}
	
	public void kick(){
		
	}

	@Override
	public void update(float delta) {
		bodies[PIVOT].setLinearVelocity((bodies[TORSO].getPosition().x - bodies[PIVOT].getPosition().x)*2,
				/*(float)(bounds.height - bodies[HEAD].getPosition().y)*/ 0);
		
		for(int i = 0; i<sprites.length; i++){
			if(bodies[i] != null && sprites[i] != null){
				sprites[i].setPosition(bodies[i].getPosition().x - dims[i].width, 
						bodies[i].getPosition().y - dims[i].height);
				sprites[i].setSize(dims[i].width * 2, dims[i].height * 2);
				sprites[i].setOriginCenter();
				sprites[i].setRotation(bodies[i].getAngle() * MathUtils.radiansToDegrees);
			}
		}
	}

	@Override
	public void render(SpriteBatch batch) {
		for(int i = 0; i<sprites.length; i++){
			if(sprites[i] != null){
				sprites[i].draw(batch);
			}
		}
	}
}
