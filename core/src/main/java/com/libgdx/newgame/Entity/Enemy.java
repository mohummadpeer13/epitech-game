package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.newgame.Management.DetectionZoneManager;
import com.libgdx.newgame.Management.Direction;

public class Enemy extends Initialize {
    private float zoneX, zoneY, zoneWidth, zoneHeight;  // Zone dans laquelle l'ennemi se déplace
    private float velocityX;  // Vitesse horizontale
    private float rotation;   // Angle de rotation en degrés
    private DetectionZoneManager detectionZoneManager;
    private boolean showDetectionZone;
    private Direction direction;

    public Enemy(float x, float y, float speed, float zoneX, float zoneY, float zoneWidth, float zoneHeight, String sprite,float detectionDistance, float detectionAngle, boolean showDetectionZone, Direction direction) {
        super(x, y, speed, sprite);
        this.zoneX = zoneX;
        this.zoneY = zoneY;
        this.zoneWidth = zoneWidth;
        this.zoneHeight = zoneHeight;
        this.detectionZoneManager = new DetectionZoneManager(detectionDistance, detectionAngle, "alert.mp3");
        this.velocityX = speed;
        this.showDetectionZone = showDetectionZone;
        this.direction = direction;
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
            direction = Direction.UP;
        }
        if (x > zoneX + zoneWidth - sprite.getWidth()) {
            x = zoneX + zoneWidth - sprite.getWidth();
            velocityX = -Math.abs(velocityX);  // Repart vers la gauche
            currentImage = leftImage;
            rotation = 180; // 180 degrés signifie qu'il regarde vers la gauche
            direction = Direction.DOWN;
        }

        // S'assurer que la position de l'ennemi reste la même en y
        y = zoneY + zoneHeight / 2 - sprite.getHeight() / 2;

        sprite.setPosition(x, y);
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (showDetectionZone){
            Vector2 cameraPosition = new Vector2(x, y);
            detectionZoneManager.renderDetectionZone(cameraPosition, direction.getAngle());
        }
    }

    // Vérifie si le joueur est dans la zone de détection de la caméra
    public void checkPlayerInDetectionZone(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 cameraPosition = new Vector2(x, y);
        detectionZoneManager.checkPlayerInDetectionZone(cameraPosition, playerPosition,direction.getAngle());
    }

    public void dispose() {
        sprite.getTexture().dispose();
    }

    public float getZoneX() { return zoneX; }
    public float getZoneY() { return zoneY; }
    public float getZoneWidth() { return zoneWidth; }
    public float getZoneHeight() { return zoneHeight; }

    // Getter pour la rotation de l'ennemi
    public float getRotation() {
        return rotation;
    }
}
