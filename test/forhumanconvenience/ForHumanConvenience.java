package forhumanconvenience;

import javax.swing.*;
import java.applet.Applet;
import java.applet.AudioClip;
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
 * CLASS USE CASE:
 * Running tests with `sbt clean compile test` takes about 5 minutes or so.
 * This class is here to provide methods to play sound clips for you to know
 * when your tests actually start, when they fail, and when they end with success.
 * use these methods in your functional tests.
 */
public class ForHumanConvenience {
    //Sounds will not play if these are not absolute (fully qualified) filepaths.
    private static final String successSoundUrlStr = "file://" + new File(".").getAbsolutePath() + "/test/forhumanconvenience/forhumanconvenienceassets/150879__nenadsimic__jazzy-chords.wav";
    private static final String failSoundUrlStr = "file://" + new File(".").getAbsolutePath()+ "/test/forhumanconvenience/forhumanconvenienceassets/327738__distillerystudio__error-01.wav";
    private static final String startSoundUrlStr = "file://" + new File(".").getAbsolutePath() + "/test/forhumanconvenience/forhumanconvenienceassets/432308__nicog__lose-bass.wav";

    JFrame statusVisualAidFrame;

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

    public static void playBeforeAllTestStartSound(){ playSound(startSoundUrlStr); }

    public static void playTestFailSound(){ playSound(failSoundUrlStr); }

    public static void playAfterAllTestSuccessSound(){ playSound(successSoundUrlStr); }

    public static void showRunningVisualAid(){

    }

    public static void showSuccessVisualAid(){}
    public static void showFailVisualAid(){}

}
