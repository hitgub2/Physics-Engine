public class Main {
	
	public static Manager manager;
	public static void main(String[] args) {
		manager = Manager.getInstance(3);
		
		//INPUT
		addThings();
		
		//INITIALIZE
		int numberOfThings = manager.getNumberOfThings();
		int nubmerOfWalls = manager.getNumberOfWalls();
		int frame = 0;
		manager.printInitialStates(frame); 
		
		//OPERATION
		/*
		 * 한번에 하나씩 충돌 가정(2개 이상 동시 충돌 X)
		 */
		Thing thing;
		Wall wall = null;
		while(isContinue()) {
			frame++;
			for(int i=0; i<numberOfThings; i++) {
				thing = manager.getThing(i);
				if(!thing.isStop() && !thing.setStop()) {
					thing.nextMove();
//					for(int j=0; j<nubmerOfWalls; j++) {
//						wall = manager.getWall(j);
						if(thing.isBounce(wall))
							thing.bounce(wall);
//					}
					for(int j=i+1; j<numberOfThings; j++) {
						if(thing.isCollision(manager.getThing(j)))
							thing.collide(manager.getThing(j));
					}
					thing.printCurrentState(frame);
				}
			}
		}
		System.out.println("   frame="+frame+" / stop!");
	}
	
	static void addThings() {
		Thing thingToAdd;
		double[][] dynamics = null;	//[DIMENTION][CONSIDERATION]
		double[] properties = null;
		dynamics = new double[][] {
			{5d, 	0d, 	0d},
			{100d, 	0d, 	Settings.GRAVITY}
		};
		properties = new double[] {2, 2};
		thingToAdd = new Circle("A", dynamics, properties);
		manager.addThing(thingToAdd);
		
		dynamics = new double[][] {
			{50d, 	1d, 	0d},
			{10d, 	5d, 	Settings.GRAVITY}
		};
		properties = new double[] {1, 3};
		thingToAdd = new Circle("B", dynamics, properties);
		manager.addThing(thingToAdd);
		
		dynamics = new double[][] {
			{10d, 	10d, 	0d},
			{20d, 	10d, 	Settings.GRAVITY}
		};
		properties = new double[] {3, 2};
		thingToAdd = new Circle("C", dynamics, properties);
		manager.addThing(thingToAdd);
		
		dynamics = new double[][] {
			{35d, 	-10d, 	0d},
			{75d, 	-10d, 	Settings.GRAVITY}
		};
		properties = new double[] {2, 4};
		thingToAdd = new Circle("D", dynamics, properties);
		manager.addThing(thingToAdd);
	}
	
	static boolean isContinue() {
		int count = manager.getNumberOfThings();
		for(int i=0; i<count; i++)
			if(!manager.isStop(i))
				return true;
		return false;
	}
}