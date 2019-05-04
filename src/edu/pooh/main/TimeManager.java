package edu.pooh.main;

public class TimeManager {

    public static int elapsedRealSeconds = 0;

    public static void incrementElapsedRealSeconds() {
        // NEED IF STATEMENT TO CHECK FOR WHEN INVENTORY IS ACTIVE and GAME IS PAUSED.
        elapsedRealSeconds++;
    }

}