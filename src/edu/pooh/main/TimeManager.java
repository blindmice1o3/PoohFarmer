package edu.pooh.main;

public class TimeManager {

    public static boolean newDay = false;
    // TODO: clockRunning, currently, does NOT get changed to false anywhere.
    public static boolean clockRunning = true;
    public static int elapsedRealSeconds = 0;

    public static void incrementElapsedRealSeconds() {
        // TODO: implement usages of SETTERS for clockRunning (e.g. setClockRunningFalse() and setClockRunningTrue()).
        if (clockRunning) {
            elapsedRealSeconds++;
        }
    }

    /**
     * Every second in real-life is a minute in-game time.
     *
     * @return String representation of in-game time (i.e. "02:37PM") based on elapsedRealSeconds.
     */
    public static String translateRealSecondsToGameHoursMinutes() {
        StringBuilder returner = new StringBuilder();

        int minutes = elapsedRealSeconds;
        int hours = minutes / 60;
        minutes = minutes % 60;

        // start day at 6AM in-game-time (0 seconds for elapsed-real-seconds-time).
        if (hours + 6 >= 12) {               //game time is PM (morning started at 6AM)
            // PM.
            hours = hours + 6 - 12;

            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            if (hours == 0) { hours = 12; }
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
            if (hours >= 6) { return "06:00PM"; }
            // @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

            // 2-digit hours.
            if (hours / 10 > 0) {
                if (minutes / 10 > 0) { // 2-digit hours, 2-digit minutes.
                    returner.append(hours).append(":").append(minutes);
                } else {                // 2-digit hours, 1-digit minutes.
                    returner.append(hours).append(":0").append(minutes);
                }
            } else { // 1-digit hours.
                if (minutes / 10 > 0) { // 1-digit hours, 2-digit minutes.
                    returner.append("0").append(hours).append(":").append(minutes);
                } else {                // 1-digit hours, 1-digit minutes.
                    returner.append("0").append(hours).append(":0").append(minutes);
                }
            }

            returner.append("PM");
        } else {
            // AM.
            hours = hours + 6;              //game time is AM (morning started at 6AM)

            // 2-digit hours.
            if (hours / 10 > 0) {
                if (minutes / 10 > 0) { // 2-digit hours, 2-digit minutes.
                    returner.append(hours).append(":").append(minutes);
                } else {                // 2-digit hours, 1-digit minutes.
                    returner.append(hours).append(":0").append(minutes);
                }
            } else { // 1-digit hours.
                if (minutes / 10 > 0) { // 1-digit hours, 2-digit minutes.
                    returner.append("0").append(hours).append(":").append(minutes);
                } else {                // 1-digit hours, 1-digit minutes.
                    returner.append("0").append(hours).append(":0").append(minutes);
                }
            }

            returner.append("AM");
        }

        return returner.toString();
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