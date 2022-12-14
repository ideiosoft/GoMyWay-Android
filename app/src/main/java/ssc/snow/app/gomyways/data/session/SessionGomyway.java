package ssc.snow.app.gomyways.data.session;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import ssc.snow.app.gomyways.data.model.ModelLogin;


public class SessionGomyway {

    private static final String PREF_NAME = "go_my_ways_ride";
    private SharedPreferences.Editor editor;
    private SharedPreferences pref;
    private int PRIVATE_MODE;
    private Context _context;
    private int securityQuestionLength = 0;

    public SessionGomyway(Context context) {

        PRIVATE_MODE = 0;
        _context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();

    }

    public Boolean isLoggedIn() {
        return pref.getBoolean("is_login", false);
    }

    public void setLoggedIn(boolean mFlag) {
        editor.putBoolean("is_login", mFlag);
        editor.commit();

    }

    /******
     * Store offline data so that we dont need to hit the api
     * *****/

    //   Store sub category and retreive sub category
    public void setMobileStatus(String mMobStatus) {
        ModelLogin mModel = getLoggedInUserDetail();
        mModel.getData().getUser().setMobile_status(mMobStatus);

        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(mModel);
        editor.putString("store_logged_in_operator_details", jsonFavorites);
        editor.commit();
    }

    public void storeLoggedInUserDetail(ModelLogin mModelCategory) {
        Gson gson = new Gson();
        String jsonFavorites = gson.toJson(mModelCategory);
        editor.putString("store_logged_in_operator_details", jsonFavorites);
        editor.commit();
    }

    //   To retrieve sub category details
    public ModelLogin getLoggedInUserDetail() {
        ModelLogin favorites;

        if (pref.contains("store_logged_in_operator_details")) {

            String jsonFavorites = pref.getString("store_logged_in_operator_details", null);
            Gson gson = new Gson();

            ModelLogin favoriteItems = gson.fromJson(jsonFavorites, ModelLogin.class);
            favorites = favoriteItems;

        } else {
            return null;
        }
        return favorites;
    }


    public void clearSession() {
        editor.clear();
        editor.commit();
    }


}
