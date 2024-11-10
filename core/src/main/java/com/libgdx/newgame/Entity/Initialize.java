package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;


public abstract class Initialize {
    protected float x;
    protected float y;
    protected float speed;
    protected Texture texture;
    protected TextureRegion currentImage;
    protected TextureRegion leftImage, rightImage, upImage, downImage;
    protected Sprite sprite;

    public Initialize(float x, float y, float speed, String sprite) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.texture = new Texture(sprite);

        downImage = new TextureRegion(texture, 170, 0, 80, 90);
        upImage = new TextureRegion(texture, 90, 0, 80, 90);
        leftImage = new TextureRegion(texture, 0, 0, 80, 90);
        rightImage = new TextureRegion(texture, 250, 0, 80, 90);

        currentImage = rightImage;

        this.sprite = new Sprite(currentImage);
        this.sprite.setPosition(x, y);
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.setRegion(currentImage);
        sprite.draw(batch);
        batch.end();
    }

    public void update(float deltaTime) {
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getSpeed() {
        return speed;
    }

    public Texture getTexture() {
        return texture;
    }

    public TextureRegion getLeftImage() {
        return leftImage;
    }

    public TextureRegion getUpImage() {
        return upImage;
    }

    public TextureRegion getRightImage() {
        return rightImage;
    }

    public TextureRegion getDownImage() {
        return downImage;
    }

    public TextureRegion getCurrentImage() {
        return currentImage;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
