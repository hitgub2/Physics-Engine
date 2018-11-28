package com.engine.simulation;

import java.util.ArrayList;

public class Manager {
	private int numOfThings;
	private int numOfWalls;
	static Manager manager;

	private static final int CAPACITY = 5;
	private Manager() {
		numOfThings = 0;
		numOfWalls = 0;

	}
	public static Manager getInstance() {
		if(manager==null)
			manager = new Manager();
		return manager;
	}
	
	//PUBLIC METHOD
	public int getNumberOfThings() {
		return this.numOfThings;
	}
	public int getNumberOfWalls() {
		return this.numOfWalls;
	}
	/*
	public void addThing(Thing thing) {
		things.add(thing);
		numOfThings++;
	}
	public void addWall(Wall wall) {
		walls.add(wall);
		numOfWalls++;
	}
	public void removeThing(int index) {
		if(numOfThings==0) {
			System.out.println("Manager.removeThing) manager.list is empty.");
		} else if(index>=numOfThings || index<0) {
			System.out.println("Manager.removeThing) out of boundary index.");
		} else {
			things.remove(index);
			numOfThings--;
		}
	}
	public void removeWall(int index) {
		if(numOfWalls==0) {
			System.out.println("Manager.removeThing) manager.list is empty.");
		} else if(index>=numOfWalls || index<0) {
			System.out.println("Manager.removeThing) out of boundary index.");
		} else {
			walls.remove(index);
			numOfWalls--;
		}
	}
	public synchronized Thing getThing(int index) {
		if(numOfThings==0) {
			System.out.println("Manager.getThing) manager.list is empty.");
			return null;
		} else if(index>=numOfThings || index<0) {
			System.out.println("Manager.getThing) out of boundary index.");
			return null;
		}
		return things.get(index);
	}
	public synchronized Wall getWall(int index) {
		if(numOfWalls==0) {
			System.out.println("Manager.getWall) manager.walls is empty.");
			return null;
		} else if(index>=numOfWalls || index<0) {
			System.out.println("Manager.getWall) out of boundary index.");
			return null;
		}
		return walls.get(index);
	}
	
	//INITIAL CONDITIONS
	private boolean isInitialized = false;
	private int initialNumOfThings;
	private int initialNumOfWalls;
	private ArrayList<Thing> initialThings;
	private ArrayList<Wall> initialWalls;
	public void setInitialConditions() {
		if(!isInitialized) {
			initialNumOfThings = numOfThings;
			initialNumOfWalls = numOfWalls;
			for(int i=0; i<initialNumOfThings; i++)
				initialThings.add((Thing)things.get(i).clone());
			for(int i=0; i<initialNumOfWalls; i++)
				initialWalls.add((Wall)walls.get(i).clone());
			isInitialized = true;
		} else
			System.out.println("All conditions are already initialized.");
	}
	public void printInitialConditions() {
		initialNumOfThings = numOfThings;
		initialNumOfWalls = numOfWalls;
		for(int i=0; i<initialNumOfThings; i++) {
			Thing t = things.get(i);
			System.out.println(t.getName() + ", pos = " + t.pos() + ", vel = " + t.vel() + ", acc = " + t.accX());
		}
		for(int i=0; i<initialNumOfWalls; i++)
			System.out.println(walls.get(i).getName());
	}
	public synchronized void applyInitalConditions() {
		for(int i=0; i<initialNumOfThings; i++)
			things.get(i).set(initialThings.get(i));
		for(int i=0; i<initialNumOfWalls; i++)
			walls.get(i).set(initialWalls.get(i));
		numOfThings = initialNumOfThings;
		numOfWalls = initialNumOfWalls;
	}*/
	
//	public void clear() {
//		walls.clear();
//		things.clear();
//		numOfThings = 0;
//		numOfWalls = 0;
//		System.out.println("Things and walls are all cleared.");
//	}
//	public boolean isStop(int index) {
//		if(numOfThings==0) {
//			System.out.println("Manager.isStop) manager.list is empty.");
//			return false;
//		} else if(index>=numOfThings || index<0) {
//			System.out.println("Manager.isStop) out of boundary index.");
//			return false;
//		}
//		return this.things.get(index).isStop();
//	}
	
//	public void printInitialStates(int frame) {
//		for(int i=0; i<numOfThings; i++)
//			things.get(i).printCurrentState(frame);
//	}
}