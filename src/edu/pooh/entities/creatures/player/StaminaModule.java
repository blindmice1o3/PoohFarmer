package edu.pooh.entities.creatures.player;

import edu.pooh.main.Handler;

import java.io.Serializable;

public class StaminaModule
        implements Serializable {

    public enum SanityLevel { SANE, FRAGMENTING, FRAGMENTED, INSANE, GUANO; }

    public Handler handler;

    private int staminaCurrent;
    private int staminaBase;
    private SanityLevel sanityLevel;

    public StaminaModule(Handler handler) {
        this.handler = handler;

        sanityLevel = SanityLevel.SANE;
        staminaBase = 100;
        staminaCurrent = staminaBase;
    } // **** end StaminaModule(Handler) constructor ****

    public void decreaseStaminaCurrent(int staminaUsage) {
        staminaCurrent = Math.max((staminaCurrent - staminaUsage), 0);

        updateSanityLevel();
    }

    public void increaseStaminaCurrent(int staminaGained) {
        staminaCurrent = Math.min((staminaCurrent + staminaGained), staminaBase);

        updateSanityLevel();
    }

    public void resetStaminaCurrent() { //TimeManager.executeSleep().
        System.out.println("Player.resetStaminaCurrent()");
        staminaCurrent = staminaBase;

        updateSanityLevel();
    }

    // SANITY LEVEL
    private void updateSanityLevel() {
        if (staminaCurrent >= 70) {
            sanityLevel = SanityLevel.SANE;
        } else if ((staminaCurrent >= 50) && (staminaCurrent < 70)) {
            sanityLevel = SanityLevel.FRAGMENTING;
        } else if ((staminaCurrent >= 30) && (staminaCurrent < 50)) {
            sanityLevel = SanityLevel.FRAGMENTED;
        } else if ((staminaCurrent >= 1) && (staminaCurrent < 30)) {
            sanityLevel = SanityLevel.INSANE;
        } else {
            sanityLevel = SanityLevel.GUANO;
        }
    }

    // GETTERS AND SETTERS

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public int getStaminaCurrent() {
        return staminaCurrent;
    }

    public int getStaminaBase() {
        return staminaBase;
    }

    public SanityLevel getSanityLevel() {
        return sanityLevel;
    }

} // **** end StaminaModule class ****