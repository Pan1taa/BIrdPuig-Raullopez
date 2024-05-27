package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Coin extends Actor {
    private Texture texture;
    private AssetManager manager;
    private int positionX;
    private int positionY;
    private int value;
    private boolean collected;
    private Rectangle bounds;
    private String imageCoin;
    private float timer;

    public Coin(int positionX, int positionY, String imageCoin, int value, AssetManager manager) {
        this.manager = manager;
        this.imageCoin = imageCoin;
        this.value = value;
        this.manager.load(imageCoin, Texture.class);
        this.manager.finishLoading(); // espera a que se cargue la textura
        texture = this.manager.get(imageCoin, Texture.class);
        this.positionX = positionX;
        this.positionY = positionY;
        collected = false;
        bounds = new Rectangle(positionX, positionY, texture.getWidth(), texture.getHeight());
    }

    @Override
    public void act(float delta) {
        timer += delta;
        moveBy(-200 * delta, 0);
        bounds.set(getX(), getY(), getWidth(), getHeight());
        if(!isVisible())
            setVisible(true);
        if (getX() < -64)
            remove();

    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        if (!collected) {
            batch.draw( manager.get(imageCoin, Texture.class), getX(), getY() );
        }
    }

    public void collect() {
        collected = true;
    }

    public boolean isCollected() {
        return collected;
    }

    public Rectangle getBounds() {
        return bounds;
    }

    public AssetManager getManager() {
        return manager;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}