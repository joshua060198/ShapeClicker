package net.studios.anchovy.shapeclickergame;

import android.graphics.Canvas;
import android.support.annotation.Nullable;

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
    }

    public Shape generateShape() {
        Shape res = null;
        int i = 0;
        int[]arrTemp = new int[3];
        int temp = config;
        while (temp > 0) {
            while (temp > 0) {
                if ((temp & 1) > 0) {
                    arrTemp[i++] = GameUtil.randomInt(config, 0, 1000000);
                } else {
                    arrTemp[i++] = 0;
                    temp = temp >> 1;
                }
            }

            int x, y, max = GameUtil.max(arrTemp[0], arrTemp[1], arrTemp[2]);
            if (max == arrTemp[0]) {
                int
            } else if (max == arrTemp[1]) {

            } else {

            }

            if (res == null) temp = config;
        }

        return res;
    }

    private Shape searchForAvailableRectangle(int x, int y, int width, int height, @Nullable String text) {
        Shape res = null;
        if (this.pool[0].size() == 0) {
            res = createRectangle(x,y,width,height,text);
            this.pool[0].add(res);
        } else {
            Iterator<Shape> it = this.pool[0].iterator();
            Rectangle temp = null;
            while (it.hasNext()) {
                temp = (Rectangle) it.next();
                if (!temp.isOnScreen()) {
                    temp.setX(x);
                    temp.setY(y);
                    temp.setWidth(width);
                    temp.setHeight(height);
                    temp.setText(text);
                    res = temp;
                    break;
                }
            }

            if (temp == null) {
                res = createRectangle(x,y,width,height,text);
                this.pool[0].add(res);
            }
        }
        res.draw();
        return res;
    }

    private Shape searchForAvailableCircle(int x, int y, int radius, @Nullable String text) {
        Shape res = null;
        if (this.pool[1].size() == 0) {
            res = createCircle(x,y,radius,text);
            this.pool[1].add(res);
        } else {
            Iterator<Shape> it = this.pool[1].iterator();
            Circle temp = null;
            while (it.hasNext()) {
                temp = (Circle) it.next();
                if (!temp.isOnScreen()) {
                    temp.setX(x);
                    temp.setY(y);
                    temp.setRadius(radius);
                    temp.setText(text);
                    res = temp;
                    break;
                }
            }

            if (temp == null) {
                res = createCircle(x,y,radius,text);
                this.pool[1].add(res);
            }
        }
        res.draw();
        return res;
    }

    private Rectangle createRectangle(int x, int y, int width, int height, @Nullable String text) {
        return new Rectangle(canvas,x,y,0,width,height,text);
    }

    private Circle createCircle(int x, int y, int radius, @Nullable String text) {
        return new Circle(canvas,x,y,0,radius,text);
    }
}