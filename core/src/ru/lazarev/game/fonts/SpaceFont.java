package ru.lazarev.game.fonts;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

public class SpaceFont {

  private final BitmapFont spaceFont;

  @SuppressWarnings("SpellCheckingInspection")
  public SpaceFont(int size) {
    FreeTypeFontGenerator generator = new FreeTypeFontGenerator(
        Gdx.files.internal("canis_minor_qdDyiV.ttf"));
    FreeTypeFontGenerator.FreeTypeFontParameter fontParameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
    fontParameter.size = size;
    fontParameter.characters = "zxcvbnm,./asdfghjkl;'qwertyuiop[ZXCVBNM<>?ASDFGHJKL:|}{POIUYTREWQ1234567890ЯЧСМИТЬБЮФЫВАПРОЛДЖЭЙЦУКЕНГШЩЗХЪячсмитьбюэждлорпавыфйцукенгшщзхъ!?.,";
    spaceFont = generator.generateFont(fontParameter);
    generator.dispose();
  }

  public void setColor(Color color) {
    spaceFont.setColor(color);
  }

  public void draw(SpriteBatch batch, String string, int x, int y) {
    spaceFont.draw(batch, string, x, y);
  }

  @SuppressWarnings("unused")
  public int getHeight() {
    return (int) spaceFont.getCapHeight();
  }
}
