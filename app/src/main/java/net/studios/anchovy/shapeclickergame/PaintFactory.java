package net.studios.anchovy.shapeclickergame;

import android.content.Context;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.LinkedList;

public class PaintFactory {
    private static PaintFactory instance;
    private static HashMap<Integer, Paint> paintList;

    public static PaintFactory getInstance() {
        if (instance == null) instance = new PaintFactory();
        return instance;
    }

    private PaintFactory() {
        paintList = new HashMap<>();
    }

    public static void generatePaintColor(Context context) {
        Paint paint1 = new Paint();
        paintList.clear();
        paint1.setColor(context.getResources().getColor(android.R.color.holo_red_dark));
        paintList.put(0, paint1);
        Paint paint2 = new Paint();
        paint2.setColor(context.getResources().getColor(android.R.color.holo_green_dark));
        paintList.put(1, paint2);
        Paint paint3 = new Paint();
        paint3.setColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        paintList.put(2, paint3);
        Paint paint4 = new Paint();
        paintList.put(3, paint4);
    }

    public Paint getPaint(int code) {
        Paint temp = paintList.get(code);
        if (code == 3) {
            temp.setARGB(255, GameUtil.randomInt(0,256), GameUtil.randomInt(0,256), GameUtil.randomInt(0,256));
        }
        return temp;
    }
}
