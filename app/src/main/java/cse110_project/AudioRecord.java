package cse110_project;

import java.io.*;
import javax.sound.sampled.*;

import javafx.scene.control.Label;

public class AudioRecord {
    private AudioFormat audioFormat;
    private TargetDataLine targetDataLine;

    private Label recordingLabel;
    String defaultLabelStyle = "-fx-font: 13 arial; -fx-pref-width: 175px; -fx-pref-height: 50px; -fx-text-fill: red; visibility: hidden";

    AudioRecord(Label rLabel) {
        audioFormat = getAudioFormat();

        recordingLabel = rLabel;
        recordingLabel.setStyle(defaultLabelStyle);
    }

    private AudioFormat getAudioFormat() {
        // the number of samples of audio per second.
        // 44100 represents the typical sample rate for CD-quality audio.
        float sampleRate = 44100;

        // the number of bits in each sample of a sound that has been digitized.
        int sampleSizeInBits = 16;

        // the number of audio channels in this format (1 for mono, 2 for stereo).
        int channels = 1;

        // whether the data is signed or unsigned.
        boolean signed = true;

        // whether the audio data is stored in big-endian or little-endian order.
        boolean bigEndian = false;

        return new AudioFormat(
                sampleRate,
                sampleSizeInBits,
                channels,
                signed,
                bigEndian);
    }

    public void startRecording() {
        Thread t = new Thread(
                new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();

                            int preference = -1;
                            System.out.println("Available recording mixers:");
                            for (int i = 0; i < mixerInfos.length; i++) {
                                Mixer mixer = AudioSystem.getMixer(mixerInfos[i]);
                                Line.Info[] lineInfos = mixer.getTargetLineInfo();
                                for (Line.Info lineInfo : lineInfos) {
                                    if (lineInfo.getLineClass().equals(TargetDataLine.class)) {
                                        if (mixerInfos[i].getName().contains("Microphone Array")) {
                                            preference = i;
                                            System.out.println("Preferring " + mixerInfos[i].getName());
                                        }
                                        System.out.println(i + ": " + mixerInfos[i].getName());
                                        break;
                                    }
                                }
                            }

                            boolean wentWithPreference = false;
                            if (preference >= 0) {
                                Mixer mixer = AudioSystem.getMixer(mixerInfos[preference]);
                                Line.Info[] lineInfos = mixer.getTargetLineInfo();
                                if (lineInfos.length >= 1 && lineInfos[0].getLineClass().equals(TargetDataLine.class)) {
                                    targetDataLine = (TargetDataLine) mixer.getLine(lineInfos[0]);
                                    System.out.println("Went with preference");
                                    wentWithPreference = true;
                                }
                            }
                            if (!wentWithPreference) {
                                // the format of the TargetDataLine
                                DataLine.Info dataLineInfo = new DataLine.Info(
                                        TargetDataLine.class,
                                        audioFormat);
                                // the TargetDataLine used to capture audio data from the microphone
                                targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);
                            }

                            targetDataLine.open(audioFormat);
                            targetDataLine.start();
                            recordingLabel.setVisible(true);

                            // the AudioInputStream that will be used to write the audio data to a file
                            AudioInputStream audioInputStream = new AudioInputStream(
                                    targetDataLine);

                            // the file that will contain the audio data
                            File audioFile = new File("recording.wav");
                            AudioSystem.write(
                                    audioInputStream,
                                    AudioFileFormat.Type.WAVE,
                                    audioFile);
                            recordingLabel.setVisible(false);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }
                });
        t.start();

    }

    public void stopRecording() {
        targetDataLine.stop();
        targetDataLine.close();
    }
}
