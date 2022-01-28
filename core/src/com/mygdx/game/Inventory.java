package com.mygdx.game;

public class Inventory {
    public static final int NUM_SLOT = 24;
    private Item[] inventory;

    public Inventory() {
        inventory = new Item[NUM_SLOT];
    }

    public int getFirstFreeSlot() {
        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] == null) return i;
        }
        return -1;
    }

    public Item getItem(int index) {
        return inventory[index];
    }

    public boolean isFreeSlot(int index) {
        return inventory[index] == null;
    }

    public boolean addItem(Item item) {
        int i = getFirstFreeSlot();
        if (i != -1) {
            inventory[i] = item;
         //   item.setIndex(i);
            return true;
        }
        return false;
    }

    public boolean addItemAtIndex(Item item, int index) {
        if (isFreeSlot(index)) {
            inventory[index] = item;
            //item.setIndex(index);
            return true;
        }
        return false;
    }

    public void removeItem(int index) {
        if (inventory[index] != null) inventory[index] = null;
    }

    public Item takeItem(int index) {
        Item ret = null;
        if (inventory[index] != null) {
            ret = inventory[index];
            inventory[index] = null;
            return ret;
        }
        return null;
    }

    public boolean isFull() {
        for (int i = 0; i < NUM_SLOT; i++) {
            if (inventory[i] == null) return false;
        }
        return true;
    }

    public void clear() {
        for (int i = 0; i < NUM_SLOT; i++) {
            removeItem(i);
        }
    }
}
