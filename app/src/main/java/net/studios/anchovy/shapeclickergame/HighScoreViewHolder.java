package net.studios.anchovy.shapeclickergame;

import android.widget.ImageView;
import android.widget.TextView;

public class HighScoreViewHolder {
    private ImageView picture;
    private TextView name, score, lastPlayed;

    public HighScoreViewHolder(ImageView picture, TextView name, TextView score, TextView lastPlayed) {
        this.picture = picture;
        this.name = name;
        this.score = score;
        this.lastPlayed = lastPlayed;
    }

    public ImageView getPicture() {
        return picture;
    }

    public TextView getName() {
        return name;
    }

    public TextView getScore() {
        return score;
    }

    public TextView getLastPlayed() {
        return lastPlayed;
    }
}
