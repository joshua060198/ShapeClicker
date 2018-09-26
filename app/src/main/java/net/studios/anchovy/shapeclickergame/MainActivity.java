package net.studios.anchovy.shapeclickergame;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Canvas;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.WindowManager;
import android.widget.Toast;

import net.studios.anchovy.shapeclickergame.fragment.HomeFragment;
import net.studios.anchovy.shapeclickergame.fragment.PlayFragment;
import net.studios.anchovy.shapeclickergame.model.PreferenceLoader;

public class MainActivity extends AppCompatActivity implements HomeFragment.HomeFragmentListener, PlayFragment.PlayFragmentListener {

    private Presenter presenter;
    private HomeFragment homeFragment;
    private PlayFragment playFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        this.presenter = new Presenter();
        this.homeFragment = HomeFragment.newInstance(getLayoutInflater());
        this.playFragment = PlayFragment.newInstance(presenter);

        changeToHomeFragment();

        int writeExternalStoragePermission = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if(writeExternalStoragePermission!= PackageManager.PERMISSION_GRANTED)
        {
            // Request user to grant write external storage permission.
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, GameUtil.PERMISSION_REQ_CODE);
        }
        PaintFactory.getInstance();
        PaintFactory.generatePaintColor(this);

        PreferenceLoader pl = PreferenceLoader.getInstance();
        pl.init(getPreferences(MODE_PRIVATE));
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, playFragment).commitAllowingStateLoss();
    }

    private void changeToSettingFragment() {

    }

    private void changeToHomeFragment() {
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, homeFragment).commitAllowingStateLoss();
    }

    private void changeToResultFragment() {

    }

    @Override
    public void playGame() {
        this.changeToPlayGameFragment();
    }

    @Override
    public void changeToSetting() {

    }


    @Override
    public void initiateFactory(Canvas canvas, int maxH, int maxW) {
        this.presenter.initiateFactory((byte)3, canvas, maxH, maxW);
    }

    @Override
    public void generateShape() {
        this.presenter.generateShape();
    }
}
