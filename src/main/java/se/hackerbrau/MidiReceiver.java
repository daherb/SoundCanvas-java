package se.hackerbrau;

import javax.sound.midi.*;
import java.util.logging.Logger;

public class MidiReceiver implements Receiver {

    MidiDevice out;
    public MidiReceiver(MidiDevice out) {
        this.out = out;
    }
    @Override
    public void send(MidiMessage midiMessage, long l) {
        System.out.println(".");
        if (out != null) {
            try {
                out.getReceiver().send(midiMessage,l);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
        if (midiMessage instanceof ShortMessage) {
            Logger.getGlobal().info("Short message: " + midiMessage);
        }
        else if (midiMessage instanceof MetaMessage)
            Logger.getGlobal().info("Meta message: " + midiMessage);
        else if (midiMessage instanceof SysexMessage)
            Logger.getGlobal().info("Sysex message: " + midiMessage);
    }

    @Override
    public void close() {
        // Nothing to do here.
    }
}
