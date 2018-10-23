package com.tkmsoft.akarat.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class ListSharePreference {

    public static class Set {
        Context context;

        public Set(Context context) {
            this.context = context;
        }

        public void setIsFirstRun(boolean isFirstRun) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putBoolean("isFirstRun", isFirstRun).apply();
        }


        public void setLanguage(String lang) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("lang", lang).apply();
        }

        public void setIsLogged(boolean isLoggedIn) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putBoolean("islogin", isLoggedIn).apply();
        }

        public void setUId(String id) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("uid", id).apply();
        }
        public void setname(String name) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("name", name).apply();
        }

        public void setemail(String email) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("email", email).apply();
        }
        public void setmobil(String mobil) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("mobil", mobil).apply();
        }
        public void setimage(String image) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("image", image).apply();
        }

        public void setfav(int id,String fav ) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString(String.valueOf(id), fav).apply();
        }
        public void setgId(String id) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("gid", id).apply();
        }
        public void settokenId(String tid) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("tid", tid).apply();
        }
        public void setkId(int id) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putInt("kid", id).apply();
        }

        public void setCategName(String name) {
            SharedPreferences.Editor prefEditor = PreferenceManager.getDefaultSharedPreferences(context).edit();
            prefEditor.putString("Cname", name).apply();
        }
    }


    public static class Get {
        Context context;

        public Get(Context context) {
            this.context = context;
        }

        public boolean getIsFirstRun() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("isFirstRun", true);
        }

        public String getLanguage() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("lang", "ar");
        }

        public String getCategName() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("Cname", "null");
        }

        public boolean getIsLogged() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getBoolean("islogin", false);
        }

        public String getUId() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("uid", "id");
        }
        public String getname() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("name", "name");
        }
        public String getemail() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("email", "example@yahoo.com");
        }

        public String getmobil() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("mobil", "01235665");
        }

        public String getimage() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("image", "http://default.jpg");
        }
        public String getFav(String fav)
        {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString(fav, "false");
        }
        public String getgId() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("gid", "id");
        }
        public String gettokenId() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getString("tid", "tid");
        }
        public int getkId() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            return prefs.getInt("kid", 0);
        }
    }
}
