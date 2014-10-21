package com.trashgames.trashsoccer.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.MassData;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;

import static com.trashgames.trashsoccer.Game.PPM;

public class Player {

	private Body body;
	private Body armR;
	private Body armL;
	private Body legR;
	private Body legL;
	
	private float bodyWidth = Gdx.graphics.getWidth()/30/PPM;
	private float bodyHeight = Gdx.graphics.getHeight()/10/PPM;
	private float headRad = Gdx.graphics.getWidth()/30/PPM;
	private float armWidth = Gdx.graphics.getWidth()/90/PPM;
	private float armHeight = Gdx.graphics.getHeight()/20/PPM;
	private float legWidth = Gdx.graphics.getWidth()/70/PPM;
	private float legHeight = Gdx.graphics.getHeight()/20/PPM;
	
	public Player(World world, Vector2 pos){
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
		body.createFixture(fdef);
		MassData md = new MassData();
		md.mass = 30f;
		md.I = 1.5f;
		body.setMassData(md);
		
// Arms
		// Right arm
		armR = world.createBody(bdef);
		md.I = 0.1f;
		md.mass = 2f;
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
		shape.setAsBox(legWidth, legHeight);
		fdef.restitution = 0.5f;
		fdef.friction = 0.7f;
		md.mass = 30f;
		md.I = 1.5f;
//		md.center.add(0, -legWidth);
		legR = world.createBody(bdef);
		legR.setMassData(md);
		legR.createFixture(fdef);
		
		// Left leg
		legL = world.createBody(bdef);
		legL.setMassData(md);
		legL.createFixture(fdef);
		
		// Right leg joint
		jdef.bodyA = body;
		jdef.bodyB = legR;
		jdef.localAnchorA.set(bodyWidth/2, -bodyHeight);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
		// Left leg joint
		jdef.bodyA = body;
		jdef.bodyB = legL;
		jdef.localAnchorA.set(-bodyWidth/2, -bodyHeight);
		jdef.localAnchorB.set(0, legHeight);
		jdef.collideConnected = false;
		jdef.lowerAngle = 0f;
		jdef.upperAngle = 0f;
		jdef.enableLimit = true;
		world.createJoint(jdef);
		
//		body.applyForceToCenter(1000, 5000, true);
//		armL.applyAngularImpulse(100, true);
//		armR.applyAngularImpulse(-100, true);
//		legR.applyAngularImpulse(-1000, true);
		
	}
	
	public void jump(){
		body.applyForceToCenter(-10000*(float)Math.sin(body.getAngle()), 10000*(float)Math.cos(body.getAngle()), true);
	}
	
	public void kick(){
		
	}
}
