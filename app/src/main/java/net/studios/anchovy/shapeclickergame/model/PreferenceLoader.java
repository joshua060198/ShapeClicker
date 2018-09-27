package net.studios.anchovy.shapeclickergame.model;

import android.content.SharedPreferences;

import net.studios.anchovy.shapeclickergame.GameUtil;

import java.io.IOException;

public class PreferenceLoader {

    private static PreferenceLoader loader;
    private SharedPreferences preference;
    private SharedPreferences.Editor editor;

    private PreferenceLoader() {

    }

    public static PreferenceLoader getInstance() {
        if(loader == null){
            loader = new PreferenceLoader();
        }
        return loader;
    }

    public void init(SharedPreferences preference) {
        this.preference = preference;
        this.editor = this.preference.edit();
    }

    public void saveInt(String key, int data) {
        this.editor.putInt(key, data);
        this.editor.commit();
    }

    public int loadInt(String key) {
        return this.preference.getInt(key, 0);

    }

    public void savePlayPausedValue(int score, int time) {
        this.saveInt(GameUtil.TIME_KEY, time);
        this.saveInt(GameUtil.SCORE, score);
    }

    public int[] loadPlayPausedValue() {
        int[] result = new  int[2];
        result[0] = loadInt(GameUtil.TIME_KEY);
        result[1] = loadInt(GameUtil.SCORE);
        return result;
    }
}
