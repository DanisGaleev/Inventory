package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Equipment {
    public static final int NUM_SLOT = 8;

    private Item[] equips;
    private Vector2[] position;

    public Equipment() {
        equips = new Item[NUM_SLOT];
        position = new Vector2[NUM_SLOT];

        position[0] = new Vector2(80, 440);
        position[1] = new Vector2(200, 440);
        position[2] = new Vector2(80, 320);
        position[3] = new Vector2(200, 320);
        position[4] = new Vector2(320, 320);
        position[5] = new Vector2(200, 200);
        position[6] = new Vector2(320, 200);
        position[7] = new Vector2(200, 80);
    }

    public boolean addEquip(Item equip) {
        if (equips[equip.getType() - 2] == null) {
            equips[equip.getType() - 2] = equip;
            equip.setEquipped(true);
            return true;
        }
        return false;
    }

    public Item removeEquip(int index) {
        Item ret = null;
        if (equips[index] != null) {
            ret = equips[index];
            equips[index] = null;
            return ret;
        }
        return null;
    }

    public Item getEquipAt(int index) {
        return equips[index];
    }
}
