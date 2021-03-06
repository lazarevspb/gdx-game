package ru.lazarev.game.animation;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import static ru.lazarev.game.utils.GfxUtils.getCursorPosition;


public class Explosion {
    private final MyAnimation animation;
    private final Vector2 position;
    private final Music music;

    public Explosion(TextureRegion textureRegion, Animation.PlayMode mode, int columns, int lines, int fps, String mName) {
        animation = new MyAnimation(textureRegion, mode, columns, lines, fps);
        music = Gdx.audio.newMusic(Gdx.files.internal(mName));
        music.play();
        position = getCursorPosition(animation.getRegion().getRegionWidth(), animation.getRegion().getRegionHeight());
    }

    private float damage;

    public Explosion(TextureRegion region, Animation.PlayMode mode, int columns, int lines, int fps, String mName, float damage){
        animation = new MyAnimation(region, mode, columns, lines, fps);
        music = Gdx.audio.newMusic(Gdx.files.internal(mName));
        music.play();
        position = getCursorPosition(animation.getRegion().getRegionWidth(), animation.getRegion().getRegionHeight());
        this.damage = damage;
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
        music.dispose();
    }

    public void setDamage(float damage) { this.damage = damage;}

    public float getDamage(){return damage;}
}
