package com.example.unisphere.service;

import android.content.SharedPreferences;

import com.example.unisphere.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class Util {

    public static final String KEY_USERNAME = "username";
    public static final String KEY_UNIVERSITY = "university";
    public static final String KEY_EMAIL = "email";
    public static final String KEY_USER_ROLE = "user_role";
    public static final String PROFILE_PICTURE = "profile_picture";
    public static final String KEY_PHONE_NUMBER = "phone_number";
    public static final String KEY_TAGS = "tags";
    public static final String USER_DATA = "USER_DATA";
    public static SimpleDateFormat TIMESTAMP_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());
    public static SimpleDateFormat USER_TIME_FORMAT = new SimpleDateFormat("MMM dd, yyyy hh:mm a");

    public static String convertDateTime(String inputDateTime) {
        try {
            Date date = TIMESTAMP_FORMAT.parse(inputDateTime);
            return USER_TIME_FORMAT.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDateString(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static boolean checkBlank(String value) {
        return value == null || value.isEmpty() || value.trim().isEmpty();
    }

    public static User getUserDataFromSharedPreferences(SharedPreferences preferences) {
        String username = preferences.getString(KEY_USERNAME, "");
        String university = preferences.getString(KEY_UNIVERSITY, "");
        String email = preferences.getString(KEY_EMAIL, "");
        String userRole = preferences.getString(KEY_USER_ROLE, "");
        String profilePicture = preferences.getString(PROFILE_PICTURE, "");

        Set<String> tags = preferences.getStringSet(KEY_TAGS, new HashSet<>());
        return new User(username, email, profilePicture, tags, userRole, university);
    }
}
