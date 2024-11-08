package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class Enemy extends Initialize {

    private float zoneX, zoneY, zoneWidth, zoneHeight;  // Zone dans laquelle l'ennemi se déplace
    private ShapeRenderer shapeRenderer;
    private float velocityX;  // Vitesse horizontale

    public Enemy(float x, float y,  float speed, float zoneX, float zoneY, float zoneWidth, float zoneHeight, String sprite) {
        super(x,y,speed, sprite);
        this.zoneX = zoneX;
        this.zoneY = zoneY;
        this.zoneWidth = zoneWidth;
        this.zoneHeight = zoneHeight;
        this.shapeRenderer = new ShapeRenderer();

        // Initialiser la vélocité sur la ligne horizontale
        this.velocityX = speed;
    }

    @Override
    public void update(float deltaTime) {
        // Déplacement horizontal seulement
        x += velocityX * deltaTime;

        // Limiter l'ennemi à la zone horizontale (rester dans la zone définie)
        if (x < zoneX) {
            x = zoneX;
            velocityX = speed; // Change la direction si l'ennemi arrive au bord gauche
            currentImage = rightImage;
        }
        if (x > zoneX + zoneWidth - sprite.getWidth()) {
            x = zoneX + zoneWidth - sprite.getWidth();
            velocityX = -speed;  // Change la direction si l'ennemi arrive au bord droit
            currentImage = leftImage;
        }

        // etre sur que la position de l'ennemi reste la même en y
        y = zoneY + zoneHeight / 2 - sprite.getHeight() / 2;

        sprite.setPosition(x, y);
    }


    // Affichage de la zone où l'ennemi se déplace
    public void renderZone() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(zoneX, zoneY, zoneWidth, zoneHeight);
        shapeRenderer.end();
    }

    public void dispose() {
        sprite.getTexture().dispose();
        shapeRenderer.dispose();
    }

    public float getZoneX() { return zoneX; }
    public float getZoneY() { return zoneY; }
    public float getZoneWidth() { return zoneWidth; }
    public float getZoneHeight() { return zoneHeight; }
}
