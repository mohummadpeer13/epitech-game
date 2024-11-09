package com.libgdx.newgame.Entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.newgame.Management.Direction;

public class Camera{
    private float x, y;

    private float detectionDistance;
    private float detectionAngle;
    private Direction direction;

    private Sprite sprite;
    private Texture texture;

    private Sound alertSound;
    private boolean isAlertPlaying = false;

    private boolean showDetectionZone;
    private ShapeRenderer shapeRenderer;

    public Camera(float x, float y, float detectionDistance, float detectionAngle, Direction direction, String image, String alertSoundFile, boolean showDetectionZone) {
        this.x = x;
        this.y = y;
        this.detectionDistance = detectionDistance;
        this.detectionAngle = detectionAngle;
        this.direction = direction;
        this.texture = new Texture(image);
        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        this.showDetectionZone = showDetectionZone;
        this.alertSound = Gdx.audio.newSound(Gdx.files.internal(alertSoundFile));
        this.shapeRenderer = new ShapeRenderer();
    }

    // Vérifie si le joueur est dans le périmètre de détection de la caméra
    public void checkPlayerInDetectionZone(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 detectionPoint = playerPosition;

        Vector2 cameraPosition = new Vector2(x, y);

        // Calcul de la distance entre la caméra et le point de détection du joueur
        float distanceToPlayer = cameraPosition.dst(detectionPoint);

        // Si le joueur est dans le périmètre de détection
        if (distanceToPlayer <= detectionDistance) {
            // Calculer l'angle entre la direction de la caméra et la position du joueur
            Vector2 cameraDirection = new Vector2(MathUtils.cosDeg(direction.getAngle()), MathUtils.sinDeg(direction.getAngle()));
            Vector2 toPlayer = new Vector2(detectionPoint.x - x, detectionPoint.y - y);

            // Calcul de l'angle entre la direction de la caméra et le joueur
            float angleToPlayer = cameraDirection.angleDeg(toPlayer);

            // Vérifie si le joueur est dans l'angle de détection
            if (Math.abs(angleToPlayer) <= detectionAngle / 2) {
                if (!isAlertPlaying) {
                    alertSound.loop();
                    isAlertPlaying = true;
                }
            } else {
                // Si le joueur sort de la zone de détection, arrêter le son
                if (isAlertPlaying) {
                    alertSound.stop();
                    isAlertPlaying = false;
                }
            }
        } else {
            // Si le joueur est en dehors du périmètre, arrêter le son
            if (isAlertPlaying) {
                alertSound.stop();
                isAlertPlaying = false;
            }
        }
    }

    // Méthod draw caméra, zone de détection
    public void render(SpriteBatch batch) {
        // caméra
        batch.begin();
        sprite.draw(batch);
        batch.end();

        // zone de détection
        if (showDetectionZone) {
            shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
            shapeRenderer.setColor(Color.RED);
            shapeRenderer.arc(x, y, detectionDistance, direction.getAngle() - detectionAngle / 2, detectionAngle);
            shapeRenderer.end();
        }
    }

    public void dispose() {
        texture.dispose();
        alertSound.dispose();
        shapeRenderer.dispose();
    }
}
