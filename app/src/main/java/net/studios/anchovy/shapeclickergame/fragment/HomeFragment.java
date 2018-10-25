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
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import net.studios.anchovy.shapeclickergame.HighScoreAdapter;
import net.studios.anchovy.shapeclickergame.R;
import net.studios.anchovy.shapeclickergame.model.User;

import java.util.ArrayList;

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
        View v = inflater.inflate(R.layout.fragment_home, container, false);
        ListView lv = v.findViewById(R.id.highscore_list_view);
        lv.setAdapter(this.adapter);

        v.findViewById(R.id.start_button).setOnClickListener(this);
        v.findViewById(R.id.setting_button).setOnClickListener(this);

        RelativeLayout linearLayout = v.findViewById(R.id.background);

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

    public ArrayList<User> getHighScoreData() {
        return this.adapter.getData();
    }

    public void loadHighScore(ArrayList<User> data) {
        this.adapter.loadData(data);
    }

    public interface HomeFragmentListener {
        void playGame();
        void changeToSetting();
    }

}
