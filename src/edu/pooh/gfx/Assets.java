package edu.pooh.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    // CONSTANTS
    private static final int WIDTH_IN_PIXEL = 16;
    private static final int HEIGHT_IN_PIXEL = 16;

    // FONT
    public static Font font28;

    // SINGLE (/TILES)
    public static BufferedImage dirtNormal, fence, dirtWalkway, signPost;
    // SINGLE (-CURRENTLY UNUSED).
    public static BufferedImage dirtHoe, dirtSeed, plantFlowering2, wood;
    // SINGLE (/ITEMS)
    public static BufferedImage plantSproutling, plantJuvenille, plantAdult;
    // SINGLE (/ENTITIES)
    public static BufferedImage plantFlowering1, rock;

    // SPANNING MULTIPLE-TILE (/TILES)
    public static BufferedImage[][] home5x4, cowBarn5x5, silos5x6, chickenCoop4x5, toolShed5x5,
            stable2x3, building2x3;

    // SPANNING MULTIPLE-TILE (/ENTITIES)
    public static BufferedImage[][] chest2x2, boulder2x2, treeStump2x2, poolWater2x2, poolWater3x3;

    // SINGLE (/ENTITIES/CREATURES/PLAYER)
    public static BufferedImage playerDownDefault, playerUpDefault, playerRightDefault, playerLeftDefault,
            playerDownRightDefault, playerDownLeftDefault, playerUpRightDefault, playerUpLeftDefault;
    public static BufferedImage[] playerDown, playerUp, playerRight, playerLeft,
                                playerDownRight, playerDownLeft, playerUpRight, playerUpLeft;

    // INVENTORY
    public static BufferedImage inventoryScreen;

    // START BUTTONS
    public static BufferedImage[] startButtons;

    public static void init() {
        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);

        inventoryScreen = ImageLoader.loadImage("/inventoryScreen.png");

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/StartButtons(96x64).png"));
        startButtons = new BufferedImage[2];
        startButtons[0] = sheet.crop(0, 0, 96, 31);
        startButtons[1] = sheet.crop(0, 32, 96, 31);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tiles/harvest moon tile sprite sheet.png"));
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

        home5x4 = new BufferedImage[4][5];
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 5; x++) {
                home5x4[y][x] = sheet.crop((2 + x) * WIDTH_IN_PIXEL,
                        (0 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL,1 * HEIGHT_IN_PIXEL);
            }
        }

        cowBarn5x5 = new BufferedImage[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                cowBarn5x5[y][x] = sheet.crop((2 + x) * WIDTH_IN_PIXEL,
                        (4 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        silos5x6 = new BufferedImage[6][5];
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                silos5x6[y][x] = sheet.crop((2 + x) * WIDTH_IN_PIXEL,
                        (9 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        chickenCoop4x5 = new BufferedImage[5][4];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 4; x++) {
                chickenCoop4x5[y][x] = sheet.crop((3 + x) * WIDTH_IN_PIXEL,
                        (15 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        toolShed5x5 = new BufferedImage[5][5];
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                toolShed5x5[y][x] = sheet.crop((2 + x) * WIDTH_IN_PIXEL,
                        (20 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        stable2x3 = new BufferedImage[3][2];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                stable2x3[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (19 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        building2x3 = new BufferedImage[3][2];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 2; x++) {
                building2x3[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (22 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        chest2x2 = new BufferedImage[2][2];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                chest2x2[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (7 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        boulder2x2 = new BufferedImage[2][2];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                boulder2x2[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (9 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        treeStump2x2 = new BufferedImage[2][2];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                treeStump2x2[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (11 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        poolWater2x2 = new BufferedImage[2][2];
        for (int y = 0; y < 2; y++) {
            for (int x = 0; x < 2; x++) {
                poolWater2x2[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (13 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        poolWater3x3 = new BufferedImage[3][3];
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 3; x++) {
                poolWater3x3[y][x] = sheet.crop((0 + x) * WIDTH_IN_PIXEL,
                        (15 + y) * HEIGHT_IN_PIXEL, 1 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL);
            }
        }

        // ******************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/Game Boy Advance - Kingdom Hearts Chain of Memories - Winnie the Pooh.png"));
        playerDownDefault = sheet.crop(178, 1061, 20, 38);
        playerUpDefault = sheet.crop(314, 1063, 20, 36);
        playerRightDefault = sheet.crop(247, 1062, 17, 37);
        playerLeftDefault = sheet.crop(111, 1062, 17, 37);
        playerDownRightDefault = sheet.crop(213, 1060, 17, 39);
        playerDownLeftDefault = sheet.crop(145, 1060, 17, 39);
        playerUpRightDefault = sheet.crop(281, 1064, 17, 35);
        playerUpLeftDefault = sheet.crop(349, 1064, 17, 35);

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

        playerDownRight = new BufferedImage[11];
        playerDownRight[0] = sheet.crop(15, 1325, 24, 33);
        playerDownRight[1] = sheet.crop(60, 1323, 23, 35);
        playerDownRight[2] = sheet.crop(103, 1322, 21, 36);
        playerDownRight[3] = sheet.crop(148, 1321, 20, 37);
        playerDownRight[4] = sheet.crop(190, 1322, 19, 36);
        playerDownRight[5] = sheet.crop(232, 1324, 18, 34);
        playerDownRight[6] = sheet.crop(274, 1326, 19, 32);
        playerDownRight[7] = sheet.crop(308, 1324, 23, 34);
        playerDownRight[8] = sheet.crop(355, 1323, 26, 35);
        playerDownRight[9] = sheet.crop(397, 1323, 27, 35);
        playerDownRight[10] = sheet.crop(437, 1325, 25, 33);

        playerDownLeft = new BufferedImage[11];
        playerDownLeft[0] = sheet.crop(20, 1375, 24, 33);
        playerDownLeft[1] = sheet.crop(65, 1373, 23, 35);
        playerDownLeft[2] = sheet.crop(108, 1372, 21, 36);
        playerDownLeft[3] = sheet.crop(147, 1373, 20, 37);
        playerDownLeft[4] = sheet.crop(185, 1372, 19, 36);
        playerDownLeft[5] = sheet.crop(228, 1374, 18, 34);
        playerDownLeft[6] = sheet.crop(269, 1376, 19, 32);
        playerDownLeft[7] = sheet.crop(303, 1374, 23, 34);
        playerDownLeft[8] = sheet.crop(349, 1373, 26, 35);
        playerDownLeft[9] = sheet.crop(391, 1373, 27, 35);
        playerDownLeft[10] = sheet.crop(431, 1375, 25, 33);

        playerUpRight = new BufferedImage[11];
        playerUpRight[0] = sheet.crop(20, 1428, 22, 36);
        playerUpRight[1] = sheet.crop(66, 1427, 20, 37);
        playerUpRight[2] = sheet.crop(108, 1426, 20, 38);
        playerUpRight[3] = sheet.crop(148, 1427, 19, 37);
        playerUpRight[4] = sheet.crop(185, 1426, 18, 38);
        playerUpRight[5] = sheet.crop(226, 1426, 20, 38);
        playerUpRight[6] = sheet.crop(265, 1425, 24, 39);
        playerUpRight[7] = sheet.crop(300, 1426, 26, 38);
        playerUpRight[8] = sheet.crop(347, 1426, 25, 38);
        playerUpRight[9] = sheet.crop(393, 1427, 24, 37);
        playerUpRight[10] = sheet.crop(430, 1429, 26, 35);

        playerUpLeft = new BufferedImage[11];
        playerUpLeft[0] = sheet.crop(20, 1493, 22, 36);
        playerUpLeft[1] = sheet.crop(66, 1492, 20, 37);
        playerUpLeft[2] = sheet.crop(108, 1491, 20, 38);
        playerUpLeft[3] = sheet.crop(148, 1492, 19, 37);
        playerUpLeft[4] = sheet.crop(185, 1491, 18, 38);
        playerUpLeft[5] = sheet.crop(226, 1491, 20, 38);
        playerUpLeft[6] = sheet.crop(265, 1490, 24, 39);
        playerUpLeft[7] = sheet.crop(300, 1491, 26, 38);
        playerUpLeft[8] = sheet.crop(347, 1491, 25, 38);
        playerUpLeft[9] = sheet.crop(393, 1492, 24, 37);
        playerUpLeft[10] = sheet.crop(430, 1494, 26, 35);
    }

} // **** end Assets class ****