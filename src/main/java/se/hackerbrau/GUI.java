package se.hackerbrau;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;

public class GUI extends JFrame {
    List<KeySplit> keySplits = new ArrayList<>();
    Alsa.AlsaInterface alsa = new Alsa.AlsaInterface();
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
        List<String> inputs = Alsa.getInputDevices();
        List<String> outputs = Alsa.getOutputDevices();
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
                alsa.setInput(inputDevices.getItemAt(inputDevices.getSelectedIndex()));
                // Try to start thread
                if (alsa.isSetup)
                    alsa.start();
                inputConnect.setEnabled(false);
                inputDisconnect.setEnabled(true);
        }
                );
        inputDisconnect.addActionListener(actionEvent ->
        {
            //in.close();
            alsa = new Alsa.AlsaInterface();
            alsa.removeInput();
            inputConnect.setEnabled(true);
            inputDisconnect.setEnabled(false);
        });
        inputDisconnect.setEnabled(false);
        JButton outputConnect = new JButton("Connect");
        JButton outputDisconnect = new JButton("Disconnect");
        outputConnect.addActionListener(actionEvent ->
                {
                        alsa.setOutput(outputDevices.getItemAt(outputDevices.getSelectedIndex()));
                        // Try to start thread
                        if (alsa.isSetup)
                            alsa.start();
                        outputConnect.setEnabled(false);
                        outputDisconnect.setEnabled(true);
                }
        );
        outputDisconnect.addActionListener(actionEvent ->
                {
                    // out.close();
                    alsa = new Alsa.AlsaInterface();
                    alsa.removeOutput();
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
            KeySplit ks = new KeySplit(this, i);
            keySplits.add(ks);
            split.add(ks);
        }
        updateSplits();
        JPanel bottom = new JPanel();
        bottom.setLayout(new BorderLayout());
        bottom.add(midi,BorderLayout.NORTH);
        bottom.add(split,BorderLayout.CENTER);
        bottom.add(new JPanel(), BorderLayout.SOUTH);
        this.add(voices);
        this.add(bottom);

    }

    protected void updateSplits() {
        alsa.setKeySplits(keySplits);
    }

    public void updateVoice(int channel, int voice, int cc) {
        alsa.changeVoice(channel,voice,cc);
    }
}
