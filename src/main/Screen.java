package main;

import java.awt.Color;

import javax.swing.JFrame;

public class Screen {
	JFrame jframe;
	WormyGame game;
	SquareSetup squares = new SquareSetup(40,40,16,16);
	
	public static final Color WORM_COLOUR = Color.RED;
	public static final Color BACKGROUND_COLOUR = Color.BLACK;
	public static final Color APPLE_COLOUR = Color.GREEN;
	public static final Color TEXT_COLOUR = Color.WHITE;
	
	public int framerate = 10;
	
	
	public Screen(int fr) {
		framerate = fr;
		jframe = new JFrame();
		jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jframe.setSize(squares.getWidth(), squares.getHeight());
		jframe.setTitle("Wormy");
		jframe.setLayout(null);
		
		
		game = new WormyGame(this);
		
		jframe.add(game);
		jframe.setVisible(true);
	}
	
	public void onEndOfGame() {
		System.exit(0);
	}
	
	public void changeTitle(String s) {
		jframe.setTitle(s);
	}
}
