package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

/***
 * Part of the frame which allows voice selection
 */
public class ChannelVoice extends JPanel {
    List<String> mt32voiceNames = Arrays.asList(
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
    List<Tuple3<Integer,String, Integer>> voiceTuples =
            Arrays.asList(// Piano
                    new Tuple3<>(0,"Piano 1", 0), // new Tuple3<>(0,"Piano 1w", 8), new Tuple3<>(0, "Piano 1d", 16),
                    new Tuple3<>(1, "Piano 2", 0), // new Tuple3<>(1, "Piano 2w", 8),
                    new Tuple3<>(2, "Piano 3", 0), // new Tuple3<>(2, "Piano 3w", 8),
                    new Tuple3<>(3, "Honky-tonk", 0), // new Tuple3<>(3, "Honky-tonk w", 8),
                    new Tuple3<>(4, "E.Piano 1", 0), // new Tuple3<>(4, "Detuned EP1", 8), new Tuple3<>(4, "E.Piano 1v" , 16), new Tuple3<>(4, "60s E.Piano", 24),
                    new Tuple3<>(5, "E-Piano 2", 0), // new Tuple3<>(5, "Detuned EP2", 8), new Tuple3<>(5, "E.Piano 2v", 16),
                    new Tuple3<>(6, "Harpsichord", 0), // new Tuple3<>(6, "Coupled Hps.", 8),
                    new Tuple3<>(7, "Clav.", 0),
                    // Chromatic percussion
                    new Tuple3<>(8, "Celesta", 0),
                    new Tuple3<>(9, "Glockenspiel", 0),
                    new Tuple3<>(10, "Music Box", 0),
                    new Tuple3<>(11, "Vibraphone", 0), // new Tuple3<>(11, "Vib.w", 8),
                    new Tuple3<>(12, "Marimba", 0), // new Tuple3<>(12, "Marimba w", 8),
                    new Tuple3<>(13, "Xylophone", 0),
                    new Tuple3<>(14, "Tubular-bell", 0), // new Tuple3<>(14, "Church Bell", 8), new Tuple3<>(14, "Carillon", 9),
                    new Tuple3<>(15, "Santur", 0),
                    // Organ
                    new Tuple3<>(16, "Organ 1", 0), // new Tuple3<>(16, "Detuned Or.1", 8), new Tuple3<>(16, "60s Organ1", 16), new Tuple3<>(16, "Organ 4", 32),
                    new Tuple3<>(17,"Organ 2", 0), // new Tuple3<>(17,"Detuned Or.2", 8), new Tuple3<>(17,"Organ 5", 32),
                    new Tuple3<>(18,"Organ 3", 0),
                    new Tuple3<>(19,"Church Org.1",0), // new Tuple3<>(19,"Church Org.2", 8), new Tuple3<>(19,"Church Org.3", 16),
                    new Tuple3<>(20,"Reed Organ", 0),
                    new Tuple3<>(21,"Accordion Fr", 0), //new Tuple3<>(21,"Accordion It", 8),
                    new Tuple3<>(22,"Harmonica", 0),
                    new Tuple3<>(23, "Bandeon", 0),
                    // Guitar
                    new Tuple3<>(24, "Nylon-str. Gt.", 0), // new Tuple3<>(24,"Ukulele", 8), new Tuple3<>(24,"Nylon Gt.o", 16), new Tuple3<>(24,"Nylon Gt.2", 32),
                    new Tuple3<>(25,"Steel-str. Gt.", 0), // new Tuple3<>(25,"12-str.Gt.", 8), new Tuple3<>(25,"Mandolin", 16),
                    new Tuple3<>(26,"Jazz Gt.", 0), // new Tuple3<>(26,"Hawaiian Gt.", 8),
                    new Tuple3<>(27,"Clean Gt.", 0), // new Tuple3<>(27,"Chorus Gt.", 8),
                    new Tuple3<>(28,"Muted Gt.", 0), // new Tuple3<>(28,"Funk Gt.", 8), new Tuple3<>(28,"Funk Gt.2",16),
                    new Tuple3<>(29,"OverdriveGt.", 0),
                    new Tuple3<>(30,"Distortion Gt.", 0), // new Tuple3<>(30,"Feeback Gt.", 8),
                    new Tuple3<>(31,"Gt.Harmonics",0), // new Tuple3<>(31,"Gt.Feedback", 8),
                    // Bass
                    new Tuple3<>(32,"Acoustic Bs.",0),
                    new Tuple3<>(33,"Fingered Bs.",0),
                    new Tuple3<>(34,"Picked Bs.",0),
                    new Tuple3<>(35,"Fretless Bs.",0),
                    new Tuple3<>(36,"Slap Bass 1",0),
                    new Tuple3<>(37,"Slapp Bass 2",0),
                    new Tuple3<>(38,"Synth Bass 1",0), // new Tuple3<>(38,"Synth Bass101",1), new Tuple3<>(38,"Synth Bass 3",8),
                    new Tuple3<>(39,"Synth Bass 2",0), // new Tuple3<>(39,"Synth Bass 4",8), new Tuple3<>(39,"Rubber Bass",16),
                    // Strings / orchestra
                    new Tuple3<>(40,"Violin",0), // new Tuple3<>(40,"Slow Violin",8),
                    new Tuple3<>(41,"Viola",0),
                    new Tuple3<>(42,"Cello",0),
                    new Tuple3<>(43,"Contrabass",0),
                    new Tuple3<>(44,"Tremolo Str",0),
                    new Tuple3<>(45,"Pizzicato Str",0),
                    new Tuple3<>(46,"Harp",0),
                    new Tuple3<>(47,"Timpani",0),
                    // Ensemble
                    new Tuple3<>(48,"Strings",0), // new Tuple3<>(48,"Orchestra",0),
                    new Tuple3<>(49,"SlowStrings",0),
                    new Tuple3<>(50,"Syn.Strings1",0), // new Tuple3<>(50,"Syn.Strings3",8),
                    new Tuple3<>(51,"Syn.Strings2",0),
                    new Tuple3<>(52,"Choir Aah",0), // new Tuple3<>(52,"Choir Aahs2",32),
                    new Tuple3<>(53,"Voice Oohs",0),
                    new Tuple3<>(54,"SynVox",0),
                    new Tuple3<>(55,"Orchestra Hit",0),
                    // Brass
                    new Tuple3<>(56,"Trumpet",0),
                    new Tuple3<>(57,"Trombone",0), // new Tuple3<>(57,"Trombone 2",1),
                    new Tuple3<>(58,"Tuba",0),
                    new Tuple3<>(59,"Muted Trumpet",0),
                    new Tuple3<>(60,"French Horn",0), // new Tuple3<>(60,"Fr.Horn 2",1),
                    new Tuple3<>(61,"Brass 1",0), // new Tuple3<>(61,"Brass 2",8),
                    new Tuple3<>(62,"Synth Brass 1",0), // new Tuple3<>(62,"Synth Brass 3",8), new Tuple3<>(62,"Analog Brass",16),
                    new Tuple3<>(63,"Synth Brass 2",0) // new Tuple3<>(63 ,"Synth Brass 4",8), new Tuple3<>(63, "Analog Brass2",16)
            );
    List<String> voiceNames = voiceTuples.stream().map(Tuple3::getSnd).collect(Collectors.toList());
    Map<String,Integer> voiceNumber =
            Map.ofEntries(voiceTuples.stream().map((t) -> Map.entry(t.snd,t.fst)).collect(Collectors.toList()).toArray(new Map.Entry[0]));
    Map<String,Integer> voiceCC =
            Map.ofEntries(voiceTuples.stream().map((t) -> Map.entry(t.snd,t.srd)).collect(Collectors.toList()).toArray(new Map.Entry[0]));

    public ChannelVoice (GUI parent, int n) {
        super(new GridLayout());
        this.add(new JLabel("Channel " + n));
        JComboBox<String> voices =
                new JComboBox<>(new Vector<>(voiceNames));

        voices.addItemListener((e) -> {
            parent.updateVoice(n-1,
                    voiceNumber.get(voiceNames.get(voices.getSelectedIndex())),
                    voiceCC.get(voiceNames.get(voices.getSelectedIndex())));
        });
        this.add(voices);
    }
}