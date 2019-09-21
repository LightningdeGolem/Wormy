package main;

import java.awt.Color;
import java.awt.Graphics2D;

public class SquareSetup {
	public int squareA = 0;
	public int squareD = 0;
	public int squareW = 0;
	public int squareH = 0;
	
	public SquareSetup(int x1, int y1, int x2, int y2) {
		squareA = x1;
		squareD = y1;
		squareW = x2;
		squareH = y2;
	}
	
	@Override
	public String toString() {
		return s(squareA)+s(squareD);
	}
	
	public int getWidth() {
		return squareA * squareW;
	}
	public int getHeight() {
		return squareD * squareH;
	}
	
	private String s(int i) {
		return Integer.toString(i);
	}
	
	public static void drawSquareAt(Color c, Graphics2D g2d, int x, int y, SquareSetup s) {
		g2d.setColor(c);
		g2d.fillRect(x*s.squareW, y*s.squareH, s.squareW, s.squareH);
	}
}
