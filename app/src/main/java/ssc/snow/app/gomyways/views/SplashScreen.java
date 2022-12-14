package ssc.snow.app.gomyways.views;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.views.home.ScreenHome;
import ssc.snow.app.gomyways.views.login.ActivityLoggedInAfterSignup;
import ssc.snow.app.gomyways.views.login.ScreenLogin;

public class SplashScreen extends CommonActivity {


    final Handler handler = new Handler();
    private Runnable mRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        printKeyHash(this);
//        if (!isTaskRoot()
//                && getIntent().hasCategory(Intent.CATEGORY_LAUNCHER)
//                && getIntent().getAction() != null
//                && getIntent().getAction().equals(Intent.ACTION_MAIN)) {
//            finish();
//            return;
//        }

        //   getSessionInstanceNotNull().setAfterSignup(true);
        if (getSessionInstanceNotNull().isAfterSignup()) {
            goForNextScreen(ActivityLoggedInAfterSignup.class);
            return;
        }

        // Check for the logged in status
        if (getSessionInstance().isLoggedIn()) {
            goForNextScreen(ScreenHome.class);
            return;
        }

        mRunnable = new Runnable() {
            @Override
            public void run() {
                goForNextScreen(ScreenLogin.class);
            }
        };

        // Post delayed
        handler.postDelayed(mRunnable, 3000);

    }


    public static String printKeyHash(Activity context) {
        PackageInfo packageInfo;
        String key = null;
        try {
            //getting application package name, as defined in manifest
            String packageName = context.getApplicationContext().getPackageName();

            //Retriving package info
            packageInfo = context.getPackageManager().getPackageInfo(packageName,
                    PackageManager.GET_SIGNATURES);

            Log.e("Package Name=", context.getApplicationContext().getPackageName());

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));

                // String key = new String(Base64.encodeBytes(md.digest()));
                Log.e("Key Hash=", key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("Exception", e.toString());
        }

        return key;
    }

    @Override
    protected void onStop() {
        super.onStop();
        // remove the all callback
        handler.removeCallbacks(mRunnable);
    }


}
