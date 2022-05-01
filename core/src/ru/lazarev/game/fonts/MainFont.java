package ru.lazarev.game.fonts;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import ru.lazarev.game.utils.GfxUtils;


public class MainFont {

  private final BitmapFont newGame;
  private Color color;

  String text;
  private final Vector2 position;

  private final Sprite sprite;
  private float height;
  private float width;

  @SuppressWarnings("unused")
  public Vector2 getPosition() {
    return position;
  }
  @SuppressWarnings("unused")
  public Sprite getSprite() {
    return sprite;
  }

  public MainFont(String text, Vector2 position, float scaleX, float scaleY) {
    this.position = position;
    this.text = text;
    newGame = new BitmapFont();
    color = Color.YELLOW;
    newGame.setColor(color);
    newGame.getData().setScale(scaleX, scaleY);
    this.sprite = new Sprite(newGame.getRegion());
  }

  public void draw(SpriteBatch batch) {
    newGame.setColor(color);
    GlyphLayout draw = newGame.draw(batch, text, position.x, position.y);
    height = draw.height;
    width = draw.width;

  }

  public Rectangle getBoundingRectangle() {
    return new Rectangle(position.x, position.y - height, width, height);
  }

  @SuppressWarnings("unused")
  public void drawBoundingRectangle() {
    GfxUtils.getRectangle(new Vector2(position.x, position.y - height), new Vector2(width, height));
  }

  public void setColor (Color color) {
    this.color = color;
  }

  public void dispose() {
    newGame.dispose();
  }
}
