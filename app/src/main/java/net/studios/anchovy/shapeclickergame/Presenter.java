package net.studios.anchovy.shapeclickergame;

import android.content.Context;
import android.graphics.Canvas;
import android.preference.PreferenceManager;

import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;
import net.studios.anchovy.shapeclickergame.model.SaveAndLoad;
import net.studios.anchovy.shapeclickergame.model.User;

import java.io.IOException;

public class Presenter {

    private ShapeFactory shapeFactory;
    private PreferenceLoader preferenceLoader;
    private SaveAndLoad saveAndLoad;
    private PaintFactory paintFactory;
    private User currentUser;

    public void initiateFactory(byte configuration, Canvas canvas, int maxH, int maxW) {
        this.shapeFactory = ShapeFactory.getInstance(configuration,canvas,maxH,maxW);
    }

    public void initiateObject(Context context) throws IOException {
        this.preferenceLoader = PreferenceLoader.getInstance();
        this.preferenceLoader.init(context.getSharedPreferences(GameUtil.PLAY_GAME_PREFERENCE_KEY, Context.MODE_PRIVATE), PreferenceManager.getDefaultSharedPreferences(context));
        this.saveAndLoad = new SaveAndLoad(context);
        PaintFactory.getInstance().generatePaintColor(context);
        String name = this.preferenceLoader.loadUserName();
        String picturePath = this.preferenceLoader.loadUserPic();
        int score = this.preferenceLoader.loadUserScore();
        long lastPlayed = System.currentTimeMillis();
        this.currentUser = new User(name,score,lastPlayed,picturePath);
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
}
