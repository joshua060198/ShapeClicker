package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.R;
import net.studios.anchovy.shapeclickergame.model.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class ResultFragment extends Fragment {

    private int totalClick, totalTrue, totalRect, totalCircle, totalTriangle;
    private User user;
    private ResultFragmentListener listener;

    public static ResultFragment newInstance() {
        return new ResultFragment();
    }

    public ResultFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_result, container, false);



        return v;
    }

    @Override
    public void onResume() {
        this.totalClick = 0;
        this.totalTriangle = 0;
        this.totalTrue = 0;
        this.totalRect = 0;
        this.totalCircle = 0;
        this.user = null;

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof  ResultFragmentListener) {
            this.listener = (ResultFragmentListener) context;
        }

    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }

    public void clicked(boolean status, byte shape) {
        if (status) this.totalTrue++;
        this.totalClick++;

        switch (shape) {
            case GameUtil.SQUARE_SHAPE:
                this.totalRect++;
                break;
            case GameUtil.CIRCLE_SHAPE:
                this.totalCircle++;
                break;
            case GameUtil.TRIANGLE_SHAPE:
                this.totalTriangle++;
                break;
        }
    }

    public interface ResultFragmentListener {
        User getCurrentUser();
    }
}