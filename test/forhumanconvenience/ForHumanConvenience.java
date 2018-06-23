package forhumanconvenience;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
import java.awt.*;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * ATTRIBUTIONS:
 * Audio clips used here were downloaded from various users on freesound.org
 *
 * Success Sound "Jazzy Chords" by user NenadSimic (https://freesound.org/people/NenadSimic/) found at https://freesound.org/people/NenadSimic/sounds/150879/
 * Start Sound "lose bass" by user nicog (https://freesound.org/people/nicog/) found at https://freesound.org/people/nicog/sounds/432308/
 * Fail Sound "Error_01" by user https://freesound.org/people/distillerystudio/ found at https://freesound.org/people/distillerystudio/sounds/327738/
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
    private static final String SINGLE_TEST_SUCCESS = "";

    private static JFrame statusVisualAidFrame;
    private static Boolean jframeIsInitialized;

    private static void playSound(String soundfileUrlStr){
        try{
            AudioClip clip = Applet.newAudioClip(new URL(soundfileUrlStr));
            clip.play();
        } catch (MalformedURLException e){
            System.err.println("Could not play sound due to Malformed URL: " + soundfileUrlStr);
        }

        //Let the sound play to completion
        try{Thread.sleep(3000);} catch (Exception e){};
    }

    public static void playAfterAllTestFailSound() {}

    public static void playBeforeAllTestStartSound(){ playSound(BEFORE_ALL_TESTS_START_SOUND_URL_STR); }

    public static void playSingleTestEndSound(){ playSound(SINGLE_TEST_FINISHED_SOUND_URL_STR); }

    public static void playAfterAllTestSuccessSound(){ playSound(AFTER_ALL_TESTS_SUCCESS_SOUND_URL_STR); }

    public static void showRunningVisualAid(){

    }
    public static void initJframe(String windowName){
        statusVisualAidFrame = new JFrame(windowName);
        statusVisualAidFrame.setSize(200, 200);
        statusVisualAidFrame.getContentPane().setBackground(Color.GREEN);
        statusVisualAidFrame.setVisible(true);
        jframeIsInitialized = Boolean.TRUE;
    }
    
    public static void showFailVisualAid(){
        statusVisualAidFrame.getContentPane().setBackground(Color.RED);
    }

}
