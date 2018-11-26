package com.engine.thing;

import java.awt.Shape;

import com.engine.simulation.Calculator;
import com.engine.simulation.Config;
import com.engine.simulation.Vec2d;
import com.engine.wall.Wall;

public abstract class Thing {
	final protected static int TYPE_NONE = 0;
	final protected static int TYPE_CIRCLE = 1;
	final protected static int TYPE_SQUARE = 2;
	final protected static int TYPE_RECTANGLE = 3;
	
	//필드 추가 시 set 메소드에도 추가!!
	private final String name;
	protected int type;
	protected Vec2d pos, vel, acc;
	protected float mass;
	protected float theta;
	protected float angular;
	protected float inertia;	//moment of inertia, https://ko.wikipedia.org/wiki/%EA%B4%80%EC%84%B1_%EB%AA%A8%EB%A9%98%ED%8A%B8
//	private boolean isStop;
		
	protected Thing(String name, Vec2d pos, Vec2d vel, Vec2d acc, float mass, float angular) {
		type = TYPE_NONE;
		this.name = name;
		this.pos = pos;
		this.vel = vel;
		this.acc = acc;
		this.mass = mass;
		this.theta = 0f;
		this.angular = angular;
	}
	
	//getter
	public Vec2d pos() { return this.pos; }
	public float posX() { return this.pos.getX(); }
	public float posY() { return this.pos.getY(); }
	public Vec2d vel() { return this.vel; }
	public float velX() { return this.vel.getX(); }
	public float velY() { return vel.getY(); }
	public Vec2d acc() { return this.acc; }
	public float accX() { return this.acc.getX(); }
	public float accY() { return this.acc.getY(); }
	public float theta() { return this.theta; }
	public float angular() { return this.angular; }
	public float mass() { return this.mass; }
//	public boolean isStop() { return this.isStop; }
	public String getName() { return this.name; }
	//setter
	public void setPos(Vec2d newPos) { this.pos = newPos; }
	public void setPosX(float x) { this.pos.setX(x); }
	public void setPosY(float y) { this.pos.setY(y); }
	public void setVel(Vec2d newVel) {this.vel = newVel; }
	public void setVelX(float x) { this.vel.setX(x); }
	public void setVelY(float y) { this.vel.setY(y); }
	public void setAcc(Vec2d newAcc) { this.acc = newAcc; }
	public void setAccX(float x) { this.acc.setX(x); }
	public void setAccY(float y) { this.acc.setY(y); }
	public void setTheta(float theta) { this.theta = theta; }
	public void setAngular(float angular) { this.angular = angular; };
	public void setMass(float mass) {this.mass = mass; }
	//set
	public void set(Thing other) {
		type = other.type;
//		this.name = other.name;
		this.pos.setX(other.pos.getX());
		this.pos.setY(other.pos.getY());
		this.vel.setX(other.vel.getX());
		this.vel.setY(other.vel.getY());
		this.acc.setX(other.acc.getX());
		this.acc.setY(other.acc.getY());
		this.theta = other.theta;
		this.angular = other.angular;
		this.mass = other.mass;
		this.inertia = other.inertia;
	}
	
	//charLength
	abstract protected float charLength(Thing other);
	abstract protected float charLength(Wall wall);
	//Shape
	abstract public Shape fillShape();
	
	
	
	public void bounce(Wall wall) {
		if(!wall.isToward(pos, vel))
			return;
		float charLength = charLength(wall);
		float dist = wall.distance(pos);
		if(dist <= charLength) {
//			System.out.println("bounce " + name + " on " + wall.getName());
//			System.out.printf("vi = [%f, %f]\t", vel.getX(), vel.getY());
			wall.bounce(this, charLength);	//앞서 구한 charLength
//			System.out.printf("vf = [%f, %f]\n\n", vel.getX(), vel.getY());
		}
	}
	public void collide(Thing other) {
		float distance = Calculator.distance(this, other);
		float char1 = this.charLength(other);
		float char2 = other.charLength(this);
		boolean isCollision = false;
		if(distance <= char1)
			isCollision = true;
		else {
			if(distance <= char2)
				isCollision = true;
			else
				isCollision = distance < char1 + char2;
		}
		if(!isCollision)
			return;
//		System.out.println("collide " + this.name + " and " + other.name);
		inelastic_collision(this, other, char1, char2);
		seperate(this, other, distance, char1, char2);
	}
	
	/*
	 * @return determine stop condition and return isStopped
	 */
//	public boolean setStop() {
//		if((Math.abs(vel.getX()) <= Config.STOP_VELOCITY) && (Math.abs(vel.getY()) <= Config.STOP_VELOCITY)
//				&& (Calculator.abs(pos.getY() - Config.DP_HEIGHT) <= Config.STOP_POSITION)) {	//수정할것
//			isStop = true;
////			LogPrinter.print(name + ") stopped!");
//		}
//		return this.isStop;
//	}
	
	
	//calculation
	final float COEF = Config.RESTITUTION_COEFF_THING + 1f;	//(1 + e)
	final float COEF_ANGULAR = Config.RESTITUTION_COEFF_THING_ANGULAR + 1f;	//(1 + e)
	private void inelastic_collision(Thing t1, Thing t2, float char1, float char2) {
		float m1 = t1.mass,			 	m2 = t2.mass;
		Vec2d v1 = t1.vel, 				v2 = t2.vel;
//		System.out.printf("v1 = [%f, %f]\tv2 = [%f, %f]\n", v1.getX(), v1.getY(), v2.getX(), v2.getY());
		float totalMass = m1 + m2;
		float vi1, vi2, vf1, vf2;
		for(int i=0; i<Config.DIMENSION; i++) {		//각 성분(x, y, z...)에 대한 나중 속도 계산
			vi1 = v1.getComponent(i);
			vi2 = v2.getComponent(i);
			vf1 = 	((m1 - m2 * Config.RESTITUTION_COEFF_THING)		* vi1 +
					(m2 * COEF) 										* vi2) / totalMass;
			vf2 = 	((m1 * COEF) 										* vi1 +
					(m2 - m1 * Config.RESTITUTION_COEFF_THING) 		* vi2) / totalMass;
			v1.setComponent(i, vf1);
			v2.setComponent(i, vf2);
		}
		
		float in1 = t1.inertia;
		float in2 = t2.inertia;
		float in = in1 + in2;
		float avi1 = t1.angular, avi2 = t2.angular;
		float avf1 = 	((in1 - in2 * Config.RESTITUTION_COEFF_THING_ANGULAR)	* avi1 +
						(in2 * COEF_ANGULAR) 										* avi2) / in;
		float avf2 = 	((in1 * COEF_ANGULAR) 										* avi1 +
						(in2 - in1 * Config.RESTITUTION_COEFF_THING_ANGULAR) 	* avi2) / in;
		t1.angular = avf1;
		t2.angular = avf2;
		
//		System.out.printf("v1 = [%f, %f]\tv2 = [%f, %f]\n\n", v1.getX(), v1.getY(), v2.getX(), v2.getY());
//		System.out.printf("t1.iner = %f\tt2.iner = %f\n", in1, in2);
//		System.out.printf("t1.angv = %f\tt2.angv = %f\n", avf1, avf2);
	}
	private void seperate(Thing t1, Thing t2, float distance, float charLength1, float charLength2) {
		float push = (charLength1 + charLength2 - distance);
//		System.out.println("push = "+push);
		if(push<0)
			return;
//		System.out.println("dist = " + distance + ", char1+2 = "+(charLength1+charLength2));
		Vec2d pushVec = t1.pos.sub(t2.pos);
		Vec2d sep1 = pushVec.unit(); sep1.multiply(push);
		Vec2d sep2 = pushVec.unit(); sep2.multiply(-push);
//		System.out.printf("pos1 = [%f, %f]\t pos2 = [%f, %f]\n", t1.pos.getX(), t1.pos.getY(), t2.pos.getX(), t2.pos.getY());
//		System.out.printf("sep1 = [%f, %f]\t sep2 = [%f, %f]\n", sep1.getX(), sep1.getY(), sep2.getX(), sep2.getY());
		t1.pos.add(sep1);
		t2.pos.add(sep2);
//		System.out.printf("pos1 = [%f, %f]\t pos2 = [%f, %f]\n", t1.pos.getX(), t1.pos.getY(), t2.pos.getX(), t2.pos.getY());
//		System.out.println("dist = " + Calculator.distance(t1, t2) + ", char1+2 = "+(charLength1+charLength2));
//		System.out.println();
	}
//	public float calc_theta() {		//replaced at Engine Thread (thing.setTheta(thing.theta() + thing.angular() * unitT)) 
//		float ret_theta = this.theta;
//		ret_theta += this.angular / 60;
//		this.theta = ret_theta;
//		return ret_theta;
//	}
}