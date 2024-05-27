package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Laser extends Actor {
    Rectangle bounds;
    boolean upsideDown;
    AssetManager manager;
    private float timer;
    Laser()
    {
        setSize(64, 230);
        //setSize(64, (MathUtils.random(100,400)));
        bounds = new Rectangle();
        setVisible(false);
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
        batch.draw( manager.get("rayo_laser.png", Texture.class), getX(), getY() );
    }
    public Rectangle getBounds() {
        return bounds;
    }
    public boolean isUpsideDown() {
        return upsideDown;
    }
    public void setUpsideDown(boolean upsideDown) {
        this.upsideDown = upsideDown;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}