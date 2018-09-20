package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.studios.anchovy.shapeclickergame.HighScoreAdapter;
import net.studios.anchovy.shapeclickergame.R;

public class HomeFragment extends Fragment implements View.OnClickListener {

    private HighScoreAdapter adapter;
    private HomeFragmentListener listener;

    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance(LayoutInflater inflater) {
        HomeFragment fragment = new HomeFragment();
        fragment.adapter = new HighScoreAdapter(inflater);
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ListView lv = v.findViewById(R.id.highscore_list_view);
        lv.setAdapter(this.adapter);

        v.findViewById(R.id.start_button).setOnClickListener(this);
        v.findViewById(R.id.setting_button).setOnClickListener(this);

        RelativeLayout linearLayout = (RelativeLayout) v.findViewById(R.id.background);

        AnimationDrawable animationDrawable = (AnimationDrawable) linearLayout.getBackground();

        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);

        animationDrawable.start();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof HomeFragmentListener) {
            this.listener = (HomeFragmentListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.listener = null;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_button:
                this.listener.playGame();
                break;
            case R.id.setting_button:
                this.listener.changeToSetting();
                break;
        }
    }

    public void addUserToHighScore(String name, String path, int score, long time) {
        this.adapter.addUser(name, path, time, score);
    }

    public interface HomeFragmentListener {
        void playGame();
        void changeToSetting();
    }

}
