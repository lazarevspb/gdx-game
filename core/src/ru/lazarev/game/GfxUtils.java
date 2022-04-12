package com.androidtest.project1;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {

    public static Vector2 getPosition() {
        float x = Gdx.input.getX();
        float y = Gdx.graphics.getHeight() - Gdx.input.getY();
        return new Vector2(x,y);
    }
}
