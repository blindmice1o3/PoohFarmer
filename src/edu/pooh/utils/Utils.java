package edu.pooh.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Utils {

    public static String loadFileAsString(String path) {
        StringBuilder builder = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            while ( (line = br.readLine()) != null ) {
                builder.append(line + "\n");
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return builder.toString();
    }

    public static int parseInt(String number) {
        try {
            return Integer.parseInt(number);
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // Returning a dirt tile by default.
            return 0;
        }
    }

    // Load map by reading RGB values and writing to int[][][].
    public static int[][][] loadMapByRGB(BufferedImage mapAsImage) {
        int width = mapAsImage.getWidth();
        int height = mapAsImage.getHeight();

        int[][][] tempArray = new int[width][height][3];

        for (int xx = 0; xx < width; xx++) {
            for (int yy = 0; yy < height; yy++) {

                int pixel = mapAsImage.getRGB(xx, yy);
                int red = (pixel >> 16) & 0xff;
                int green = (pixel >> 8) & 0xff;
                int blue = (pixel) & 0xff;

                for (int rgb = 0; rgb < 3; rgb++) {
                    switch (rgb) {
                        case 0:
                            tempArray[xx][yy][rgb] = red;
                            break;
                        case 1:
                            tempArray[xx][yy][rgb] = green;
                            break;
                        case 2:
                            tempArray[xx][yy][rgb] = blue;
                            break;
                        default:
                            tempArray[xx][yy][rgb] = 0;
                            break;
                    }
                }

                if (red == 255) {

                }
                if (green == 255) {

                }
                if (blue == 255) {

                }
                if (red == 255 && blue == 255) {

                }

            }
        }

        return tempArray;
    }

} // **** end Utils class ****