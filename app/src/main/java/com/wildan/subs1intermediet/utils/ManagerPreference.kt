package com.wildan.subs1intermediet.utils

import android.content.Context
import android.content.SharedPreferences
import com.wildan.subs1intermediet.data.config.ResultLogin

class ManagerPreference(context: Context) {
    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(ConstVar.PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun setPreferenceString(prefKey: String, value: String) {
        editor.putString(prefKey, value)
        editor.apply()
    }

    fun setPrefLogin(userItem: ResultLogin) {
        userItem.let{
            setPreferenceString(ConstVar.TOKEN_KEY, it.token)
            setPreferenceString(ConstVar.NAME_KEY, it.name)
        }
    }

    fun clearAllPreferences() {
        editor.remove(ConstVar.TOKEN_KEY)
        editor.remove(ConstVar.NAME_KEY)
        editor.apply()
    }

    val getToken = prefs.getString(ConstVar.TOKEN_KEY, "") ?: ""
    val name = prefs.getString(ConstVar.NAME_KEY, "") ?: ""
}