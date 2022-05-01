package ru.lazarev.game.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class GfxUtils {

  private static final int centerX = Gdx.graphics.getHeight() >> 1;
  private static final int centerY = Gdx.graphics.getWidth() >> 1;

  public static int getCenterX() {
    return centerX;
  }
  public static int getCenterY() {
    return centerY;
  }

  public static Vector2 getCursorPosition() {
    float x = Gdx.input.getX();
    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
    return new Vector2(x, y);
  }

  public static Vector2 getCursorPosition(int width, int height) {
    float x = Gdx.input.getX();
    float y = Gdx.graphics.getHeight() - Gdx.input.getY();
    x -= width >> 1;
    x = (x < Gdx.graphics.getWidth() - width) ? x : Gdx.graphics.getWidth() - width;
    x = (x < 0) ? 0 : x;
    y -= height >> 1;
    y = (y < Gdx.graphics.getHeight() - height) ? y : Gdx.graphics.getHeight() - height;
    y = (y < 0) ? 0 : y;
    return new Vector2(x, y);
  }

  public static float getAngle(Vector2 pos) {
    return 360 - MathUtils.atan2(getCursorPosition().x - pos.x, getCursorPosition().y - pos.y)
        * MathUtils.radiansToDegrees;
  }

  public static void getRectangle(Vector2 position, Vector2 size) {
    final ShapeRenderer shapeRenderer = new ShapeRenderer();
    shapeRenderer.begin(ShapeType.Line);
    Rectangle rectangle = new Rectangle(position.x, position.y, size.x, size.y);
    shapeRenderer.rect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
    shapeRenderer.end();
    shapeRenderer.dispose();
  }
}
