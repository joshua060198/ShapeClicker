package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
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

public class PlayFragment extends Fragment implements View.OnTouchListener {

    private ImageView imageView;
    private Canvas canvas;

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
        final Bitmap[] bitmap = new Bitmap[1];
        imageView.post(new Runnable() {
            @Override
            public void run() {
            bitmap[0] = Bitmap.createBitmap(
                    imageView.getWidth(),
                    imageView.getHeight(),
                    Bitmap.Config.ARGB_4444
            );
            imageView.setImageBitmap(bitmap[0]);
            canvas = new Canvas(bitmap[0]);
            canvas.drawColor(getResources().getColor(R.color.lightGrey));
            }
        });
        imageView.setOnTouchListener(this);

//        animationDrawable.start();

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
        Log.d("tag", event.getRawX() + ", " + event.getRawY());
        return false;
    }
}
