package com.engine.simulation;

import java.util.ArrayList;

import com.engine.rigidbody.RigidBody;

public class Manager {
	private ArrayList<RigidBody> rigidBodies;
	private int nRigidBodies;
	static Manager manager;

	private static final int CAPACITY = 5;
	private Manager() {
		nRigidBodies = 0;
		initialNumOfRigidbodys = 0;
		rigidBodies = new ArrayList<RigidBody>(CAPACITY);
		initialRigidBodies = new ArrayList<RigidBody>(CAPACITY);
	}
	public static Manager getInstance() {
		if(manager==null)
			manager = new Manager();
		return manager;
	}

	//PUBLIC METHOD
	public int getNumOfRigidBodies() {
		return this.nRigidBodies;
	}
	public synchronized RigidBody getRigidBody(int index) {
		return rigidBodies.get(index);
	}
	public void addRigidBody(RigidBody rb) {
		rigidBodies.add(rb);
		nRigidBodies++;
	}
	public void removeRigidBody(int index) {
		rigidBodies.remove(index);
		nRigidBodies--;
	}
	public void removeRigidBody(RigidBody rb) {
		rigidBodies.remove(rb);
		nRigidBodies--;
	}

	//INITIAL CONDITIONS
	private boolean isInitialized = false;
	private ArrayList<RigidBody> initialRigidBodies;
	private int initialNumOfRigidbodys;
	public void setInitialConditions() {
		if(!isInitialized) {
			initialNumOfRigidbodys = nRigidBodies;
			for(int i=0; i<initialNumOfRigidbodys; i++)
				initialRigidBodies.add((RigidBody)rigidBodies.get(i).clone());
			isInitialized = true;
		} else
			System.out.println("All conditions are already initialized.");
	}
	public synchronized void applyInitalConditions() {
		for(int i=0; i<initialNumOfRigidbodys; i++)
			rigidBodies.get(i).set(initialRigidBodies.get(i));
		nRigidBodies = initialNumOfRigidbodys;
	}

	//plan to delete
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