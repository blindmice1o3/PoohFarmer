package edu.pooh.main;

public class TimeManager {

    public static boolean newDay = false;
    public static boolean clockRunning = false;
    public static int elapsedRealSeconds = 0;

    public static void incrementElapsedRealSeconds() {
        // NEED IF STATEMENT TO CHECK FOR WHEN INVENTORY IS ACTIVE and GAME IS PAUSED.
        elapsedRealSeconds++;
    }

    public static void resetElapsedRealSeconds() {
        elapsedRealSeconds = 0;
    }

    public static void setClockRunningTrue() {
        clockRunning = true;
    }

    public static void setClockRunningFalse() {
        clockRunning = false;
    }

    public static void setNewDayTrue() {
        newDay = true;
    }

    public static boolean getNewDay() {
        return newDay;
    }

    public static void setNewDayFalse() {
        newDay = false;
    }

}