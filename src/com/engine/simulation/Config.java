package com.engine.simulation;

public interface Config {
	public static int DIMENSION = 2;					//2D
	public static int CONSIDERATION = 3;
	public static float GRAVITY = 30.0f;
	
	public static float RESTITUTION_COEFF_THING = 0.9f;
	public static float RESTITUTION_COEFF_THING_ANGULAR = 0.5f;
	public static float RESTITUTION_COEFF_WALL = 0.6f;
	public static float RESTITUTION_COEFF_WALL_ANGULAR = 0.5f;
	
	public static float FRICTION_COEFF = 0.999f;
	
	public static final int DP_WIDTH  = 800;
	public static final int DP_HEIGHT = 550;
	
	public static final int DP_RIGHT = DP_WIDTH-16;
	public static final int DP_BOTTOM = DP_HEIGHT-40;
	
	public static final String TITLE = "Engine  v.0.2";
}