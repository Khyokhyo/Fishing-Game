import java.io.File;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * @Author: U Khyoi Nu
 * @Reg. No. : 2012331064
 * @Class: This class plays the background sound at the very beginning of the game.
 */

public class SoundThread implements Runnable
{
    /**
     * Constructor.
     * Initiates the Thread.
     */
    SoundThread()
    {
        Thread t = new Thread(this, "Sound Thread");
        t.setPriority(2);
        t.start();
    }
    
    /**
     * Main Run Method.
     * Plays the background sound.
     * Starts again if the clip stops playing.
     */
    public void run()
    {
            try 
            {
                 AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("Files/Sounds/Background.wav").getAbsoluteFile());
                 Clip clip = AudioSystem.getClip();
                 clip.open(audioInputStream);
                 clip.start();
                 while(clip.isOpen()==true)
                     clip.loop(1);
            }
            catch(Exception ex){}
    }
}
