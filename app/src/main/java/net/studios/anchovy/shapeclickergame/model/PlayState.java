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

public class PlayState {

    private Shape[] shapeList;
    private Shape soal;
    private long time;
    private int score;

    public Shape[] getShapeList() {
        return shapeList;
    }

    public void setShapeList(Shape[] shapeList) {
        this.shapeList = shapeList;
    }

    public Shape getSoal() {
        return soal;
    }

    public void setSoal(Shape soal) {
        this.soal = soal;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public PlayState(Shape[] shapeList, Shape soal, long time, int score) {
        this.shapeList = shapeList;
        this.soal = soal;
        this.time = time;
        this.score = score;
    }
}
