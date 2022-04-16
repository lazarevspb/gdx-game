package ru.lazarev.game.animation;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class MyAnimation {
    private final Animation<TextureRegion> animation;
    private float time;
    private final Texture texture;

    public MyAnimation(String name, Animation.PlayMode mode, int columns, int lines, int fps) {
        texture = new Texture(name);
        TextureRegion[][] tmpRegion = new TextureRegion(texture).split(texture.getWidth() / columns, texture.getHeight() / lines);
        TextureRegion[] regions = new TextureRegion[tmpRegion.length * tmpRegion[0].length];
        int count = 0;

        for (TextureRegion[] textureRegions : tmpRegion) {
            for (int j = 0; j < tmpRegion[0].length; j++) {
                regions[count++] = textureRegions[j];
            }
        }
        time = 0;
        animation = new Animation<>(1.0f / fps, regions);
        animation.setPlayMode(mode);
    }

    public TextureRegion getRegion() {
        return animation.getKeyFrame(time);
    }

    public void setTime(float dTime) {
        time += dTime;
    }

    public boolean isFinished() {
        return animation.isAnimationFinished(time);
    }

    public void resetTime() {
        time = 0;
    }

    public void dispose() {
        texture.dispose();
    }
}
