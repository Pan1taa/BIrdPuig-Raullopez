package com.mygdx.game;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Player extends Actor {
    Rectangle bounds;
    AssetManager manager;
    float speedy, gravity;
    boolean fly;
    boolean inAir;
    float timer;
    Player()
    {
        setX(200);
        setY(125);
        setSize(75,55);
        bounds = new Rectangle();

        speedy = 0;
        gravity = 1200f;

    }
    @Override
    public void act(float delta){
    //Actualitza la posició del jugador amb la velocitat vertical
    moveBy(0, speedy * delta);
    //Actualitza la velocitat vertical amb la gravetat
    speedy -= gravity * delta;
    bounds.set(getX(), getY(), getWidth(), getHeight());
    timer += delta;
    if(timer>0.9)timer = 0;


    }

    void impulso()
    {
        speedy = 350f;
    }


    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);

        if(inAir){
            if (fly) {
                batch.draw(manager.get("perrovolando.png", Texture.class),
                        getX(), getY());
            } else {
                batch.draw(manager.get("perronovolando.png", Texture.class),
                        getX(), getY());
            }
        }
        else if(timer<0.3f){
            batch.draw(manager.get("perrosuelo1.png", Texture.class),
                    getX(), getY());
        }
        else if (timer < 0.6f && timer >0.3f){
            batch.draw(manager.get("perrosuelo2.png", Texture.class),
                    getX(), getY());
        }
        else {
            batch.draw(manager.get("perrosuelo3.png", Texture.class),
                    getX(), getY());
        }

    }
    public Rectangle getBounds() {
        return bounds;
    }
    public void setManager(AssetManager manager) {
        this.manager = manager;
    }
}
