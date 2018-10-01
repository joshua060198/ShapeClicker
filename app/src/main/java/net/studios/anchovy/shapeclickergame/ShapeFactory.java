package net.studios.anchovy.shapeclickergame;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.Log;

import net.studios.anchovy.shapeclickergame.model.Circle;
import net.studios.anchovy.shapeclickergame.model.Rectangle;
import net.studios.anchovy.shapeclickergame.model.Shape;

import java.util.Iterator;
import java.util.LinkedList;

public class ShapeFactory {
    private static ShapeFactory instance ;
    private static byte config;
    private static Canvas canvas;
    private static int maxWidth, maxHeight;
    private Shape soal,tapped;

    private LinkedList<Shape> pool[];
    private LinkedList<Shape> active;

    public static ShapeFactory getInstance(byte configuration, Canvas canvasInp, int maxH, int maxW) {
       if (instance == null) {
           instance = new ShapeFactory();
       }

       config = configuration;
       canvas = canvasInp;
       maxHeight = maxH;
       maxWidth = maxW;

       return instance;
    }

    private ShapeFactory() {
        this.pool = new LinkedList[3];
        for (int i = 0; i < 3; i++) {
            this.pool[i] = new LinkedList<>();
        }
        this.active = new LinkedList<>();
        this.soal = null;
        this.tapped = null;
    }

    public Shape generateShape() {
        Shape res = null;
        int[]arrTemp = new int[3];
        int temp = config;
        while (temp > 0) {
            for (int i = 0; i < 3; i++) {
                if ((temp & 1) > 0) {
                    arrTemp[i] = GameUtil.randomInt(0, 1000000);
                } else {
                    arrTemp[i] = 0;
                }

                temp = temp >> 1;
            }

            Paint paint = PaintFactory.getInstance().getPaint(GameUtil.randomInt(2,4));
            int max = GameUtil.max(arrTemp[0], arrTemp[1], arrTemp[2]);
            if (max == arrTemp[0]) {
                int width = GameUtil.randomInt(GameUtil.MIN_WIDTH_SQUARE, GameUtil.MAX_WIDTH_SQUARE);
                int height = GameUtil.randomInt(GameUtil.MIN_HEIGHT_SQUARE, GameUtil.MAX_HEIGHT_SQUARE);
                int x = GameUtil.randomInt(0, maxWidth-width);
                int y = GameUtil.randomInt(0, maxHeight-height);
                int velocity = 0;
                res = getAvailableRectangle(x,y,velocity,width,height,null,paint);
            } else if (max == arrTemp[1]) {
                int radius = GameUtil.randomInt(GameUtil.MIN_RADIUS_CIRCLE, GameUtil.MAX_RADIUS_CIRCLE);
                int x = GameUtil.randomInt(radius, maxWidth-radius);
                int y = GameUtil.randomInt(radius, maxHeight-radius);
                int velocity = 0;
                res = getAvailableCircle(x,y,velocity,radius,null,paint);
            } else {
                //TODO : FOR TRIANGLE
            }

            if (res == null || checkColliding(res))  {
                temp = config;
            }
        }
        active.add(res);

        return res;
    }

    public void showSoal() {
        int count = this.active.size();
        int choosen = GameUtil.randomInt(0,count-1);
        this.soal = this.active.get(choosen);
        this.soal.drawSoal(maxHeight, maxWidth);
    }

    public boolean isTappedInsideAShape(float x, float y) {
//        Log.d("DEBUG", "TOTAL: " + active.size());
        for (Shape anActive : active) {
            tapped = anActive;
//            Log.d("DEBUG", "TAPPED = " + tapped.getX() + " " + tapped.getY() + "CLICKED = " + x + " " + y);
            if (this.tapped.isInside(x, y)) {
//                Log.d("DEBUG", "INSIDE");
                return true;
            }
        }
        return false;
    }

    public boolean isTappedCorrectly() {
//        Log.d("DEBUG", "TAPPED CORRECTLY");
        return this.tapped == this.soal;
    }

    public void clearSoal() {
        this.tapped.clear();
        if (this.tapped instanceof Rectangle) {
            this.pool[0].add(this.tapped);
        } else if (this.tapped instanceof Circle) {
            this.pool[1].add(this.tapped);
        }
        this.active.remove(this.tapped);
        this.soal.clearSoal(maxWidth, maxHeight);
        this.tapped = null;
        this.soal = null;
    }

    public void clearAll() {
        Iterator<Shape> it = active.iterator();
        Shape temp;
        while (it.hasNext()) {
            temp = it.next();
            if (temp instanceof Rectangle) {
                this.pool[0].add(temp);
            } else  if (temp instanceof Circle) {
                this.pool[1].add(temp);
            }
            it.remove();
        }
    }

    private boolean checkColliding (Shape s1) {
        for (Shape s : active) {
            if (s1.isCollide(s)) return true;
        }
        return false;
    }

    private Shape getAvailableRectangle(int x, int y, int velocity, int width, int height, @Nullable String text, Paint paint) {
        Rectangle res;
        if (this.pool[0].size() == 0) {
            res = createRectangle(x,y,velocity,width,height,text,paint);
        } else {
            res = (Rectangle) this.pool[0].getFirst();
            res.setX(x);
            res.setY(y);
            res.setWidth(width);
            res.setHeight(height);
            res.setText(text);
            res.setVelocity(velocity);
            res.setPaint(paint);
            this.pool[0].removeFirst();
        }
        return res;
    }

    public void drawAll() {
        for(Shape s:active) {
            s.draw();
        }
    }

    private Shape getAvailableCircle(int x, int y, int velocity, int radius, @Nullable String text, Paint paint) {
        Circle res;
        if (this.pool[1].size() == 0) {
            res = createCircle(x, y, velocity, radius, text, paint);
        } else {
            res = (Circle) this.pool[1].getFirst();
            res.setX(x);
            res.setY(y);
            res.setRadius(radius);
            res.setText(text);
            res.setVelocity(velocity);
            res.setPaint(paint);
            this.pool[1].removeFirst();
        }

        return res;
    }

    private Rectangle createRectangle(int x, int y, int velocity, int width, int height, @Nullable String text, Paint paint) {
        return new Rectangle(canvas,x,y,velocity,width,height,text,paint);
    }

    private Circle createCircle(int x, int y, int velocity, int radius, @Nullable String text, Paint paint) {
        return new Circle(canvas,x,y,velocity,radius,text,paint);
    }
}