package com.engine.rigidbody;

import java.awt.Graphics2D;

import com.engine.simulation.Vec2d;

public class Polygon extends RigidBody implements Cloneable {
	private int[] xi;
	private int[] yi;
	private int nVertices;

	public Polygon(String name, Vec2d pos, Vec2d vel, Vec2d acc, double theta, double angular, double mass, int[] xi, int[] yi) {
		super(name, pos, vel, acc, theta, angular, mass);
		this.xi = xi;
		this.yi = yi;
		nVertices = xi.length;
		inertia = 1d;
		
		x = new int[nVertices];
		y = new int[nVertices];
    }
	@Override
	public void set(RigidBody other) {
		try {
			Polygon p = (Polygon)other;
			super.set(other);
			this.xi = p.xi;
			this.yi = p.yi;
			this.nVertices = p.nVertices;
		} catch(ClassCastException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Object clone() {
		Polygon polygon = (Polygon)super.clone();
		polygon.xi = this.xi.clone();
		polygon.yi = this.yi.clone();
		return polygon;
	}


	private int[] x;	//current verctices x location
	private int[] y;	//current verctices y location
	@Override
	public void draw(Graphics2D g2d) {
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		for(int i=0; i<nVertices; i++) {
		   x[i] = (int)pos.getX() + (int)(cos * xi[i] + sin * yi[i]);
		   y[i] = (int)pos.getY() + (int)(-sin * xi[i] + cos * yi[i]);
		}
		g2d.fillPolygon(x, y, nVertices);
	}
}
