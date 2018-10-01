package net.studios.anchovy.shapeclickergame.fragment;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import net.studios.anchovy.shapeclickergame.R;

public class SettingFragment extends PreferenceFragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preference);
    }
}
