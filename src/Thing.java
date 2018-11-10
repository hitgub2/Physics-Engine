abstract class Thing {
	final protected static int TYPE_NONE = 0;
	final protected static int TYPE_CIRCLE = 1;
	final protected static int TYPE_SQUARE = 2;
	final protected static int TYPE_RECTANGLE = 3;

	private final String name;
	protected int type;
	
	protected double[] x;	//x of CM
	protected double[] y;	//y of CM
	protected boolean stop;
	
	private double mass;
	
	/*
	 * @param dynamics = {{x_pos, x_vel, x_acc}, {y_pos, y_vel, y_acc}}
	 * @param properties = {mass}
	 */
	Thing(String name, double[][] dynamics, double[] properties) {
		type = TYPE_NONE;
		this.name = name;
		this.x = new double[Settings.CONSIDERATION];
		this.y = new double[Settings.CONSIDERATION];
		for(int i=0; i<Settings.DIMENTION; i++)
			setVector(i, dynamics[i]);
		mass = properties[0];
	}

	//free fall
	public void nextMove() {
		double[] vector;
		double[] delta;
		for(int i=0; i<Settings.DIMENTION; i++) {
			vector = getVector(i);
			delta = uniformly_accelerated_motion(vector[1], vector[2]);
			vector[0] += delta[0];
			vector[1] += delta[1];
		}
	}
	
	public boolean isGroundCollision() {
		if(y[0]<=0) {
			System.out.println(name + ") Ground collision!");
			return true;
		}
		return false;
	}
	
	public void boundOnGround() {
		y[0] *= -1d;
		y[0] *= Settings.FRICTION_COEFF;
		x[1] *= Settings.FRICTION_COEFF;
		y[1] /= 2d;
		y[1] *= -1d;
	}
	
	public boolean isThingCollision(Thing other) {
		double distance = Calculator.distance(this, other);
		double charLenOne = charLength(other);
		if(distance < charLenOne)
			return true;
		double charLenOther = other.charLength(this);
		if(distance < charLenOther)
			return true;
		return distance < charLenOne + charLenOther;
	}
	
	public void collideOnThing(Thing other) {
		System.out.println(name + ") Collision with " + other.name);
		///*
		System.out.println("Before collision, ");
		System.out.printf("vel_%s = [%f, %f], vel_%s = [%f, %f]\n", this.name, this.x[1], this.y[1], other.name, other.x[1], other.y[1]);
		//*/
		double totalMass = mass + other.mass;
		double[] vel;
		double[] vecOne, vecOther;
		for(int i=0; i<Settings.DIMENTION; i++) {
			vecOne = this.getVector(i);
			vecOther = other.getVector(i);
			vel = inelastic_collision(this.mass, other.mass, totalMass, vecOne[1], vecOther[1]);
			vecOne[1] = vel[0];
			vecOther[1] = vel[1];
		}
		///*
		System.out.println("After collision, ");
		System.out.printf("vel_%s = [%f, %f], vel_%s = [%f, %f]\n", this.name, this.x[1], this.y[1], other.name, other.x[1], other.y[1]);
		//*/
	}

	public void printCurrentState(int frame) {
		System.out.printf("%s) frame=%d / pos = [%f, %f] \tvel = [%f, %f]\n", name, frame, x[0], y[0], x[1], y[1]);
	}

	public boolean isStop() {
		if(!stop
				&& (Math.abs(y[0]) <= Settings.STOP_POSITION)
				&& ((Math.abs(x[1]) <= Settings.STOP_VELOCITY)
				&& (Math.abs(y[1]) <= Settings.STOP_VELOCITY))) {
			stop = true;
			System.out.println(name + ") stopped!");
		}
		return this.stop;
	}
	
	/*
	 * getVector
	 * @param i : itr
	 * @return : the vector of x(i=0), y(i=1), z(i=2), and null otherwisei
	 */
	private double[] getVector(int i) {
		switch(i) {
		case 0 : return x;
		case 1 : return y;
		}
		return null;
	}
	private void setVector(int i, double[] vector) {
		switch(i) {
		case 0 : x = vector; break;
		case 1 : y = vector; break;
		}
	}

	private double[] uniformly_accelerated_motion(double vel, double acc) {	//등가속도운동
		double[] delta = new double[2];	//{delta x or y, delta vel}
		delta[1] = acc * Settings.delT;
		delta[0] = (vel + delta[1] / 2)*Settings.delT;
		return delta;
	}
	
	private double[] inelastic_collision(double m1, double m2, double totalMass, double vi1, double vi2) {
		double coef = 1 + 1 / Settings.RESTITUTION_COEFF;
		double[] vf = new double[2];
		vf[0] = (m1 - m2 / Settings.RESTITUTION_COEFF) * vi1 + m2 * coef * vi2;
		vf[1] = m1 * coef * vi1 + (m2 - m1 / Settings.RESTITUTION_COEFF) * vi2;
		vf[0] /= totalMass;
		vf[1] /= totalMass;
		return vf;
	}
	
	/*
	 * @return [x_pos, y_pos]
	 */
	public double[] getXY() {
		double[] xy = new double[] {x[0], y[0]};
		return xy;
	}
	
	public int getType() {
		return this.type;
	}
	abstract double charLength(Thing other);	//각 도형의 chariteristic length
}

class Circle extends Thing {
	protected double radius;
	/*
	 * @param properties = {mass, radius}
	 */
	Circle(String name, double[][] dynamics, double[] properties) {
		super(name, dynamics, properties);
		super.type = TYPE_CIRCLE;
		this.radius = properties[1];
	}
	
	@Override
	double charLength(Thing other) {
		return radius;
	}
	
}

class Square extends Thing {
	protected double width;
	/*
	 * @param properties = {mass, width}
	 */
	Square(String name, double[][] dynamics, double[] properties) {
		super(name, dynamics, properties);
		super.type = TYPE_SQUARE;
		this.width = properties[1];
	}
	
	@Override
	double charLength(Thing other) {
		double slope = (this.y[0] - other.y[0]) / (this.x[0] - other.x[0]);
		double slope2 = Math.pow(slope, 2);	//slope ^ 2
		slope = Math.abs(slope);
		if(slope<=1)
			return (width / 2) * Math.sqrt(1 + slope2);
		else
			return (width / 2) * Math.sqrt(1 + 1 / slope2);
	}
}

class Rectangle extends Square {
	protected double height;
	protected double tangent;
	/*
	 * @param properties = {mass, width, height}
	 */
	Rectangle(String name, double[][] dynamics, double[] properties) {
		super(name, dynamics, properties);
		super.type = TYPE_RECTANGLE;
		this.height = properties[2];
		this.tangent = height / width;
	}
	
	@Override
	double charLength(Thing other) {
		double slope = (this.y[0] - other.y[0]) / (this.x[0] - other.x[0]);
		double slope2 = Math.pow(slope, 2);	//slope ^ 2
		slope = Math.abs(slope);
		if(slope<=tangent)
			return (width / 2) * Math.sqrt(1 + slope2);
		else
			return (height / 2) * Math.sqrt(1 + 1 / slope2);
	}
}