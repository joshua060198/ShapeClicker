package net.studios.anchovy.shapeclickergame;

import android.content.Context;
import android.graphics.Paint;

import java.util.HashMap;
import java.util.LinkedList;

public class PaintFactory {
    private static PaintFactory instance;
    private static HashMap<Integer, Paint> paintList;
    private static String config;

    public static PaintFactory getInstance() {
        if (instance == null) instance = new PaintFactory();
        return instance;
    }

    private PaintFactory() {
        paintList = new HashMap<>();
    }

    public void generatePaintColor(Context context) {
        paintList.clear();
        Paint paint1 = new Paint();
        paint1.setColor(context.getResources().getColor(android.R.color.holo_red_dark));
        paintList.put(2, paint1);
        Paint paint2 = new Paint();
        paint2.setColor(context.getResources().getColor(android.R.color.holo_green_dark));
        paintList.put(3, paint2);
        Paint paint3 = new Paint();
        paint3.setColor(context.getResources().getColor(android.R.color.holo_blue_dark));
        paintList.put(4, paint3);
        Paint paint4 = new Paint();
        paintList.put(5, paint4);
        Paint paint5 = new Paint();
        paint5.setColor(context.getResources().getColor(android.R.color.white));
        paintList.put(0, paint5);
        Paint paint6 = new Paint();
        paint6.setColor(context.getResources().getColor(android.R.color.black));
        paintList.put(1, paint6);
    }

    public static void setConfig(String newConfig) {
        config = newConfig;
    }

    public Paint getPaint() {
        Paint temp;
        if (config.equals(GameUtil.SETTING_COLOUR_VALUES_RGB)) {
            temp = paintList.get(GameUtil.randomInt(2,4));
        } else if (config.equals(GameUtil.SETTING_COLOUR_VALUES_RANDOM)) {
            temp = new Paint();
            temp.setARGB(255, GameUtil.randomInt(0,255), GameUtil.randomInt(0,255), GameUtil.randomInt(0,255));
        } else {
            temp = paintList.get(1);
        }
        return temp;
    }

    public Paint getPaintByCode(int code) {
        Paint temp = paintList.get(code);
        if (code == 5) {
            temp.setARGB(255, GameUtil.randomInt(0,255), GameUtil.randomInt(0,255), GameUtil.randomInt(0,255));
        }
        return temp;
    }
}
