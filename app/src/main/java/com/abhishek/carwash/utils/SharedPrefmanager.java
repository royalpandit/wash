package com.abhishek.carwash.utils;


public class SharedPrefmanager {


/*

    private static final String SHARED_PREF_NAME = "carWash";
    private static final String KEY_USERID = "keyuserId";
    private static final String KEY_USERFIRSTNAME = "keyfirstName";
    private static final String KEY_USERLASTNAME = "keylastName";
    private static final String KEY_EMAIL = "keyemail";
    private static final String KEY_MOBILENUMBER = "keyMobileNumber";

   // public static SharedPreferences.Editor editor;

    private static SharedPrefmanager mInstance;
    private static Context mCtx;

    public SharedPrefmanager(Context context) {
        mCtx = context;
    }




    public static synchronized SharedPrefmanager getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new SharedPrefmanager(context);
        }
        return mInstance;
    }



    public void userLogin(ModalLogin user) {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(KEY_USERID, user.getU_id());
        editor.putString(KEY_USERFIRSTNAME, user.getFirst_name());
        editor.putString(KEY_USERLASTNAME, user.getLast_name());
        editor.putString(KEY_EMAIL, user.getEmail());
        editor.putString(KEY_MOBILENUMBER, user.getPhone_number());
        editor.apply();
    }

    public boolean isLoggedIn() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(KEY_USERID, null) != null;
    }

    //this method will give the logged in user
    public ModalLogin getUser() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        return new ModalLogin(
                sharedPreferences.getString(KEY_USERID, "-1"),
                sharedPreferences.getString(KEY_USERFIRSTNAME,"-1"),
                sharedPreferences.getString(KEY_USERLASTNAME, "-1"),
                sharedPreferences.getString(KEY_EMAIL, "-1"),
                sharedPreferences.getString(KEY_MOBILENUMBER,"-1")

        );
    }

    //this method will logout the user
    public void logout() {
        SharedPreferences sharedPreferences = mCtx.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        mCtx.startActivity(new Intent(mCtx, LoginActivity.class));
    }
*/

}
