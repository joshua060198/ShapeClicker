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

    public void saveLong(String key, long data) {
        this.editor.putLong(key, data);
        this.editor.commit();
    }

    public long loadLong(String key){
        return this.preference.getLong(key, 0);
    }

    public int loadInt(String key) {
        return this.preference.getInt(key, 0);

    }

    public void savePlayPausedValue(int score, long time) {
        this.saveInt(GameUtil.TIME_KEY, (int) time);
        this.saveInt(GameUtil.SCORE, score);
    }

    public int[] loadPlayPausedValue() {
        int[] result = new  int[2];
        result[0] = loadInt(GameUtil.TIME_KEY);
        result[1] = loadInt(GameUtil.SCORE);
        return result;
    }

    public void saveUserData(String s){
        this.editor.putString(GameUtil.USER_DATA, s);
    }

    public String loadUserData() {
        return this.preference.getString(GameUtil.USER_DATA, "");
    }

    public void saveStateData(String s){
        this.editor.putString(GameUtil.STATE_DATA, s);
    }

    public String loadStateData() {
        return this.preference.getString(GameUtil.STATE_DATA, "");
    }
}
