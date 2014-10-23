package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
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

public class Player {

	private TextureManager tm;
	private Sprite sprite;
	
	private Body body;
	private Body armR;
	private Body armL;
	private Body legR;
	private Body legL;
	private Body footR;
	private Body footL;
	private Body pivot;
	private Body junction;
	
	
	private float bodyWidth = Gdx.graphics.getWidth()/35/PPM;
	private float bodyHeight = Gdx.graphics.getHeight()/10/PPM;
	private float headRad = bodyWidth;
	private float armWidth = Gdx.graphics.getWidth()/100/PPM;
	private float armHeight = Gdx.graphics.getHeight()/30/PPM;
	private float legWidth = bodyWidth/2;
	private float legHeight = Gdx.graphics.getHeight()/20/PPM;
	
	public Player(World world, Vector2 pos, Filter filter, TextureManager tm){
		this.tm = tm;
		sprite = new Sprite();
		
		// Body creation
		BodyDef bdef = new BodyDef();
		bdef.position.set(pos);
		bdef.type = BodyType.DynamicBody;
		bdef.angle = 0.1f * (float)Math.PI;
		body = world.createBody(bdef);
		
		// Human body
		PolygonShape shape = new PolygonShape();
		shape.setAsBox(bodyWidth, bodyHeight);
		FixtureDef fdef = new FixtureDef();
		fdef.shape = shape;
		fdef.restitution = 0.5f;
		body.createFixture(fdef);
		
		// Human head
		CircleShape cshape = new CircleShape();
		cshape.setRadius(headRad);
		cshape.setPosition(new Vector2(0, headRad + bodyHeight));
		fdef.shape = cshape;
		body.createFixture(fdef).setFilterData(filter);
		MassData md = new MassData();
		md.mass = 10f;
		md.I = 0.5f;
		body.setMassData(md);
		
// Arms
		// Right arm
		armR = world.createBody(bdef);
		md.I = 0.1f;
		md.mass = 0.2f;
		armR.setMassData(md);
		shape.setAsBox(armWidth, armHeight);
//		shape.setRadius(90f);
		fdef.shape = shape;
		fdef.restitution = 0.2f;
		armR.createFixture(fdef);
		
		// Left arm
		armL = world.createBody(bdef);
		armL.setMassData(md);
		armL.createFixture(fdef);
		
		// Right arm joint
		RevoluteJointDef jdef = new RevoluteJointDef();
		jdef.bodyA = body;
		jdef.bodyB = armR;
		jdef.localAnchorA.set(Gdx.graphics.getWidth()/30/PPM, Gdx.graphics.getHeight()/20/PPM);
		jdef.localAnchorB.set(-armWidth, armHeight);
		jdef.collideConnected = true;
		world.createJoint(jdef);
		
		// Left arm joint
		jdef.bodyA = body;
		jdef.bodyB = armL;
		jdef.localAnchorA.set(-bodyWidth, Gdx.graphics.getHeight()/20/PPM);
		jdef.localAnchorB.set(armWidth, armHeight);
		world.createJoint(jdef);
		
// Legs
		// Right leg
		
		filter.categoryBits = 1;
		filter.maskBits = 8;
		shape.setAsBox(legWidth, legHeight);
		fdef.restitution = 0.2f;
		fdef.friction = 0.8f;
		md.mass = 30f;
		md.I = 0.7f;
//		md.center.set(0, -legWidth+(legHeight*2));
		legR = world.createBody(bdef);
		legR.setMassData(md);
		legR.createFixture(fdef).setFilterData(filter);
		
		// Left leg

		legL = world.createBody(bdef);
		legL.setMassData(md);
		legL.createFixture(fdef).setFilterData(filter);
		
		// Right leg joint
		jdef.bodyA = body;
		jdef.bodyB = legR;
		jdef.localAnchorA.set(bodyWidth/2, -bodyHeight+10/PPM);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
		// Left leg joint
		jdef.bodyA = body;
		jdef.bodyB = legL;
		jdef.localAnchorA.set(-bodyWidth/2, -bodyHeight+10/PPM);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
// Feet
//		// Right foot
		bdef.angle = 0;
//		bdef.position.set(pos.x-bodyWidth/2, pos.y-bodyHeight-legHeight);
//		footR = world.createBody(bdef);
//		cshape.setRadius(legWidth);
		cshape.setPosition(new Vector2(0, -legHeight));
//		fdef.shape = cshape;
//		fdef.restitution = 0.5f;
//		footR.createFixture(fdef).setFilterData(filter);
//		md.mass = 30f;
//		md.I = 0f;
//		footR.setMassData(md);
//		
//		// Left foot
//		footL = world.createBody(bdef);
//		footL.createFixture(fdef).setFilterData(filter);
//		footL.setMassData(md);
		
		// Motor joint Right
//		MotorJointDef mdef = new MotorJointDef();
//		mdef.bodyA = legR;
//		mdef.bodyB = footR;
//		mdef.angularOffset = 0;
//		mdef.linearOffset.setZero();
//		mdef.maxTorque = 0f;
//		mdef.maxForce = 99999f;
//		world.createJoint(mdef);
//		
//		// Motor joint Right
//		mdef.bodyA = legL;
//		mdef.bodyB = footL;
//		world.createJoint(mdef);
		
		// Pivot
		bdef.type = BodyType.KinematicBody;
		bdef.position.set(pos.add(0, bodyHeight*2));
		pivot = world.createBody(bdef);
//		cshape.setRadius(bodyWidth);
//		cshape.setPosition(pos.add(0, bodyHeight*2));
//		fdef.shape = cshape;
//		pivot.createFixture(fdef).setFilterData(filter);
		
		
		// Junction
		bdef.type = BodyType.DynamicBody;
		bdef.linearDamping = 0.9f;
		bdef.angularDamping = 0.9f;
		junction = world.createBody(bdef);
		md.mass = 0;
		junction.setMassData(md);
		shape.setAsBox(1/PPM, 1/PPM);
		fdef.shape = shape;
		junction.createFixture(fdef).setFilterData(filter);

		// Spring
		DistanceJointDef djdef = new DistanceJointDef();
		djdef.bodyA = junction;
		djdef.bodyB = body;
		djdef.localAnchorA.setZero();
		djdef.localAnchorB.setZero();
		djdef.length = bodyHeight-bodyWidth;
		djdef.dampingRatio = 0.2f;
		djdef.frequencyHz = 2.15f;
		world.createJoint(djdef);
		
		// Rope
		RopeJointDef rdef = new RopeJointDef();
		rdef.bodyA = pivot;
		rdef.bodyB = junction;
		rdef.localAnchorA.setZero();
		rdef.localAnchorB.setZero();
		rdef.maxLength = bodyHeight*2;
		world.createJoint(rdef);
		
				
//		body.applyForceToCenter(1000, 5000, true);
//		armL.applyAngularImpulse(100, true);
//		armR.applyAngularImpulse(-100, true);
//		legR.applyAngularImpulse(-1000, true);
		
	}
	
	public void move(){
		pivot.setLinearVelocity((body.getPosition().x - pivot.getPosition().x)*2, 0);

	}
	
	public void jump(){
		body.applyForceToCenter(-30000*(float)Math.sin(body.getAngle()), 30000*(float)Math.cos(body.getAngle()), true);
	}
	
	public void kick(){
		
	}
	
	public Sprite generateSprite(Body bd, float halfWidth, float halfHeight, Texture texture){
		sprite.setTexture(texture);
		sprite.setRegion(texture);
		sprite.setPosition(bd.getPosition().x - halfWidth, bd.getPosition().y - halfHeight);
		sprite.setRotation(bd.getAngle() * MathUtils.radiansToDegrees);
		sprite.setSize(halfWidth * 2, halfHeight * 2);
		sprite.setOriginCenter();
		return sprite;
	}
	
	public void draw(SpriteBatch sb){
		generateSprite(legR, legWidth, legHeight, tm.get("leg")).draw(sb);
		generateSprite(legL, legWidth, legHeight, tm.get("leg")).draw(sb);
		generateSprite(armR, armWidth, armHeight, tm.get("BACKGROUND")).draw(sb);
		generateSprite(armL, armWidth, armHeight, tm.get("BACKGROUND")).draw(sb);
		generateSprite(body, bodyWidth, bodyHeight+headRad, tm.get("ros")).draw(sb);
	}
}
