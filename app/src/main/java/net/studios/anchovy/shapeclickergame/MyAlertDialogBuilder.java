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
