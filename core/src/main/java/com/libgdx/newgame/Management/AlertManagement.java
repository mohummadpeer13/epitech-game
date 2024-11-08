package com.libgdx.newgame.Management;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.libgdx.newgame.Entity.Enemy;
import com.libgdx.newgame.Entity.Player;

public class AlertManagement {
    private static Texture alertTexture;
    private static Sprite alertSprite;
    private static boolean alert = false;

    // Initialisation de l'alerte
    public static void initializeAlert() {
        alertTexture = new Texture("alert.png");
        alertSprite = new Sprite(alertTexture);
    }

    // Méthode pour vérifier si le joueur est dans la zone de l'ennemi
    public static boolean isInEnemyZone(Player player, Enemy enemy) {
        float playerX = player.getX();
        float playerY = player.getY();
        float playerWidth = player.getSprite().getWidth();
        float playerHeight = player.getSprite().getHeight();

        float enemyZoneX = enemy.getZoneX();
        float enemyZoneY = enemy.getZoneY();
        float enemyZoneWidth = enemy.getZoneWidth();
        float enemyZoneHeight = enemy.getZoneHeight();

        // Vérifier si le joueur est à l'intérieur de la zone de l'ennemi
        return playerX < enemyZoneX + enemyZoneWidth &&
            playerX + playerWidth > enemyZoneX &&
            playerY < enemyZoneY + enemyZoneHeight &&
            playerY + playerHeight > enemyZoneY;
    }

    // Afficher l'alerte si le joueur est dans la zone de l'ennemi
    public static void renderAlert(SpriteBatch batch, Player player, Enemy enemy) {
        if (isInEnemyZone(player, enemy)) {
            if (!alert) {
                alert = true;
            }
            batch.begin();
            alertSprite.setPosition(enemy.getX() + enemy.getSprite().getWidth()/5, enemy.getY() + enemy.getSprite().getHeight());
            alertSprite.draw(batch);
            batch.end();
        } else {
            alert = false;
        }
    }

    public static void dispose() {
        alertTexture.dispose();
    }
}
