package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.AbstractAction;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class WormyGame extends JPanel implements ActionListener{
	private static final long serialVersionUID = 3350710760250147620L;
	SquareSetup s;
	Worm worm;
	Apple apple;
	private Screen screen;
	
	private long lastTick;
	
	
	private int bpm = 120;
	Music music;
	
	private static final int pointsPerApple = 4;
	
	private int levelUpX = -1;
	
	private AbstractAction P1U;
	private AbstractAction P1D;
	private AbstractAction P1L;
	private AbstractAction P1R;
	
	private int speed = 10;
	private int applesCollected = 0;
	private int level = 0;
	
	private GameState state = GameState.BEFORE_START;
	
	private int lastScore = 0;
	
	private void died() {
		music.reset();
		bpm = 120;
		music.setSpeed(120);
	}
	
	
	private void calculateSpeed() {
		int prevLevel = level;
		level = Math.round(applesCollected/4)*4;
		boolean levelUp = prevLevel != level;
		
		if (levelUp) {
			levelUp();
		}
		
	}
	
	private void levelUp(){
		speed += level^2;
		bpm = bpm + 20;
		music.setSpeed(bpm);
		music.levelUp();
		levelUpX = 0;
	}
	
	private void pause() {
		if (state == GameState.PLAYING) {
			state = GameState.PAUSED;
			return;
		}
		else if (state == GameState.PAUSED){
			state = GameState.PLAYING;
		}
		else {
			state = GameState.PLAYING;
			speed = 10;
			level = 0;
			applesCollected = 0;
		}
	}
	
	public void onKeyPress() {
		if (state == GameState.BEFORE_START) {
			state = GameState.PLAYING;
		}
	}
	
	private void setBind(String def, AbstractAction a, String s) {
		this.getInputMap().put(KeyStroke.getKeyStroke(def), s);
		this.getActionMap().put(s, a);
	}
	private void setEvents() {
		P1U = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				worm.setDirection(new Direction(0,-1), true);
				onKeyPress();
			}
		};
		P1D = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				worm.setDirection(new Direction(0,1), true);
				onKeyPress();
			}
		};
		P1L = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				worm.setDirection(new Direction(-1,0), true);
				onKeyPress();
			}
		};
		P1R = new AbstractAction() {
			private static final long serialVersionUID = 1L;
	
			@Override
			public void actionPerformed(ActionEvent e) {
				worm.setDirection(new Direction(1,0), true);
				onKeyPress();
			}
		};
		AbstractAction PAUSE = new AbstractAction() {
			private static final long serialVersionUID = -4722068139641527604L;

			@Override
			public void actionPerformed(ActionEvent e) {
				pause();
				
			}
			
		};
		setBind("UP",P1U,"1u");
		setBind("LEFT",P1L,"1l");
		setBind("DOWN",P1D,"1d");
		setBind("RIGHT",P1R,"1r");
		setBind("SPACE",PAUSE,"p");
	}
	
	public WormyGame(Screen sc) {
		music = new Music("/resources/music.txt");
		music.play();
		lastTick = System.currentTimeMillis();
		
		screen = sc;
		this.s = screen.squares;
		setSize(s.getWidth(), s.getHeight());
		worm = new Worm(s,Screen.WORM_COLOUR);
		worm.setDirection(new Direction(1,0));
		apple = new Apple(Screen.APPLE_COLOUR,s);
		screen.changeTitle("Wormy - press a key to start");
		
		setEvents();
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		try {
			Thread.sleep(1000/60);
			repaint();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Screen.BACKGROUND_COLOUR);
		g2d.fillRect(0, 0, s.getWidth(), s.getHeight());
		
		g2d.setColor(Screen.TEXT_COLOUR);
		Font font = new Font("Sans-serif", 10,30);
		g2d.setFont(font);
		if (state == GameState.PLAYING) {
			g2d.setColor(Screen.TEXT_COLOUR);
			g2d.drawString("Score: "+worm.getScore(), 0, 50);
			apple.render(g2d);
			if (levelUpX >= 0) {
				g2d.setColor(Color.GREEN);
				g2d.drawString("Level up", levelUpX, s.getHeight()/2);
				
				levelUpX += speed * 3;
				if (levelUpX > s.getWidth() || state == GameState.DIED) {
					levelUpX = -1;
				}
			}
		}
		else if (state == GameState.BEFORE_START) {
			g2d.setColor(Screen.TEXT_COLOUR);
			g2d.drawString("Welcome to wormy", 0, 50);
			screen.jframe.setTitle("Wormy - press a key to start");
		}
		else if (state == GameState.DIED) {
			font = new Font("Sans-serif", 10,100);
			g2d.setFont(font);
			g2d.setColor(Color.RED);
			g2d.drawString("YOU DIED", s.getWidth()/2-250, s.getHeight()/2);
			
			font = new Font("Sans-serif", 10,25);
			g2d.setFont(font);
			g2d.setColor(Color.WHITE);
			g2d.drawString("Score: "+lastScore+" - press space to start", s.getWidth()/2-250, s.getHeight()/2+50);
			screen.jframe.setTitle("Wormy - you died");
		}
		
		worm.render(g2d);
		if (System.currentTimeMillis()-(1000/speed) > lastTick) {
			lastTick = System.currentTimeMillis();
		}
		else {
			return;
		}
		
		
		calculateSpeed();
		
		if (state == GameState.PLAYING) {
			worm.next();
			if (worm.collidedWithSelf() || worm.offEdge()) {
				died();
				lastScore = worm.getScore();
				state = GameState.DIED;
				worm.reset();
				apple.randomPosition();
			}
			
			if (worm.collidedWith(apple)) {
				worm.addToCounter(pointsPerApple);
				applesCollected++;
				apple.randomPosition();
			}
			
			
			screen.jframe.setTitle("Wormy");
		}
		
		else if (state == GameState.PAUSED) {
			g2d.setColor(Screen.TEXT_COLOUR);
			g2d.drawString("PAUSED", 0, 50);
			screen.jframe.setTitle("Wormy - paused");
		}
		
		
		
	}
	
	public static final Point generatePoints(SquareSetup ss) {
		double x = Math.random();
		double y = Math.random();
		x = x * (ss.squareA-1);
		y = y * (ss.squareD-1);
		int xi = (int) Math.floor(x);
		int yi = (int) Math.floor(y);
		
		Point point = new Point(xi,yi);
		
		return point;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
//	private double getFreq(int note) {
//		return 440*
//				Math.pow(
//						2,
//						(note-69.0)/12
//					);
//	}
	
}
