package com.engine.rigidbody;

import java.awt.Graphics2D;

import com.engine.simulation.Vec2d;

public class Circle extends RigidBody implements Cloneable {
	private double rad;
	private double dia;
	
	public Circle(String name, Vec2d pos, Vec2d vel, Vec2d acc, double theta, double angular, double mass, double rad) {
		super(name, pos, vel, acc, theta, angular, mass);
		this.rad = rad;
		this.dia = 2 * rad;
	}
	@Override
	public void set(RigidBody other) {
		try {
			Circle p = (Circle)other;
			super.set(other);
			this.rad = p.rad;
			this.dia = p.dia;
		} catch(ClassCastException e) {
			e.printStackTrace();
		}
	}

	@Override
	public Object clone() {
		Circle cloned = (Circle)super.clone();
		return cloned;
	}

	@Override
	public void draw(Graphics2D g2d) {
		g2d.fillOval((int)(pos.getX()-rad), (int)(pos.getY()-rad), (int)dia, (int)dia);
	}

}
