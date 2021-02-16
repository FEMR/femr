package forhumanconvenience;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ASSET ATTRIBUTIONS:
 * Audio clip(s) used here were downloaded from various users on freesound.org
 * Graphic(s) used here downloaded from thenounproject.com
 *
 * Success Sound "Jazzy Chords" by user NenadSimic (https://freesound.org/people/NenadSimic/) found at https://freesound.org/people/NenadSimic/sounds/150879/
 * Start Sound "lose bass" by user nicog (https://freesound.org/people/nicog/) found at https://freesound.org/people/nicog/sounds/432308/
 * Fail Sound "Error_01" by user https://freesound.org/people/distillerystudio/ found at https://freesound.org/people/distillerystudio/sounds/327738/
 * Checkered Finish Flags: "checkered flag" by Kangrif from the Noun Project found at https://thenounproject.com/search/?q=finish%20flag&i=1423984
 */

/**
 * CLASS USE CASE/MOTIVATION:
 * Running tests with `sbt clean compile test` takes about 5 minutes or so.
 * This class is here to provide methods to play sound clips for you to know
 * when your tests actually start, when they fail, and when they end with success.
 * use these methods in your functional tests.
 */
public class ForHumanConvenience {
    //Sounds will not play if these are not absolute (fully qualified) filepaths.
    private static final String AFTER_ALL_TESTS_SUCCESS_SOUND_URL_STR = "file://" + new File(".").getAbsolutePath() + "/test/forhumanconvenience/forhumanconvenienceassets/150879__nenadsimic__jazzy-chords.wav";
    private static final String SINGLE_TEST_FINISHED_SOUND_URL_STR = "file://" + new File(".").getAbsolutePath()+ "/test/forhumanconvenience/forhumanconvenienceassets/327738__distillerystudio__error-01.wav";
    private static final String BEFORE_ALL_TESTS_START_SOUND_URL_STR = "file://" + new File(".").getAbsolutePath() + "/test/forhumanconvenience/forhumanconvenienceassets/432308__nicog__lose-bass.wav";
    private static final String CHECKERED_FINISH_FLAG_IMAGE_PATH = new File(".").getAbsolutePath() + "/test/forhumanconvenience/forhumanconvenienceassets/noun_1423984_cc.png";

    private static final int FRAME_WIDTH = 200;
    private static final int FRAME_HEIGHT = 200;
    private static JFrame statusVisualAidFrame;

    private static Boolean jframeIsInitialized;

    private static void wait(int millis){
        try{ Thread.sleep(millis); } catch (InterruptedException e) {}
    }

    private static void playSound(String soundfileUrlStr){
        try{
            AudioClip clip = Applet.newAudioClip(new URL(soundfileUrlStr));
            clip.play();
        } catch (MalformedURLException e){
            System.err.println("Could not play sound due to Malformed URL: " + soundfileUrlStr);
        }

        //Let the sound play to completion
        wait(3000);
    }

    public static void playAfterAllTestFailSound() {}

    public static void playBeforeAllTestStartSound(){ playSound(BEFORE_ALL_TESTS_START_SOUND_URL_STR); }

    public static void playSingleTestEndSound(){ playSound(SINGLE_TEST_FINISHED_SOUND_URL_STR); }

    public static void playAfterAllTestSuccessSound(){ playSound(AFTER_ALL_TESTS_SUCCESS_SOUND_URL_STR); }

    public static void showRunningVisualAid(){

    }
    public static void initJframe(String windowName){
        statusVisualAidFrame = new JFrame(windowName);
        statusVisualAidFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        statusVisualAidFrame.setSize(FRAME_WIDTH, FRAME_WIDTH);
        statusVisualAidFrame.getContentPane().setBackground(Color.GREEN);
        statusVisualAidFrame.setVisible(true);
        jframeIsInitialized = Boolean.TRUE;
    }
    
    public static void showFailVisualAid(){
        statusVisualAidFrame.toFront();
        statusVisualAidFrame.getContentPane().setBackground(Color.RED);
    }

    public static void showTestFinished(){

        ImageIcon finishIcon = new ImageIcon(
                new ImageIcon(CHECKERED_FINISH_FLAG_IMAGE_PATH)
                        .getImage()
                        .getScaledInstance(FRAME_WIDTH, FRAME_HEIGHT, Image.SCALE_DEFAULT)
        );

        statusVisualAidFrame.add(new JLabel(finishIcon));
        //make refresh jframe to show checkered flag
        statusVisualAidFrame.revalidate();
        statusVisualAidFrame.repaint();

        statusVisualAidFrame.toFront();
        wait(10000);
    }
}
