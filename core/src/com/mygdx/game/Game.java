package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.DragListener;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonWriter;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Game extends ApplicationAdapter {
    private Stage stage;
    private Item[] inventory;
    private Item[] equips;
    private int prevX, prevY, presX, presY;
    private static final Rectangle INVENTORY_AREA = new Rectangle(600, 160, 600, 400);
    private static final Rectangle EQUIPS_AREA = new Rectangle(200, 160, 100, 200);
    private static final float ItemWidth = 100;
    private static final float ItemHeight = 100;

    private boolean dragging = false;
    private boolean itemSelected = false;
    private Item currentItem;

    private void addInventoryEvent(final Item item) {
        item.getImage().clearListeners();
        item.getImage().addListener(new DragListener() {
            @Override
            public void dragStart(InputEvent event, float x, float y, int pointer) {
                prevX = (int) (item.getImage().getX() + item.getImage().getWidth() / 2);
                prevY = (int) (item.getImage().getY() + item.getImage().getHeight() / 2);
            }

            @Override
            public void drag(InputEvent event, float x, float y, int pointer) {
                item.getImage().moveBy(x - item.getImage().getWidth() / 2, y - item.getImage().getHeight() / 2);
            }

            @Override
            public void dragStop(InputEvent event, float x, float y, int pointer) {
                presX = (int) (item.getImage().getX() + item.getImage().getWidth() / 2);
                presY = (int) (item.getImage().getY() + item.getImage().getHeight() / 2);
                if (INVENTORY_AREA.contains(prevX, prevY) && INVENTORY_AREA.contains(presX, presY)) {
                    int hi = getIndexNearCor(presX, presY);
                    if (inventory[hi] == null) {
                        inventory[hi] = item;
                        inventory[hi].getImage().setPosition(getCorByIndex(hi).x, getCorByIndex(hi).y);
                        inventory[getIndexNearCor(prevX, prevY)] = null;
                    } else {
                        Item swap = inventory[hi];
                        inventory[getIndexNearCor(prevX, prevY)] = swap;
                        int index = getIndexNearCor(prevX, prevY);
                        inventory[index].getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                        inventory[hi] = item;
                        inventory[hi].getImage().setPosition(getCorByIndex(hi).x, getCorByIndex(hi).y);
                    }
                } else if (INVENTORY_AREA.contains(prevX, prevY) && !INVENTORY_AREA.contains(presX, presY)
                        && !EQUIPS_AREA.contains(presX, presY)) {
                    int index = getIndexNearCor(prevX, prevY);
                    inventory[getIndexNearCor(prevX, prevY)] = item;
                    item.getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                } else if (INVENTORY_AREA.contains(prevX, prevY) && EQUIPS_AREA.contains(presX, presY)) {
                    if (item.getType() >= 2) {
                        int hi = getIndexNearCor(presX, presY);
                        if (equips[hi] == null) {
                            equips[hi] = item;
                            equips[hi].getImage().setPosition(getCorByIndexEquip(hi).x, getCorByIndexEquip(hi).y);
                            inventory[getIndexNearCor(prevX, prevY)] = null;
                        } else {
                            float xx = equips[hi].getImage().getX();
                            float yy = equips[hi].getImage().getY();
                            Item swap = equips[hi];
                            equips[hi] = item;
                            equips[hi].getImage().setPosition(xx, yy);
                            inventory[getIndexNearCor(prevX, prevY)] = swap;
                            inventory[getIndexNearCor(prevX, prevY)].getImage().setPosition(prevX - ItemWidth / 2, prevY - ItemHeight / 2);
                        }
                    } else {
                        int index = getIndexNearCor(prevX, prevY);
                        inventory[getIndexNearCor(prevX, prevY)] = item;
                        item.getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                    }
                } else if (EQUIPS_AREA.contains(presX, presY) && EQUIPS_AREA.contains(prevX, prevY)) {
                    equips[getIndexNearCor(prevX, prevY)].getImage().setPosition(prevX - ItemWidth / 2, prevY - ItemHeight / 2);
                } else if (EQUIPS_AREA.contains(prevX, prevY) && !EQUIPS_AREA.contains(presX, presY)
                        && !INVENTORY_AREA.contains(presX, presY)) {
                    equips[getIndexNearCor(prevX, prevY)].getImage().setPosition(prevX - ItemWidth / 2, prevY - ItemHeight / 2);
                } else if (EQUIPS_AREA.contains(prevX, prevY) && INVENTORY_AREA.contains(presX, presY)) {
                    if (inventory[getIndexNearCor(presX, presY)] == null) {
                        int index = getIndexNearCor(presX, presY);
                        inventory[index] = item;
                        inventory[index].getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                        equips[getIndexNearCor(prevX, prevY)] = null;
                    } else {
                        equips[getIndexNearCor(prevX, prevY)].getImage().setPosition(prevX - ItemWidth / 2, prevY - ItemHeight / 2);
                    }
                }
                /*
                if (EQUIPS_AREA.contains(presX, presY)) {
                    int hi = getIndexNearCor(presX, presY);
                    if (INVENTORY_AREA.contains(prevX, prevY)) {
                        if (item.getType() == 2) {
                            if (equips[hi] == null) {
                                equips[hi] = item;
                                equips[hi].getImage().setPosition(presX - ItemWidth / 2, presY - ItemHeight / 2);
                                inventory[getIndexNearCor(prevX, prevY)] = null;
                            }
                        }
                    }

                } else {
                    System.out.println("kkkkk");
                    equips[getIndexNearCor(prevX, prevY)] = item;
                    item.getImage().setPosition(prevX - ItemWidth / 2, prevY - ItemHeight / 2);
                    System.out.println(item.getImage().getX() + " " + item.getImage().getY());
                }*//*
                if (INVENTORY_AREA.contains(presX, presY)) {
                    int hi = getIndexNearCor(presX, presY);
                    if (inventory[hi] == null) {
                        inventory[hi] = item;
                        inventory[hi].getImage().setPosition(getCorByIndex(hi).x, getCorByIndex(hi).y);
                        inventory[getIndexNearCor(prevX, prevY)] = null;
                    } else {
                        Item swap = inventory[hi];
                        inventory[getIndexNearCor(prevX, prevY)] = swap;
                        int index = getIndexNearCor(prevX, prevY);
                        inventory[index].getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                        inventory[hi] = item;
                        inventory[hi].getImage().setPosition(getCorByIndex(hi).x, getCorByIndex(hi).y);
                    }
                } else {
                    int index = getIndexNearCor(prevX, prevY);
                    inventory[getIndexNearCor(prevX, prevY)] = item;
                    item.getImage().setPosition(getCorByIndex(index).x, getCorByIndex(index).y);
                    System.out.println("999999");
                }
                */
            }
        });
    }


    private void addToStage(Actor actor) {
        stage.addActor(actor);
    }

    private int getIndexNearCor(int x, int y) {
        if (INVENTORY_AREA.contains(x, y))
            return (x - 600) / 100 + (y - 160) / 100 * 6;
        else if (EQUIPS_AREA.contains(x, y))
            return (x - 200) / 100 + (y - 160) / 100;
        return -1;
    }

    private Vector2 getCorByIndex(int index) {
        return new Vector2(600 + (index % 6) * 100, 160 + (index / 6) * 100);
    }

    private Vector2 getCorByIndexEquip(int index) {
        return new Vector2(200 + (index % 1) * 100, 160 + (index / 1) * 100);
    }

    @Override
    public void create() {
        stage = new Stage(new FitViewport(1280, 720));
        inventory = new Item[24];
        equips = new Item[2];
        Skin skin = new Skin();
        skin.add("ggg", new Texture("5.45_39.png"));
        skin.add("ddd", new Texture("7.62_39.png"));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                int x = 600 + 100 * j;
                int y = 160 + 100 * i;
                int index = j + i * 6;
                Image image = new Image(skin.getDrawable("ggg"));
                image.setPosition(x, y);
                inventory[index] = new Item("", "", image, 2);
                addToStage(inventory[index].getImage());
                addInventoryEvent(inventory[index]);
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 1; j++) {
                int x = 200 + 100 * j;
                int y = 160 + 100 * i;
                int index = j + i * 1;
                Image image = new Image(skin.getDrawable("ddd"));
                image.setPosition(x, y);
                equips[index] = new Item("", "", image, 2);
                addToStage(equips[index].getImage());
                addInventoryEvent(equips[index]);
            }
        }
        Json json = new Json(JsonWriter.OutputType.json);
        WeaponArray weaponArray = json.fromJson(WeaponArray.class, Gdx.files.internal("weapon.json"));
        for (Weapon w : weaponArray.weapons) {
            System.out.println(w.getName() + " " + w.getDamage());
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render() {
        ScreenUtils.clear(1, 1, 1, 1);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
    }
}
