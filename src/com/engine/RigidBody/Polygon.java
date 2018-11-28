package com.engine.RigidBody;

import com.engine.simulation.Vec2d;

import java.util.ArrayList;

public class Polygon {
    private ArrayList<Vec2d> vertices;
    Vec2d pos;
    Vec2d vel, acc;
    double theta;
    double angular;
    double inertia;
    double mass;
    String name;

    public Polygon(String name, Vec2d pos, Vec2d vel, Vec2d acc, ArrayList<Vec2d> vertices, double theta, double angular, double mass) {
        this.name = name;
        this.pos = pos;
        this.vel = vel;
        this.acc = acc;
        this.vertices = vertices;
        this.theta = theta;
        this.angular = angular;
        this.mass = mass;
//        shape = new Rectangle2D.Float(pos.getX() - (width/2), pos.getY() - (height/2), width, height);
//        shape = new Polygon
    }

    private Polygon shape;
    public void fillShape() {

    }

}
