package com.example.wellness_app

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    private val editor: SharedPreferences.Editor = sharedPreferences.edit()

    companion object {
        private const val PREF_NAME = "WellnessAppSession"
        private const val KEY_USER_TYPE = "userType"
        private const val KEY_USER_ID = "userId"
        private const val KEY_USERNAME = "username"
        private const val KEY_NAME = "name"
        private const val KEY_EMAIL = "email"
        private const val KEY_PASSWORD = "password"
        private const val KEY_CONTACT = "contact"
        private const val KEY_NIN = "nin"
    }

    fun createLoginSession(userType: String, userId: Int, username: String, name: String, email: String, contact: String, password: String, nin: String) {
        editor.putString(KEY_USER_TYPE, userType)
        editor.putInt(KEY_USER_ID, userId)
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_CONTACT, contact)
        editor.putString(KEY_NIN, nin)
        editor.apply()
    }

    fun updateUserSession(username: String, password: String, name: String, email: String, contact: String, nin: String) {
        editor.putString(KEY_USERNAME, username)
        editor.putString(KEY_PASSWORD, password)
        editor.putString(KEY_NAME, name)
        editor.putString(KEY_EMAIL, email)
        editor.putString(KEY_CONTACT, contact)
        editor.putString(KEY_NIN, nin)
        editor.apply()
    }

    fun getUserType(): String? {
        return sharedPreferences.getString(KEY_USER_TYPE, null)
    }

    fun getUserId(): Int {
        return sharedPreferences.getInt(KEY_USER_ID, -1)
    }

    fun getUsername(): String? {
        return sharedPreferences.getString(KEY_USERNAME, null)
    }

    fun getName(): String? {
        return sharedPreferences.getString(KEY_NAME, null)
    }

    fun getEmail(): String? {
        return sharedPreferences.getString(KEY_EMAIL, null)
    }

    fun getContact(): String? {
        return sharedPreferences.getString(KEY_CONTACT, null)
    }

    fun getPassword(): String? {
        return sharedPreferences.getString(KEY_PASSWORD, null)
    }

    fun getNIN(): String? {
        return sharedPreferences.getString(KEY_NIN, null)
    }

    fun logoutSession() {
        editor.clear()
        editor.apply()
    }
}
