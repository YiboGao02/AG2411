package Lab0b;

public class Point {
	// Attributes
	public String name;
	public double x;
	public double y;

	// Methods
	// Constructor
	public Point(String name, double x, double y) {
		this.name = name; // name on LHS is attribute, name on RHS is input parameter
		this.x = x; // x on LHS is attribute, x on RHS is input parameter
		this.y = y; // y on LHS is attribute, y on RHS is input parameter
	}

	// Others
	public void setName(String name) {
		this.name = name;
	}

	public void setX(double x) {
		this.x = x;
	}

	public void setY(double y) {
		this.y = y;
	}

	public double getDistanceFromOrigin() {
		double deltaX = this.x - 0; // variable definition and value assignment is combined
		double deltaY = this.y - 0;
		double distance = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2)); // the pow method is a Math operation
		return distance;
	}

	public double getDistanceToOrigin() {
		double distance = this.getDistanceFromOrigin(); // distance from and is the same!
		return distance;
	}

	public double getDirectionFromOrigin() { // We didn�t have time to implement this method.
		double distance = 0;
		return distance;
	}

	public double getDirectionToOrigin() { // We didn�t have time to implement this method.
		double distance = 0;
		return distance;
	}
}
