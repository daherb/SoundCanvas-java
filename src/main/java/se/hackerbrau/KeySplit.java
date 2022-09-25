package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.stream.Collectors;

/***
 * Part of the frame which allows key splitting and channel selection
 */
public class KeySplit extends JPanel {
    List<String> notes = Arrays.asList("c","c#","d","d#","e","f","f#","g","g#","a","a#/bb","b");
    List<String> allNotes = new ArrayList<>();
    List<JCheckBox> checkBoxes = new ArrayList<>();
    boolean[] checked = new boolean[16];
    JComboBox<String> startList;
    JComboBox<String> endList;
    JSpinner offset;
    public KeySplit(GUI parent, int num) {
        this.setLayout(new GridLayout(1,22));
        for (int i = 0 ; i <= 8 ; i++) {
            int finalI = i;
            allNotes.addAll(notes.stream().map((n) -> n + finalI).collect(Collectors.toList()));
        }
        allNotes.add("c9");
        this.add(new JLabel("Split " + num));
        startList = new JComboBox<>(new Vector<>(allNotes));
        endList = new JComboBox<>(new Vector<>(allNotes));
        endList.setSelectedItem("c9");
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
                // Set the value in checked by flipping it logically
                checked[finalI] = !(checked[finalI]);
                parent.updateSplits();
            });
            this.add(box);
            checkBoxes.add(box);
        }
        this.add(new JLabel("Offset"));
        offset = new JSpinner();
        offset.addChangeListener((c) -> ((GUI) this.getParent()).updateSplits());
        offset.setModel(new SpinnerNumberModel(0,-16,16,1));
        this.add(offset);
    }
    public int getStartNote() {
        return 12 + startList.getSelectedIndex();
    }
    public int getEndNote() {
        return 12 + endList.getSelectedIndex();
    }

    public List<Integer> getChannels() {
        List<Integer> channels = new ArrayList<>();
        for (int i = 0; i < 16; i++) {
            if (checked[i])
                channels.add(i);
        }
        return channels;
    }

    public int getOffset() {
        return Integer.parseInt(String.valueOf(offset.getValue()));
    }
}