package edu.pooh.gfx;

import java.awt.image.BufferedImage;

public class Assets {

    // CONSTANTS
    private static final int WIDTH_IN_PIXEL = 16;
    private static final int HEIGHT_IN_PIXEL = 16;

    // TEXTURES (/TILES)
    public static BufferedImage dirtNormal, dirtHoe, dirtSeed;
    public static BufferedImage plantSproutling, plantJuvenille, plantAdult;
    public static BufferedImage plantFlowering1, plantFlowering2, rock, wood, signPost, fence, dirtWalkway;
    public static BufferedImage home5x4, cowBarn5x5, silos5x6, chickenCoop4x5, toolShed5x5;
    public static BufferedImage boulder2x2, treeStump2x2, poolWater2x2, stable2x3, poolWater3x3, building2x3;


    // GAME OBJECT (/ENTITIES)
    public static BufferedImage playerDefault;
    public static BufferedImage[] playerDown, playerUp, playerRight, playerLeft;

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

        playerDown = new BufferedImage[11];
        playerDown[0] = sheet.crop(17, 1114, 26, 36);
        playerDown[1] = sheet.crop(58, 1113, 26, 37);
        playerDown[2] = sheet.crop(99, 1112, 26, 38);
        playerDown[3] = sheet.crop(140, 1114, 26, 36);
        playerDown[4] = sheet.crop(181, 1115, 26, 35);
        playerDown[5] = sheet.crop(224, 1115, 24, 35);
        playerDown[6] = sheet.crop(264, 1113, 26, 37);
        playerDown[7] = sheet.crop(305, 1112, 26, 38);
        playerDown[8] = sheet.crop(350, 1113, 26, 37);
        playerDown[9] = sheet.crop(393, 1115, 26, 35);
        playerDown[10] = sheet.crop(434, 1116, 26, 34);

        playerUp = new BufferedImage[11];
        playerUp[0] = sheet.crop(21, 1163, 27, 39);
        playerUp[1] = sheet.crop(62, 1164, 27, 38);
        playerUp[2] = sheet.crop(102, 1163, 28, 39);
        playerUp[3] = sheet.crop(142, 1162, 28, 40);
        playerUp[4] = sheet.crop(184, 1162, 26, 40);
        playerUp[5] = sheet.crop(229, 1162, 24, 40);
        playerUp[6] = sheet.crop(269, 1164, 26, 38);
        playerUp[7] = sheet.crop(311, 1164, 26, 38);
        playerUp[8] = sheet.crop(348, 1163, 28, 39);
        playerUp[9] = sheet.crop(392, 1163, 27, 39);
        playerUp[10] = sheet.crop(430, 1162, 26, 40);

        playerRight = new BufferedImage[11];
        playerRight[0] = sheet.crop(20, 1217, 24, 34);
        playerRight[1] = sheet.crop(64, 1217, 19, 34);
        playerRight[2] = sheet.crop(107, 1215, 19, 36);
        playerRight[3] = sheet.crop(146, 1214, 22, 37);
        playerRight[4] = sheet.crop(184, 1215, 23, 36);
        playerRight[5] = sheet.crop(227, 1218, 25, 33);
        playerRight[6] = sheet.crop(269, 1215, 19, 36);
        playerRight[7] = sheet.crop(309, 1214, 19, 37);
        playerRight[8] = sheet.crop(354, 1213, 19, 38);
        playerRight[9] = sheet.crop(395, 1214, 21, 37);
        playerRight[10] = sheet.crop(433, 1216, 24, 35);

        playerLeft = new BufferedImage[11];
        playerLeft[0] = sheet.crop(20, 1268, 24, 34);
        playerLeft[1] = sheet.crop(64, 1268, 19, 34);
        playerLeft[2] = sheet.crop(107, 1266, 19, 36);
        playerLeft[3] = sheet.crop(146, 1265, 22, 37);
        playerLeft[4] = sheet.crop(184, 1266, 23, 36);
        playerLeft[5] = sheet.crop(227, 1269, 25, 33);
        playerLeft[6] = sheet.crop(269, 1266, 19, 36);
        playerLeft[7] = sheet.crop(309, 1265, 19, 37);
        playerLeft[8] = sheet.crop(354, 1264, 19, 38);
        playerLeft[9] = sheet.crop(395, 1265, 21, 37);
        playerLeft[10] = sheet.crop(433, 1267, 24, 35);


    }

} // **** end Assets class ****