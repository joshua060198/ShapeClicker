package net.studios.anchovy.shapeclickergame;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class MyAlertDialogBuilder {
    private AlertDialog stopGame, exitApp, resumeGame;
    private static MyAlertDialogBuilder instance;
    private AlertDialogListener listener;

    public static MyAlertDialogBuilder getInstance() {
        if (instance == null){
            instance = new MyAlertDialogBuilder();
        }
        return instance;
    }

    private MyAlertDialogBuilder() {
    }

    public void init(Context context) {
        if (context instanceof AlertDialogListener) {
            this.listener = (AlertDialogListener) context;
        }

        this.stopGame = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.stop_game_dialog))
                .setPositiveButton(context.getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.endGame();
                    }
                })
                .setNegativeButton(context.getString(R.string.no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        stopGame.dismiss();
                    }
                }).setCancelable(true).create();

        this.exitApp = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.exit_app_dialog))
                .setPositiveButton(context.getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.closeApp();
                    }
                })
                .setNegativeButton(context.getString(R.string.no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        exitApp.dismiss();
                    }
                }).setCancelable(true).create();
        this.resumeGame = new AlertDialog.Builder(context)
                .setMessage(context.getString(R.string.exit_app_dialog))
                .setPositiveButton(context.getString(R.string.yes_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.resumeGame();
                    }
                })
                .setNegativeButton(context.getString(R.string.no_text), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        resumeGame.dismiss();
                    }
                }).setCancelable(true).create();
    }

    public void showEndGameDialog() {
        this.stopGame.show();
    }

    public void showExitAppDialog() {
        this.exitApp.show();
    }

    public void showResumeGameDialog() { this.resumeGame.show(); }

    public interface AlertDialogListener {
        void endGame();
        void closeApp();
        void resumeGame();
    }
}
