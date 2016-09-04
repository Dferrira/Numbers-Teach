package com.dferreira.numbers_teach.preferences;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.preference.PreferenceFragmentCompat;
import android.support.v7.preference.PreferenceScreen;
import android.view.MenuItem;

import com.dferreira.numbers_teach.R;
import com.dferreira.numbers_teach.commons.GenericActivity;

/**
 * Activity with preferences of for the user
 */
public class PreferencesActivity extends GenericActivity implements PreferenceFragmentCompat.OnPreferenceStartScreenCallback {
    private final static String PREFERENCE_KEY = "preference_key";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.preferences_activity);

        this.setupActionBar(R.mipmap.ic_settings_black_48dp);

        if (savedInstanceState == null) {
            Fragment p = new PreferencesFragment();

            String key = getIntent().getStringExtra(PREFERENCE_KEY);
            if (key != null) {
                Bundle args = new Bundle();
                args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, key);
                p.setArguments(args);
            }

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.preferences, p, null)
                    .commit();
        }


    }

    @Override
    public boolean onPreferenceStartScreen(PreferenceFragmentCompat preferenceFragmentCompat, PreferenceScreen preferenceScreen) {
        Intent intent = new Intent(PreferencesActivity.this, PreferencesActivity.class);
        intent.putExtra(PREFERENCE_KEY, preferenceScreen.getKey());
        startActivity(intent);
        return true;
    }

    /**
     * Threats the menu item selected
     *
     * @param item which was the item selected
     * @return true if was possible launch threat the selected option
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}