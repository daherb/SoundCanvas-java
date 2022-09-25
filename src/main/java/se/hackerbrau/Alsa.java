package se.hackerbrau;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.ShortMessage;
import java.io.*;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/***
 * Accessing alsa using amidi
 */
public class Alsa {
    enum Direction { INPUT, OUTPUT, BOTH }

    public static class AlsaInterface extends Thread {
        String inputDevice;
        String outputDevice;
        Process inputProcess;
        Process outputProcess;
        boolean hasInput = false;
        boolean hasOutput = false;
        boolean isSetup = false;
        List<KeySplit> keySplits = new ArrayList<>();

        public AlsaInterface() {

        }

        public void setInput(String device) {
            if (getInputDevices().contains(device)) {
                try {
                    Map<String,String> devices = getDeviceMap(Direction.INPUT);
                    for (String d : devices.keySet()) {
                        if (devices.get(d).equalsIgnoreCase(device)) {
                            inputDevice = d;
                            hasInput = true;
                            isSetup = hasOutput;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void removeInput() {
            hasInput = false;
            isSetup = false;
        }

        public void setOutput(String device) {
            if (getOutputDevices().contains(device)) {
                try {
                    Map<String,String> devices = getDeviceMap(Direction.OUTPUT);
                    for (String d : devices.keySet()) {
                        if (devices.get(d).equalsIgnoreCase(device)) {
                            outputDevice = d;
                            hasOutput = true;
                            isSetup = hasInput;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void removeOutput() {
            hasOutput = false;
            isSetup = false;
        }

        public void setKeySplits(List<KeySplit> splits) {
            keySplits = splits;
        }

        public void changeVoice(int channel, int voice, int cc) {
            try {
//                ShortMessage ccMessage = new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 0, cc);
//                sendMessage(ccMessage);
//                flushMessages();
//                ccMessage = new ShortMessage(ShortMessage.CONTROL_CHANGE, channel, 32, 0);
//                sendMessage(ccMessage);
//                flushMessages();
                ShortMessage updateMessage = new ShortMessage(ShortMessage.PROGRAM_CHANGE, channel, voice,0);
                sendMessage(updateMessage);
                flushMessages();
            } catch (InvalidMidiDataException | IOException e) {
                e.printStackTrace();
            }
        }

        public boolean isSetup() {
            return isSetup;
        }

        @Override
        public void run() {
            if (isSetup) {
                Logger.getGlobal().info("Starting thread");
                try {
                    inputProcess = Runtime.getRuntime().exec(new String[]{"amidicat", "--port", inputDevice});
                    outputProcess = Runtime.getRuntime().exec(new String[]{"amidicat", "--port", outputDevice});
                    // BufferedReader br = new BufferedReader(new InputStreamReader(inputProcess.getInputStream()));
                    InputStream is = inputProcess.getInputStream();
                    while (true) {
                        byte[] buffer = new byte[3];
                        int bRead = is.read(buffer,0,3);
                        if (bRead == 3) {
                            ShortMessage inMessage = decodeMessage(buffer);
                            logMessage(inMessage,"Receive ");
                            if (inMessage.getCommand() == ShortMessage.NOTE_ON || inMessage.getCommand() == ShortMessage.NOTE_OFF) {
                                ShortMessage outMessage = new ShortMessage();
                                for (KeySplit ks : keySplits) {
                                    if (inMessage.getData1() >= ks.getStartNote() && inMessage.getData1() <= ks.getEndNote()) {
                                        for (Integer channel : ks.getChannels()) {
                                            outMessage.setMessage(inMessage.getCommand(),channel,inMessage.getData1() + ks.getOffset()
                                                    ,inMessage.getData2());
                                            sendMessage(outMessage);
                                            flushMessages();
                                        }
                                    }
                                }
                            }
                            // os.write(buffer,0,bRead);
                            // os.flush();
                        }

                    }
                } catch (IOException | InvalidMidiDataException e) {
                    e.printStackTrace();
                }
            }
        }

        private void sendMessage(ShortMessage sm) throws IOException {
            byte[] buffer;
            if (sm.getCommand() == ShortMessage.PROGRAM_CHANGE) {
                buffer = new byte[2];
                buffer[0] = Integer.valueOf(sm.getChannel() + sm.getCommand()).byteValue();
                buffer[1] = Integer.valueOf(sm.getData1()).byteValue();
                Logger.getGlobal().info(String.format("%x %x",buffer[0],buffer[1]));
            }
            else {
                buffer = new byte[3];
                buffer[0] = Integer.valueOf(sm.getChannel() + sm.getCommand()).byteValue();
                buffer[1] = Integer.valueOf(sm.getData1()).byteValue();
                buffer[2] = Integer.valueOf(sm.getData2()).byteValue();
                Logger.getGlobal().info(String.format("%x %x %x",buffer[0],buffer[1],buffer[2]));
            }
            OutputStream os = outputProcess.getOutputStream();
            os.write(buffer);
            os.flush();
        }

        private void flushMessages() throws IOException {
            OutputStream os = outputProcess.getOutputStream();
            os.flush();
        }

        private void logMessage(ShortMessage sm, String prefix) {
            Logger.getGlobal().info(String.format("%smessage Channel: %d Command: %x Data1: %x Data2: %x", prefix,
                    sm.getChannel(), sm.getCommand(), sm.getData1(), sm.getData2()));
        }
    }

    public static Map<String,String> getDeviceMap() throws IOException {
        return getDeviceMap(Direction.BOTH);
    }

    public static List<String> getInputDevices() {
        return getDevices(Direction.INPUT);
    }

    public static List<String> getOutputDevices() {
        return getDevices(Direction.OUTPUT);
    }

    public static List<String> getDevices(Direction direction) {
        List<String> devices = new ArrayList<>();
        try {
            Map<String,String> deviceMap = getDeviceMap(direction);
            devices.addAll(deviceMap.keySet().stream().map(deviceMap::get).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return devices;
    }

    static Map<String,String> getDeviceMap(Direction dir) throws IOException {
        Map<String,String> devices = new HashMap<>();
        Runtime rt = Runtime.getRuntime();
        Process amidi = rt.exec(new String[]{"amidicat", "--list"});
        StringWriter sw = new StringWriter();
        new InputStreamReader(amidi.getInputStream()).transferTo(sw);
        for (String line : sw.toString().trim().split("\n")) {
            List<String> columns = Arrays.asList(line.split("\\s{2,}"));
            if (!columns.get(0).equalsIgnoreCase("Port")) {
                if (
                        dir == Direction.INPUT && columns.get(3).contains("R") ||
                                dir == Direction.OUTPUT && columns.get(3).contains("W") ||
                                dir == Direction.BOTH
                )
                    devices.put(columns.get(0), columns.get(1));
            }
        }
        return devices;
    }

    private static ShortMessage decodeMessage(byte[] buffer) throws InvalidMidiDataException {
        return new ShortMessage(Byte.toUnsignedInt(buffer[0]),Byte.toUnsignedInt(buffer[1]),
                Byte.toUnsignedInt(buffer[2]));
    }

}
