package com.example.move.util

import android.content.Context
import android.content.SharedPreferences
import com.example.move.R

class SessionManager(context: Context) {
    private var preferences: SharedPreferences =
        context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)

    fun loadMode(): Boolean {
        return preferences.getBoolean(IS_LIST_MODE, false)
    }
    fun saveMode(mode: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(IS_LIST_MODE, mode)
        editor.apply()
    }

    fun loadSound(): Boolean {
        return preferences.getBoolean(SOUND_ON, true)
    }
    fun saveSound(sound: Boolean) {
        val editor = preferences.edit()
        editor.putBoolean(SOUND_ON, sound)
        editor.apply()
    }

    fun loadAuthToken(): String? {
        return preferences.getString(AUTH_TOKEN, null)
    }

    fun removeAuthToken() {
        val editor = preferences.edit()
        editor.remove(AUTH_TOKEN)
        editor.apply()
    }

    fun saveAuthToken(token: String) {
        val editor = preferences.edit()
        editor.putString(AUTH_TOKEN, token)
        editor.apply()
    }

    companion object {
        const val PREFERENCES_NAME = "my_app"
        const val AUTH_TOKEN = "auth_token"
        const val IS_LIST_MODE = "mode"
        const val SOUND_ON = "sound"
    }
}