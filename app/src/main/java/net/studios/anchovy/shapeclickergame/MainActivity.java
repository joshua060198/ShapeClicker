package net.studios.anchovy.shapeclickergame;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.preference.Preference;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

import net.studios.anchovy.shapeclickergame.fragment.HomeFragment;
import net.studios.anchovy.shapeclickergame.fragment.PlayFragment;
import net.studios.anchovy.shapeclickergame.fragment.ResultFragment;
import net.studios.anchovy.shapeclickergame.fragment.SettingFragment;
import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;
import net.studios.anchovy.shapeclickergame.model.User;

import java.io.IOException;
import java.util.List;

public class MainActivity extends AppCompatActivity implements Preference.OnPreferenceChangeListener, HomeFragment.HomeFragmentListener, PlayFragment.PlayFragmentListener, ResultFragment.ResultFragmentListener, MyAlertDialogBuilder.AlertDialogListener {

    private Presenter presenter;
    private HomeFragment homeFragment;
    private PlayFragment playFragment;
    private ResultFragment resultFragment;
    private SettingFragment settingFragment;
    private byte gameState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.presenter = new Presenter();
        this.homeFragment = HomeFragment.newInstance(getLayoutInflater());
        this.playFragment = PlayFragment.newInstance(presenter);
        this.resultFragment = ResultFragment.getInstance();
        this.settingFragment =  new SettingFragment();

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GameUtil.PERMISSION_REQ_CODE);
        }

        try {
            this.presenter.initiateObject(this);
        } catch (IOException e) {
            Toast.makeText(this, "ERROR TO INITIATE OBJECT!\nPlease restart the app or contact the developer!", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

        MyAlertDialogBuilder.getInstance().init(this);

//        this.gameState = this.presenter.loadState();

        this.gameState = 0;
        switch (gameState) {
            case GameUtil.HOME_STATE:
                changeToHomeFragment();
                break;
            case GameUtil.PLAY_STATE:
                changeToPlayGameFragment();
                break;
            case GameUtil.RESULT_STATE:
                changeToResultFragment();
                break;
            case GameUtil.SETTING_STATE:
                changeToSettingFragment();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == GameUtil.PERMISSION_REQ_CODE)
        {
            int grantResultsLength = grantResults.length;
            if(grantResultsLength > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "You grant write external storage permission.", Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeToPlayGameFragment() {
        saveState(GameUtil.PLAY_STATE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playFragment).commitAllowingStateLoss();
    }

    private void changeToSettingFragment() {
        saveState(GameUtil.SETTING_STATE);
        List<Fragment> fragList = getSupportFragmentManager().getFragments();
        for (Fragment f:fragList) {
            getSupportFragmentManager().beginTransaction().remove(f).commitAllowingStateLoss();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, settingFragment).commitAllowingStateLoss();
    }

    private void changeToHomeFragment() {
        saveState(GameUtil.HOME_STATE);
        getFragmentManager().beginTransaction().remove(settingFragment).commitAllowingStateLoss();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commitAllowingStateLoss();
    }

    private void changeToResultFragment() {
        saveState(GameUtil.RESULT_STATE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultFragment).commitAllowingStateLoss();
    }

    private void saveState(byte state) {
        this.gameState = state;
        this.presenter.saveState(this.gameState);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.gameState = this.presenter.loadState();

        switch (gameState) {
            case GameUtil.HOME_STATE:
                changeToHomeFragment();
                break;
            case GameUtil.PLAY_STATE:
                changeToPlayGameFragment();
                break;
            case GameUtil.RESULT_STATE:
                changeToResultFragment();
                break;
            case GameUtil.SETTING_STATE:
                changeToSettingFragment();
                break;
        }
    }

    @Override
    public void onBackPressed() {
        this.gameState = this.presenter.loadState();

        switch (gameState) {
            case GameUtil.HOME_STATE:
                MyAlertDialogBuilder.getInstance().showExitAppDialog();
                break;
            case GameUtil.PLAY_STATE:
                MyAlertDialogBuilder.getInstance().showEndGameDialog();
                break;
            case GameUtil.RESULT_STATE:
                // TODO
                break;
            case GameUtil.SETTING_STATE:
                changeToHomeFragment();
                break;
        }
    }

    @Override
    public void playGame() {
//        PreferenceLoader.getInstance().saveInt(GameUtil.TIME_KEY, 60000);
        this.presenter.saveTime(60000);
        this.presenter.saveScore(0);
        this.changeToPlayGameFragment();
    }

    @Override
    public void changeToSetting() {
        this.changeToSettingFragment();
    }


    @Override
    public void initiateFactory(Canvas canvas, int maxH, int maxW) {
        this.presenter.initiateFactory((byte)3, canvas, maxH, maxW);
    }

    @Override
    public void generateShape() {
        this.presenter.generateShape();
    }

    @Override
    public void generateSoal() {
        this.presenter.generateSoal();
    }

    @Override
    public void clearSoal() {
        this.presenter.clearSoal();
    }

    @Override
    public void saveTime(long time) {
        this.presenter.saveTime(time);
    }

    @Override
    public void saveScore(int score) {
        this.presenter.saveScore(score);
    }

    @Override
    public int loadScore() {
        return this.presenter.loadScore();
    }

    @Override
    public long loadTime() {
        return this.presenter.loadTime();
    }

    @Override
    public boolean isTappedCorrectly() {
        return this.presenter.isTappedCorrectly();
    }

    @Override
    public boolean isTappedInsideAShape(float x, float y) {
        return this.presenter.isTappedInsideAShape(x,y);
    }

    @Override
    public void goToResult(int score) {
        this.presenter.saveTime(60000);
        this.presenter.saveScore(0);
        //TODO : CHANGE TO RESULT
        changeToHomeFragment();
    }

    @Override
    public void endGame() {
//        changeToResultFragment();\
        this.presenter.saveTime(60000);
        this.presenter.saveScore(0);
        //TODO : CHANGE TO RESULT
        changeToHomeFragment();

    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public User getCurrentUser() {
        return this.presenter.getCurrentUser();
    }

    @Override
    public int loadMaxShapes() {
        return this.presenter.loadMaxShapes(getString(R.string.setting_game_max_shape_total));
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        if (preference.getKey().equals(getString(R.string.setting_game_max_shape_total))) {

        }
    }
}
