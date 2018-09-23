package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.studios.anchovy.shapeclickergame.HighScoreAdapter;
import net.studios.anchovy.shapeclickergame.R;
import net.studios.anchovy.shapeclickergame.ShapeFactory;
import net.studios.anchovy.shapeclickergame.model.Shape;

public class PlayFragment extends Fragment implements View.OnTouchListener {

    private ImageView imageView;
    private Canvas canvas;
    private Bitmap bitmap;
    private ShapeFactory shapeFactory;

    public PlayFragment() {
        // Required empty public constructor
    }

    public static PlayFragment newInstance(LayoutInflater inflater) {
        PlayFragment fragment = new PlayFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_play, container, false);


        RelativeLayout linearLayout = (RelativeLayout) v.findViewById(R.id.background);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);

        this.imageView = v.findViewById(R.id.canvas);
        imageView.post(new Runnable() {
            @Override
            public void run() {
                bitmap = Bitmap.createBitmap(
                        imageView.getWidth(),
                        imageView.getHeight(),
                        Bitmap.Config.ARGB_4444
                );
                imageView.setImageBitmap(bitmap);
                canvas = new Canvas(bitmap);
            }
        });
        imageView.setOnTouchListener(this);
        this.shapeFactory = ShapeFactory.getInstance((byte) 1, this.canvas);
        animationDrawable.start();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.d("tag", event.getX() + ", " + event.getY());
        Paint p = new Paint();
        p.setColor(getResources().getColor(R.color.colorPrimary));
        this.canvas.drawCircle(event.getX(), event.getY(), 100, p);
        v.invalidate();
//        this.shapeFactory.generateShape((int) event.getX(), (int) event.getY(), 100, 100, 10.0, "ho");
        return true;
    }
}
