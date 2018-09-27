package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.HighScoreAdapter;
import net.studios.anchovy.shapeclickergame.PaintFactory;
import net.studios.anchovy.shapeclickergame.Presenter;
import net.studios.anchovy.shapeclickergame.R;
import net.studios.anchovy.shapeclickergame.ShapeFactory;
import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;
import net.studios.anchovy.shapeclickergame.model.Shape;

public class PlayFragment extends Fragment implements View.OnTouchListener {

    private ImageView imageView;
    private TextView timer, highscore;
    private Presenter presenter;
    private PlayFragmentListener listener;
    private int timeLeft;
    private long prevTime;
    private PreferenceLoader preferenceLoader;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance(Presenter presenter) {
        PlayFragment fragment = new PlayFragment();
        fragment.presenter = presenter;
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play, container, false);

        this.imageView = v.findViewById(R.id.canvas);
        this.timer = v.findViewById(R.id.timer);
        this.highscore = v.findViewById(R.id.highscore_text);

        imageView.post(new Runnable() {
            @Override
            public void run() {
                Bitmap bitmap = Bitmap.createBitmap(
                        imageView.getWidth(),
                        imageView.getHeight(),
                        Bitmap.Config.ARGB_4444
                );
                imageView.setImageBitmap(bitmap);
                Canvas canvas = new Canvas(bitmap);
                listener.initiateFactory(canvas, imageView.getHeight()-GameUtil.JARAK_SOAL, imageView.getWidth());
                Paint line = PaintFactory.getInstance().getPaint(GameUtil.WARNA_GARIS_SOAL);
                line.setStrokeWidth(GameUtil.STROKE_GARIS_SOAL);
                canvas.drawLine(0,imageView.getHeight()- GameUtil.JARAK_SOAL,imageView.getWidth(),imageView.getHeight()- GameUtil.JARAK_SOAL, line);
                for (int i = 0; i < 10; i++) listener.generateShape();
                listener.generateSoal();
                imageView.invalidate();
            }
        });
        imageView.setOnTouchListener(this);
        imageView.invalidate();

        this.preferenceLoader = PreferenceLoader.getInstance();
        return v;
    }

    public void endGame(){
        this.preferenceLoader.savePlayPausedValue(0,60000);
    }

    @Override
    public void onResume() {
        super.onResume();
        int[] values = this.preferenceLoader.loadPlayPausedValue();
        if(values[0] != 0) this.timeLeft = values[0];
    }

    @Override
    public void onPause() {
        super.onPause();
        this.preferenceLoader.savePlayPausedValue(0, timeLeft);
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
//        Log.d("tag", event.getX() + ", " + event.getY());
//        Paint p = new Paint();
//        p.setColor(getResources().getColor(R.color.colorPrimary));
//        this.canvas.drawCircle(event.getX(), event.getY(), 100, p);
//        v.invalidate();
//        this.shapeFactory.generateShape((int) event.getX(), (int) event.getY(), 100, 100, 10.0, "ho");
        listener.generateShape();
        imageView.invalidate();
//        Toast.makeText(getContext(), "YES", Toast.LENGTH_SHORT).show();
        return true;
    }

    public interface PlayFragmentListener {
        void initiateFactory(Canvas canvas, int maxH, int maxW);
        void generateShape();
        void generateSoal();
    }
}
