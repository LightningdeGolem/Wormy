package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Apple implements Collidable{
	private Point position;
	private Color colour;
	private SquareSetup ss;

	public Apple(Color c, Point p, SquareSetup s) {
		position = p;
		colour = c;
		ss= s;
	}
	
	public Apple(Color c, SquareSetup s) {
		colour = c;
		ss= s;
		
		randomPosition();
	}
	
	public Point randomPosition() {
		double x = Math.random();
		double y = Math.random();
		x = x * (ss.squareA-1);
		y = y * (ss.squareD-1);
		int xi = (int) Math.floor(x);
		int yi = (int) Math.floor(y);
		
		Point point = new Point(xi,yi);
		
		position = point;
		
		return point;
		
	}
	
	public void render(Graphics2D g2d) {
		SquareSetup.drawSquareAt(colour, g2d, position.getX(), position.getY(), ss);
	}
	
	@Override
	public boolean collidedWith(Collidable c) {
		for (Point p : c.getCollisionPoints()) {
			if (p.equals(position)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Point> getCollisionPoints() {
		ArrayList<Point> p = new ArrayList<Point>();
		p.add(position);
		return p;
	}

}
