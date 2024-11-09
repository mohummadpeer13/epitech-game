package com.libgdx.newgame.Entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Camera{
    private float x, y;                  // Position de la caméra
    private float rotation;              // Rotation de la caméra (en degrés)
    private float detectionDistance;     // Distance maximale de détection
    private float detectionAngle;        // Angle de détection (en degrés)
    private Sprite sprite;               // Sprite représentant la caméra
    private Texture texture;             // Texture associée au sprite de la caméra

    // Constructeur de la caméra avec position, rotation, distance, angle et image du sprite
    public Camera(float x, float y, float rotation, float detectionDistance, float detectionAngle, String img) {
        this.x = x;
        this.y = y;
        this.rotation = rotation;
        this.detectionDistance = detectionDistance;
        this.detectionAngle = detectionAngle;
        this.texture = new Texture(img); // Sauvegarde de la texture
        this.sprite = new Sprite(texture);  // Création du sprite avec la texture
        this.sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2); // Centrer l'origine du sprite
        this.sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2); // Positionner le sprite
    }

    // Vérifie si un objet (comme un joueur) est dans la zone de détection de la caméra
    public boolean isObjectInDetectionZone(Vector2 objectPosition) {
        // Vecteur de la caméra vers l'objet
        Vector2 cameraToObject = objectPosition.cpy().sub(x, y);

        // Calcul de la distance entre la caméra et l'objet
        float distanceToObject = cameraToObject.len();

        // Si l'objet est trop loin de la caméra, il ne sera pas détecté
        if (distanceToObject > detectionDistance) {
            return false;
        }

        // Calcul de l'angle entre la direction de la caméra et l'objet
        Vector2 cameraDirection = new Vector2(MathUtils.cosDeg(rotation), MathUtils.sinDeg(rotation));
        float angleToObject = cameraDirection.angleDeg(cameraToObject);

        // Si l'angle de l'objet par rapport à la direction de la caméra est dans la plage de détection
        return Math.abs(angleToObject) <= detectionAngle / 2;  // Détection dans un cône de détection de taille 'detectionAngle'
    }

    // Met à jour la position du sprite (et la caméra si nécessaire)
    public void updatePosition(float newX, float newY) {
        this.x = newX;
        this.y = newY;
        sprite.setPosition(x - sprite.getWidth() / 2, y - sprite.getHeight() / 2);  // Met à jour la position du sprite
    }

    // Met à jour la rotation du sprite (et la caméra si nécessaire)
    public void updateRotation(float newRotation) {
        this.rotation = newRotation;
        sprite.setRotation(rotation);  // Applique la rotation au sprite
    }

    // Méthode pour dessiner la caméra et son sprite
    public void render(SpriteBatch batch) {
        batch.begin();
        sprite.draw(batch);  // Dessine le sprite de la caméra
        batch.end();
    }

    // Getter pour obtenir la position de la caméra
    public Vector2 getPosition() {
        return new Vector2(x, y);
    }

    // Getter pour obtenir la rotation de la caméra
    public float getRotation() {
        return rotation;
    }

    // Getter pour la distance de détection
    public float getDetectionDistance() {
        return detectionDistance;
    }

    // Getter pour l'angle de détection
    public float getDetectionAngle() {
        return detectionAngle;
    }

    // Getter pour le sprite de la caméra
    public Sprite getSprite() {
        return sprite;
    }

    // Getter pour la texture du sprite
    public Texture getTexture() {
        return texture;
    }

    // Dispose de la texture et du sprite
    public void dispose() {
        texture.dispose();  // Dispose de la texture pour libérer les ressources
    }
}
