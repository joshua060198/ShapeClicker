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
