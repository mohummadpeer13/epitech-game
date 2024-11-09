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
    private float x, y;                  // Position de la caméra
    private float detectionDistance;     // Distance maximale de détection
    private float detectionAngle;  // L'angle de détection (100° ici)

    private Sprite sprite;               // Sprite représentant la caméra
    private Texture texture;             // Texture associée au sprite de la caméra

    private Sound alertSound;            // Son d'alerte
    private boolean isAlertPlaying = false; // Pour éviter de rejouer le son en boucle

    private boolean showDetectionZone;
    private ShapeRenderer shapeRenderer; // Renderer pour dessiner la zone de détection

    private Direction direction;         // Direction de détection de la caméra (via l'enum)


    // Constructeur de la caméra avec l'enum Direction comme paramètre
    public Camera(float x, float y, float detectionDistance, float detectionAngle, Direction direction, String image, String alertSoundFile, boolean showDetectionZone) {
        this.x = x;
        this.y = y;
        this.detectionDistance = detectionDistance;
        this.detectionAngle = detectionAngle;
        this.direction = direction; // Affecter la direction via l'enum
        this.texture = new Texture(image);
        this.sprite = new Sprite(texture);
        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);
        this.showDetectionZone = showDetectionZone;

        // Charger le son d'alerte
        this.alertSound = Gdx.audio.newSound(Gdx.files.internal(alertSoundFile));

        // Initialisation de ShapeRenderer pour la zone de détection
        this.shapeRenderer = new ShapeRenderer();
    }

    // Vérifie si le joueur est dans le périmètre de détection de la caméra
    public void checkPlayerInDetectionZone(Player player) {
        Vector2 playerPosition = player.getPosition();
        Vector2 detectionPoint = playerPosition;  // Utilisation de la position du joueur sans distinction haut/bas

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

            // Vérifie si le joueur est dans l'angle de détection de 100 degrés (50° à gauche et à droite)
            if (Math.abs(angleToPlayer) <= detectionAngle / 2) {  // Angle de 100° autour de la direction de la caméra
                if (!isAlertPlaying) {
                    alertSound.loop();  // Joue le son en boucle
                    isAlertPlaying = true;
                }
            } else {
                // Si le joueur sort de la zone de détection, arrêter le son
                if (isAlertPlaying) {
                    alertSound.stop();  // Arrête la boucle
                    isAlertPlaying = false;
                }
            }
        } else {
            // Si le joueur est en dehors du périmètre, arrêter le son
            if (isAlertPlaying) {
                alertSound.stop();  // Arrête la boucle
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
