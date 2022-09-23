package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import javax.sound.midi.*;

public class GUI extends JFrame {
    List<KeySplit> keySplits = new ArrayList<>();
    MidiDevice in;
    MidiDevice out;

    public GUI() {
        super("SoundCanvas");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // BorderLayout layout = new BorderLayout();
        GridLayout layout = new GridLayout(2,1);
        this.setLayout(layout);
        JPanel voices = new JPanel(new GridLayout(16,2));
        for (int i = 1 ; i <= 16; i++) {
            voices.add(new ChannelVoice(this, i));
        }
        JPanel midi = new JPanel(new GridLayout(2,4));
        List<String> outputs =
                Arrays.stream(MidiSystem.getMidiDeviceInfo())
                        .filter((info) -> {
                            try {
                                return MidiSystem.getMidiDevice(info) instanceof Synthesizer ||
                                        MidiSystem.getMidiDevice(info).getClass().getName().matches(".*MidiOut.*");
                            } catch (MidiUnavailableException e) {
                                return false;
                            }
                        })
                        .map(MidiDevice.Info::getDescription)
                        .collect(Collectors.toList());
        List<String> inputs =
                Arrays.stream(MidiSystem.getMidiDeviceInfo())
                        .filter((info) -> {
                            try {
                                return MidiSystem.getMidiDevice(info) instanceof Sequencer ||
                                        MidiSystem.getMidiDevice(info).getClass().getName().matches(".*MidiIn.*");
                            } catch (MidiUnavailableException e) {
                                return false;
                            }
                        })
                        .map(MidiDevice.Info::getDescription)
                        .collect(Collectors.toList());
        midi.add(new JLabel("Input"));
        JComboBox<String> inputDevices = new JComboBox<>(new Vector<>(inputs));
        midi.add(inputDevices);
        midi.add(new JLabel("Output"));
        JComboBox<String> outputDevices = new JComboBox<>(new Vector<>(outputs));
        midi.add(outputDevices);
        JButton inputConnect = new JButton("Connect");
        JButton inputDisconnect = new JButton("Disconnect");
        inputConnect.addActionListener(actionEvent ->
        {
            try {
                MidiDevice in =
                        MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[(inputDevices.getSelectedIndex())]);
                in.open();
                in.getTransmitter().setReceiver(new MidiReceiver(out));
                inputConnect.setEnabled(false);
                inputDisconnect.setEnabled(true);
            } catch (MidiUnavailableException e) {
                e.printStackTrace();
            }
        }
                );
        inputDisconnect.addActionListener(actionEvent ->
        {
            in.close();
            inputConnect.setEnabled(true);
            inputDisconnect.setEnabled(false);
        });
        inputDisconnect.setEnabled(false);
        JButton outputConnect = new JButton("Connect");
        JButton outputDisconnect = new JButton("Disconnect");
        outputConnect.addActionListener(actionEvent ->
                {
                    try {
                        out =
                                MidiSystem.getMidiDevice(MidiSystem.getMidiDeviceInfo()[(inputDevices.getSelectedIndex())]);
                        out.open();
                        outputConnect.setEnabled(false);
                        outputDisconnect.setEnabled(true);
                    } catch (MidiUnavailableException e) {
                        e.printStackTrace();
                    }
                }
        );
        outputDisconnect.addActionListener(actionEvent ->
                {
                    out.close();
                    outputConnect.setEnabled(true);
                    outputDisconnect.setEnabled(false);
                }

        );
        outputDisconnect.setEnabled(false);
        midi.add(inputConnect);
        midi.add(inputDisconnect);
        midi.add(outputConnect);
        midi.add(outputDisconnect);
        JPanel split = new JPanel();
        split.setLayout(new GridLayout(5,3));
        for (int i = 1 ; i <= 5 ; i++) {
            KeySplit ks = new KeySplit(i);
            keySplits.add(ks);
            split.add(ks);
        }
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(midi,BorderLayout.NORTH);
        bottom.add(split,BorderLayout.CENTER);
        bottom.add(new JPanel(), BorderLayout.SOUTH);
        this.add(voices);
        this.add(bottom);

    }
}
