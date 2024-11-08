package com.libgdx.newgame.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player extends  Initialize{

    private float zoneWidth, zoneHeight;

    public Player(float x, float y, float speed, float zoneWidth, float zoneHeight, String sprite) {
        super(x,y,speed,sprite);
        this.zoneWidth = zoneWidth;
        this.zoneHeight = zoneHeight;
    }

    @Override
    public void update(float deltaTime) {
        // Contrôles du joueur avec les touches du clavier
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            y += speed * deltaTime;
            currentImage = upImage;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
            y -= speed * deltaTime;
            currentImage = downImage;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            x -= speed * deltaTime;
            currentImage = leftImage;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            x += speed * deltaTime;
            currentImage = rightImage;
        }

        // Limiter les déplacements à la zone délimitée
        if (x < 0) x = 0;
        if (x > zoneWidth - sprite.getWidth()) x = zoneWidth - sprite.getWidth();
        if (y < 0) y = 0;
        if (y > zoneHeight - sprite.getHeight()) y = zoneHeight - sprite.getHeight();

        sprite.setPosition(x, y);
    }

    public void dispose() { sprite.getTexture().dispose();}
    public float getZoneWidth() { return zoneWidth; }
    public float getZoneHeight() { return zoneHeight; }
}
