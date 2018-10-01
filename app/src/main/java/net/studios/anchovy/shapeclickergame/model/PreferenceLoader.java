package net.studios.anchovy.shapeclickergame.model;

import android.content.SharedPreferences;
import android.preference.Preference;

import net.studios.anchovy.shapeclickergame.GameUtil;

import java.io.IOException;

public class PreferenceLoader {

    private static PreferenceLoader loader;
    private SharedPreferences sp, preference;
    private SharedPreferences.Editor editor, preferenceEditor;

    private PreferenceLoader() {

    }

    public static PreferenceLoader getInstance() {
        if(loader == null){
            loader = new PreferenceLoader();
        }
        return loader;
    }

    public void init(SharedPreferences sp, SharedPreferences preference) {
        this.sp = sp;
        this.preference = preference;
        this.editor = this.sp.edit();
        this.preferenceEditor = this.preference.edit();
    }

    private void saveInt(String key, int data) {
        this.editor.putInt(key, data);
        this.editor.commit();
    }

    private void saveLong(String key, long data) {
        this.editor.putLong(key, data);
        this.editor.commit();
    }

    private long loadLong(String key){
        return this.sp.getLong(key, 0);
    }

    private int loadInt(String key) {
        return this.sp.getInt(key, 0);
    }

    public byte loadLastState() {
        return (byte) this.loadInt(GameUtil.STATE);
    }

    public void saveState(byte state) { this.saveInt(GameUtil.STATE, state); }

    public void saveTimerCountDownTime(long time) {
        this.saveLong(GameUtil.TIME_KEY, time);
    }

    public void saveCurrentScore(int score) {
        this.saveInt(GameUtil.SCORE, score);
    }

    public long loadTimerCountDownTime() {
        return this.loadLong(GameUtil.TIME_KEY);
    }

    public int loadLastScore() {
        return this.loadInt(GameUtil.SCORE);
    }

    public String loadUserName() {
        return this.preference.getString(GameUtil.USER_NAME, "USER");
    }

    public String loadUserPic() {
        //TODO
        return this.preference.getString(GameUtil.USER_PIC, "");
    }

    public int loadUserScore() {
        return this.preference.getInt(GameUtil.USER_SCORE, 0);
    }

    public long loadLastPlayed() {
        return this.preference.getLong(GameUtil.USER_LAST_PLAYED, 0);
    }

    public int loadSettingMaxShapes(String key) { return Integer.parseInt(this.preference.getString(key, "5")); }
}
