package com.smiesko1.semestralka.pracaSulozenim

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveData(key: String, value: Array<String>) {
        val editor = sharedPreferences.edit()
        val concatenatedString = value.joinToString(separator = ",")
        editor.putString(key, concatenatedString)
        editor.apply()
    }

    fun getData(key: String, defaultValue: Array<String>): Array<String> {
        val storedString = sharedPreferences.getString(key, null)
        return storedString?.split(",")?.toTypedArray() ?: defaultValue
    }
    fun getDataAsString(key: String): String? {

        return sharedPreferences.getString(key, null)

    }
}
