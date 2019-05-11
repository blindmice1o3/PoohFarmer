package edu.pooh.inventory;

public class ResourceManager {

    public static int fodderCount = 3000;
    public static int woodCount = 1000;
    public static int currencyUnitCount = 2000;

    public static void increaseFodderCount(int numberOfUnits) {
        fodderCount += numberOfUnits;
    }

    public static void decreaseFodderCount(int numberOfUnits) {
        fodderCount -= numberOfUnits;
    }

    public static void increaseWoodCount(int numberOfUnits) {
        woodCount += numberOfUnits;
    }

    public static void decreaseWoodCount(int numberOfUnits) {
        woodCount -= numberOfUnits;
    }

    public static void increaseCurrencyUnitCount(int numberOfUnits) {
        currencyUnitCount += numberOfUnits;
    }

    public static void decreaseCurrencyUnitCount(int numberOfUnits) {
        currencyUnitCount -= numberOfUnits;
    }

    public static int getFodderCount() {
        return fodderCount;
    }

    public static int getWoodCount() {
        return woodCount;
    }

    public static int getCurrencyUnitCount() {
        return currencyUnitCount;
    }

} // **** end ResourceManager class ****