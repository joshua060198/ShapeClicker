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

package net.studios.anchovy.shapeclickergame;

import java.util.Random;

public class GameUtil {
    //FOR REQUEST CODE
    public static final int PERMISSION_REQ_CODE_WRITE = 1201;
    public static final int PERMISSION_REQ_CODE_READ = 1202;
    public static final int INTENT_FOR_SELECT_IMAGE_CODE = 1101;
    public static final int INTENT_FOR_SELECT_IMAGE_CODE_KITKAT = 1102;

    //FOR SHAPE
    public static final byte SQUARE_SHAPE = 1;
    public static final byte CIRCLE_SHAPE = 2;
    public static final int MAX_WIDTH_SQUARE = 200;
    public static final int MAX_HEIGHT_SQUARE = 200;
    public static final int MAX_RADIUS_CIRCLE = 135;
    public static final int MIN_WIDTH_SQUARE = 50;
    public static final int MIN_HEIGHT_SQUARE = 50;
    public static final int MIN_RADIUS_CIRCLE = 50;

    //FOR PLAY FRAGMENT
    public static final int JARAK_SOAL = 300;
    public static final int WARNA_GARIS_SOAL = 1;
    public static final int STROKE_GARIS_SOAL = 10;

    public static final int ANSWER_CORRECT = 5;
    public static final int ANSWER_WRONG = -3;

    //KEY FOR PREFERENCE
    public static final String PLAY_GAME_PREFERENCE_KEY = "A9E9LZCAhoRTGoFhFwzL";
    public static final String TIME_KEY = "EgYoVaNDI5nexZPK6HHD";
    public static final String SCORE = "XOD8I03jGdPzfS7p6B5e";
    public static final String STATE = "RLC4WgOVE4C9fbhe397N";
    public static final String USER_DATA = "yMcyXzfByvgxE6Q7aYfb";
    public static final String STATE_DATA = "h6lHe0qXydq3W4J7MyxX";

    //GAME STATE
    public static final byte HOME_STATE = 0;
    public static final byte SETTING_STATE = 1;
    public static final byte PLAY_STATE = 2;
    public static final byte RESULT_STATE = 3;

    //KEY FOR COLOUR
    public static final String SETTING_COLOUR_VALUES_RGB = "RGB COLOR";
    public static final String SETTING_COLOUR_VALUES_RANDOM = "RANDOM COLOR";

    //RANDOM OBJECT
    private static final Random r = new Random();

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

    public static String generateRandomString(int startChar, int endChar, int length) {
        StringBuilder buffer = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomLimitedInt = startChar + (int)
                    (r.nextFloat() * (endChar - startChar + 1));
            buffer.append((char) randomLimitedInt);
        }
        return buffer.toString();
    }
}

/*
hv69qaBgP44fi9yD7g6c
VFBIZXSh3sIBjhxugPI5
3m8U4n1cM9vA9AFZXH71
mAhdW1GuOI4YWHv8sJwn
2nmubEkY4Nh4gKGIJsUd
 */