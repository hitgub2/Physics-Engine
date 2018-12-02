package com.engine.rigidbody;

import java.awt.Graphics2D;

import com.engine.simulation.Vec2d;

abstract public class RigidBody implements Cloneable {
	private final String name;
    protected Vec2d pos;
    protected Vec2d vel;
    protected Vec2d acc;
    protected double theta;
    protected double angular;
    protected double mass;
    protected double inertia;
	
    protected RigidBody(String name, Vec2d pos, Vec2d vel, Vec2d acc, double theta, double angular, double mass) {
    	this.name = name;
    	this.pos = pos;
    	this.vel = vel;
    	this.acc = acc;
    	this.theta = theta;
    	this.angular = angular;
    	this.mass = mass;
    }
    public void set(RigidBody other) {
    	this.pos = (Vec2d)other.pos.clone();
    	this.vel = (Vec2d)other.vel.clone();
    	this.acc = (Vec2d)other.acc.clone();
    	this.theta = other.theta;
    	this.angular = other.angular;
    	this.mass = other.mass;
    	this.inertia = other.inertia;
    }
  //getter
  	public Vec2d pos() { return this.pos; }
  	public double posX() { return this.pos.getX(); }
  	public double posY() { return this.pos.getY(); }
  	public Vec2d vel() { return this.vel; }
  	public double velX() { return this.vel.getX(); }
  	public double velY() { return vel.getY(); }
  	public Vec2d acc() { return this.acc; }
  	public double accX() { return this.acc.getX(); }
  	public double accY() { return this.acc.getY(); }
  	public double theta() { return this.theta; }
  	public double angular() { return this.angular; }
  	public double mass() { return this.mass; }
  	public String getName() { return this.name; }
  	//setter
  	public void setPos(Vec2d newPos) { this.pos = newPos; }
  	public void setPosX(double x) { this.pos.setX(x); }
  	public void setPosY(double y) { this.pos.setY(y); }
  	public void setVel(Vec2d newVel) {this.vel = newVel; }
  	public void setVelX(double x) { this.vel.setX(x); }
  	public void setVelY(double y) { this.vel.setY(y); }
  	public void setAcc(Vec2d newAcc) { this.acc = newAcc; }
  	public void setAccX(double x) { this.acc.setX(x); }
  	public void setAccY(double y) { this.acc.setY(y); }
  	public void setTheta(double theta) { this.theta = theta; }
  	public void setAngular(double angular) { this.angular = angular; };
  	public void setMass(double mass) {this.mass = mass; }
    
    @Override
	public Object clone() {
    	try {
    		RigidBody cloned = (RigidBody)super.clone();
    		cloned.pos = (Vec2d)this.pos.clone();
    		cloned.vel = (Vec2d)this.vel.clone();
    		cloned.acc = (Vec2d)this.acc.clone();
    		return cloned;
    	} catch(CloneNotSupportedException e) {
    		e.printStackTrace();
    		return null;
    	}
	}

	abstract public void draw(Graphics2D g2d);
}
