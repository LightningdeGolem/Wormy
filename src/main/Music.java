package main;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MetaEventListener;
import javax.sound.midi.MetaMessage;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;

public class Music {
//	private Sequencer se;
	public Sequencer se;
	private int bpm = 120;
	
	ArrayList<Integer> done = new ArrayList<Integer>();
	
    private Sequence getSequence(String filename) {
    		try {
    				
    				URL url = getClass().getResource(filename);
    				return MidiSystem.getSequence(url);
				
			} catch (InvalidMidiDataException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
    }
	
    public Music(String file) {
        try {
            se = MidiSystem.getSequencer(); // Get the default Sequencer
            if (se==null) {
                System.err.println("Sequencer device not supported");
                return;
            }
            
            se.open(); // Open device
            // Create sequence, the File must contain MIDI file data.
            Sequence sequence = getSequence(file);
            
            se.setSequence(sequence); // load it into se
            muteAll();
            
            se.addMetaEventListener(new MetaEventListener() {

				@Override
				public void meta(MetaMessage event) {
	            	  if (event.getType() == 47) {
	            	   if (se != null && se.isOpen()) {
	            	       se.setTickPosition(0);
	            	       updateTempo();
	            	       se.start();
	            	   }
	            	  }
	            	}
            		
            });
        } catch (MidiUnavailableException | InvalidMidiDataException ex) {
            ex.printStackTrace();
        }
    }
    
    
    public void play() {
    		se.start();
    		
    }
    
    public void stop() {
    		se.stop();
    }
    
    public void loop() {
    		play();
//    		se.start();
    }
    
    public void setSpeed(int sp) {
    		bpm = sp;
    		updateTempo();
    }
    
    public int randomNos(int min, int max) {
    		double d = Math.random();
    		double a = d*(max-min);
    		int r = (int) Math.floor(a);
    		return r;
    }
    
    public void levelUp() {
    		ArrayList<Integer> choices = new ArrayList<Integer>();
    		for (int i = 1;i<7; i++) {
    			if (!done.contains(i)) {
    				choices.add(i);
    			}
    		}
    		
    		int c = choices.get(randomNos(0, choices.size() - 1));
    		done.add(c);
    		se.setTrackMute(c, false);
    		
    }
    
    public void reset() {
    		muteAll();
    }
    
    private void muteAll() {
	    	for (int i = 1; i<7; i++) {
	    		se.setTrackMute(i, true);
	    }
	    	done = new ArrayList<Integer>();
    }
    
    private void updateTempo() {
    		se.setTempoInBPM(bpm);
    }
}
