package com.example.trivia.util;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class Prefs {

private SharedPreferences preferences;

public Prefs(Activity context) {
    this.preferences = context.getPreferences(Context.MODE_PRIVATE);
  }




 }
