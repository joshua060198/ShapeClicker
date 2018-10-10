package net.studios.anchovy.shapeclickergame;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.preference.Preference;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
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

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, PlayFragment.PlayFragmentListener, ResultFragment.ResultFragmentListener, MyAlertDialogBuilder.AlertDialogListener, SettingFragment.SettingListener {

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
        this.resultFragment = ResultFragment.newInstance();
        this.settingFragment =  SettingFragment.newInstance();

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GameUtil.PERMISSION_REQ_CODE_WRITE);
        }

        int readExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);

        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, GameUtil.PERMISSION_REQ_CODE_READ);
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
                MyAlertDialogBuilder.getInstance().showResumeGameDialog();
                break;
            case GameUtil.RESULT_STATE:
                changeToResultFragment();
                break;
            case GameUtil.SETTING_STATE:
                changeToSettingFragment();
                break;
        }
        PreferenceManager.setDefaultValues(this, R.xml.preference, false);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == GameUtil.PERMISSION_REQ_CODE_WRITE)
        {
            int grantResultsLength = grantResults.length;
            if(grantResultsLength > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "You grant write external storage permission.", Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(getApplicationContext(), "You denied write external storage permission.", Toast.LENGTH_LONG).show();
            }
        } else if(requestCode == GameUtil.PERMISSION_REQ_CODE_READ)
        {
            int grantResultsLength = grantResults.length;
            if(grantResultsLength > 0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
            {
                Toast.makeText(getApplicationContext(), "You grant read external storage permission.", Toast.LENGTH_LONG).show();
            }else
            {
                Toast.makeText(getApplicationContext(), "You denied read external storage permission.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void changeToPlayGameFragment() {
        presenter.changeMusicToPlay(this);
        saveState(GameUtil.PLAY_STATE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playFragment).commitNowAllowingStateLoss();
    }

    private void changeToSettingFragment() {
        saveState(GameUtil.SETTING_STATE);
        List<Fragment> fragList = getSupportFragmentManager().getFragments();
        for (Fragment f:fragList) {
            getSupportFragmentManager().beginTransaction().remove(f).commitNowAllowingStateLoss();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, settingFragment).commit();
    }

    private void changeToHomeFragment() {
        if (gameState == GameUtil.PLAY_STATE) presenter.changeMusicToHome(this);
        saveState(GameUtil.HOME_STATE);
        getFragmentManager().beginTransaction().remove(settingFragment).commit();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commitNowAllowingStateLoss();
    }

    private void changeToResultFragment() {
        presenter.changeMusicToHome(this);
        saveState(GameUtil.RESULT_STATE);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, resultFragment).commitNowAllowingStateLoss();
    }

    private void saveState(byte state) {
        this.gameState = state;
        this.presenter.saveState(this.gameState);
    }

    private void goToResultScreen() {
        changeToHomeFragment();
        this.presenter.saveTime(60000);
        this.presenter.saveScore(0);
        User temp = presenter.getCurrentUser();
        this.homeFragment.addUserToHighScore(temp.getName(), temp.getPicturePath(), temp.getScore(), temp.getLastPlayed());
        //TODO : CHANGE TO RESULT
    }

    @Override
    protected void onStart() {
        super.onStart();

        homeFragment.loadHighScore(presenter.loadHighScore());
    }

    @Override
    protected void onPause() {
        super.onPause();

        presenter.stopBackSound(this);

        presenter.saveHighScore(homeFragment.getHighScoreData());
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
        byte res = 0;
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.setting_game_shape_rect), false)) {
            res |= GameUtil.SQUARE_SHAPE;
        }
        if (PreferenceManager.getDefaultSharedPreferences(this).getBoolean(getResources().getString(R.string.setting_game_shape_circle), false)) {
            res |= GameUtil.CIRCLE_SHAPE;
        }
        this.presenter.initiateFactory(res, canvas, maxH, maxW);
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
    public void goToResult() {
        goToResultScreen();
    }

    @Override
    public void endGame() {
        goToResultScreen();
    }

    @Override
    public void closeApp() {
        super.onBackPressed();
    }

    @Override
    public void resumeGame() {
        //TODO
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
    public void updateUserName(String name) {
        this.presenter.updateUserName(name);
    }

    @Override
    public void updateUserProfilePicture(String path) {
        this.presenter.updateUserPicture(getResources().getString(R.string.setting_user_profile_picture), path);
    }

    @Override
    public void updateBackSoundGame() {
        presenter.changeMusicToHome(this);
    }

    @Override
    public void updateShapeColor(String newConfig) {
        presenter.updatePaintFactoryConfig(newConfig);
    }

    @Override
    public void updateShapeJenis() {
        initiateFactory(playFragment.getCanvas(), playFragment.getMaxH()-GameUtil.JARAK_SOAL, playFragment.getMaxW());
    }

    @Override
    public String getUserName() {
        return presenter.getCurrentUser().getName();
    }

    @Override
    public String getProfilePicture() {
        return presenter.getCurrentUser().getPicturePath();
    }

    @Override
    public void clearAll() {
        presenter.clearAll();
    }

    @Override
    public void updateScore(int currentScore) {
        presenter.updateScore(currentScore);
    }

    @Override
    public void updateLastPlayed(long lastPlayed) {
        presenter.updateLastPlayed(lastPlayed);
    }
}
