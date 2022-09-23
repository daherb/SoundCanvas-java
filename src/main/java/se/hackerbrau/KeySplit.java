package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/***
 * Part of the frame which allows key splitting and channel selection
 */
public class KeySplit extends JPanel {
    List<String> notes = Arrays.asList("c","d","e","f","g","a","b");
    List<String> allNotes = new ArrayList<>();
    List<JCheckBox> checkBoxes = new ArrayList<>();
    boolean[] checked = new boolean[16];
    JComboBox<String> startList;
    JComboBox<String> endList;
    public KeySplit(int num) {
        this.setLayout(new GridLayout(1,20));
        for (int i = 0 ; i <= 6 ; i++) {
            int finalI = i;
            allNotes.addAll(notes.stream().map((n) -> n + finalI).collect(Collectors.toList()));
        }
        this.add(new JLabel("Split " + num));
        startList = new JComboBox<>(new Vector<>(allNotes));
        endList = new JComboBox<>(new Vector<>(allNotes));
        endList.setSelectedItem("b6");
        this.add(startList);
        this.add(endList);
        this.add(new JLabel("Channels"));
        for (int i = 1; i <= 16; i++) {
            JCheckBox box = new JCheckBox(String.valueOf(i));
            if (num == 1 && i == 1) {
                checked[0] = true;
                box.setSelected(true);
            }
            int finalI = i-1;
            box.addActionListener((e) -> {
                checked[finalI] = !(checked[finalI]);
                Logger.getGlobal().info(getChannels().toString());
            });
            this.add(box);
            checkBoxes.add(box);
        }
    }
    public int getStartNote() {
        return 24 + startList.getSelectedIndex();
    }
    public int getEndNote() {
        return 24 + endList.getSelectedIndex();
    }

    public List<Integer> getChannels() {
        List<Integer> channels = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (checked[i])
                channels.add(i);
        }
        return channels;
    }
}