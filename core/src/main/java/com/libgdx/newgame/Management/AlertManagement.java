package com.libgdx.newgame.Management;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.libgdx.newgame.Entity.Enemy;
import com.libgdx.newgame.Entity.Player;

public class AlertManagement {
    private static Texture alertTexture;
    private static Sprite alertSprite;
    private static boolean alert = false;
    private static Sound son;

    public static void initializeAlert() {
        alertTexture = new Texture("alert.png");
        alertSprite = new Sprite(alertTexture);
        son = Gdx.audio.newSound(Gdx.files.internal("alert.mp3"));
    }

    // Méthode pour vérifier si le joueur est dans le cône de détection de l'ennemi
    public static boolean isInEnemyZone(Player player, Enemy enemy) {
        // Position de l'ennemi et du joueur
        Vector2 enemyPosition = new Vector2(enemy.getX() + enemy.getSprite().getWidth() / 2,
            enemy.getY() + enemy.getSprite().getHeight() / 2);
        Vector2 playerPosition = new Vector2(player.getX(), player.getY());

        // Vecteur de direction de l'ennemi basé sur la rotation
        Vector2 enemyDirection = new Vector2(MathUtils.cosDeg(enemy.getRotation()),
            MathUtils.sinDeg(enemy.getRotation()));

        // Vecteur de l'ennemi vers le joueur
        Vector2 toPlayer = playerPosition.cpy().sub(enemyPosition);

        // Calcul de la distance et de l'angle
        float distanceToPlayer = toPlayer.len();
        float angleToPlayer = enemyDirection.angleDeg(toPlayer);

        // Paramètres de détection
        float detectionDistance = 200;  // Distance maximale de détection (par exemple, 500 pixels)
        float detectionAngle = 50;      // 60° de chaque côté pour un total de 120° devant l'ennemi

        // Vérifie si le joueur est dans le cône de détection
        boolean isInDetectionRange = distanceToPlayer <= detectionDistance && Math.abs(angleToPlayer) <= detectionAngle;

        // Si le joueur est dans la zone de détection, nous retournons vrai
        return isInDetectionRange;
    }



    // Afficher l'alerte si le joueur est dans le cône de détection de l'ennemi
    public static void renderAlert(SpriteBatch batch, Player player, Enemy enemy) {
        if (isInEnemyZone(player, enemy)) {
            if (!alert) {
                alert = true;
                son.play();
            }
            batch.begin();
            alertSprite.setPosition(enemy.getX() + enemy.getSprite().getWidth() / 5,
                enemy.getY() + enemy.getSprite().getHeight());
            alertSprite.draw(batch);
            batch.end();
        } else {
            alert = false;
            son.stop();
        }
    }

    public static void dispose() {
        alertTexture.dispose();
        son.dispose();
    }
}
