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

    private LinkedList<Shape> pool[];

    public static ShapeFactory getInstance(byte configuration, Canvas canvasInp) {
       if (instance == null) {
           instance = new ShapeFactory();
       }

       config = configuration;
       canvas = canvasInp;

       return instance;
    }

    private ShapeFactory() {
    }

    public Shape generateShape(int x, int y, int width, int height, double velocity, @Nullable String text) {
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

            if (arrTemp[0] > arrTemp[1]) {
                if (arrTemp[0] > arrTemp[2]) {
                    res = searchForAvailableShape(0);
                } else {
                    res = searchForAvailableShape(2);
                }
            } else {
                if (arrTemp[1] > arrTemp[2]) {
                    res = searchForAvailableShape(1);
                } else {
                    res = searchForAvailableShape(2);
                }
            }

            if (res == null) temp = config;
        }

        res.setX(x);
        res.setY(y);
        res.setWidth(width);
        res.setHeight(height);
        if (text != null) {
            res.setText(text);
        }

        return res;
    }

    private Shape searchForAvailableShape(int idx) {
        if (this.pool[idx].size() == 0) {
            Shape temp;
            if (idx == 0) {
                temp = new Rectangle(canvas);
            } else {
                temp = new Circle(canvas);
            }
            this.pool[idx].add(temp);
            return temp;
        } else {
            Iterator<Shape> it = this.pool[idx].iterator();
            Shape temp = null;
            while (it.hasNext()) {
                temp = it.next();
                if (!temp.isOnScreen()) {
                    return temp;
                }
            }

            if (temp == null) {
                if (idx == 0) {
                    temp = new Rectangle(canvas);
                } else {
                    temp = new Circle(canvas);
                }
                this.pool[idx].add(temp);
            }

            return temp;
        }
    }
}