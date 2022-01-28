package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

import javafx.stage.Stage;

public class InventoryUI {
    private static final int NUM_COLS = 6;
    private static final int SLOT_WIDTH = 80;
    private static final int SLOT_HEIGTH = 80;
    private static final Rectangle EQUIPS_AREA = new Rectangle(80, 80, 320, 440);
    private static final Rectangle INVENTORY_AREA = new Rectangle(520, 80, 680, 440);

    private boolean dragging = false;
    // to differentiate between dragging and clicking
    private int prevX, prevY;
    private boolean itemSelected = false;
    private Item currentItem;

    public InventoryUI() {

    }

    private void addInventory(Stage s) {

    }
}
