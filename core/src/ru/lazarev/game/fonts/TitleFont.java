package ru.lazarev.game.fonts;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.math.Vector2;

public class TitleFont {

  @SuppressWarnings("unused")
  private Color color;

  @SuppressWarnings("unused")
  String text;
  private final Vector2 position;

  @SuppressWarnings({"unused", "FieldCanBeLocal"})
  private final Sprite sprite;

  @SuppressWarnings("unused")
  private float height;
  @SuppressWarnings("unused")
  private float width;
  private final BitmapFont spaceFont;

  public TitleFont(int size, Vector2 position) {
    this.sprite = new Sprite();
    this.position = position;
    //noinspection SpellCheckingInspection
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal("canis_minor_qdDyiV.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    fontParameter.size = size;
    //noinspection SpellCheckingInspection
    fontParameter.characters = "zxcvbnm,./asdfghjkl;'qwertyuiop[ZXCVBNM<>?ASDFGHJKL:|}{POIUYTREWQ1234567890ЯЧСМИТЬБЮФЫВАПРОЛДЖЭЙЦУКЕНГШЩЗХЪячсмитьбюэждлорпавыфйцукенгшщзхъ!?.,";
    spaceFont = generator.generateFont(fontParameter);
    generator.dispose();
  }

  public TitleFont(int size) {
    this(size, new Vector2(0, 0));
  }

  public void setColor(Color color) {
    spaceFont.setColor(color);
  }

  public void draw(SpriteBatch batch, String string) {
    @SuppressWarnings("unused")
    GlyphLayout draw = spaceFont.draw(batch, string, position.x, position.y);

  }

  public void draw(SpriteBatch batch, String string, Vector2 position) {
    @SuppressWarnings("unused")
    GlyphLayout draw = spaceFont.draw(batch, string, position.x, position.y);
  }
  @SuppressWarnings("unused")
  public Sprite getSprite() {
    return new Sprite(spaceFont.getRegion());
  }

  @SuppressWarnings("unused")
  public int getHeight() {
    return (int) spaceFont.getCapHeight();
  }

  public int getWidth() {
    return spaceFont.getRegion().getRegionWidth();
  }

  @SuppressWarnings("unused")
  public TextureRegion getRegion() {
    return spaceFont.getRegion();
  }

}
