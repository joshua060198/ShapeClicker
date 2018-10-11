package net.studios.anchovy.shapeclickergame.model;

import android.support.annotation.NonNull;

import java.util.Locale;

public class User implements Comparable<User> {
    private String name;
    private int score;
    private long lastPlayed;
    private String picturePath;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public long getLastPlayed() {
        return lastPlayed;
    }

    public void setLastPlayed(long lastPlayed) {
        this.lastPlayed = lastPlayed;
    }

    public String getPicturePath() {
        return picturePath;
    }

    public void setPicturePath(String picturePath) {
        this.picturePath = picturePath;
    }

    public User(String name, int score, long lastPlayed, String picturePath) {
        this.name = name;
        this.score = score;
        this.lastPlayed = lastPlayed;
        this.picturePath = picturePath;
    }

    @Override
    public String toString() {
        return String.format(Locale.getDefault(), "%s,%s,%d,%d", this.picturePath, this.name, this.score, this.lastPlayed);
    }

    @Override
    public int compareTo(@NonNull User o) {
        return o.getScore() - this.score;
    }
}
