/*
    Shape Clicker Games v1.0
    Copyright (C) 2018  Anchovy Studios

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package net.studios.anchovy.shapeclickergame.model;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.annotation.Nullable;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.PaintFactory;

public class Rectangle extends Shape {

    private int width, height;

    public Rectangle(Canvas canvas, int x, int y, int velocity, int width, int height, @Nullable String text, Paint paint) {
        super(canvas, x, y, velocity, text, paint);
        this.width = width;
        this.height = height;
    }

    @Override
    public void move() {

    }

    @Override
    public void draw() {
        this.canvas.drawRect(x,y,x+width,y+height,paint);
    }

    @Override
    public void update() {

    }

    @Override
    public boolean isCollide(Shape other) {
        if (other instanceof Rectangle) {
            return isCollideRectWithRect(this, (Rectangle) other);
        } else if (other instanceof Circle) {
            return isCollideRectWithCircle((Circle)other, this);
        } else return false;
    }

    @Override
    public void drawSoal(int maxHeight, int maxWidth) {
        float x = (maxWidth/2.0f) - (this.width/2.0f);
        float y = maxHeight + ((GameUtil.JARAK_SOAL-this.height)/2.0f);
        this.canvas.drawRect(x,y,x+this.width,y+this.height,paint);
    }

    @Override
    public boolean isInside(float x, float y) {
//        Log.d("DEBUG", "RECT : " + this.x + " " + x + " "+ (this.width+this.x)+ " " +this.y + " " + y + " " + (this.height+ this.y));
//        if ((this.x <= x) && (x < this.x + this.width) && (this.y <= y) && (y < this.y + this.height)) Log.d("DEBUG", "TRUE");
//        else Log.d("DEBUG", "FALSE");
        return (this.x <= x) && (x < this.x + this.width) && (this.y <= y) && (y < this.y + this.height);
    }

    @Override
    public void clear() {
        this.canvas.drawRect(x,y,x+width,y+height, PaintFactory.getInstance().getPaintByCode(0));
    }

    @Override
    public void clearSoal(int maxWidth, int maxHeight) {
        float x = (maxWidth/2.0f) - (this.width/2.0f);
        float y = maxHeight + ((GameUtil.JARAK_SOAL-this.height)/2.0f);
        this.canvas.drawRect(x,y,x+this.width,y+this.height,PaintFactory.getInstance().getPaintByCode(0));
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
}
