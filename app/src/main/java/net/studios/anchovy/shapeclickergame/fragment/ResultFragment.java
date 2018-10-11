package net.studios.anchovy.shapeclickergame.fragment;


import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import net.studios.anchovy.shapeclickergame.R;
import net.studios.anchovy.shapeclickergame.model.Circle;
import net.studios.anchovy.shapeclickergame.model.Rectangle;
import net.studios.anchovy.shapeclickergame.model.Shape;
import net.studios.anchovy.shapeclickergame.model.User;

import java.util.Locale;
public class ResultFragment extends Fragment implements View.OnClickListener {

    private int totalClick, totalTrue, totalRect, totalCircle;
    private ResultFragmentListener listener;
    private Shape currentClick;

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

        User now = listener.getCurrentUser();

        TextView score = v.findViewById(R.id.score_res);
        score.setText(String.format(Locale.getDefault(), getResources().getString(R.string.result_val), now.getScore()));

        TextView name = v.findViewById(R.id.res_user_name);
        name.setText(now.getName());

        ImageView pic = v.findViewById(R.id.res_user_pic);
        pic.setImageBitmap(BitmapFactory.decodeFile(now.getPicturePath()));

        v.findViewById(R.id.btn_menu_game).setOnClickListener(this);
        v.findViewById(R.id.btn_try_again).setOnClickListener(this);

        TextView no_clik = v.findViewById(R.id.no_clicks_val);
        no_clik.setText(String.format(Locale.getDefault(), getResources().getString(R.string.result_val), totalClick));

        TextView click_true = v.findViewById(R.id.no_clicks_per_val);
        click_true.setText(String.format(Locale.getDefault(), getResources().getString(R.string.result_val) + " %c", (int)((totalTrue*1.0)/(totalClick*1.0)*100), '%'));

        TextView click_circle = v.findViewById(R.id.no_clicks_circle_val);
        click_circle.setText(String.format(Locale.getDefault(), getResources().getString(R.string.result_val), totalCircle));

        TextView click_rect = v.findViewById(R.id.no_clicks_rect_val);
        click_rect.setText(String.format(Locale.getDefault(), getResources().getString(R.string.result_val), totalRect));

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();

        this.totalClick = 0;
        this.totalTrue = 0;
        this.totalRect = 0;
        this.totalCircle = 0;
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

    public void clicked(boolean status) {
        if (currentClick == null) return;
        if (status) {
            this.totalTrue++;
            if(currentClick instanceof Circle) {
                totalCircle++;
            } else if (currentClick instanceof Rectangle) {
                totalRect++;
            }
        }
        this.totalClick++;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_menu_game:
                listener.toMainMenu();
                break;
            case R.id.btn_try_again:
                listener.tryAgain();
                break;
        }
    }

    public void setCurrentClick(Shape currentClick) {
        this.currentClick = currentClick;
    }

    public interface ResultFragmentListener {
        User getCurrentUser();
        void tryAgain();
        void toMainMenu();
    }
}