package com.dune.game.core;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

import java.awt.*;

public class BattleMap {
    //public static int[][] massMap;
    public static int[][] massMap= {
            {0,0,0,1,0,0,0,0,1},
            {0,0,1,0,0,0,1,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,1},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,1,0,0,0,1,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,1,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,0},
            {0,0,0,1,0,0,0,0,0},
            {0,0,0,0,0,0,0,0,1}
    };
    private TextureRegion grassTexture;
    private TextureRegion resourceTexture;


    public BattleMap() {
        this.grassTexture = Assets.getInstance().getAtlas().findRegion("grass");
        this.resourceTexture = Assets.getInstance().getAtlas().findRegion("resource");
    }

    public void render(SpriteBatch batch) {



        for (int i = 0; i < 16; i++) {
            for (int j = 0; j < 9; j++) {
                if (massMap[i][j] == 0){
                    batch.draw(grassTexture, i * 80, j * 80);
                } else {
                    batch.draw(resourceTexture, i * 80, j * 80);
                }
            }
        }
    }
}