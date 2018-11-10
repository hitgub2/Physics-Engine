import java.util.ArrayList;

class Manager {
	ArrayList<Thing> list;
	private int num;
	static Manager manager;
	private Manager(int capacity) {
		list = new ArrayList<>(capacity);
		num = 0;
	}
	public static Manager getInstance(int capacity) {
		if(manager==null)
			manager = new Manager(capacity);
		return manager;
	}
	
	//PUBLIC METHOD
	public int getNumberOfThings() {
		return this.num;
	}
	public void addThing(Thing thing) {
		list.add(thing);
		num++;
	}
	public void removeThing(int index) {
		if(num==0) {
			System.out.println("Manager.removeThing) manager.list is empty.");
		} else if(index>=num || index<0) {
			System.out.println("Manager.removeThing) out of boundary index.");
		} else {
			list.remove(index);
			num--;
		}
	}
	public Thing getThing(int index) {
		if(num==0) {
			System.out.println("Manager.getThing) manager.list is empty.");
			return null;
		} else if(index>=num || index<0) {
			System.out.println("Manager.getThing) out of boundary index.");
			return null;
		}
		return list.get(index);
	}
	public boolean isStop(int index) {
		if(num==0) {
			System.out.println("Manager.isStop) manager.list is empty.");
			return false;
		} else if(index>=num || index<0) {
			System.out.println("Manager.isStop) out of boundary index.");
			return false;
		}
		return this.list.get(index).isStop();
	}
	
	public void printInitialStates(int frame) {
		for(int i=0; i<num; i++)
			list.get(i).printCurrentState(frame);
	}
}