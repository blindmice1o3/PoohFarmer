package edu.pooh.gfx;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Assets {

    // CONSTANTS
    private static final int WIDTH_IN_PIXEL = 16;
    private static final int HEIGHT_IN_PIXEL = 16;

    // FONT
    public static Font font28;

    // START BUTTONS
    public static BufferedImage[] startButtons;

    // INVENTORY
    public static BufferedImage inventoryBackground, dateDisplayerBackground;

    // SELF-CREATED TEXTURE
    public static BufferedImage waterFX;

    // SPANNING SINGLE-TILE (/TILES)
    public static BufferedImage dirtNormal, /*dirtTilled, dirtSeed,*/ fence, dirtWalkway, signPostNotTransparent,
            signPostTransparent, berry, rockMountain, flowerYellow, flowerPurple;

    // SPANNING MULTIPLE-TILE (/TILES)
    public static BufferedImage[][] home5x4, cowBarn5x5, silos5x6, chickenCoop4x5, toolShed5x5,
            stable2x3, building2x3, chest2x2, poolWater2x2, poolWater3x3;

    // SINGLE (/ENTITIES)
    public static BufferedImage plantSproutling, plantJuvenille, plantAdult, plantFlowering2, honeyPot,
            plantFlowering1, rock, wood;

    // SPANNING MULTIPLE-TILE (/ENTITIES)
    public static BufferedImage[][] boulder2x2, treeStump2x2;

    // ITEMS (/TOOLS)
    public static BufferedImage wateringCan, scythe, shovel, hammer, axe,
            goldSprinkler, goldScythe, goldShovel, goldAxe, goldHammer;

    // ENTITIES (/CROPS)
    public static BufferedImage dirtTilledDry, dirtTilledWatered,
            dirtSeedsDry, dirtSeedsWatered,
            secretGardenEmpty, secretGardenFlower,
            turnip0Whole, turnip0Fragmented, turnip1Dry, turnip1Watered, turnip2Dry, turnip2Watered,
            potato0Whole, potato0Fragmented, potato1Dry, potato1Watered, potato2Dry, potato2Watered,
            tomato0Whole, tomato0Fragmented, tomato1Dry, tomato1Watered, tomato2Dry, tomato2Watered,
            tomato3Dry, tomato3Watered, tomato4Dry, tomato4Watered,
            corn0Whole, corn0Fragmented, corn1Dry, corn1Watered, corn2Dry, corn2Watered,
            corn3Dry, corn3Watered, corn4Dry, corn4Watered;

    // ENTITIES STANDING-STILL (/PLAYER)
    public static BufferedImage playerUpDefault, playerDownDefault, playerLeftDefault, playerRightDefault,
            playerUpLeftDefault, playerUpRightDefault, playerDownLeftDefault, playerDownRightDefault;
    // ENTITIES MOVING/ANIMATED (/PLAYER)
    public static BufferedImage[] playerUp, playerDown, playerLeft, playerRight,
            playerUpLeft, playerUpRight, playerDownLeft, playerDownRight;

    // ENTITIES MOVING/ANIMATED (/HAWKER)
    public static BufferedImage[] hawkerUp, hawkerDown, hawkerLeft, hawkerRight;

    // ENTITIES MOVING/ANIMATED (/KERO DOG)
    public static BufferedImage[] xDogUp, xDogDown, xDogLeft, xDogRight, xDogLeftPee;

    // ENTITIES MOVING/ANIMATED (/DOG, COW, CHICKEN, HORSE, JACK_WALKING, JACK_RUNING)
    public static BufferedImage[] dogUp, dogDown, dogLeft, dogRight,
            cowUp, cowDown, cowLeft, cowRight,
            chickenUp, chickenDown, chickenLeft, chickenRight,
            horseUp, horseDown, horseLeft, horseRight,
            jackWalkingUp, jackWalkingDown, jackWalkingLeft, jackWalkingRight,
            jackRunningUp, jackRunningDown, jackRunningLeft, jackRunningRight;

    // TRAVELINGFENCESTATE (STORE/SHOPPING STATE)
    public static BufferedImage shoppingScreen;

    // STATE (HOMESTATE)
    //public static BufferedImage homeStateBackground;
    public static BufferedImage homeStateBackground2, chickenCoopStateBackground, cowBarnStateBackground,
            toolShedStateBackground, crossroadStateBackground, mountainStateBackground;

    // LOADING MAP BY RGB VALUES
    public static BufferedImage tilesGameViaRGB, tilesHomeViaRGB, tilesChickenCoopViaRGB, tilesCowBarnViaRGB,
            tilesToolShedViaRGB, tilesCrossroadViaRGB, tilesMountainViaRGB;
    public static BufferedImage entitiesGameViaRGB, entitiesHomeViaRGB, entitiesChickenCoopViaRGB, entitiesCowBarnViaRGB,
            entitiesToolShedViaRGB, entitiesCrossroadViaRGB , entitiesMountainViaRGB;



/* |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+| */
/* |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+| */
/* |+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+|+| */



    public static void init() {
        // **************************************************************
        // |+|+|+|+|+|+|+| FONT: SLKSCR.TTF (size == 28) |+|+|+|+|+|+|+|
        // **************************************************************
        font28 = FontLoader.loadFont("res/fonts/slkscr.ttf", 28);

        // **************************************************************
        // |+|+|+|+|+|+|+| INVENTORY SCREEN (BACKGROUND) |+|+|+|+|+|+|+|
        // **************************************************************
        inventoryBackground = ImageLoader.loadImage("/InventoryBackground.png");
        dateDisplayerBackground = ImageLoader.loadImage("/DateDisplayerBackground.png");

        // ************************************************************
        // |+|+|+|+|+|+|+| SHOPPING SCREEN (BACKGROUND) |+|+|+|+|+|+|+|
        // ************************************************************
        shoppingScreen = ImageLoader.loadImage("/shady sundial salesman in hercules (TravelingFenceState500x270).jpg");

        // *****************************************************
        // |+|+|+|+|+|+|+| (unused???) WATERITEM |+|+|+|+|+|+|+|
        // *****************************************************
        waterFX = ImageLoader.loadImage("/textures/entities/waterItem.png");

        // ********************************************************
        // |+|+|+|+|+|+|+| MENUSTATE: STARTBUTTONS |+|+|+|+|+|+|+|
        // ********************************************************

        SpriteSheet sheet = new SpriteSheet(ImageLoader.loadImage("/StartButtons(96x64).png"));
        startButtons = new BufferedImage[2];
        startButtons[0] = sheet.crop(0, 0, 96, 31);
        startButtons[1] = sheet.crop(0, 32, 96, 31);

        // *****************************************************************
        // |+|+|+|+|+|+|+| TILES: SINGLE AND MULTIPLE TILES |+|+|+|+|+|+|+|
        // *****************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/tiles/harvest moon tile sprite sheet.png"));

        dirtNormal = sheet.crop(0 * WIDTH_IN_PIXEL, 0 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        /*
        dirtTilled = sheet.crop(0 * WIDTH_IN_PIXEL, 1 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        dirtSeed = sheet.crop(0 * WIDTH_IN_PIXEL, 2 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        */
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
        signPostNotTransparent = sheet.crop(1 * WIDTH_IN_PIXEL, 4 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        fence = sheet.crop(1 * WIDTH_IN_PIXEL, 5 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        dirtWalkway = sheet.crop(0 * WIDTH_IN_PIXEL, 6 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        signPostTransparent = sheet.crop(1 * WIDTH_IN_PIXEL, 6 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        berry = sheet.crop(0 * WIDTH_IN_PIXEL, 18 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        rockMountain = sheet.crop(1 * WIDTH_IN_PIXEL, 18 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        flowerYellow = sheet.crop(2 * WIDTH_IN_PIXEL, 18 * HEIGHT_IN_PIXEL,
                1 * WIDTH_IN_PIXEL, 1* HEIGHT_IN_PIXEL);
        flowerPurple = sheet.crop(2 * WIDTH_IN_PIXEL, 19 * HEIGHT_IN_PIXEL,
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

        // ************************************************************
        // |+|+|+|+|+|+|+| ITEM: TOOLS (TIER0 && TIER1 |+|+|+|+|+|+|+|
        // ************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Tools.png"));
        wateringCan = sheet.crop(6, 10, 16, 12);
        scythe = sheet.crop(22, 8, 16, 14);
        shovel = sheet.crop(38, 6, 14, 16);
        hammer = sheet.crop(54, 8, 15, 14);
        axe = sheet.crop(71, 7, 13, 15);
        goldSprinkler = sheet.crop(7, 26, 15, 12);
        goldScythe = sheet.crop(22, 24, 16, 14);
        goldShovel = sheet.crop(39, 22, 13, 16);
        goldAxe = sheet.crop(54, 22, 15, 16);
        goldHammer = sheet.crop(70, 23, 16, 15);

        // ****************************************************************
        // |+|+|+|+|+|+|+| DIRT and CROPS (DRY && WATERED) |+|+|+|+|+|+|+|
        // ****************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Crops.png"));
        dirtTilledDry = sheet.crop(32, 41, 16, 16);
        dirtTilledWatered = sheet.crop(50, 41, 16, 16);
        dirtSeedsDry = sheet.crop(99, 41, 16, 16);
        dirtSeedsWatered = sheet.crop(117, 41, 16, 16);
        secretGardenEmpty = sheet.crop(171, 41, 16, 16);
        secretGardenFlower = sheet.crop(189, 41, 16, 16);
        turnip0Whole = sheet.crop(13, 171, 16, 16);
        turnip0Fragmented = sheet.crop(31, 171, 16, 16);
        turnip1Dry = sheet.crop(13, 135, 16, 16);
        turnip1Watered = sheet.crop(31, 135, 16, 16);
        turnip2Dry = sheet.crop(13, 153, 16, 16);
        turnip2Watered = sheet.crop(31, 153, 16, 16);
        potato0Whole = sheet.crop(75, 171, 16, 16);
        potato0Fragmented = sheet.crop(93, 171, 16, 16);
        potato1Dry = sheet.crop(75, 135, 16, 16);
        potato1Watered = sheet.crop(93, 135, 16, 16);
        potato2Dry = sheet.crop(75, 153, 16, 16);
        potato2Watered = sheet.crop(93, 153, 16, 16);
        tomato0Whole = sheet.crop(139, 207, 16, 16);
        tomato0Fragmented = sheet.crop(157, 207, 16, 16);
        tomato1Dry = sheet.crop(139, 135, 16, 16);
        tomato1Watered = sheet.crop(157, 135, 16, 16);
        tomato2Dry = sheet.crop(139, 153, 16, 16);
        tomato2Watered = sheet.crop(157, 153, 16, 16);
        tomato3Dry = sheet.crop(139, 171, 16, 16);
        tomato3Watered = sheet.crop(157, 171, 16, 16);
        tomato4Dry = sheet.crop(139, 189, 16, 16);
        tomato4Watered = sheet.crop(157, 189, 16, 16);
        corn0Whole = sheet.crop(202, 207, 16, 16);
        corn0Fragmented = sheet.crop(220, 207, 16, 16);
        corn1Dry = sheet.crop(202, 135, 16, 16);
        corn1Watered = sheet.crop(220, 135, 16, 16);
        corn2Dry = sheet.crop(202, 153, 16, 16);
        corn2Watered = sheet.crop(220, 153, 16, 16);
        corn3Dry = sheet.crop(202, 171, 16, 16);
        corn3Watered = sheet.crop(220, 171, 16, 16);
        corn4Dry = sheet.crop(202, 189, 16, 16);
        corn4Watered = sheet.crop(220, 189, 16, 16);

        // **************************************************************
        // |+|+|+|+|+|+|+| PLAYER MOVEMENTS && HONEY POT |+|+|+|+|+|+|+|
        // **************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/Game Boy Advance - Kingdom Hearts Chain of Memories - Winnie the Pooh.png"));
        honeyPot = sheet.crop(318, 1556, 38, 37);
        playerUpDefault = sheet.crop(314, 1063, 20, 36);
        playerDownDefault = sheet.crop(178, 1061, 20, 38);
        playerLeftDefault = sheet.crop(111, 1062, 17, 37);
        playerRightDefault = sheet.crop(247, 1062, 17, 37);

        playerUpLeftDefault = sheet.crop(349, 1064, 17, 35);
        playerUpRightDefault = sheet.crop(281, 1064, 17, 35);
        playerDownLeftDefault = sheet.crop(145, 1060, 17, 39);
        playerDownRightDefault = sheet.crop(213, 1060, 17, 39);

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


        // *******************************************
        // |+|+|+|+|+|+|+| ENTITY: DOG |+|+|+|+|+|+|+|
        // *******************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Kero Dog.png"));
        xDogUp = new BufferedImage[5];
        xDogUp[0] = sheet.crop(6, 91, 13, 17);
        xDogUp[1] = sheet.crop(36, 91, 13, 18);
        xDogUp[2] = sheet.crop(66, 91, 13, 18);
        xDogUp[3] = sheet.crop(96, 91, 13, 18);
        xDogUp[4] = sheet.crop(126, 91, 13, 18);

        xDogDown = new BufferedImage[5];
        xDogDown[0] = sheet.crop(5, 61, 15, 17);
        xDogDown[1] = sheet.crop(37, 61, 12, 17);
        xDogDown[2] = sheet.crop(65, 62, 15, 15);
        xDogDown[3] = sheet.crop(95, 63, 15, 14);
        xDogDown[4] = sheet.crop(126, 62, 13, 16);

        xDogLeft = new BufferedImage[5];
        xDogLeft[0] = sheet.crop(3, 3, 20, 13);
        xDogLeft[1] = sheet.crop(30, 1, 25, 17);
        xDogLeft[2] = sheet.crop(63, 3, 20, 13);
        xDogLeft[3] = sheet.crop(93, 2, 20, 15);
        xDogLeft[4] = sheet.crop(123, 0, 19, 20);

        xDogLeftPee = new BufferedImage[5];
        xDogLeftPee[0] = sheet.crop(6, 33, 19, 14);
        xDogLeftPee[1] = sheet.crop(34, 33, 18, 14);
        xDogLeftPee[2] = sheet.crop(63, 32, 18, 15);
        xDogLeftPee[3] = sheet.crop(92, 33, 20, 14);
        xDogLeftPee[4] = sheet.crop(120, 32, 25, 15);

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Kero Dog (right).png"));

        xDogRight = new BufferedImage[5];
        xDogRight[0] = sheet.crop(390, 3, 20, 13);
        xDogRight[1] = sheet.crop(358, 1, 25, 17);
        xDogRight[2] = sheet.crop(330, 3, 20, 13);
        xDogRight[3] = sheet.crop(300, 2, 20, 15);
        xDogRight[4] = sheet.crop(271, 0, 19, 20);

        // ********************************************************************************************
        // |+|+|+|+|+|+|+| ENTITY: DOG, COW, CHICKEN, HORSE, JACK_WALKING, JACK_RUNNING |+|+|+|+|+|+|+|
        // ********************************************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Creatures.png"));

        dogUp = new BufferedImage[3];
        dogUp[0] = sheet.crop(6, 141, 13, 18);
        dogUp[1] = sheet.crop(30, 142, 13, 17);
        dogUp[2] = sheet.crop(54, 141, 13, 18);

        dogDown = new BufferedImage[3];
        dogDown[0] = sheet.crop(6, 204, 13, 19);
        dogDown[1] = sheet.crop(30, 205, 13, 18);
        dogDown[2] = sheet.crop(54, 204, 13, 19);

        dogLeft = new BufferedImage[3];
        dogLeft[0] = sheet.crop(3, 242, 19, 13);
        dogLeft[1] = sheet.crop(27, 242, 19, 13);
        dogLeft[2] = sheet.crop(51, 242, 19, 13);

        dogRight = new BufferedImage[3];
        dogRight[0] = sheet.crop(3, 178, 19, 13);
        dogRight[1] = sheet.crop(27, 178, 19, 13);
        dogRight[2] = sheet.crop(51, 178, 19, 13);


        cowUp = new BufferedImage[3];
        cowUp[0] = sheet.crop(77, 139, 15, 21);
        cowUp[1] = sheet.crop(101, 139, 15, 21);
        cowUp[2] = sheet.crop(125, 139, 15, 21);

        cowDown = new BufferedImage[3];
        cowDown[0] = sheet.crop(77, 205, 15, 19);
        cowDown[1] = sheet.crop(101, 205, 15, 19);
        cowDown[2] = sheet.crop(125, 205, 15, 19);

        cowLeft = new BufferedImage[3];
        cowLeft[0] = sheet.crop(74, 240, 21, 15);
        cowLeft[1] = sheet.crop(98, 239, 21, 16);
        cowLeft[2] = sheet.crop(122, 240, 21, 15);

        cowRight = new BufferedImage[3];
        cowRight[0] = sheet.crop(74, 176, 21, 15);
        cowRight[1] = sheet.crop(98, 175, 21, 16);
        cowRight[2] = sheet.crop(122, 176, 21, 15);


        chickenUp = new BufferedImage[3];
        chickenUp[0] = sheet.crop(147, 144, 16, 16);
        chickenUp[1] = sheet.crop(172, 145, 14, 15);
        chickenUp[2] = sheet.crop(195, 144, 16, 16);

        chickenDown = new BufferedImage[3];
        chickenDown[0] = sheet.crop(147, 207, 16, 16);
        chickenDown[1] = sheet.crop(171, 208, 16, 15);
        chickenDown[2] = sheet.crop(195, 207, 16, 16);

        chickenLeft = new BufferedImage[3];
        chickenLeft[0] = sheet.crop(147, 239, 16, 16);
        chickenLeft[1] = sheet.crop(171, 240, 16, 15);
        chickenLeft[2] = sheet.crop(195, 239, 16, 16);

        chickenRight = new BufferedImage[3];
        chickenRight[0] = sheet.crop(147, 175, 16, 16);
        chickenRight[1] = sheet.crop(171, 176, 16, 15);
        chickenRight[2] = sheet.crop(195, 175, 16, 16);


        horseUp = new BufferedImage[3];
        horseUp[0] = sheet.crop(222, 137, 12, 22);
        horseUp[1] = sheet.crop(246, 137, 12, 22);
        horseUp[2] = sheet.crop(270, 137, 12, 22);

        horseDown = new BufferedImage[3];
        horseDown[0] = sheet.crop(222, 204, 12, 19);
        horseDown[1] = sheet.crop(246, 204, 12, 19);
        horseDown[2] = sheet.crop(270, 204, 12, 19);

        horseLeft = new BufferedImage[3];
        horseLeft[0] = sheet.crop(216, 237, 24, 18);
        horseLeft[1] = sheet.crop(240, 235, 24, 20);
        horseLeft[2] = sheet.crop(264, 237, 24, 18);

        horseRight = new BufferedImage[3];
        horseRight[0] = sheet.crop(216, 173, 24, 18);
        horseRight[1] = sheet.crop(240, 171, 24, 20);
        horseRight[2] = sheet.crop(264, 173, 24, 18);


        jackWalkingUp = new BufferedImage[3];
        jackWalkingUp[0] = sheet.crop(76, 7, 16, 24);
        jackWalkingUp[1] = sheet.crop(100, 8, 16, 23);
        jackWalkingUp[2] = sheet.crop(124, 7, 16, 24);

        jackWalkingDown = new BufferedImage[3];
        jackWalkingDown[0] = sheet.crop(76, 71, 16, 24);
        jackWalkingDown[1] = sheet.crop(100, 72, 16, 23);
        jackWalkingDown[2] = sheet.crop(124, 71, 16, 24);

        jackWalkingLeft = new BufferedImage[3];
        jackWalkingLeft[0] = sheet.crop(76, 103, 16, 24);
        jackWalkingLeft[1] = sheet.crop(100, 104, 16, 23);
        jackWalkingLeft[2] = sheet.crop(124, 103, 16, 24);

        jackWalkingRight = new BufferedImage[3];
        jackWalkingRight[0] = sheet.crop(76, 39, 16, 24);
        jackWalkingRight[1] = sheet.crop(100, 40, 16, 23);
        jackWalkingRight[2] = sheet.crop(124, 39, 16, 24);


        jackRunningUp = new BufferedImage[3];
        jackRunningUp[0] = sheet.crop(148, 5, 16, 26);
        jackRunningUp[1] = sheet.crop(172, 8, 16, 23);
        jackRunningUp[2] = sheet.crop(196, 5, 16, 26);

        jackRunningDown = new BufferedImage[3];
        jackRunningDown[0] = sheet.crop(149, 70, 15, 25);
        jackRunningDown[1] = sheet.crop(172, 72, 16, 23);
        jackRunningDown[2] = sheet.crop(197, 70, 15, 25);

        jackRunningLeft = new BufferedImage[3];
        jackRunningLeft[0] = sheet.crop(148, 102, 16, 25);
        jackRunningLeft[1] = sheet.crop(172, 104, 16, 23);
        jackRunningLeft[2] = sheet.crop(196, 102, 16, 25);

        jackRunningRight = new BufferedImage[3];
        jackRunningRight[0] = sheet.crop(148, 38, 16, 25);
        jackRunningRight[1] = sheet.crop(172, 40, 16, 23);
        jackRunningRight[2] = sheet.crop(196, 38, 16, 25);






        // ***********************************************************************************
        // |+|+|+|+|+|+|+| ENTITY: TRAVELINGFENCE (name == The Finn) (HAWKER) |+|+|+|+|+|+|+|
        // ***********************************************************************************

        sheet = new SpriteSheet(ImageLoader.loadImage("/textures/entities/SNES - Harvest Moon - Hawker and Peddler.png"));
        hawkerUp = new BufferedImage[3];
        hawkerUp[0] = sheet.crop(12, 39, 19, 25);
        hawkerUp[1] = sheet.crop(44, 39, 19, 24);
        hawkerUp[2] = sheet.crop(76, 39, 19, 25);

        hawkerDown = new BufferedImage[3];
        hawkerDown[0] = sheet.crop(12, 6, 19, 26);
        hawkerDown[1] = sheet.crop(44, 6, 19, 25);
        hawkerDown[2] = sheet.crop(75, 6, 19, 26);

        hawkerLeft = new BufferedImage[3];
        hawkerLeft[0] = sheet.crop(8, 70, 28, 26);
        hawkerLeft[1] = sheet.crop(40, 70, 27, 25);
        hawkerLeft[2] = sheet.crop(72, 70, 28, 26);

        hawkerRight = new BufferedImage[3];
        hawkerRight[0] = sheet.crop(6, 102, 28, 26);
        hawkerRight[1] = sheet.crop(39, 102, 27, 25);
        hawkerRight[2] = sheet.crop(70, 102, 28, 26);

        // ************************************************************
        // |+|+|+|+|+|+|+| STATE (HOMESTATE BACKGROUND) |+|+|+|+|+|+|+|
        // ************************************************************
        //homeStateBackground = ImageLoader.loadImage("/worlds/Game Boy GBC - Harvest Moon GBC - Home Background.png");
        homeStateBackground2 = ImageLoader.loadImage("/worlds/HomeState background2.png");
        chickenCoopStateBackground = ImageLoader.loadImage("/worlds/ChickenCoopState background.jpg");
        cowBarnStateBackground = ImageLoader.loadImage("/worlds/CowBarnState background.jpg");
        toolShedStateBackground = ImageLoader.loadImage("/worlds/ToolShedState background.png");
        crossroadStateBackground = ImageLoader.loadImage("/worlds/CrossroadState background.png");
        mountainStateBackground = ImageLoader.loadImage("/worlds/MountainState background (no entities).png");
        //mountainStateBackground = ImageLoader.loadImage("/worlds/MountainState background (entities as tiles).png");

        // **********************************************************************
        // |+|+|+|+|+|+|+| LOAD MAP/LEVEL VIA IMAGE (RGB/PIXELS) |+|+|+|+|+|+|+|
        // **********************************************************************

        tilesGameViaRGB = ImageLoader.loadImage("/worlds/chapter1 tiles (rgb).png");
        tilesHomeViaRGB = ImageLoader.loadImage("/worlds/HomeState tiles (rgb).png");
        tilesChickenCoopViaRGB = ImageLoader.loadImage("/worlds/ChickenCoopState tiles (rgb).png");
        tilesCowBarnViaRGB = ImageLoader.loadImage("/worlds/CowBarnState tiles (rgb).png");
        tilesToolShedViaRGB = ImageLoader.loadImage("/worlds/ToolShedState tiles (rgb).png");
        tilesCrossroadViaRGB = ImageLoader.loadImage("/worlds/CrossroadState tiles (rgb).png");
        tilesMountainViaRGB = ImageLoader.loadImage("/worlds/MountainState tiles (rgb).png");
        entitiesGameViaRGB = ImageLoader.loadImage("/worlds/chapter1 entities (rgb).png");
        entitiesHomeViaRGB = ImageLoader.loadImage("/worlds/HomeState entities (rgb).png");
        entitiesChickenCoopViaRGB = ImageLoader.loadImage("/worlds/ChickenCoopState entities (rgb).png");
        entitiesCowBarnViaRGB = ImageLoader.loadImage("/worlds/CowBarnState entities (rgb).png");
        entitiesToolShedViaRGB = ImageLoader.loadImage("/worlds/ToolShedState entities (rgb).png");
        entitiesCrossroadViaRGB = ImageLoader.loadImage("/worlds/CrossroadState entities (rgb).png");
        entitiesMountainViaRGB = ImageLoader.loadImage("/worlds/MountainState entities (rgb).png");

    }

} // **** end Assets class ****