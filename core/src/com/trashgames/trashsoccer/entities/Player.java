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
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.trashgames.trashsoccer.graphics.TextureManager;

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
	private float bodyWidth; 
	private float armWidth; 
	private float legWidth; 
	private float headRadius;
	private float bodyHeight;
	private float legHeight; 
	private float armHeight; 
	
	// To make sure the Player will fit in a defined dimension
	private Rectangle bounds;
	
	public Player(World world, Rectangle bounds, Filter filter, AssetManager assetManager){
		this.world = world;
		this.bounds = bounds;
		
		bodies = new Body[10];
		sprites = new Sprite[10];
		
		createBodies(filter);
	}
	
	public void createBodies(Filter filter){
		// Destroy first
		destroy();
		
		// Set parts dimension proportional to bound rect (half dimension)
		float bodyWidth = (float)(bounds.width / 2 * 0.6);
		float armWidth = (float)(bounds.width / 2 * 0.2);
		float legWidth = (float)(bounds.width / 2 * 0.2);
		float headRadius = (float)(bounds.height / 2 * 0.2);
		float bodyHeight =  (float)(bounds.height / 2 * 0.6);
		float legHeight = (float)(bounds.height / 2 * 0.4);
		float armHeight = (float)(bounds.height / 2 * 0.3);
		
		// Get box2d torso position
		Vector2 pos = new Vector2(
				bounds.x + (2*armWidth + bodyWidth),
				bounds.y + (2*legHeight + bodyHeight));
		
		// ##### TORSO #####
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		bdef.angle = 0.1f * (float)Math.PI;
		bodies[TORSO] = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.restitution = 0.5f;
		bodies[TORSO].createFixture(fdef);
		
		// ##### HEAD #####
		CircleShape cshape = new CircleShape();
		cshape.setRadius(headRadius);
		cshape.setPosition(new Vector2(0, headRadius + bodyHeight));
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
		shape.setAsBox(armWidth, armHeight);
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
		jdef.localAnchorA.set(bodyWidth, bodyHeight);
		jdef.localAnchorB.set(-armWidth, armHeight);
		jdef.collideConnected = true;
		world.createJoint(jdef);
		
		// #### LEFT ARM JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_ARM];
		jdef.localAnchorA.set(-bodyWidth, bodyHeight);
		jdef.localAnchorB.set(armWidth, armHeight);
		world.createJoint(jdef);
				
		
		// #### RIGHT LEG ####
		filter.categoryBits = 1;
		filter.maskBits = 8;
		shape.setAsBox(legWidth, legHeight);
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
		jdef.localAnchorA.set(bodyWidth/2, -bodyHeight+10/PPM);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
		// #### LEFT LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_LEG];
		jdef.localAnchorA.set(-bodyWidth/2, -bodyHeight+10/PPM);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);

		bdef.angle = 0;
		cshape.setPosition(new Vector2(0, -legHeight));
		
		// #### PIVOT ####
		bdef.type = BodyType.KinematicBody;
		bdef.position.set(pos.add(0, bodyHeight*2));
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
		djdef.localAnchorB.setZero();
		djdef.length = bodyHeight-bodyWidth;
		djdef.dampingRatio = 0.2f;
		djdef.frequencyHz = 2.15f;
		world.createJoint(djdef);
		
		// #### ROPE ####
		RopeJointDef rdef = new RopeJointDef();
		rdef.bodyA = bodies[PIVOT];
		rdef.bodyB = bodies[JUNCTION];
		rdef.localAnchorA.setZero();
		rdef.localAnchorB.setZero();
		rdef.maxLength = bodyHeight*2;
		world.createJoint(rdef);
				
	}
	
	public void move(){
		bodies[PIVOT].setLinearVelocity((bodies[TORSO].getPosition().x - bodies[PIVOT].getPosition().x)*2, 0);

	}
	
	public void jump(){
		bodies[TORSO].applyForceToCenter(-30000*(float)Math.sin(bodies[TORSO].getAngle()), 
				30000*(float)Math.cos(bodies[TORSO].getAngle()), 
				true);
	}
	
	public void kick(){
		
	}
	
	public Sprite generateSprite(Body bd, float halfWidth, float halfHeight, Texture texture){
		/*sprite.setTexture(texture);
		sprite.setRegion(texture);
		sprite.setPosition(bd.getPosition().x - halfWidth, bd.getPosition().y - halfHeight);
		sprite.setRotation(bd.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(halfWidth * 2, halfHeight * 2);
		sprite.setOriginCenter();
		return sprite;*/
		return null;
	}
	
	public void draw(SpriteBatch sb){
		/*
		generateSprite(bodies.get(RIGHT_LEG).body, legWidth, legHeight, tm.get("leg")).draw(sb);
		generateSprite(bodies.get(LEFT_LEG).body, legWidth, legHeight, tm.get("leg")).draw(sb);
		generateSprite(bodies.get(RIGHT_ARM).body, armWidth, armHeight, tm.get("BACKGROUND")).draw(sb);
		generateSprite(bodies.get(LEFT_ARM).body, armWidth, armHeight, tm.get("BACKGROUND")).draw(sb);
		generateSprite(bodies.get(TORSO).body, bodyWidth, bodyHeight+headRadius, tm.get("ros")).draw(sb);
		*/
	}

	@Override
	public void update(float delta) {
		
	}

	@Override
	public void render() {
				
	}
}
