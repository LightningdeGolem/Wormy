package main;

public class Point implements Cloneable{
	private int x = 0;
	private int y = 0;
	
	public Point(int x, int y) {
		this.setX(x);
		this.setY(y);
	}
	public Point() {}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public void changeByDirection(Direction d) {
		x = x + d.getX();
		y = y + d.getY();
	}
	
	public Point createNewPoint(Direction d) {
		return new Point(d.getX() + x, d.getY() + y);
	}
	
	@Override
	public String toString() {
		return "["+x+","+y+"]";
	}
	
	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point p = (Point) o;
			if (p.getX() == getX() && p.getY() == getY()) {
				return true;
			}
			else {
				return false;
			}
		}
		else {
			return super.equals(o);
		}
	}
}
