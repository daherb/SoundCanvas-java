package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import java.util.stream.Collectors;

/***
 * Part of the frame which allows voice selection
 * TODO use abstract to handle both drums and regular voices
 */
public class DrumVoice extends JPanel {
    List<Tuple3<Integer,String, Integer>> voiceTuples =
            Arrays.asList(// Standard
                    new Tuple3<>(1,"STANDARD Set",0),
                    new Tuple3<>(9,"ROOM Set",0),
                    new Tuple3<>(17,"POWER Set",0),
                    new Tuple3<>(25,"ELECTRONIC Set",0),
                    new Tuple3<>(26,"TR-808 Set",0),
                    new Tuple3<>(33,"JAZZ Set",0),
                    new Tuple3<>(41,"BRUSH Set",0),
                    new Tuple3<>(49,"ORCHESTRA Set",0)
            );
    List<String> voiceNames = voiceTuples.stream().map(Tuple3::getSnd).collect(Collectors.toList());
    Map<String,Integer> voiceNumber =
            Map.ofEntries(voiceTuples.stream().map((t) -> Map.entry(t.snd,t.fst)).collect(Collectors.toList()).toArray(new Map.Entry[0]));
    Map<String,Integer> voiceCC =
            Map.ofEntries(voiceTuples.stream().map((t) -> Map.entry(t.snd,t.srd)).collect(Collectors.toList()).toArray(new Map.Entry[0]));

    public DrumVoice(GUI parent, int n) {
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