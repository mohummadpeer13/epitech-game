package com.libgdx.newgame.Management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

public class DetectionZoneManager {
    private float detectionDistance;
    private float detectionAngle;
    private Sound alertSound;
    private boolean isAlertPlaying = false;
    private ShapeRenderer shapeRenderer;

    public DetectionZoneManager(float detectionDistance, float detectionAngle, String alertSoundFile) {
        this.detectionDistance = detectionDistance;
        this.detectionAngle = detectionAngle;
        this.alertSound = Gdx.audio.newSound(Gdx.files.internal(alertSoundFile));
        this.shapeRenderer = new ShapeRenderer();
    }

    // Vérifie si le joueur est dans la zone de détection
    public void checkPlayerInDetectionZone(Vector2 cameraPosition, Vector2 playerPosition, float cameraAngle) {
        // Calcul de la distance entre la caméra et le joueur
        float distanceToPlayer = cameraPosition.dst(playerPosition);

        // Si le joueur est dans la portée de détection
        if (distanceToPlayer <= detectionDistance) {
            // Calculer l'angle entre la direction de la caméra et la position du joueur
            Vector2 cameraDirection = new Vector2(MathUtils.cosDeg(cameraAngle), MathUtils.sinDeg(cameraAngle));
            Vector2 toPlayer = playerPosition.cpy().sub(cameraPosition);

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
            // Si le joueur est hors de la zone de détection, arrêter le son
            if (isAlertPlaying) {
                alertSound.stop();
                isAlertPlaying = false;
            }
        }
    }

    public void renderDetectionZone(Vector2 cameraPosition, float cameraAngle) {
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);

        // Dessiner un arc représentant la zone de détection
        float startAngle = cameraAngle - detectionAngle / 2;  // Calculer l'angle de départ
        shapeRenderer.arc(cameraPosition.x, cameraPosition.y, detectionDistance, startAngle, detectionAngle);

        shapeRenderer.end();
    }

    public float getDetectionDistance() {
        return detectionDistance;
    }

    public float getDetectionAngle() {
        return detectionAngle;
    }

    public void dispose() {
        alertSound.dispose();
    }
}
