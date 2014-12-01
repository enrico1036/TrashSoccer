package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
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
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.DistanceJointDef;
import com.badlogic.gdx.physics.box2d.joints.MotorJoint;
import com.badlogic.gdx.physics.box2d.joints.MotorJointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJoint;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.RopeJointDef;
import com.trashgames.trashsoccer.Asset;
import com.trashgames.trashsoccer.B2DFilter;
import com.trashgames.trashsoccer.Dimension;
import com.trashgames.trashsoccer.MyContactListener;

import static com.trashgames.trashsoccer.Game.PPM;

public class Player extends Entity {
	// Positions of bodies into body array
	// Renderable first
	private static final int RIGHT_LEG = 0;
	private static final int LEFT_LEG = 1;
	private static final int RIGHT_ARM = 2;
	private static final int LEFT_ARM = 3;
	private static final int HEAD = 4;
	private static final int TORSO = 5;
	// Invisible then
	private static final int FOOT_SENSOR = 6;
	private static final int PIVOT = 7;
	private static final int JUNCTION = 8;
	private static final int GHOST_LEG = 9;
	
	private float totalMass = 29.7f;

	private RevoluteJoint kickJoint;
	private RevoluteJoint ghostJoint;
	private boolean leftfacing;
	private float terrainSurface;
	
	private Sound kick;
	private Sound jump;
	
	private boolean canJump = false;
	private boolean isKicking;
	

	public Player(World world, Rectangle bounds, Filter filter, AssetManager assetManager, boolean leftfacing, float terrainSurface, boolean userControlled) {
		this.world = world;
		this.bounds = bounds;
		this.leftfacing = leftfacing;
		this.terrainSurface = terrainSurface;
		setFilter(filter);
		
		//totalMass = 27.01568727f / (bounds.getHeight() * bounds.getWidth());//rect area * mass density
		
		bodies = new Body[10];
		sprites = new Sprite[10];
		dims = new Dimension[12];

		for (int i = 0; i < dims.length; i++) {
			dims[i] = new Dimension(0, 0, 0);
		}

		createBodies();

		// Creating Sprites
		sprites[HEAD] = new Sprite(assetManager.get(userControlled ? Asset.TEX_HEAD_A : Asset.TEX_HEAD_B, Texture.class));
		sprites[TORSO] = new Sprite(assetManager.get(userControlled ? Asset.TEX_TORSO_A : Asset.TEX_TORSO_B, Texture.class));
		sprites[RIGHT_ARM] = new Sprite(assetManager.get(userControlled ? Asset.TEX_ARM_A : Asset.TEX_ARM_B, Texture.class));
		sprites[LEFT_ARM] = new Sprite(assetManager.get(userControlled ? Asset.TEX_ARM_A : Asset.TEX_ARM_B, Texture.class));
		sprites[RIGHT_LEG] = new Sprite(assetManager.get(userControlled ? Asset.TEX_LEG_A : Asset.TEX_LEG_B, Texture.class));
		sprites[LEFT_LEG] = new Sprite(assetManager.get(userControlled ? Asset.TEX_LEG_A : Asset.TEX_LEG_B, Texture.class));
		
		kick = assetManager.get(Asset.SND_KICK);
		jump = assetManager.get(Asset.SND_JUMP);
		
		for (Sprite sprite : sprites){
			if (sprite != null){
				sprite.setOriginCenter();
				// Make the sprite face player's direction
				sprite.setFlip(!leftfacing, false);
			}
		}
	}

	@Override
	protected void createBodies() {

		// Set parts dimension proportional to bound rect (half dimension)
		dims[TORSO].width = (float) (bounds.width / 2 * 0.6);
		dims[RIGHT_ARM].width = (float) (bounds.width / 2 * 0.2);
		dims[RIGHT_LEG].width = (float) (bounds.width / 2 * 0.2);
		dims[FOOT_SENSOR].width = dims[TORSO].width;
		dims[HEAD].height = dims[HEAD].width = (float) (bounds.height / 2 * 0.3);
		dims[TORSO].height = (float) (bounds.height / 2 * 0.6);
		dims[RIGHT_LEG].height = (float) (bounds.height / 2 * 0.4);
		dims[RIGHT_ARM].height = (float) (bounds.height / 2 * 0.3);
		dims[FOOT_SENSOR].height = .5f / PPM;

		dims[LEFT_ARM] = dims[RIGHT_ARM];
		dims[LEFT_LEG] = dims[RIGHT_LEG];

		// Get box2d torso position
		Vector2 pos = new Vector2(bounds.x + (2 * dims[RIGHT_ARM].width + dims[TORSO].width), bounds.y + (2 * dims[RIGHT_LEG].height + dims[TORSO].height));

		// ##### TORSO #####
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		bdef.angle = 0.1f * (float) Math.PI;
		bdef.angularDamping = 0;
		bdef.allowSleep = false;
		bodies[TORSO] = world.createBody(bdef);

		PolygonShape shape = new PolygonShape();
		shape.setAsBox(dims[TORSO].width, dims[TORSO].height);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.restitution = 0.5f;
		bodies[TORSO].createFixture(fdef).setFilterData(filter);
		MassData md = new MassData();
		md.mass = totalMass * 0.2693602694f;
		md.I = 0.5f;
		bodies[TORSO].setMassData(md);
		

		// ##### HEAD #####
		bdef.angle = 0;
		bodies[HEAD] = world.createBody(bdef);
		CircleShape cshape = new CircleShape();
		cshape.setRadius(dims[HEAD].width);
		fdef.shape = cshape;
		bodies[HEAD].createFixture(fdef).setFilterData(filter);
		md.mass = totalMass * 0.05050505051f;
		md.I = 0.5f;
		bodies[HEAD].setMassData(md);
		
		// #### HEAD JOINT####
		RevoluteJointDef jdef = new RevoluteJointDef();
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[HEAD];
		jdef.localAnchorA.set(0, dims[TORSO].height);
		jdef.localAnchorB.set(0, -dims[HEAD].height);
		jdef.lowerAngle = -0.180f;
		jdef.upperAngle = 0.180f;
		jdef.enableLimit = true;
		jdef.collideConnected = false;
		world.createJoint(jdef);
		
		// #### RIGHT ARM ####
		bodies[RIGHT_ARM] = world.createBody(bdef);
		md.I = 0.1f;
		md.mass = totalMass * 0.006734006734f;
		bodies[RIGHT_ARM].setMassData(md);
		shape.setAsBox(dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);

		fdef.shape = shape;
		fdef.restitution = 0.2f;
		bodies[RIGHT_ARM].createFixture(fdef).setFilterData(filter);

		// #### LEFT ARM ####
		bodies[LEFT_ARM] = world.createBody(bdef);
		bodies[LEFT_ARM].setMassData(md);
		bodies[LEFT_ARM].createFixture(fdef).setFilterData(filter);

		// #### RIGHT ARM JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[RIGHT_ARM];
		jdef.localAnchorA.set(dims[TORSO].width, dims[TORSO].height);
		jdef.localAnchorB.set(-dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);
		jdef.lowerAngle = 0;
		jdef.upperAngle = 3.1415f / 2;
		jdef.enableLimit = true;
		jdef.collideConnected = false;
		world.createJoint(jdef);

		// #### LEFT ARM JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_ARM];
		jdef.localAnchorA.set(-dims[TORSO].width, dims[TORSO].height);
		jdef.localAnchorB.set(dims[RIGHT_ARM].width, dims[RIGHT_ARM].height);
		jdef.lowerAngle = -3.1415f / 2;
		jdef.upperAngle = 0;
		jdef.collideConnected = false;
		world.createJoint(jdef);

		// #### RIGHT LEG ####
		shape.setAsBox(dims[RIGHT_LEG].width, dims[RIGHT_LEG].height);
		fdef.restitution = 0f;
		fdef.friction = 1f;
		if(leftfacing)
			md.mass = totalMass * 0.3367003367f;
		else
			md.mass = totalMass * 0.1683501684f;
		md.I = 0.7f;
		bodies[RIGHT_LEG] = world.createBody(bdef);
		bodies[RIGHT_LEG].setMassData(md);
		bodies[RIGHT_LEG].createFixture(fdef).setFilterData(filter);

		// #### GHOST LEG ####
		md.mass = totalMass * 0.1683501684f;
		fdef.filter.maskBits = 0;
		bodies[GHOST_LEG] = world.createBody(bdef);
		bodies[GHOST_LEG].setMassData(md);
		bodies[GHOST_LEG].createFixture(fdef);
		
		// #### LEFT LEG ####
		if(leftfacing)
			md.mass = totalMass * 0.1683501684f;
		else
			md.mass = totalMass * 0.3367003367f;
		bodies[LEFT_LEG] = world.createBody(bdef);
		bodies[LEFT_LEG].setMassData(md);
		bodies[LEFT_LEG].createFixture(fdef).setFilterData(filter);

		// #### RIGHT LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[RIGHT_LEG];
		jdef.localAnchorA.set(dims[TORSO].width / 2, -dims[TORSO].height + 10 / PPM);
		jdef.localAnchorB.set(0, dims[RIGHT_LEG].height);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		if(leftfacing)
			world.createJoint(jdef);
		else
			kickJoint = (RevoluteJoint) world.createJoint(jdef);
		
		// #### GHOST LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[GHOST_LEG];
		if(leftfacing)
			jdef.localAnchorA.set(-dims[TORSO].width / 2, -dims[TORSO].height + 10 / PPM);
		else
			jdef.localAnchorA.set(dims[TORSO].width / 2, -dims[TORSO].height + 10 / PPM);
		jdef.upperAngle = 0f;
		jdef.lowerAngle = 0f;
		jdef.enableLimit = true;
		jdef.collideConnected = false;
		ghostJoint = (RevoluteJoint) world.createJoint(jdef);

		// #### LEFT LEG JOINT ####
		jdef.bodyA = bodies[TORSO];
		jdef.bodyB = bodies[LEFT_LEG];
		jdef.localAnchorA.set(-dims[TORSO].width / 2, -dims[TORSO].height + 10 / PPM);
		jdef.localAnchorB.set(0, dims[RIGHT_LEG].height);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		if(leftfacing)
			kickJoint = (RevoluteJoint) world.createJoint(jdef);
		else
			world.createJoint(jdef);

		bdef.angle = 0;
		cshape.setPosition(new Vector2(0, -dims[RIGHT_LEG].height));
		
		// #### FOOT ####
		shape.setAsBox(dims[FOOT_SENSOR].width, dims[FOOT_SENSOR].height, new Vector2(0, -(dims[TORSO].height + dims[RIGHT_LEG].height * 2) + 10 / PPM), 0);
		fdef.shape = shape;
		fdef.isSensor = true;
		fdef.filter.categoryBits = B2DFilter.FOOT_SENSOR;
		fdef.filter.maskBits = B2DFilter.TERRAIN;
		bodies[TORSO].createFixture(fdef).setUserData(this);
		fdef.isSensor = false;
		
		// #### PIVOT ####
		bdef.type = BodyType.KinematicBody;
		bdef.position.set(bounds.x + bounds.width / 2, terrainSurface + Gdx.graphics.getHeight() * 0.238f / PPM + bounds.height);//bounds.y + bounds.height / 2);
		bodies[PIVOT] = world.createBody(bdef);

		// #### JUNCTION ####
		bdef.type = BodyType.DynamicBody;
		bdef.linearDamping = 0.9f;
		bodies[JUNCTION] = world.createBody(bdef);
		md.mass = 0;
		bodies[JUNCTION].setMassData(md);
		shape.setAsBox(1 / PPM, 1 / PPM);
		fdef.shape = shape;
		bodies[JUNCTION].createFixture(fdef);

		// #### SPRING ####
		DistanceJointDef djdef = new DistanceJointDef();
		djdef.bodyA = bodies[JUNCTION];
		djdef.bodyB = bodies[TORSO];
		djdef.localAnchorA.setZero();
		djdef.localAnchorB.set(0, dims[TORSO].height);
		djdef.length = 0.00063f * Gdx.graphics.getHeight() / 2;//dims[TORSO].height / 2;	// TODO add (+) something proportional to window.height
		djdef.dampingRatio = 0.1f;
		djdef.frequencyHz = 2.15f;
		djdef.collideConnected = false;
		world.createJoint(djdef);

		// #### ROPE ####
		RopeJointDef rdef = new RopeJointDef();
		rdef.bodyA = bodies[PIVOT];
		rdef.bodyB = bodies[JUNCTION];
		rdef.localAnchorA.setZero();
		rdef.localAnchorB.setZero();
		rdef.maxLength = 0.00063f * Gdx.graphics.getHeight() * 2;// + dims[TORSO].height;	// TODO add (+) something proportional to window.height
		rdef.collideConnected = false;
		world.createJoint(rdef);

	}
	
	public void setJump(boolean canJump){
		this.canJump = canJump;
	}


	public void jump() {
		if(canJump){
			bodies[TORSO].applyForceToCenter(-10000 * (float) Math.sin(bodies[TORSO].getAngle()), 10000 * (float) Math.cos(bodies[TORSO].getAngle()), true);
			jump.play(0.1f, 1, 0);
		}
	}
	
	public void toggleKick() {
		
		isKicking = !isKicking;
		
		if(isKicking){
			if(leftfacing){
				bodies[LEFT_LEG].applyAngularImpulse(-20f, true);
				kickJoint.setLimits(-3.1415f / 2,0);
				ghostJoint.setLimits(0, 3.1415f / 2);
			}else{
				bodies[RIGHT_LEG].applyAngularImpulse(20f, true);
				kickJoint.setLimits(0, 3.1415f / 2);
				ghostJoint.setLimits(-3.1415f / 2,0);
			}
			kick.play(0.1f, 1, 0);
		}else{
			kickJoint.setLimits(0, 0);
			ghostJoint.setLimits(0, 0);
		}
		// Make the ghost leg follow the other one
		if(leftfacing)
			bodies[GHOST_LEG].setAngularVelocity(-bodies[LEFT_LEG].getAngularVelocity());
		else
			bodies[GHOST_LEG].setAngularVelocity(-bodies[RIGHT_LEG].getAngularVelocity());
		
		
	}
	
	@Override
	public void update(float delta) {

		// Make the pivot follow the player
		bodies[PIVOT].setLinearVelocity((bodies[TORSO].getPosition().x - bodies[PIVOT].getPosition().x)*10, 0);
		
		super.update(delta);
	}

}
