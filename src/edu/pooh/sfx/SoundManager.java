package edu.pooh.sfx;

import java.applet.Applet;
import java.applet.AudioClip;

public class SoundManager {

    public static AudioClip[] sounds;

    public static void init() {
        sounds = new AudioClip[256];

        sounds[0] = Applet.newAudioClip(SoundManager.class.getResource("/38 - shotgun.wav"));
        sounds[1] = Applet.newAudioClip(SoundManager.class.getResource("/6 - cowbell.wav"));
        sounds[2] = Applet.newAudioClip(SoundManager.class.getResource("/7 - denied (buzz).wav"));
    }

} // **** end SoundManager class ****