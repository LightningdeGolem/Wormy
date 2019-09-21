package main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HighScore {
	public static int getHighScore() {
		InputStream s =  HighScore.class.getResourceAsStream("/resources/highScore.txt");
		InputStreamReader sr = new InputStreamReader(s);
		BufferedReader br = new BufferedReader(sr);
		try {
			return Integer.parseInt(br.readLine());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return 0;
		}
		catch(NumberFormatException e){
			return 0;
		}
	}
}
