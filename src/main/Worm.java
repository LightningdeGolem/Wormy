package main;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Worm implements Collidable, Iterable<Point>{
	private ArrayList<Point> points = new ArrayList<Point>();
	private Direction direction = new Direction();
	private Color colour;
	
	private int counter = 0;
	
	SquareSetup ss;
	
	public Worm(SquareSetup s, Color c) {
		ss = s;
		colour = c;
		points.add(WormyGame.generatePoints(ss));
	}
	public Worm(SquareSetup s, Color c, Point p) {
		ss = s;
		colour = c;
		points.add(p);
	}
	
	public void reset() {
		points.clear();
		points.add(WormyGame.generatePoints(ss));
	}
	
	public Direction getDirection() {
		return direction;
	}
	
	public void setDirection(Direction d) {
		setDirection(d, false);
	}
	
	public void setDirection(Direction d, boolean check) {
		if (check && points.size() > 1) {
			if (Math.abs(d.getX()) > 0 && d.getX()*-1 == direction.getX()) {
				return;
			}
			if (Math.abs(d.getY()) > 0 && d.getY()*-1 == direction.getY()) {
				return;
			}
		}
		direction = d;
	}
	
	public void render(Graphics2D g2d) {
		for (Point p : points) {
			SquareSetup.drawSquareAt(colour, g2d, p.getX(), p.getY(), ss);
		}
	}
	
	public void next() {
		if (points.size() > 0) {
			points.add(0, points.get(0).createNewPoint(direction));
			if (counter > 0) {
				counter --;
			}
			else {
				points.remove(points.size() - 1);
			}
			
		}
		
	}

	@Override
	public boolean collidedWith(Collidable c) {
		for (Point p : c.getCollisionPoints()) {
			if (this.getCollisionPoints().contains(p)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public List<Point> getCollisionPoints() {
		return points;
	}
	
	public void addToCounter(int amount) {
		counter += amount;
	}
	public int getCounterAmount() {
		return counter;
	}

	@Override
	public Iterator<Point> iterator() {
		return points.iterator();
	}
	
	public Point getHead() {
		return points.get(0);
	}
	
	public boolean offEdge() {
		if (getHead().getX() > ss.squareA || 
				getHead().getY() > ss.squareD ||
				getHead().getX() < 0 ||
				getHead().getY() < 0
			) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean collidedWithSelf() {
		@SuppressWarnings("unchecked")
		ArrayList<Point> ps = (ArrayList<Point>) points.clone();
		ps.remove(0);
		if (ps.contains(points.get(0))) {
			return true;
		}
		return false;
	}
	
	public int getScore() {
		return points.size();
	}
	
}
