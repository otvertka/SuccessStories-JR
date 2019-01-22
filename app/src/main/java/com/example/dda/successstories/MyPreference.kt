package com.example.dda.successstories

import android.content.Context
import android.util.Log

class MyPreference(context: Context) {

    private val preference_name = "mySP"
    private val preference_text_size = "textSize"

    private val preference = context.getSharedPreferences(preference_name, Context.MODE_PRIVATE)

    fun getTextSize(): Int {
        return preference.getInt(preference_text_size, 12)
    }

    fun setTextSize(size: Int){
        Log.d("My", "setTextSize - $size")
        val editor = preference.edit()
        editor.putInt(preference_text_size, size)
        editor.apply()
    }

}