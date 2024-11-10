package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.newgame.Management.DetectionZoneManager;
import com.libgdx.newgame.Management.Direction;

public class Camera{
    public float x;
    public float y;
    public Sprite sprite;
    public Texture texture;
    private DetectionZoneManager detectionZoneManager;
    private Direction direction;
    public boolean showDetectionZone;

    public Camera(float x, float y, float detectionDistance, float detectionAngle, Direction direction,
                  String image, String alertSoundFile, boolean showDetectionZone) {
        this.x = x;
        this.y = y;
        this.texture = new Texture(image);
        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);

        this.direction = direction;
        this.detectionZoneManager = new DetectionZoneManager(detectionDistance, detectionAngle, alertSoundFile);
        this.showDetectionZone = showDetectionZone;
    }

    // Vérifie si le joueur est dans la zone de détection de la caméra
    public void checkPlayerInDetectionZone(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 cameraPosition = new Vector2(x, y);

        detectionZoneManager.checkPlayerInDetectionZone(cameraPosition, playerPosition, direction.getAngle());
    }

    // Méthode pour dessiner la caméra et zone detection
    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.draw(batch);
        batch.end();

        if (showDetectionZone) {
            Vector2 cameraPosition = new Vector2(x, y);
            detectionZoneManager.renderDetectionZone(cameraPosition, direction.getAngle());
        }
    }

    public void dispose() {
        texture.dispose();
        detectionZoneManager.dispose();
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public Texture getTexture() {
        return texture;
    }

    public Sprite getSprite() {
        return sprite;
    }
}
