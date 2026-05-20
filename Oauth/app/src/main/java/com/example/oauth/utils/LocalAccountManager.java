package com.example.oauth.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalAccountManager {

    private static final String PREF_NAME = "local_account";
    private static final String KEY_FULL_NAME = "full_name";
    private static final String KEY_USERNAME = "username";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_PHONE = "phone";
    private static final String KEY_PASSWORD = "password";
    private final SharedPreferences prefs;

    public LocalAccountManager(Context context) {
        prefs = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
    }

    public void saveAccount(String fullName, String username, String email, String phone, String password) {
        prefs.edit()
                .putString(KEY_FULL_NAME, fullName)
                .putString(KEY_USERNAME, username)
                .putString(KEY_EMAIL, email)
                .putString(KEY_PHONE, phone)
                .putString(KEY_PASSWORD, password)
                .apply();
    }

    public boolean hasAccount() {
        return prefs.contains(KEY_EMAIL) && prefs.contains(KEY_PASSWORD);
    }

    public boolean isValidLogin(String email, String password) {
        String savedEmail = prefs.getString(KEY_EMAIL, "");
        String savedPassword = prefs.getString(KEY_PASSWORD, "");
        return savedEmail.equalsIgnoreCase(email.trim()) && savedPassword.equals(password);
    }

    public String getFullName() {
        return prefs.getString(KEY_FULL_NAME, "");
    }

    public String getEmail() {
        return prefs.getString(KEY_EMAIL, "");
    }

    public void clearAccount() {
        prefs.edit().clear().apply();
    }
}
