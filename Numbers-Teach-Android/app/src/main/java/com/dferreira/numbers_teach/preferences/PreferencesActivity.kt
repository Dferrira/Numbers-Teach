package com.dferreira.numbers_teach.preferences

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceScreen
import com.dferreira.numbers_teach.R
import com.dferreira.numbers_teach.commons.GenericActivity

/**
 * Activity with preferences of for the user
 */
class PreferencesActivity() : GenericActivity(),
    PreferenceFragmentCompat.OnPreferenceStartScreenCallback {

    private val preferencesFragmentFactory: PreferencesFragmentFactory =
        PreferencesFragmentFactory()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences_activity)
        setupActionBar(R.mipmap.ic_settings_black_48dp)
        if (savedInstanceState == null) {
            createFragmentAndAddItToActivity()
        }
    }

    private fun createFragmentAndAddItToActivity() {
        val preferencesFragment = preferencesFragmentFactory.createPreferencesFragment()
        val key = intent.getStringExtra(PREFERENCE_KEY)
        if (key != null) {
            val args = Bundle()
            args.putString(PreferenceFragmentCompat.ARG_PREFERENCE_ROOT, key)
            preferencesFragment.arguments = args
        }
        supportFragmentManager.beginTransaction()
            .add(R.id.preferences, preferencesFragment, null)
            .commit()
    }

    override fun onPreferenceStartScreen(
        preferenceFragmentCompat: PreferenceFragmentCompat,
        preferenceScreen: PreferenceScreen
    ): Boolean {
        val intent = Intent(this@PreferencesActivity, PreferencesActivity::class.java)
        intent.putExtra(PREFERENCE_KEY, preferenceScreen.key)
        startActivity(intent)
        return true
    }

    /**
     * Threats the menu item selected
     *
     * @param item which was the item selected
     * @return true if was possible launch threat the selected option
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private const val PREFERENCE_KEY = "preference_key"
    }

}