package fish;

import java.applet.Applet;
import java.applet.AudioClip;
import java.io.File;
import java.net.URL;

import javax.swing.ImageIcon;
import javax.swing.JFrame;

import java.io.*;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.FloatControl;

public class FishJFrame extends JFrame {
	static volatile boolean stop=false;
	FishJFrame() {
		this.setTitle("捕鱼达人");
		this.setBounds(100, 100, 800, 480);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setIconImage(new ImageIcon("images/icon.jpg").getImage());
		this.setResizable(false);
		FishJPanel fjp = new FishJPanel();
		fjp.startFish();
		this.add(fjp);
		this.setVisible(true);
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new FishJFrame();
		//AudioPlayDemo a = new AudioPlayDemo();
		//a.APlay();
		while (true){
			Play("sounds/bmg.wav");
		}
	}

	public static void Play(String fileurl) {
		try {
            AudioInputStream ais = AudioSystem.getAudioInputStream(new File(fileurl));
            AudioFormat aif = ais.getFormat();//System.out.println(aif);
            final SourceDataLine sdl;
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, aif);
            sdl = (SourceDataLine) AudioSystem.getLine(info);
            sdl.open(aif);
            sdl.start();
            FloatControl fc=(FloatControl)sdl.getControl(FloatControl.Type.MASTER_GAIN);
            //value可以用来设置音量，从0-2.0
            double value=2;
            float dB = (float)
                  (Math.log(value==0.0?0.0001:value)/Math.log(10.0)*20.0);
            fc.setValue(dB);
            int nByte = 0;
            int writeByte = 0;
            final int SIZE=1024*64;
            byte[] buffer = new byte[SIZE];
            while (nByte != -1) {
                nByte = ais.read(buffer, 0, SIZE);
                sdl.write(buffer, 0, nByte);
            }
            sdl.stop();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class AudioPlayDemo extends Applet {
	AudioClip audioClip;
	
	public void APlay() {
		try{
			URL cb;
			File f = new File("sounds/bmg.wav");
			cb = f.toURL();
			audioClip = Applet.newAudioClip(cb);
			audioClip.loop();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
