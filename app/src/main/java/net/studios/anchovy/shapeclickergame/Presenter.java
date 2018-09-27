package net.studios.anchovy.shapeclickergame;

import android.graphics.Canvas;

public class Presenter {

    private ShapeFactory shapeFactory;

    public void initiateFactory(byte configuration, Canvas canvas, int maxH, int maxW) {
        this.shapeFactory = ShapeFactory.getInstance(configuration,canvas,maxH,maxW);
    }

    public void generateShape() {
        this.shapeFactory.generateShape();
        this.shapeFactory.drawAll();
    }

    public void generateSoal() {
        this.shapeFactory.showSoal();
    }
}
