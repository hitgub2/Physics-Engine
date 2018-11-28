package com.engine.thing;

import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.Rectangle2D;

import com.engine.simulation.Vec2d;
import com.engine.wall.Wall;

public class Rectangle extends Thing implements Cloneable {
	protected float width;
	protected float height;
	protected float tangent;
	/*
	 * @param properties = {mass, width, height}
	 */
	public Rectangle(String name, Vec2d pos, Vec2d vel, Vec2d acc, float mass, float width, float height, float angular) {
		super(name, pos, vel, acc, mass, angular);
		super.type = TYPE_RECTANGLE;
		this.width = width;
		this.height = height;
		this.tangent = height / width;
		this.inertia = (width*width + height*height)*mass/12f;

		halfWidth = width / 2;
		halfHeight = height / 2;
//		shape = new Rectangle2D.Float(pos.getX() - halfWidth, pos.getY() - halfHeight, width, height);
		
		xi = new int[] {(int)halfWidth, (int)-halfWidth, (int)-halfWidth, (int)halfWidth};
		yi = new int[] {(int)halfHeight, (int)halfHeight, (int)-halfHeight, (int)-halfHeight};
		x = new int[4];
		y = new int[4];
		shape = new Polygon();
		shape.addPoint(0, 0);
		shape.xpoints = x;
		shape.ypoints = y;
	}
	public void set(Rectangle other) {
		super.set(other);
		this.width = other.width;
		this.height = other.height;
		this.tangent = other.tangent;
		halfWidth = width / 2;
		halfHeight = height / 2;
	}
	@Override
	public Object clone() {
		return super.clone();
	}
	
	@Override
	protected float charLength(Thing other) {
		double slope = (this.posY() - other.posY()) / (this.posX() - other.posX());
		double slope2 = Math.pow(slope, 2);	//slope ^ 2
		slope = Math.abs(slope);
		if(slope<=tangent)
			return (float)(halfWidth * Math.sqrt(1 + slope2));
		else
			return (float)(halfHeight * Math.sqrt(1 + 1 / slope2));
	}
	
	private float halfWidth;
	private float halfHeight;
	@Override
	protected float charLength(Wall wall) {
		switch(wall.getType()) {
		case Wall.TYPE_HORIZONTAL :
			return (float)(halfWidth*Math.abs(Math.sin(theta)) + halfHeight*Math.abs(Math.cos(theta)));
		case Wall.TYPE_VERTICAL :
			return (float)(halfWidth*Math.abs(Math.cos(theta)) + halfHeight*Math.abs(Math.sin(theta)));
		}
		System.out.println("Rectangle.charLength) not defined wall");
		return 0;
	}
	
	private Rectangle2D.Float shape2;
	private Polygon shape;
	private int[] xi;
	private int[] yi;
	private int[] x;
	private int[] y;
	@Override
	public Shape fillShape() {
//		shape.x = pos.getX() - halfWidth;
//		shape.y = pos.getY() - halfHeight;
		double sin = Math.sin(theta);
		double cos = Math.cos(theta);
		for(int i=0; i<4; i++) {
			x[i] = (int)pos.getX() + (int)(cos * xi[i] + sin * yi[i]);
			y[i] = (int)pos.getY() + (int)(-sin * xi[i] + cos * yi[i]);
		}
		return shape;
	}
	public int[] xs() { return this.x;}
	public int[] ys() { return this.y;}
}