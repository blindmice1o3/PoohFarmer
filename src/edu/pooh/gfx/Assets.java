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
    public static BufferedImage home5x4, cowBarn5x5, silos5x6, chickenCoop4x5, toolShed5x5;
    public static BufferedImage boulder2x2, treeStump2x2, poolWater2x2, stable2x3, poolWater3x3, building2x3;


    // GAME OBJECT
    public static BufferedImage playerDefault;

    public static void init() {
        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tiles/harvest moon tile sprite sheet.png"));

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

        home5x4 = sheet.crop(2 * WIDTH_IN_PIXEL, 0 * HEIGHT_IN_PIXEL,
                5 * WIDTH_IN_PIXEL, 4 * HEIGHT_IN_PIXEL);
        cowBarn5x5 = sheet.crop(2 * WIDTH_IN_PIXEL, 4 * HEIGHT_IN_PIXEL,
                5 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL);
        silos5x6 = sheet.crop(2 * WIDTH_IN_PIXEL, 9 * HEIGHT_IN_PIXEL,
                5 * WIDTH_IN_PIXEL, 6 * HEIGHT_IN_PIXEL);
        chickenCoop4x5 = sheet.crop(3 * WIDTH_IN_PIXEL, 15 * HEIGHT_IN_PIXEL,
                4 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL);
        toolShed5x5 = sheet.crop(2 * WIDTH_IN_PIXEL, 20 * HEIGHT_IN_PIXEL,
                5 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL);

        boulder2x2 = sheet.crop(0, 8 * HEIGHT_IN_PIXEL,
                2 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL);
        treeStump2x2 = sheet.crop(0, 10 * HEIGHT_IN_PIXEL,
                2 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL);
        poolWater2x2 = sheet.crop(0, 12 * HEIGHT_IN_PIXEL,
                2 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL);
        stable2x3 = sheet.crop(0, 14 * HEIGHT_IN_PIXEL,
                2 * WIDTH_IN_PIXEL, 3 * HEIGHT_IN_PIXEL);
        poolWater3x3 = sheet.crop(0, 17 * HEIGHT_IN_PIXEL,
                3 * WIDTH_IN_PIXEL, 3 * HEIGHT_IN_PIXEL);
        building2x3 = sheet.crop(0, 20 * HEIGHT_IN_PIXEL,
                2 * WIDTH_IN_PIXEL, 3 * HEIGHT_IN_PIXEL);

        // ******************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/Game Boy Advance - Kingdom Hearts Chain of Memories - Winnie the Pooh.png"));
        playerDefault = sheet.crop(178, 1061, 20, 38);
    }

} // **** end Assets class ****