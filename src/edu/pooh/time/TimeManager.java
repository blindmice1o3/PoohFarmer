package edu.pooh.time;

import edu.pooh.entities.Entity;
import edu.pooh.entities.statics.statics2x2.ShippingBin;
import edu.pooh.inventory.ResourceManager;
import edu.pooh.main.Handler;
import edu.pooh.main.ISellable;
import edu.pooh.states.ChickenCoopState;
import edu.pooh.states.CowBarnState;
import edu.pooh.states.GameState;
import edu.pooh.states.StateManager;

import java.io.Serializable;

public class TimeManager
        implements Serializable {

    public boolean clockRunning = true;
    public boolean newDay = false;

    /////////////////////
    // TIME in ONE DAY //
    /////////////////////
    //determines: (1) AM or PM, (2) hours, and (3) minutes of in-game elapsed time
    // (stops at 6PM, resets when newDay is true).
    public int elapsedRealSeconds = 0;

    ///////////////////////
    // TIME in ONE MONTH //
    ///////////////////////
    public int elapsedInGameDays = 1;

    public int gameYear = 0;
    public String gameSeason = "Spring";
    public String gameMonth = "March";
    public int gameDay = 1;

    private transient Handler handler;

    private boolean executed6pm, executed5pm, executed3pm, executed12pm, executed9am, executed6am;

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public TimeManager(Handler handler) {
        this.handler = handler;

        setAllTimeRelatedBooleansToFalse();
    } // **** end TimeManager(Handler) constructor ****

    /**
     * TimeManager's tick().
     */
    public void incrementElapsedRealSeconds() {
        if (clockRunning && (elapsedRealSeconds + 1 <= 720)) {   //less than one game day (6am-6pm, 12hours).
            elapsedRealSeconds++;
        }

        //************************
        // TIME SPECIFIC ACTIONS (e.g. meal time, shipping bin collection time)
        checkTimeRelatedActions();
        //************************
    }

    //accessed via BedTile
    public void executeSleep() {
        setNewDayTrue();

        if (!executed6am) {
            execute6am();
        }
        if (!executed9am) {
            execute9am();
        }
        if (!executed12pm) {
            execute12pm();
        }
        if (!executed3pm) {
            execute3pm();
        }
        ////////////////////////
        if (!executed5pm) {
            execute5pm();
        }
        ////////////////////////
        if (!executed6pm) {
            execute6pm();
        }

        setAllTimeRelatedBooleansToFalse();
        handler.getWorld().getEntityManager().getPlayer().getStaminaModule().resetStaminaCurrent();

        System.out.println("TimeManager.executeSleep()");
    }

    private void checkTimeRelatedActions() {
        // Within it's hourly range AND have not executed (e.g. will only run if executed6am is false).
        if (elapsedRealSeconds >= 0 && elapsedRealSeconds < 180 && !executed6am) {
            execute6am();
        } else if (elapsedRealSeconds >= 180 && elapsedRealSeconds < 360 && !executed9am) {
            execute9am();
        } else if (elapsedRealSeconds >= 360 && elapsedRealSeconds < 540 && !executed12pm) {
            execute12pm();
        } else if (elapsedRealSeconds >= 540 && elapsedRealSeconds < 660 && !executed3pm) {
            execute3pm();
        }
        // ****************** | 5pm | *********************
        else if (elapsedRealSeconds >= 660 && elapsedRealSeconds < 720 && !executed5pm) {
            execute5pm();
        }
        // ************************************************
        else if (elapsedRealSeconds == 720 && !executed6pm) {
            execute6pm();
        }
    }

    public void setAllTimeRelatedBooleansToFalse() {
        System.out.println("TimeManager.setAllTimeRelatedBooleansToFalse()");

        executed6am = false;
        executed9am = false;
        executed12pm = false;
        executed3pm = false;
        executed5pm = false;
        executed6pm = false;
    }

    public void execute6pm() {

        System.out.println("TimeManager.execute6pm()");
        executed6pm = true;
    }

    private void sellFromShippingBin(ShippingBin shippingBin) {
        int totalPriceFromShippingBin = shippingBin.calculateTotal();

        System.out.println("I'll give you | " + totalPriceFromShippingBin + " | currencyUnit for the: \n");
        for (ISellable sellable : shippingBin.getInventory()) {
            System.out.println(sellable.getPrice() + ": " + sellable);
        }

        handler.getResourceManager().increaseCurrencyUnitCount(totalPriceFromShippingBin);
        shippingBin.emptyShippingBin();
    }

    public void execute5pm() {
        // Collect ShippingBin - GameState
        for (Entity e : ((GameState)handler.getStateManager().getIState(StateManager.GameState.GAME)).getWorld().getEntityManager().getEntities()) {
            if (e instanceof ShippingBin) {
                //////////////////////////////////////////////////////////
                sellFromShippingBin( (ShippingBin)e );
                break;
                //////////////////////////////////////////////////////////
            }
        }
        // Collect ShippingBin - ChickenCoopState
        for (Entity e : ((ChickenCoopState)handler.getStateManager().getIState(StateManager.GameState.CHICKEN_COOP)).getWorld().getEntityManager().getEntities()) {
            if (e instanceof ShippingBin) {
                //////////////////////////////////////////////////////////
                sellFromShippingBin( (ShippingBin)e );
                break;
                //////////////////////////////////////////////////////////
            }
        }
        // Collect ShippingBin - CowBarnState
        for (Entity e : ((CowBarnState)handler.getStateManager().getIState(StateManager.GameState.COW_BARN)).getWorld().getEntityManager().getEntities()) {
            if (e instanceof ShippingBin) {
                //////////////////////////////////////////////////////////
                sellFromShippingBin( (ShippingBin)e );
                break;
                //////////////////////////////////////////////////////////
            }
        }

        System.out.println("TimeManager.execute5pm()");
        executed5pm = true;
    }

    public void execute3pm() {

        System.out.println("TimeManager.execute3pm()");
        executed3pm = true;
    }

    public void execute12pm() {

        System.out.println("TimeManager.execute12pm()");
        executed12pm = true;
    }

    public void execute9am() {

        System.out.println("TimeManager.execute9am()");
        executed9am = true;
    }

    public void execute6am() {

        System.out.println("TimeManager.execute6am()");
        executed6am = true;
    }

    //@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

    public void translateElapsedInGameDaysToGameYearsSeasonsMonthsDays() {
        System.out.println("TimeManager.translateElapsedInGameDaysToGameYearsSeasonsMonthsDays()");

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

    private String convertMonthsIntToSeasonString(int months) {
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

    private String convertMonthsIntToMonthString(int months) {
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

    public void consoleOutputTimeInfo() {
        System.out.println( "****************************************** " +
                "\ngameYear: " +            gameYear +
                "\ngameSeason: " +          gameSeason +
                "\ngameMonth: " +           gameMonth +
                "\ngameDay: " +             gameDay +
                "\nelapsedInGameDays: " +   elapsedInGameDays +
                "\ngameHoursMinutes: " +    translateElapsedRealSecondsToGameHoursMinutes() );
    }

    /**
     * In-game time is completely based on elapsedRealSeconds. In order to increase in-game time by 1 hour
     * (or 60 in-game minutes, which is equivalent to 60 real-life seconds), the REAL-LIFE SECONDS VARIABLE
     * elapsedRealSeconds IS MUTATED/WRITTEN-OVER!!!!!! The plan to implement usage of setClockRunningFalse()
     * and setClockRunningTrue() methods will basically compromise the semantic of elapsedRealSeconds
     * (and therefore its future credibility/reliability to be used as a 'total-time-played' data source),
     * and since it's already compromised, I will mutate that variable here as well.
     * //TODO: In the future, develop the timing system to include both an elapsedRealSeconds variable AND a separate
     * //in-game time that uses the elapsedRealSeconds as an engine BUT NOT as direct-data.
     */
    public void incrementElapsedRealSecondsBy60() {
        if ((elapsedRealSeconds + 60 <= 720)) {
            elapsedRealSeconds += 60;
        } else {
            elapsedRealSeconds = 720;
        }
    }

    public void incrementElapsedInGameDays() {
        System.out.println("TimeManager.incrementElapsedInGameDays()");

        elapsedInGameDays++;
    }

    /**
     * Every second in real-life is a minute in-game time.
     *
     * @return String representation of in-game time (i.e. "02:37PM") based on elapsedRealSeconds.
     */
    public String translateElapsedRealSecondsToGameHoursMinutes() {
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

    public void resetElapsedRealSeconds() {
        System.out.println("TimeManager.resetElapsedRealSeconds()");

        elapsedRealSeconds = 0;
    }

    public void setClockRunningTrue() {
        System.out.println("TimeManager.setClockRunningTrue()");

        clockRunning = true;
    }

    public void setClockRunningFalse() {
        clockRunning = false;
    }

    public void setNewDayTrue() {
        newDay = true;
    }

    public boolean getNewDay() {
        return newDay;
    }

    public void setNewDayFalse() {
        System.out.println("TimeManager.setNewDayFalse()");

        newDay = false;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

}