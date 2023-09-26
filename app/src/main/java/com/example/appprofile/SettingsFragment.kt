package com.example.appprofile

import android.os.Bundle
import androidx.core.content.edit
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat

class SettingsFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
        val deleteUserDataPreference = findPreference<Preference>(getString(R.string.preferences_key_delete_data))
        deleteUserDataPreference?.setOnPreferenceClickListener {
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharedPreference.edit {
                putString(getString(R.string.key_image), null)
                putString(getString(R.string.key_nombre), null)
                putString(getString(R.string.key_email), null)
                putString(getString(R.string.key_website), null)
                putString(getString(R.string.key_phone), null)
                putString(getString(R.string.key_latitud), null)
                putString(getString(R.string.key_longitud), null)
                apply()
            }
            true
        }
        val switchPreferenceCompat = findPreference<SwitchPreferenceCompat>(getString(R.string.preferences_key_enable_clicks))
        val listPreference = findPreference<ListPreference>(getString(R.string.preferences_ui_img_size_key))
        val restoreAllPreference = findPreference<Preference>(getString(R.string.preferences_key_restore_data))
        restoreAllPreference?.setOnPreferenceClickListener{
            val sharedPreference = PreferenceManager.getDefaultSharedPreferences(requireContext())
            sharedPreference.edit().clear().apply()
            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_img_size_large_key)
            true
        }
        val restoreConfiguration = findPreference<Preference>(getString(R.string.preferences_key_restore_settings))
        restoreConfiguration?.setOnPreferenceClickListener {
            switchPreferenceCompat?.isChecked = true
            listPreference?.value = getString(R.string.preferences_img_size_large_key)
            true
        }
        }

    }