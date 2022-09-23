package se.hackerbrau;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiDevice;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.ShortMessage;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

/***
 * Part of the frame which allows voice selection
 */
public class ChannelVoice extends JPanel {
    List<String> voiceNames = Arrays.asList(
            "Acou Piano1", "Acou Piano2", "Acou Piano3",
            "Elec Piano1", "Elec Piano2", "Elec Piano3", "Elec Piano4",
            "Honkytonk",
            "Elec Org 1", "Elec Org2 ", "Elec Org 3", "Elec Org 4",
            "Pipe Org 1", "Pipe Org 2", "Pipe Org 3",
            "Accordion",
            "Harpsi 1", "Harpsi 2", "Harpsi 3",
            "Clavi 1", "Clavi 2", "Clavi 3",
            "Celesta 1", "Celesta 2",
            "Syn Brass 1", "Syn Brass 2", "Syn Brass 3", "Syn Brass 4",
            "Syn Bass 1", "Syn Bass 2", "Syn Bass 3", "Syn Bass 4",
            "Fantasy",
            "Harmo Pan",
            "Chorale",
            "Glasses",
            "Soundtrack",
            "Atmosphere",
            "Warm Bell",
            "Funny Vox",
            "Echo Bell",
            "Ice Rain",
            "Oboe 2001",
            "Echo Pan",
            "Doctor Solo",
            "School Gaze",
            "Bellsinger",
            "Square Wave",
            "Str Sect 1", "Str Sect 2", "Str Sect 3",
            "Pizzicato",
            "Violin 1", "Violin 2",
            "Cello 1", "Cello 2",
            "Contrabass",
            "Harp 1", "Harp 2",
            "Guitar 1", "Guitar 2",
            "Elec Gtr 1", "Elec Gtr 2",
            "Sitar",
            "Acou Bass 1", "Acou Bass 2",
            "Elec Bass 1", "Elec Bass 2",
            "Slap Bass 1", "Slapp Bass 2",
            "Fretless 1", "Fretless 2",
            "Flute 1", "Flute 2",
            "Piccolo 1", "Piccolo 2",
            "Recorder",
            "Pan Pipes",
            "Sax 1", "Sax 2", "Sax 3", "Sax 4",
            "Clarinet 1", "Clarinet 2",
            "Oboe",
            "Engl Horn",
            "Bassoon",
            "Harmonica",
            "Trumpet 1", "Trumpet 2",
            "Trombone 1", "Trombone 2",
            "Fr Horn 1", "Fr Horn 2",
            "Tuba",
            "Brs Sect 1", "Brs Sect 2",
            "Vibe 1", "Vibe 2",
            "Syn Mallet",
            "Windbell",
            "Glock",
            "Tube Bell",
            "Zylophone",
            "Marimba",
            "Koto",
            "Sho",
            "Shakuhachi",
            "Whistle 1", "Whistle 2",
            "Bottleblow",
            "Breathpipe",
            "Timpani",
            "Melodic Tom",
            "Deep Snare",
            "Elec Perc 1", "Elec Perc 2",
            "Taiko",
            "Taiko Rim",
            "Cymbal",
            "Castanets",
            "Triangle",
            "Orche Hit",
            "Telephone",
            "Bird Tweet",
            "OneNote Jam",
            "Water Bell",
            "Jungle Tune");
    public ChannelVoice (GUI parent, int n) {
        super(new GridLayout());
        this.add(new JLabel("Channel " + n));
        JComboBox<String> voices = new JComboBox<>(new Vector<>(voiceNames));
        voices.addActionListener((e) -> {
            Logger.getGlobal().info(parent.out.toString());
            if (parent.out != null && parent.out.isOpen()) {
                try {
                    ShortMessage myMsg = new ShortMessage();
                    myMsg.setMessage(ShortMessage.PROGRAM_CHANGE,n-1,voices.getSelectedIndex());
                    Logger.getGlobal().info(myMsg.toString());
                    parent.out.getReceiver().send(myMsg, -1);
                } catch (MidiUnavailableException | InvalidMidiDataException ex) {
                    ex.printStackTrace();
                }
            }
        });
        this.add(voices);
    }
}