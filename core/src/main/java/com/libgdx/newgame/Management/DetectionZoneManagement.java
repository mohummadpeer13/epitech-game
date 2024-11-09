package com.libgdx.newgame.Management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class DetectionZoneManagement {
    private float detectionDistance; // Distance de détection
    private float detectionAngle;    // Angle de détection (en degrés)
    private Sound alertSound;        // Son d'alerte
    private boolean alertTriggered = false; // Indicateur si l'alerte a été déclenchée

    // Constructeur de DetectionZoneManagement
    public DetectionZoneManagement(float detectionDistance, float detectionAngle, String alertSoundFile) {
        this.detectionDistance = detectionDistance;
        this.detectionAngle = detectionAngle;
        this.alertSound = Gdx.audio.newSound(Gdx.files.internal(alertSoundFile));
    }

    // Méthode pour vérifier si le joueur est dans la zone de détection
    public void checkPlayerInDetectionZone(Vector2 cameraPosition, Vector2 playerPosition, float cameraAngle) {
        Vector2 directionToPlayer = playerPosition.cpy().sub(cameraPosition); // Vecteur de la caméra au joueur

        // Calcul de la distance entre la caméra et le joueur
        float distanceToPlayer = directionToPlayer.len();

        // Si le joueur est trop loin, il est hors de la zone de détection
        if (distanceToPlayer > detectionDistance) {
            if (alertTriggered) {
                stopAlertSound();
            }
            return;
        }

        // Calcul de l'angle entre la direction de la caméra et le joueur
        float angleToPlayer = directionToPlayer.angleDeg() - cameraAngle;

        // Vérifie si le joueur est dans l'angle de détection
        if (Math.abs(angleToPlayer) <= detectionAngle / 2) {
            if (!alertTriggered) {
                playAlertSound();
            }
            alertTriggered = true;
        } else {
            if (alertTriggered) {
                stopAlertSound();
            }
            alertTriggered = false;
        }
    }

    // Méthode pour jouer le son d'alerte
    private void playAlertSound() {
        alertSound.loop(); // Jouer le son en boucle
    }

    // Méthode pour arrêter le son d'alerte
    public void stopAlertSound() {
        alertSound.stop(); // Arrêter le son
    }

    // Méthode pour obtenir la distance de détection
    public float getDetectionDistance() {
        return detectionDistance;
    }

    // Méthode pour disposer des ressources
    public void dispose() {
        alertSound.dispose(); // Libérer la ressource audio
    }
}
