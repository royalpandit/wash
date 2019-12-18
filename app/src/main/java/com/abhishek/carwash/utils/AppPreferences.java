package com.abhishek.carwash.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.abhishek.carwash.loginregister.LoginActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class AppPreferences {

    private static final String SHARED_PREF_NAME = "carWash";
    private static final String KEY_USERID = "keyuserId";
    private static final String KEY_USERFIRSTNAME = "keyfirstName";
    private static final String KEY_USERLASTNAME = "keylastName";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_MOBILENUMBER = "keyMobileNumber";
    private static final String KEY_FID = "keyFid";
    private static final String KEY_Fphoto = "keyFphoto";
    private static final String KEY_GID = "keyGid";
    private static final String KEY_Gphoto = "keyGphoto";

    public static Editor editor;


    private static AppPreferences mInstance;

    public AppPreferences(Context context) {
    }


    public static synchronized AppPreferences getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new AppPreferences(context);
        }
        return mInstance;
    }

    public static void SaveUserdetail(Context ctx, ModalLogin modal) {
        SharedPreferences prefs = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        Editor editor = prefs.edit();

        editor.putString(KEY_USERID, modal.getU_id());
        editor.putString(KEY_USERFIRSTNAME, modal.getFirst_name());
        editor.putString(KEY_USERLASTNAME, modal.getLast_name());
        editor.putString(KEY_EMAIL, modal.getEmail());
        editor.putString(KEY_MOBILENUMBER, modal.getPhone_number());
        editor.putString(KEY_FID, modal.getFid());
        editor.putString(KEY_GID, modal.getGid());
        editor.putString(KEY_Gphoto, modal.getGphoto());
        editor.putString(KEY_Fphoto, modal.getfPhoto());
        editor.commit();
    }

    public static ModalLogin getSavedUser(Context ctx) {
        SharedPreferences sharedPreferences = ctx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        ModalLogin modal = new ModalLogin();
        modal.setU_id(sharedPreferences.getString(KEY_USERID, "-1"));
        modal.setFirst_name(sharedPreferences.getString(KEY_USERFIRSTNAME, "-1"));
        modal.setLast_name(sharedPreferences.getString(KEY_USERLASTNAME, "-1"));
        modal.setEmail(sharedPreferences.getString(KEY_EMAIL, "-1"));
        modal.setPhone_number(sharedPreferences.getString(KEY_MOBILENUMBER, "-1"));
        modal.setFid(sharedPreferences.getString(KEY_FID, "-1"));
        modal.setFid(sharedPreferences.getString(KEY_GID, "-1"));
        modal.setFid(sharedPreferences.getString(KEY_Gphoto, "-1"));
        modal.setFid(sharedPreferences.getString(KEY_Fphoto, "-1"));

        return modal;
    }

    public static void clearPrefsdata(Context ctx) {
        SharedPreferences prefs = ctx.getSharedPreferences(SHARED_PREF_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.clear();
        editor.apply();
        /*editor = prefs.edit();
        editor.clear().commit();*/
        FacebookSdk.sdkInitialize(getApplicationContext());
       if (KEY_FID == KEY_FID) {
           LoginManager.getInstance().logOut();
       }
        ctx.startActivity(new Intent(ctx, LoginActivity.class));

    }

}
