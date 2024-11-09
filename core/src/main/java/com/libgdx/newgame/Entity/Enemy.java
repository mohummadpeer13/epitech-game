package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Enemy extends Initialize {
    private float zoneX, zoneY, zoneWidth, zoneHeight;  // Zone dans laquelle l'ennemi se déplace
    private ShapeRenderer shapeRenderer;
    private float velocityX;  // Vitesse horizontale
    private float rotation;   // Angle de rotation en degrés

    public Enemy(float x, float y, float speed, float zoneX, float zoneY, float zoneWidth, float zoneHeight, String sprite) {
        super(x, y, speed, sprite);
        this.zoneX = zoneX;
        this.zoneY = zoneY;
        this.zoneWidth = zoneWidth;
        this.zoneHeight = zoneHeight;
        this.shapeRenderer = new ShapeRenderer();
        this.velocityX = speed;

        // Initialiser l'orientation de l'ennemi (vers la droite par défaut)
        this.rotation = 0; // 0 degrés signifie qu'il regarde vers la droite
    }

    @Override
    public void update(float deltaTime) {
        // Déplacement horizontal seulement
        x += velocityX * deltaTime;

        // Limiter l'ennemi à la zone horizontale (rester dans la zone définie)
        if (x < zoneX) {
            x = zoneX;
            velocityX = Math.abs(velocityX); // Repart vers la droite
            currentImage = rightImage;
            rotation = 0;  // 0 degrés signifie qu'il regarde vers la droite
        }
        if (x > zoneX + zoneWidth - sprite.getWidth()) {
            x = zoneX + zoneWidth - sprite.getWidth();
            velocityX = -Math.abs(velocityX);  // Repart vers la gauche
            currentImage = leftImage;
            rotation = 180; // 180 degrés signifie qu'il regarde vers la gauche
        }

        // S'assurer que la position de l'ennemi reste la même en y
        y = zoneY + zoneHeight / 2 - sprite.getHeight() / 2;

        // Mettre à jour la position du sprite
        sprite.setPosition(x, y);
    }

    // Méthode pour afficher la zone de détection de 180° de l'ennemi
    public void renderZone() {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Position de l'ennemi
        Vector2 enemyPosition = new Vector2(x, y);

        // Rayon de la zone de détection (par exemple, 200 pixels)
        float detectionRadius = 200;

        // Dessiner un secteur de cercle de 180° devant l'ennemi
        float startAngle = rotation - 90; // L'angle de début du secteur (-90° à +90°)
        shapeRenderer.arc(enemyPosition.x, enemyPosition.y, detectionRadius, startAngle, 180);

        shapeRenderer.end();
    }

    // Libérer les ressources
    public void dispose() {
        sprite.getTexture().dispose();
        shapeRenderer.dispose();
    }

    // Getters pour la zone de déplacement
    public float getZoneX() { return zoneX; }
    public float getZoneY() { return zoneY; }
    public float getZoneWidth() { return zoneWidth; }
    public float getZoneHeight() { return zoneHeight; }

    // Getter pour la rotation de l'ennemi
    public float getRotation() {
        return rotation;
    }
}
