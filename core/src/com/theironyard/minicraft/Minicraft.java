package com.theironyard.minicraft;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class Minicraft extends ApplicationAdapter {
    final int WIDTH = 100;
    final int HEIGHT = 100;

    SpriteBatch batch;
    TextureRegion down, up, right, left;
    FitViewport viewport;

    float x;
    float y;
    float xv = 0;
    float yv = 0;
    float time = 0;

    final float MAX_VELOCITY = 100;
    final int DRAW_WIDTH = WIDTH;//making player smaller by taking away *3
    final int DRAW_HEIGHT = HEIGHT;

    @Override
    public void create() {
        batch = new SpriteBatch();
        viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        Texture tiles = new Texture("tiles.png");
        TextureRegion[][] grid = TextureRegion.split(tiles, 16, 16);
        down = grid[6][0];
        up = grid[6][1];
        right = grid[6][2];
        left = grid[6][3];
    }

    @Override
    public void render() {
        move();
        draw();

    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }


    void move() {
        if (x >= 0 && y >= 0) {
            if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
                yv = MAX_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.DOWN)) {
                yv = MAX_VELOCITY * -1;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
                xv = MAX_VELOCITY;
            }
            if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                xv = MAX_VELOCITY * -1;
            }

            float oldx = x;
            float oldy = y;

            x += xv * Gdx.graphics.getDeltaTime();
            y += yv * Gdx.graphics.getDeltaTime();

            if (x < 0 || x > (Gdx.graphics.getWidth() - WIDTH)) {
                x = oldx;
            }

            if (y < 0 || y > (Gdx.graphics.getHeight() - HEIGHT)) {
                y = oldy;
            }

            xv *= 0.9;//dampening, adding friction so image slows down
            yv *= 0.9;
        }
    }

    void draw() {
        time += Gdx.graphics.getDeltaTime();
        TextureRegion img;

        if (Math.abs(yv) > Math.abs(xv)) {
            if (yv > 0) {
                img = up;
            } else {
                img = down;
            }
        }//end of outer If
        else {
            if (xv > 0) {
                img = right;
            } else {
                img = left;
            }
        }//end of outer else

        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        if (xv >= 0) {
            batch.draw(img, x, y, DRAW_WIDTH, DRAW_HEIGHT);
        } else {
            batch.draw(img, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
        }
        batch.end();
    }
}

