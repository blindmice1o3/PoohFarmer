package edu.pooh.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    // CONSTANTS
    private static final int WIDTH_IN_PIXEL = 16;
    private static final int HEIGHT_IN_PIXEL = 16;

    // TEXTURES
    public static BufferedImage dirtNormal, dirtHoe, dirtSeed;
    public static BufferedImage plantSproutling, plantJuvenille, plantAdult;
    public static BufferedImage plantFlowering1, plantFlowering2, rock, wood, signPost, fence, dirtWalkway;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/world/harvest moon tile sprite sheet.png"));

        dirtNormal = sheet.crop(0 * WIDTH_IN_PIXEL, 0 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        dirtHoe = sheet.crop(0 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        dirtSeed = sheet.crop(0 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);

        plantSproutling = sheet.crop(0 * WIDTH_IN_PIXEL, 3 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        plantJuvenille = sheet.crop(0 * WIDTH_IN_PIXEL, 4 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        plantAdult = sheet.crop(0 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);

        plantFlowering1 = sheet.crop(1 * WIDTH_IN_PIXEL, 0 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        plantFlowering2 = sheet.crop(1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        rock = sheet.crop(1 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        wood = sheet.crop(1 * WIDTH_IN_PIXEL, 3 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        signPost = sheet.crop(1 * WIDTH_IN_PIXEL, 4 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        fence = sheet.crop(1 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        dirtWalkway = sheet.crop(0 * WIDTH_IN_PIXEL, 6 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
    }

} // **** end Assets class ****