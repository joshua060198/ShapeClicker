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

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.util.Log;

import net.studios.anchovy.shapeclickergame.model.Shape;
import net.studios.anchovy.shapeclickergame.model.service.BackgroundMusicServiceHome;
import net.studios.anchovy.shapeclickergame.model.JSONParser;
import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;
import net.studios.anchovy.shapeclickergame.model.User;
import net.studios.anchovy.shapeclickergame.model.service.BackgroundMusicServicePlay;

import java.io.IOException;
import java.util.ArrayList;

public class Presenter {

    private ShapeFactory shapeFactory;
    private PreferenceLoader preferenceLoader;
    private User currentUser;
    private JSONParser jsonParser;
    private Intent backsound;

    public void initiateFactory(byte configuration, Canvas canvas, int maxH, int maxW) {
        this.shapeFactory = ShapeFactory.getInstance(configuration,canvas,maxH,maxW);
    }

    public void initiateObject(Context context) {
        this.preferenceLoader = PreferenceLoader.getInstance();
        this.preferenceLoader.init(context.getSharedPreferences(GameUtil.PLAY_GAME_PREFERENCE_KEY, Context.MODE_PRIVATE), PreferenceManager.getDefaultSharedPreferences(context));
        PaintFactory.getInstance().generatePaintColor(context);
        String name = this.preferenceLoader.loadStringSetting(context.getResources().getString(R.string.setting_user_profile_username));
        String picturePath = this.preferenceLoader.loadStringSetting(context.getResources().getString(R.string.setting_user_profile_picture));
        int score = 0;
        long lastPlayed = System.currentTimeMillis();
        this.currentUser = new User(name,score,lastPlayed,picturePath);
        jsonParser = JSONParser.getInstance();
        changeMusicToHome(context);
        String settingTemp = preferenceLoader.loadStringSetting(context.getResources().getString(R.string.setting_game_shape_color));
        if (settingTemp.isEmpty()) {
            updatePaintFactoryConfig(GameUtil.SETTING_COLOUR_VALUES_RGB);
        } else {
            updatePaintFactoryConfig(preferenceLoader.loadStringSetting(context.getResources().getString(R.string.setting_game_shape_color)));
        }
    }

    public void generateShape() {
        this.shapeFactory.generateShape();
        this.shapeFactory.drawAll();
    }

    public void generateSoal() {
        this.shapeFactory.showSoal();
    }

    public User getCurrentUser() {
        return this.currentUser;
    }

    public void saveTime(long time) {
        this.preferenceLoader.saveTimerCountDownTime(time);
    }

    public void saveScore(int score) {
        this.preferenceLoader.saveCurrentScore(score);
    }

    public long loadTime() {
        return this.preferenceLoader.loadTimerCountDownTime();
    }

    public int loadScore() {
        return this.preferenceLoader.loadLastScore();
    }

    public boolean isTappedCorrectly() {
        return this.shapeFactory.isTappedCorrectly();
    }

    public boolean isTappedInsideAShape(float x, float y) {
        return this.shapeFactory.isTappedInsideAShape(x,y);
    }

    public void clearSoal() {
        this.shapeFactory.clearSoal();
    }

    public byte loadState() {
        return this.preferenceLoader.loadLastState();
    }

    public void saveState(byte state) {
        this.preferenceLoader.saveState(state);
    }

    public int loadMaxShapes(String key) {
        return this.preferenceLoader.loadSettingMaxShapes(key);
    }

    public void updateUserName(String name) {
        this.currentUser.setName(name);
    }

    public void updateUserPicture(String key, String path) {
        this.currentUser.setPicturePath(path);
        this.preferenceLoader.saveStringPathCurrentUser(key, path);
    }

    private void printLog() {
        Log.d("USER_TEST", this.currentUser.toString());
    }

    public void stopBackSound(Context context) {
        if (backsound!=null)context.stopService(backsound);
    }

    public void changeMusicToPlay(Context context) {
        if (backsound!=null) context.stopService(backsound);
        if (preferenceLoader.loadBooleanSetting(context.getResources().getString(R.string.setting_game_sound))) {
            backsound = new Intent(context, BackgroundMusicServicePlay.class);
            context.startService(backsound);
        }
    }

    public void changeMusicToHome(Context context) {
        if (backsound!=null) context.stopService(backsound);
        if (preferenceLoader.loadBooleanSetting(context.getResources().getString(R.string.setting_game_sound))) {
            backsound = new Intent(context, BackgroundMusicServiceHome.class);
            context.startService(backsound);
        }
    }

    public void updatePaintFactoryConfig(String newConfig) {
        PaintFactory.setConfig(newConfig);
    }

    public void clearAll() {
        this.shapeFactory.clearAll();
    }

    public void updateScore(int score) {
        this.currentUser.setScore(score);
    }

    public void updateLastPlayed(long lastPlayed) {
        this.currentUser.setLastPlayed(lastPlayed);
    }

    public void saveHighScore(ArrayList<User> data) {
        preferenceLoader.saveUserData(this.jsonParser.parseUserToJson(data));
    }

    public ArrayList<User> loadHighScore() {
        return this.jsonParser.getUserListFromJson(preferenceLoader.loadUserData());
    }

    public Shape getCurrentClick() {
        return this.shapeFactory.getTapped();
    }


}
