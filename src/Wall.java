
abstract class Wall {
	public final static int TYPE_NONE = 0;
	public final static int TYPE_VERTICAL = 1;
	public final static int TYPE_HORIZONTAL = 2;
	public final static int TYPE_DEPENDENT_OF_X = 3;
	public final static int TYPE_DEPENDENT_OF_Y = 4;
	
	protected int type;
	protected Wall() {
		this.type = 0;
	}
	
	abstract double getDistance(double[] center);
}


class VerticalWall extends Wall {
	protected double x1;
	public VerticalWall(double x1) {
		this.type = TYPE_VERTICAL;
		this.x1 = x1;
	}
	public double getX() {
		return this.x1;
	}
	
	@Override
	double getDistance(double[] center) {
		
		return 0;
	}
}
class DependentOfXWall extends VerticalWall {
	protected String function;
	public DependentOfXWall(String function) {
		super(0);
		this.type = TYPE_DEPENDENT_OF_X;
		this.function = function;
	}
	
}

class HorizontalWall extends Wall {
	protected double y1;
	public HorizontalWall(double y1) {
		this.y1 = y1;
	}
	public double getY() {
		return this.y1;
	}
	
	@Override
	double getDistance(double[] center) {
		
		return 0;
	}
}
class DependentOfYWall extends HorizontalWall {
	protected String function;
	public DependentOfYWall(String function) {
		super(0);
		this.type = TYPE_DEPENDENT_OF_Y;
		this.function = function;
	}
}