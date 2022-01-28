package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class Item {
    private String name; // name of item
    private String information; // information about item
    private Image image; // item's image
    private int type;
    private boolean equipped = false;
    /*
    0 - other
    1 - bullets
    2 - weapon
     */
    public Item(String name, String information, Image image, int type){
        this.name = name;
        this.information =information;
        this.image = image;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInformation() {
        return information;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }



    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(boolean equipped) {
        this.equipped = equipped;
    }
}
