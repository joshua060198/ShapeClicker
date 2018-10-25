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
