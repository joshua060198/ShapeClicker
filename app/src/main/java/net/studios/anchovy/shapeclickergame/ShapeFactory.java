package net.studios.anchovy.shapeclickergame;

import android.graphics.Canvas;
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
    }

    public Shape generateShape() {
//        Shape res = new Rectangle(canvas, 10,10,0,100,100,null);
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

            int max = GameUtil.max(arrTemp[0], arrTemp[1], arrTemp[2]);
            if (max == arrTemp[0]) {
                int width = GameUtil.randomInt(GameUtil.MIN_WIDTH_SQUARE, GameUtil.MAX_WIDTH_SQUARE);
                int height = GameUtil.randomInt(GameUtil.MIN_HEIGHT_SQUARE, GameUtil.MAX_HEIGHT_SQUARE);
                int x = GameUtil.randomInt(0, maxWidth-width);
                int y = GameUtil.randomInt(0, maxHeight-height);
                int velocity = 0;
                res = getAvailableRectangle(x,y,velocity,width,height,null);
            } else if (max == arrTemp[1]) {
                int radius = GameUtil.randomInt(GameUtil.MIN_RADIUS_CIRCLE, GameUtil.MAX_RADIUS_CIRCLE);
                int x = GameUtil.randomInt(radius, maxWidth-radius);
                int y = GameUtil.randomInt(radius, maxHeight-radius);
                int velocity = 0;
                res = getAvailableCircle(x,y,velocity,radius,null);
            } else {
                //TODO : FOR TRIANGLE
            }

            if (res == null || checkColliding(res))  {
                Log.e("FFFFF", "AGAIN");
                temp = config;
            }
        }
        active.add(res);

        return res;
    }

    private boolean checkColliding (Shape s1) {
        for (Shape s : active) {
            if (s1.isCollide(s)) return true;
        }
        return false;
    }

    private Shape getAvailableRectangle(int x, int y, int velocity, int width, int height, @Nullable String text) {
        Rectangle res;
        if (this.pool[0].size() == 0) {
            res = createRectangle(x,y,velocity,width,height,text);
        } else {
            res = (Rectangle) this.pool[0].getFirst();
            res.setX(x);
            res.setY(y);
            res.setWidth(width);
            res.setHeight(height);
            res.setText(text);
            res.setVelocity(velocity);
            this.pool[0].add(res);
            this.pool[0].removeFirst();
        }
        return res;
    }

    public void drawAll() {
        for(Shape s:active) {
            s.draw();
        }
    }

    private Shape getAvailableCircle(int x, int y, int velocity, int radius, @Nullable String text) {
        Circle res;
        if (this.pool[1].size() == 0) {
            res = createCircle(x, y, velocity, radius, text);
        } else {
            res = (Circle) this.pool[1].getFirst();
            res.setX(x);
            res.setY(y);
            res.setRadius(radius);
            res.setText(text);
            res.setVelocity(velocity);
            this.pool[1].add(res);
            this.pool[1].removeFirst();
        }

        return res;
    }

    private Rectangle createRectangle(int x, int y, int velocity, int width, int height, @Nullable String text) {
        return new Rectangle(canvas,x,y,velocity,width,height,text);
    }

    private Circle createCircle(int x, int y, int velocity, int radius, @Nullable String text) {
        return new Circle(canvas,x,y,velocity,radius,text);
    }
}