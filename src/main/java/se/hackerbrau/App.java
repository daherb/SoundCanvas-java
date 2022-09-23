package se.hackerbrau;

import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;
import java.util.Arrays;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        GUI gui = new GUI();
        gui.pack();
        gui.setSize(gui.getPreferredSize());
        gui.setVisible(true);
    }
}
