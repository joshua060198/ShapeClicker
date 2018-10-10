package net.studios.anchovy.shapeclickergame;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.preference.PreferenceManager;
import android.util.Log;

import net.studios.anchovy.shapeclickergame.model.service.BackgroundMusicServiceHome;
import net.studios.anchovy.shapeclickergame.model.JSONParser;
import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;
import net.studios.anchovy.shapeclickergame.model.SaveAndLoad;
import net.studios.anchovy.shapeclickergame.model.User;
import net.studios.anchovy.shapeclickergame.model.service.BackgroundMusicServicePlay;

import java.io.IOException;
import java.util.ArrayList;

public class Presenter {

    private ShapeFactory shapeFactory;
    private PreferenceLoader preferenceLoader;
    private SaveAndLoad saveAndLoad;
    private PaintFactory paintFactory;
    private User currentUser;
    private JSONParser jsonParser;
    private Intent backsound;

    public void initiateFactory(byte configuration, Canvas canvas, int maxH, int maxW) {
        this.shapeFactory = ShapeFactory.getInstance(configuration,canvas,maxH,maxW);
    }

    public void initiateObject(Context context) throws IOException {
        this.preferenceLoader = PreferenceLoader.getInstance();
        this.preferenceLoader.init(context.getSharedPreferences(GameUtil.PLAY_GAME_PREFERENCE_KEY, Context.MODE_PRIVATE), PreferenceManager.getDefaultSharedPreferences(context));
        this.saveAndLoad = new SaveAndLoad(context);
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

    public void updateUserScore(int score) {
        this.currentUser.setScore(score);
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
}
