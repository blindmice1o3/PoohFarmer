package edu.pooh.time;

public class TimeManager {

    // TODO: clockRunning, currently, does NOT get changed to false anywhere.
    public static boolean clockRunning = true;

    public static boolean newDay = false;

    ///////////////////////
    // TIME in ONE MONTH //
    ///////////////////////
    public static int elapsedInGameDays = 1;

    public static void incrementElapsedInGameDays() {
        elapsedInGameDays++;
    }

    public static int gameYear = 0;
    public static String gameSeason = "Spring";
    public static String gameMonth = "March";
    public static int gameDay = 1;

    public static void translateElapsedInGameDaysToGameYearsSeasonsMonthsDays() {
        int days = elapsedInGameDays;
        int monthsInt = days / 30; // 30 days in 1 month.

        gameDay = days % 30;   // The remaining days left-over, after converting days to months.

        ///////////////////////////////////
        if (gameDay == 0) {
            gameDay = 30;
            monthsInt = monthsInt - 1;
        }
        ///////////////////////////////////

        gameMonth = convertMonthsIntToMonthString(monthsInt);

        gameSeason = convertMonthsIntToSeasonString(monthsInt);

        gameYear = elapsedInGameDays / 360; // 360 days (NOT 365 days because 12 months * 30 days) in 1 year.
    }

    private static String convertMonthsIntToSeasonString(int months) {
        switch (months) {
            case 0:
            case 1:
            case 2:
                return "Spring";
            case 3:
            case 4:
            case 5:
                return "Summer";
            case 6:
            case 7:
            case 8:
                return "Autumn";
            case 9:
            case 10:
            case 11:
                return "Winter";
            default:
                return "No applicable SeasonString";

        }
    }

    private static String convertMonthsIntToMonthString(int months) {
        switch (months) {
            case 0:
                return "March";
            case 1:
                return "April";
            case 2:
                return "May";
            case 3:
                return "June";
            case 4:
                return "July";
            case 5:
                return "August";
            case 6:
                return "September";
            case 7:
                return "October";
            case 8:
                return "November";
            case 9:
                return "December";
            case 10:
                return "January";
            case 11:
                return "February";
            default:
                return "No applicable MonthString";
        }
    }

    public static void consoleOutputTimeInfo() {
        System.out.println( "****************************************** " +
                "\ngameYear: " +            TimeManager.gameYear +
                "\ngameSeason: " +          TimeManager.gameSeason +
                "\ngameMonth: " +           TimeManager.gameMonth +
                "\ngameDay: " +             TimeManager.gameDay +
                "\nelapsedInGameDays: " +   TimeManager.elapsedInGameDays +
                "\ngameHoursMinutes: " +    TimeManager.translateElapsedRealSecondsToGameHoursMinutes() );
    }

    /////////////////////
    // TIME in ONE DAY //
    /////////////////////
    //determines: (1) AM or PM, (2) hours, and (3) minutes
    // of in-game elapsed time (stops at 6PM, resets when newDay is true).
    public static int elapsedRealSeconds = 0;
    //TODO: pause (clockRunning = false) if indoors or if text window (dialog) is opened.
    public static void incrementElapsedRealSeconds() {
        // TODO: implement usages of SETTERS for clockRunning (e.g. setClockRunningFalse() and setClockRunningTrue()).
        if (clockRunning && (elapsedRealSeconds + 1 <= 720)) {   //less than one game day (6am-6pm, 12hours).
            elapsedRealSeconds++;
        }
    }

    /**
     * In-game time is completely based on elapsedRealSeconds. In order to increase in-game time by 1 hour
     * (or 60 in-game minutes, which is equivalent to 60 real-life seconds), the REAL-LIFE SECONDS VARIABLE
     * elapsedRealSeconds IS MUTATED/WRITTEN-OVER!!!!!! The plan to implement usage of setClockRunningFalse()
     * and setClockRunningTrue() methods will basically compromise the semantic of elapsedRealSeconds
     * (and therefore its future credibility/reliability to be used as a 'total-time-played' data source),
     * and since it's already compromised, I will mutate that variable here as well.
     * //TODO: In the future, develop the timing system to include both an elapsedRealSeconds variable AND a separate
     * //TODO: in-game time that uses the elapsedRealSeconds as an engine BUT NOT as direct-data.
     */
    public static void incrementElapsedRealSecondsBy60() {
        if ((elapsedRealSeconds + 60 <= 720)) {
            elapsedRealSeconds += 60;
        } else {
            elapsedRealSeconds = 720;
        }
    }

    /**
     * Every second in real-life is a minute in-game time.
     *
     * @return String representation of in-game time (i.e. "02:37PM") based on elapsedRealSeconds.
     */
    public static String translateElapsedRealSecondsToGameHoursMinutes() {
        StringBuilder returner = new StringBuilder();

        int minutes = elapsedRealSeconds;   // 1 real-life second is 1 in-game minute.
        int hours = minutes / 60;   // 60 minutes in 1 hour.
        minutes = minutes % 60;     // The remaining minutes left-over, after converting minutes to hours.


        //have the start be relative to 6am instead of hour 0.
        hours = hours + 6;

        //converts from military-time-format to conventional-time-format while avoiding 12pm==0 bug.
        if (elapsedRealSeconds >= 420) { //if 1pm (7hours passed since 6am)
            hours = hours - 12;
        }

        //formatting.
        if (hours / 10 > 0) {   // 2-digit hours.
            if (minutes / 10 > 0) {     // 2-digit hours, 2-digit minutes.
                returner.append(hours).append(":").append(minutes);
            } else {                    // 2-digit hours, 1-digit minutes.
                returner.append(hours).append(":0").append(minutes);
            }
        } else {                // 1-digit hours.
            if (minutes / 10 > 0) {     // 1-digit hours, 2-digit minutes.
                returner.append("0").append(hours).append(":").append(minutes);
            } else {                    // 1-digit hours, 1-digit minutes.
                returner.append("0").append(hours).append(":0").append(minutes);
            }
        }

        //determine AM/PM.
        if (elapsedRealSeconds < 360) { //game time is AM.
            returner.append("AM");
        } else {                        //game time is PM.
            returner.append("PM");
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