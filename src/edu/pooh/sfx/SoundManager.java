package edu.pooh.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

public class SoundManager {

    public static AudioClip[] sounds;

    public static void init() {
        sounds = new AudioClip[256];

        sounds[0] = Applet.newAudioClip(SoundManager.class.getResource("/38 - shotgun.wav"));
    }

} // **** end SoundManager class ****