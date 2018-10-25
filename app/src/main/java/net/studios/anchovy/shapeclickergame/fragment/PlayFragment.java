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

package net.studios.anchovy.shapeclickergame.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.MyAlertDialogBuilder;
import net.studios.anchovy.shapeclickergame.PaintFactory;
import net.studios.anchovy.shapeclickergame.Presenter;
import net.studios.anchovy.shapeclickergame.R;

import java.util.Locale;

public class PlayFragment extends Fragment implements View.OnTouchListener, GestureDetector.OnGestureListener, View.OnClickListener {

    private ImageView imageView;
    private TextView timer, highscore;
    private ProgressBar timeProgress;
    private PlayFragmentListener listener;
    private long timeLeft;
    private CountDownTimer timerController;
    private int currentScore, maxH, maxW;
    private GestureDetector gestureDetector;
    private Canvas canvas;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance() {
        return new PlayFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_play, container, false);

        this.imageView = v.findViewById(R.id.canvas);
        this.timer = v.findViewById(R.id.timer);
        this.highscore = v.findViewById(R.id.highscore_score);
        this.timeProgress = v.findViewById(R.id.time_progress);
        this.gestureDetector = new GestureDetector(getContext(), this);

        v.findViewById(R.id.play_end).setOnClickListener(this);

        imageView.post(new Runnable() {
            @Override
            public void run() {
                maxH = imageView.getHeight();
                maxW = imageView.getWidth();
                Bitmap bitmap = Bitmap.createBitmap(
                        maxW,
                        maxH,
                        Bitmap.Config.ARGB_4444
                );
                imageView.setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);
                resetCanvas();
                int maxShapes = listener.loadMaxShapes();
                for (int i = 0; i < maxShapes; i++) listener.generateShape();
                listener.generateSoal();
                imageView.invalidate();
            }
        });
        imageView.setOnTouchListener(this);
        imageView.invalidate();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        long time = listener.loadTime();
        currentScore = listener.loadScore();
        if(time != 0) {
            this.timerController = new CountDownTimer(time, 7) {
                @Override
                public void onTick(long millisUntilFinished) {
                    long second = millisUntilFinished/1000;
                    int milis = (int) millisUntilFinished%1000;
                    timer.setText(String.format(Locale.getDefault(), "%02d:%03d", second, milis));
                    timeProgress.setProgress((int)(60000-millisUntilFinished));

                    if(millisUntilFinished < 10000){
                        timer.setTextColor(getResources().getColor(R.color.red));
                    }
                    timeLeft = millisUntilFinished;
                }

                @Override
                public void onFinish() {
                    timer.setText(getResources().getString(R.string.timer));
                    timeLeft = 60000;
                    currentScore = 0;
                    this.cancel();
                    listener.goToResult();
                }
            };
            this.timerController.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        listener.saveTime(timeLeft);
        listener.saveScore(currentScore);
        timerController.cancel();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof PlayFragmentListener) {
            this.listener = (PlayFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return gestureDetector.onTouchEvent(event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return true;
    }

    @Override
    public void onShowPress(MotionEvent e) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        if (listener.isTappedInsideAShape(e.getX(), e.getY())) {
            if(listener.isTappedCorrectly()) {
                currentScore += GameUtil.ANSWER_CORRECT;
                this.listener.clearSoal();
                this.listener.generateShape();
                this.listener.generateSoal();
            } else {
                currentScore += GameUtil.ANSWER_WRONG;
            }

            listener.updateScore(currentScore);
            listener.updateLastPlayed(System.currentTimeMillis());
            this.highscore.setText(String.format(Locale.getDefault(), "%d", this.currentScore));
        }
        return true;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return true;
    }

    @Override
    public void onLongPress(MotionEvent e) {

    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_end:
                MyAlertDialogBuilder.getInstance().showEndGameDialog();
                break;
        }
    }

    public int getMaxH() {
        return maxH;
    }

    public int getMaxW() {
        return maxW;
    }

    public Canvas getCanvas() {
        return canvas;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        listener.clearAll();
        resetCanvas();
    }

    private void resetCanvas() {
        canvas.drawColor(getResources().getColor(android.R.color.white));
        listener.initiateFactory(canvas, maxH-GameUtil.JARAK_SOAL, maxW);
        Paint line = PaintFactory.getInstance().getPaintByCode(GameUtil.WARNA_GARIS_SOAL);
        line.setStrokeWidth(GameUtil.STROKE_GARIS_SOAL);
        canvas.drawLine(0,maxH- GameUtil.JARAK_SOAL,maxW,maxH- GameUtil.JARAK_SOAL, line);
        imageView.invalidate();
    }

    public interface PlayFragmentListener {
        void initiateFactory(Canvas canvas, int maxH, int maxW);
        void generateShape();
        void generateSoal();
        void clearSoal();
        void saveTime(long time);
        void saveScore(int score);
        int loadScore();
        long loadTime();
        int loadMaxShapes();
        boolean isTappedCorrectly();
        boolean isTappedInsideAShape(float x, float y);
        void goToResult();
        void clearAll();
        void updateScore(int currentScore);
        void updateLastPlayed(long lastPlayed);
    }
}
