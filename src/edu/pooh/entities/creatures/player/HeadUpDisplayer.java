package edu.pooh.entities.creatures.player;

import edu.pooh.gfx.Assets;
import edu.pooh.gfx.Text;
import edu.pooh.inventory.Inventory;
import edu.pooh.items.Item;
import edu.pooh.items.crops.tier0.WateringCan;
import edu.pooh.main.Game;
import edu.pooh.main.Handler;

import java.awt.*;
import java.io.Serializable;

import static edu.pooh.entities.creatures.player.StaminaModule.SanityLevel.*;

public class HeadUpDisplayer
        implements Serializable {

    private Handler handler;
    private Player player;

    public HeadUpDisplayer(Handler handler, Player player) {
        this.handler = handler;
        this.player = player;
    } // **** end HeadUpDisplayer(Handler, Player) constructor ****

    public void tick() {
        //TODO: tick() can be used as a count-down ticker for future ComponentHUD instances.
    }

    public void render(Graphics g) {
        // CANNABIS COUNTER VISUAL (TOP-LEFT CORNER)
        g.setColor(Color.BLUE);
        g.drawRect((25 - 2), (25 - 2), (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        Text.drawString(g, Integer.toString(handler.getResourceManager().getCurrencyUnitCount()),
                (25 + (Item.ITEM_WIDTH / 2)), (25 + (Item.ITEM_HEIGHT / 2)), true, Color.YELLOW, Assets.font28);

        // IN-GAME TIME (YELLOW) and REAL-LIFE ELAPSED SECONDS (BLUE) (TOP-CENTER OF SCREEN)
        Text.drawString(g, handler.getTimeManager().translateElapsedRealSecondsToGameHoursMinutes(),
                (handler.getWidth() / 2), 30, true, Color.YELLOW, Assets.font28);
        Text.drawString(g, Integer.toString(handler.getTimeManager().elapsedRealSeconds),
                (handler.getWidth() / 2), 55, true, Color.BLUE, Assets.font28);

        // CURRENT SELECTED ITEM FROM INVENTORY (TOP-RIGHT CORNER)
        Inventory inventory = player.getInventory();
        g.setColor(Color.BLUE);
        g.drawRect((Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) - 2), 25 - 2,
                (Item.ITEM_WIDTH + 3), (Item.ITEM_HEIGHT + 3));
        g.drawImage( inventory.getItem(inventory.getIndex()).getTexture(),
                (Game.  WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH)), 25,
                Item.ITEM_WIDTH, Item.ITEM_HEIGHT, null);
        if (inventory.getItem(inventory.getIndex()).getId() == Item.ID.WATERING_CAN) {
            WateringCan temp = (WateringCan)inventory.getItem(inventory.getIndex());
            Text.drawString(g, Integer.toString(temp.getCountWater()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.BLUE, Assets.font28);
        } else {
            Text.drawString(g, Integer.toString(inventory.getItem(inventory.getIndex()).getCount()),
                    (Game.WIDTH_OF_FRAME - (25 + Item.ITEM_WIDTH) + (Item.ITEM_WIDTH / 2)),
                    25 + Item.ITEM_HEIGHT + 15, true, Color.YELLOW, Assets.font28);
        }

        // STAMINA TRACKER VISUAL (LEFT TOP-ISH OF SCREEN)
        StaminaModule playerStaminaModule = player.getStaminaModule();
        g.setColor(Color.BLUE);
        g.fillRect(33, 81, 15, playerStaminaModule.getStaminaBase() + 4);
        g.setColor(Color.YELLOW);
        g.fillRect(35, 83 + (playerStaminaModule.getStaminaBase() - playerStaminaModule.getStaminaCurrent()),
                11, playerStaminaModule.getStaminaCurrent());

        // SANITY LEVEL
        if (playerStaminaModule.getSanityLevel() == SANE) {
            Text.drawString(g, "sanityLevel: " + playerStaminaModule.getSanityLevel(),
                    (int)(player.getX() - handler.getGameCamera().getxOffset() - 34),
                    (int)(player.getY() - handler.getGameCamera().getyOffset() - 10), false, Color.GREEN, Assets.font14);
        } else if ( (playerStaminaModule.getSanityLevel() == FRAGMENTING) ||
                (playerStaminaModule.getSanityLevel() == FRAGMENTED) ) {
            Text.drawString(g, "sanityLevel: " + playerStaminaModule.getSanityLevel(),
                    (int)(player.getX() - handler.getGameCamera().getxOffset() - 34),
                    (int)(player.getY() - handler.getGameCamera().getyOffset() - 10), false, Color.YELLOW, Assets.font14);
        } else if (playerStaminaModule.getSanityLevel() == INSANE) {
            Text.drawString(g, "sanityLevel: " + playerStaminaModule.getSanityLevel(),
                    (int)(player.getX() - handler.getGameCamera().getxOffset() - 34),
                    (int)(player.getY() - handler.getGameCamera().getyOffset() - 10), false, Color.RED, Assets.font14);
        } else if (playerStaminaModule.getSanityLevel() == GUANO) {
            Text.drawString(g, "sanityLevel: " + playerStaminaModule.getSanityLevel(),
                    (int)(player.getX() - handler.getGameCamera().getxOffset() - 34),
                    (int)(player.getY() - handler.getGameCamera().getyOffset() - 10), false, Color.BLACK, Assets.font14);
        }
    }

    // GETTERS AND SETTERS

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

} // **** end HeadUpDisplayer class ****