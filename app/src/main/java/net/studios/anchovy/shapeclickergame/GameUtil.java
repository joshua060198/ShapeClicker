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
    public static final int MAX_WIDTH_SQUARE = 200;
    public static final int MAX_HEIGHT_SQUARE = 200;
    public static final int MAX_RADIUS_CIRCLE = 135;
    public static final int MIN_WIDTH_SQUARE = 50;
    public static final int MIN_HEIGHT_SQUARE = 50;
    public static final int MIN_RADIUS_CIRCLE = 50;

    public static final int JARAK_SOAL = 300;
    public static final int WARNA_GARIS_SOAL = 2;
    public static final int STROKE_GARIS_SOAL = 10;

    //key untuk save ke preference
    public static final String TIME_KEY = "opeeennnsesameeeee";
    public static final String SCORE = "leroyjenkins";
    public static final String USER_DATA = "smokeweedeveryday";
    public static final String STATE_DATA = "statedoesntmatter";

    private static final Random r = new Random();

//    public static final int[] PAINT_LIST = {{0,0xff0000}, {1,0x00ff00}, {2,0x0000ff}};

    public static int randomInt(int start, int end) {
        return start + r.nextInt(end-start);
    }

    public static int max(int... values) {
        int res = Integer.MIN_VALUE;
        for(int i:values) if (i > res) res = i;
        return res;
    }

    public static int min(int... values) {
        int res = Integer.MAX_VALUE;
        for(int i:values) if (i < res) res = i;
        return res;
    }

    public static String generateColor() {
        final char [] hex = { '0', '1', '2', '3', '4', '5', '6', '7',
                '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
        char [] s = new char[7];
        int     n = randomInt(0,0x1000000);

        s[0] = '#';
        for (int i=1;i<7;i++) {
            s[i] = hex[n & 0xf];
            n >>= 4;
        }
        return new String(s);
    }
}
