package edu.pooh.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class FontGrabber {

    private static BufferedImage[][] cropFontFromSpriteSheet() {
        int rows = 7;
        int cols = 10;

        int xInit = 324;
        int yInit = 148;
        int widthFont = 10;
        int heightFont = 10;
        int xOffset = 6;
        int yOffset = 6;

        BufferedImage[][] font = new BufferedImage[rows][cols];

        for (int y = 0; y < rows; y++) {
            if (y == 3) {
                yInit = yInit-2;
            } else if (y == 6) {
                yInit = yInit-2;
            }


            for (int x = 0; x < cols; x++) {
                font[y][x] = Assets.fontSpriteSheet.getSubimage(xInit+(x*(widthFont+xOffset)),
                        yInit+(y*(heightFont+yOffset)), widthFont, heightFont);
            }

            xInit = 324;
        }

        return font;
    }
    public static Map<String, BufferedImage> initFont() {
        BufferedImage[][] fontNestedArray = cropFontFromSpriteSheet();

        Map<String, BufferedImage> fontHashMap = new HashMap<String, BufferedImage>();

        fontHashMap.put("A", fontNestedArray[0][0]);
        fontHashMap.put("B", fontNestedArray[0][1]);
        fontHashMap.put("C", fontNestedArray[0][2]);
        fontHashMap.put("D", fontNestedArray[0][3]);
        fontHashMap.put("E", fontNestedArray[0][4]);
        fontHashMap.put("F", fontNestedArray[0][5]);
        fontHashMap.put("G", fontNestedArray[0][6]);
        fontHashMap.put("H", fontNestedArray[0][7]);
        fontHashMap.put("I", fontNestedArray[0][8]);
        fontHashMap.put("null0", fontNestedArray[0][9]);

        fontHashMap.put("J", fontNestedArray[1][0]);
        fontHashMap.put("K", fontNestedArray[1][1]);
        fontHashMap.put("L", fontNestedArray[1][2]);
        fontHashMap.put("M", fontNestedArray[1][3]);
        fontHashMap.put("N", fontNestedArray[1][4]);
        fontHashMap.put("O", fontNestedArray[1][5]);
        fontHashMap.put("P", fontNestedArray[1][6]);
        fontHashMap.put("Q", fontNestedArray[1][7]);
        fontHashMap.put("R", fontNestedArray[1][8]);
        fontHashMap.put("null1", fontNestedArray[1][9]);

        fontHashMap.put("S", fontNestedArray[2][0]);
        fontHashMap.put("T", fontNestedArray[2][1]);
        fontHashMap.put("U", fontNestedArray[2][2]);
        fontHashMap.put("V", fontNestedArray[2][3]);
        fontHashMap.put("W", fontNestedArray[2][4]);
        fontHashMap.put("X", fontNestedArray[2][5]);
        fontHashMap.put("Y", fontNestedArray[2][6]);
        fontHashMap.put("Z", fontNestedArray[2][7]);
        fontHashMap.put("null2", fontNestedArray[2][8]);
        fontHashMap.put("null3", fontNestedArray[2][9]);

        fontHashMap.put("a", fontNestedArray[3][0]);
        fontHashMap.put("b", fontNestedArray[3][1]);
        fontHashMap.put("c", fontNestedArray[3][2]);
        fontHashMap.put("d", fontNestedArray[3][3]);
        fontHashMap.put("e", fontNestedArray[3][4]);
        fontHashMap.put("f", fontNestedArray[3][5]);
        fontHashMap.put("g", fontNestedArray[3][6]);
        fontHashMap.put("h", fontNestedArray[3][7]);
        fontHashMap.put("i", fontNestedArray[3][8]);
        fontHashMap.put("null4", fontNestedArray[3][9]);

        fontHashMap.put("j", fontNestedArray[4][0]);
        fontHashMap.put("k", fontNestedArray[4][1]);
        fontHashMap.put("l", fontNestedArray[4][2]);
        fontHashMap.put("m", fontNestedArray[4][3]);
        fontHashMap.put("n", fontNestedArray[4][4]);
        fontHashMap.put("o", fontNestedArray[4][5]);
        fontHashMap.put("p", fontNestedArray[4][6]);
        fontHashMap.put("q", fontNestedArray[4][7]);
        fontHashMap.put("r", fontNestedArray[4][8]);
        fontHashMap.put("null5", fontNestedArray[4][9]);

        fontHashMap.put("s", fontNestedArray[5][0]);
        fontHashMap.put("t", fontNestedArray[5][1]);
        fontHashMap.put("u", fontNestedArray[5][2]);
        fontHashMap.put("v", fontNestedArray[5][3]);
        fontHashMap.put("w", fontNestedArray[5][4]);
        fontHashMap.put("x", fontNestedArray[5][5]);
        fontHashMap.put("y", fontNestedArray[5][6]);
        fontHashMap.put("z", fontNestedArray[5][7]);
        fontHashMap.put("null6", fontNestedArray[5][8]);
        fontHashMap.put(" ", fontNestedArray[5][9]);

        fontHashMap.put("0", fontNestedArray[6][0]);
        fontHashMap.put("1", fontNestedArray[6][1]);
        fontHashMap.put("2", fontNestedArray[6][2]);
        fontHashMap.put("3", fontNestedArray[6][3]);
        fontHashMap.put("4", fontNestedArray[6][4]);
        fontHashMap.put("5", fontNestedArray[6][5]);
        fontHashMap.put("6", fontNestedArray[6][6]);
        fontHashMap.put("7", fontNestedArray[6][7]);
        fontHashMap.put("8", fontNestedArray[6][8]);
        fontHashMap.put("9", fontNestedArray[6][9]);

        fontHashMap.put("symbol", Assets.pokeballToken);

        return fontHashMap;
    }

    public static void renderString(Graphics g, String text, int x, int y, int width, int height) {
        int xOffset = 0;
        for (int i = 0; i < text.length(); i++) {
            if ( Assets.fontHashMap.containsKey(text.substring(i, i+1)) ) {
                g.drawImage(Assets.fontHashMap.get(text.substring(i, i + 1)), x + xOffset, y,
                        width, height, null);
            } else {
                g.drawImage(Assets.fontHashMap.get("symbol"), x + xOffset, y,
                        width, height, null);
            }
            xOffset += width;
        }
    }

} // **** end FontGrabber class ****