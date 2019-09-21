package main;

import java.util.List;

public interface Collidable {
	public boolean collidedWith(Collidable c);
	public List<Point> getCollisionPoints();
}
