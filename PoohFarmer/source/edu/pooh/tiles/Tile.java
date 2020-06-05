package edu.pooh.tiles;

import edu.pooh.gfx.Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.Serializable;

public class Tile
        implements Serializable {

    // STATIC INSTANCES HERE

    public static Tile[] tiles = new Tile[256];

    /*
    public static Tile dirtNormalTile = new Tile(Assets.dirtNormal, 0) {
        @Override
        public boolean isSolid() {
            return false;
        }
    };
    */

    public static Tile fenceTile = new Tile(Assets.fence, 1);
    public static Tile dirtWalkway = new Tile(Assets.dirtWalkway, 2) {
        @Override
        public boolean isSolid() {
            return false;
        }
    };
    //not really a Tile? more of a StaticEntity?
    public static Tile signPostTile = new Tile(Assets.signPostTransparent, 3);


    private static int idMultiTiles = 100;
    // Multiple-spanning-Tile.
    static {

        System.out.println("idMultiTiles BEFORE home5x4: " + idMultiTiles);
        for (int y = 0; y < 4; y++) {
            for (int x = 0; x < 5; x++) {
                // if entry-way, override to be walked-on.
                if (x == 2 && y == 3) {
                    new Tile(Assets.home5x4[y][x], idMultiTiles) {
                        @Override
                        public boolean isSolid() {
                            return false;
                        }
                    };
                } else {    // otherwise just make a regular home tile.
                    new Tile(Assets.home5x4[y][x], idMultiTiles);
                }

                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER home5x4: " + idMultiTiles);

        // at this point idMultiTiles is 120 (to be used by the next building).
        System.out.println("idMultiTiles BEFORE cowBarn5x5: " + idMultiTiles);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 5; x++) {
                // if entry-way, override to be walked-on.
                if (x == 2 && y == 4) {
                    new Tile(Assets.cowBarn5x5[y][x], idMultiTiles) {
                        @Override
                        public boolean isSolid() {
                            return false;
                        }
                    };
                } else { // otherwise just make a regular cowBarn tile.
                    new Tile(Assets.cowBarn5x5[y][x], idMultiTiles);
                }
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER cowBarn5x5: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE silos5x6: " + idMultiTiles);
        for (int y = 0; y < 6; y++) {
            for (int x = 0; x < 5; x++) {
                new Tile(Assets.silos5x6[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER silos5x6: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE chickenCoop4x5: " + idMultiTiles);
        for (int y = 0; y < 5; y++) {
            for (int x = 0; x < 4; x++) {
                // if entry-way, override to be walked-on.
                if (x == 1 && y == 4) {
                    new Tile(Assets.chickenCoop4x5[y][x], idMultiTiles) {
                        @Override
                        public boolean isSolid() {
                            return false;
                        }
                    };
                } else { // otherwise just make a regular chickenCoop tile.
                    new Tile(Assets.chickenCoop4x5[y][x], idMultiTiles);
                }

                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER chickenCoop4x5: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE toolShed5x5: " + idMultiTiles);
        for (int y = 0; y < 5; y ++) {
            for (int x = 0; x < 5; x++) {
                // if entry-way, override to be walked-on.
                if (x == 2 && y == 4) {
                    new Tile(Assets.toolShed5x5[y][x], idMultiTiles) {
                        @Override
                        public boolean isSolid() {
                            return false;
                        }
                    };
                } else { // otherwise just make a regular toolShed tile.
                    new Tile(Assets.toolShed5x5[y][x], idMultiTiles);
                }

                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER toolShed5x5: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE stable2x3: " + idMultiTiles);
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 2; x++) {
                new Tile(Assets.stable2x3[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER stable2x3: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE building2x3: " + idMultiTiles);
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 2; x++) {
                new Tile(Assets.building2x3[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER building2x3: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE shippingBin2x2: " + idMultiTiles);
        for (int y = 0; y < 2; y ++) {
            for (int x = 0; x < 2; x++) {
                new Tile(Assets.shippingBin2x2[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER shippingBin2x2: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE poolWater2x2: " + idMultiTiles);
        for (int y = 0; y < 2; y ++) {
            for (int x = 0; x < 2; x++) {
                new Tile(Assets.poolWater2x2[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER poolWater2x2: " + idMultiTiles);

        System.out.println("idMultiTiles BEFORE poolWater3x3: " + idMultiTiles);
        for (int y = 0; y < 3; y ++) {
            for (int x = 0; x < 3; x++) {
                new Tile(Assets.poolWater3x3[y][x], idMultiTiles);
                idMultiTiles++;
            }
        }
        System.out.println("idMultiTiles AFTER poolWater3x3: " + idMultiTiles);

    } // @@@@ end static initialization block @@@@

    // CLASS

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 64;

    protected transient BufferedImage texture;
    protected final int id;

    public Tile(BufferedImage texture, int id) {
        this.texture = texture;
        this.id = id;

        // CLEVER! confusing!
        tiles[id] = this;
    } // **** end Tile(BufferedImage, int) constructor ****

    public void tick() {

    }

    public void render(Graphics g, int x, int y) {
        g.drawImage(texture, x, y, TILE_WIDTH, TILE_HEIGHT, null);
    }

    public boolean isSolid() {
        return true;
    }

    public int getId() {
        return id;
    }

    public BufferedImage getTexture() {
        return texture;
    }

    public void setTexture(BufferedImage texture) {
        this.texture = texture;
    }

} // **** end Tile class ****