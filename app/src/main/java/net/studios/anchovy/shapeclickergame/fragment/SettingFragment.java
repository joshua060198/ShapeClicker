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
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.provider.MediaStore;
import android.widget.BaseAdapter;

import net.studios.anchovy.shapeclickergame.GameUtil;
import net.studios.anchovy.shapeclickergame.R;

import static android.app.Activity.RESULT_OK;

public class SettingFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceClickListener {

    private SettingListener listener;

    public SettingFragment() {
        //REQUIRED EMPTY CONSTRUCTOR
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preference);

        findPreference(getResources().getString(R.string.setting_user_profile_picture)).setOnPreferenceClickListener(this);

        findPreference(getResources().getString(R.string.setting_user_profile_username)).setSummary(listener.getUserName());
        findPreference(getResources().getString(R.string.setting_user_profile_picture)).setSummary(listener.getProfilePicture());
        ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof SettingListener) {
            this.listener = (SettingListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        this.listener = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceManager().getSharedPreferences().registerOnSharedPreferenceChangeListener(this);

    }

    @Override
    public void onPause() {
        getPreferenceManager().getSharedPreferences().unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getResources().getString(R.string.setting_user_profile_username))) {
            listener.updateUserName(sharedPreferences.getString(key, "USER"));
            findPreference(key).setSummary(sharedPreferences.getString(key, "USER"));
            ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
        } else if (key.equals(getResources().getString(R.string.setting_user_profile_picture))) {
            listener.updateUserProfilePicture(sharedPreferences.getString(key, Environment.getExternalStorageDirectory().getAbsolutePath()));
            findPreference(key).setSummary(sharedPreferences.getString(key, Environment.getExternalStorageDirectory().getAbsolutePath()));
            ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
        } else if (key.equals(getResources().getString(R.string.setting_game_sound))) {
            listener.updateBackSoundGame();
        } else if (key.equals(getResources().getString(R.string.setting_game_shape_color))) {
            listener.updateShapeColor(sharedPreferences.getString(key, GameUtil.SETTING_COLOUR_VALUES_RGB));
        } else if (key.equals(getResources().getString(R.string.setting_game_shape_rect)) || key.equals(getResources().getString(R.string.setting_game_shape_circle))) {
            listener.updateShapeJenis();
        }
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        /**
         * If the apps run in SDK < 19

            if (preference.getKey().equals(getResources().getString(R.string.setting_user_profile_picture))) {
                if (Build.VERSION.SDK_INT < 19){
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Select Picture"), GameUtil.INTENT_FOR_SELECT_IMAGE_CODE);
                } else {
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/jpeg");
                    startActivityForResult(intent, GameUtil.INTENT_FOR_SELECT_IMAGE_CODE_KITKAT);
                }
            }

         **/

        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/jpeg");
        startActivityForResult(intent, GameUtil.INTENT_FOR_SELECT_IMAGE_CODE_KITKAT);

        return true;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode != RESULT_OK) return;
        if (null == data) return;
        Uri originalUri = data.getData();

        String imagePath = null;

        if(requestCode == GameUtil.INTENT_FOR_SELECT_IMAGE_CODE_KITKAT){

            final int takeFlags = data.getFlags()
                    & (Intent.FLAG_GRANT_READ_URI_PERMISSION
                    | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            getActivity().getContentResolver().takePersistableUriPermission(originalUri, takeFlags);

            String id = originalUri.getLastPathSegment().split(":")[1];
            final String[] imageColumns = {MediaStore.Images.Media.DATA };
            final String imageOrderBy = null;

            Uri uri = getUri();

            Cursor imageCursor = getActivity().managedQuery(uri, imageColumns,
                    MediaStore.Images.Media._ID + "="+id, null, imageOrderBy);

            if (imageCursor.moveToFirst()) {
                imagePath = imageCursor.getString(imageCursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
        }
        /**
         * For the app run in SDK < 19
         *
            else if(requestCode == GameUtil.INTENT_FOR_SELECT_IMAGE_CODE) {
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getActivity().getContentResolver().query(originalUri, filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imagePath = cursor.getString(columnIndex);
                cursor.close();
            }
         **/

        if (imagePath != null) {
            findPreference(getResources().getString(R.string.setting_user_profile_picture)).setSummary(imagePath);
            ((BaseAdapter)getPreferenceScreen().getRootAdapter()).notifyDataSetChanged();
            listener.updateUserProfilePicture(imagePath);
        }
        listener.continueBacksound();
    }

    private Uri getUri() {
        String state = Environment.getExternalStorageState();
        if(!state.equalsIgnoreCase(Environment.MEDIA_MOUNTED))
            return MediaStore.Images.Media.INTERNAL_CONTENT_URI;

        return MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
    }

    public interface SettingListener {
        void updateUserName(String name);
        void updateUserProfilePicture(String path);
        void updateBackSoundGame();
        void updateShapeColor(String newConfig);
        void updateShapeJenis();
        void continueBacksound();
        String getUserName();
        String getProfilePicture();
    }
}
