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

import android.content.SharedPreferences;

import net.studios.anchovy.shapeclickergame.GameUtil;

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
        this.editor.apply();
    }

    private void saveLong(String key, long data) {
        this.editor.putLong(key, data);
        this.editor.apply();
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

    public void saveStringPathCurrentUser(String key, String path) {
        this.preferenceEditor.putString(key, path);
        this.preferenceEditor.apply();
    }

    public String loadStringSetting(String key) {
        return this.preference.getString(key, "");
    }

    public int loadIntSetting(String key) {
        return this.preference.getInt(key, 0);
    }

    public boolean loadBooleanSetting(String key) {
        return this.preference.getBoolean(key, true);
    }

    public int loadSettingMaxShapes(String key) { return Integer.parseInt(this.preference.getString(key, "5")); }

    public void saveUserData(String s){
        this.editor.putString(GameUtil.USER_DATA, s);
        this.editor.apply();
    }

    public String loadUserData() {
        return this.sp.getString(GameUtil.USER_DATA, "");
    }

    public void saveStateData(String s){
        this.editor.putString(GameUtil.STATE_DATA, s);
        this.editor.apply();
    }

    public String loadStateData() {
        return this.preference.getString(GameUtil.STATE_DATA, "");
    }
}
