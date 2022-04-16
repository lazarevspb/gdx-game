package ru.lazarev.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ru.lazarev.game.utils.GfxUtils.getPosition;


public class Explosion {
    private final MyAnimation animation;
    private final Vector2 position;
    private final Music music;

    public Explosion(String texture, Animation.PlayMode mode, int columns, int lines, int fps, String mName) {
        animation = new MyAnimation(texture, mode, columns, lines, fps);
        music = Gdx.audio.newMusic(Gdx.files.internal(mName));
        music.play();
        position = getPosition(animation.getRegion().getRegionWidth(), animation.getRegion().getRegionHeight());
    }

    public void setTime(float dTime) {
        animation.setTime(dTime);
    }

    public Vector2 getPos() {
        return position;
    }

    public TextureRegion getRegion() {
        return animation.getRegion();
    }

    public boolean isFinished() {
        return animation.isFinished();
    }

    public void dispose() {
        animation.dispose();
        music.dispose();
    }
}
