package com.example.lostcats

import android.os.Bundle
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import java.util.*

class PreferenceFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val nightModePreference: SwitchPreferenceCompat? = findPreference("nightmode")

        val languagePreference: ListPreference? = findPreference("language")
        //Locale.setDefault(Locale("en"))
    }
}