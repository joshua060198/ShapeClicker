package net.studios.anchovy.shapeclickergame;

import java.util.Random;

public class GameUtil {
    public static final int PERMISSION_REQ_CODE = 1201;
    public static final byte SQUARE_SHAPE = 1;
    public static final byte CIRCLE_SHAPE = 2;
    public static final byte TRIANGLE_SHAPE = 4;
    public static final byte EASY_MAX_SHAPE = 5;
    public static final byte MEDIUM_MAX_SHAPE = 7;
    public static final byte HARD_MAX_SHAPE = 10;


    public static int randomInt(int seed, int start, int end) {
        Random r = new Random(seed);
        return start + r.nextInt(end-start);
    }

    public static int max(int... values) {
        int res = Integer.MIN_VALUE;
        for(int i:values) if (i > res) res = i;
        return res;
    }
}
