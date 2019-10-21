package edu.pooh.inventory;

import edu.pooh.main.Handler;

import java.io.Serializable;

public class ResourceManager
        implements Serializable {

    private transient Handler handler;

    private int fodderCount = 3000;
    private int woodCount = 1000;
    private int currencyUnitCount = 3000000;
    private int chickenCounter = 0;
    private int cowCounter = 0;

    public ResourceManager(Handler handler) {
        this.handler = handler;
    } // **** end ResourceManager(Handler) constructor ****

    public void increaseFodderCount(int numberOfUnits) {
        fodderCount += numberOfUnits;
    }

    public void decreaseFodderCount(int numberOfUnits) {
        fodderCount -= numberOfUnits;
    }

    public void increaseWoodCount(int numberOfUnits) {
        woodCount += numberOfUnits;
    }

    public void decreaseWoodCount(int numberOfUnits) {
        woodCount -= numberOfUnits;
    }

    public void increaseCurrencyUnitCount(int numberOfUnits) {
        currencyUnitCount += numberOfUnits;
    }

    public void decreaseCurrencyUnitCount(int numberOfUnits) {
        currencyUnitCount -= numberOfUnits;
    }

    public void increaseChickenCounter(int numberOfChicken) { chickenCounter += numberOfChicken; }

    public void decreaseChickenCounter(int numberOfChicken) { chickenCounter -= numberOfChicken; }

    public void increaseCowCounter(int numberOfCow) { cowCounter += numberOfCow; }

    public void decreaseCowCounter(int numberOfCow) { cowCounter -= numberOfCow; }

    public int getFodderCount() {
        return fodderCount;
    }

    public int getWoodCount() {
        return woodCount;
    }

    public int getCurrencyUnitCount() {
        return currencyUnitCount;
    }

    public int getChickenCounter() { return chickenCounter; }

    public int getCowCounter() { return  cowCounter; }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end ResourceManager class ****