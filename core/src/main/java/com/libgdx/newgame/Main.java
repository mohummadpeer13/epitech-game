package com.libgdx.newgame;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.libgdx.newgame.Entity.Enemy;
import com.libgdx.newgame.Entity.Player;
import com.libgdx.newgame.Management.AlertManagement;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends Game {

    private SpriteBatch batch;

    private Player player;
    private Enemy enemy;

    private ShapeRenderer shapeRenderer;

    private float screenWidth, screenHeight;

    @Override
    public void create() {
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();

        player = new Player(400, 800, 200, screenWidth, screenHeight, "player.png");
        enemy = new Enemy(200,300, 50f, 200, 200, 600, 300, "player.png");

        AlertManagement.initializeAlert();
    }

    @Override
    public void render() {

        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        Gdx.gl.glClearColor(0, 0, 0, 1);  // Fond noir

        enemy.update(Gdx.graphics.getDeltaTime());
        player.update(Gdx.graphics.getDeltaTime());

        player.render(batch);
        enemy.render(batch);
        enemy.renderZone();

        AlertManagement.renderAlert(batch, player, enemy);
    }

    @Override
    public void dispose() {
        batch.dispose();
        player.dispose();
        enemy.dispose();
        shapeRenderer.dispose();
        AlertManagement.dispose();
    }
}
