package main;

public class Direction {
	private int xAccel = 0;
	private int yAccel = 0;
	
	public int getX() {
		return xAccel;
	}
	public void setX(int xAccel) {
		this.xAccel = xAccel;
	}
	public int getY() {
		return yAccel;
	}
	public void setY(int yAccel) {
		this.yAccel = yAccel;
	}
	
	public Direction(int x, int y) {
		setX(x);
		setY(y);
	}
	public Direction() {}
}
