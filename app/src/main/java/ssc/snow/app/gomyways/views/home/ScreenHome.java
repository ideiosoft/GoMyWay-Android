package ssc.snow.app.gomyways.views.home;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.data.utility.CommonActivity;
import ssc.snow.app.gomyways.views.home.controler.menu.ScreenMenu;
import ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostOrRequestTrip;
import ssc.snow.app.gomyways.views.home.controler.profile.ScreenProfile;
import ssc.snow.app.gomyways.views.home.controler.search.SearchRide;
import ssc.snow.app.gomyways.views.home.fragment.FragmentMessage;
import ssc.snow.app.gomyways.views.home.fragment.FragmentNotification;
import ssc.snow.app.gomyways.views.home.my_trips.FragmentUpcomgAndRecentTrips;

public class ScreenHome extends CommonActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {

    @InjectView(R.id.nav_view)
    BottomNavigationView navView;
    @InjectView(R.id.img_center_car)
    ImageView mImgCenterCar;


    // fragment decalarations
    private FragmentMessage mFragmentMessage;
    private FragmentNotification mFragmentNotifications;
    private FragmentUpcomgAndRecentTrips mFragmentUpcomingRecentTrips;

    private boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.screen_home);
        ButterKnife.inject(this);

        // initialisation
        init();

    }

    private void init() {
        navView.setOnNavigationItemSelectedListener(this);
        navView.getMenu().setGroupCheckable(0, false, true);

        // initialisations of fragments
        mFragmentMessage = new FragmentMessage();
        mFragmentNotifications = new FragmentNotification();
        mFragmentUpcomingRecentTrips = new FragmentUpcomgAndRecentTrips();

        // implement the fragments default

        //  Todo: Modified
        //  putFragment(mFragmentMessage, "Message");
        //  Menu nav_Menu = navView.getMenu();
        //  nav_Menu.findItem(R.id.navigation_msg).setChecked(true);

     //   clearAllChecked();
        // set orange color car in the center
        mImgCenterCar.setImageResource(R.mipmap.car_orange_72);
       putFragment(mFragmentUpcomingRecentTrips, "Trips");

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // call to clear all views when press the button
        clearAllChecked();
        navView.getMenu().setGroupCheckable(0, true, true);
        switch (item.getItemId()) {

            case R.id.navigation_more:
                goForNextScreenWithoutFinish(ScreenMenu.class);
                return true;

            case R.id.navigation_notifications:
                putFragment(mFragmentNotifications, "Notifications");
                return true;

            case R.id.navigation_msg:
                putFragment(mFragmentMessage, "Message");
                return true;

            case R.id.navigation_profile:
                goForNextScreenWithoutFinish(ScreenProfile.class);
                return true;
        }
        return false;
    }

    @OnClick({R.id.img_search, R.id.img_plus, R.id.img_center_car})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.img_search:
                goForNextScreenWithoutFinish(SearchRide.class);
                break;
            case R.id.img_plus:
                goForNextScreenWithoutFinish(ScreenPostOrRequestTrip.class);
                break;
            case R.id.img_center_car:
                navView.getMenu().setGroupCheckable(0, false, true);
                // set orange color car in the center
                mImgCenterCar.setImageResource(R.mipmap.car_orange_72);

                putFragment(mFragmentUpcomingRecentTrips, "Trips");
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

    }

    public void putFragment(@NonNull Fragment mFrament, @NonNull String mTitle) {
        // set title on top
        // toolbarTextHeader.setText(mTitle);

        FragmentTransaction mTrans = getSupportFragmentManager().beginTransaction();
        mTrans.replace(R.id.frame_, mFrament, mTitle);
        mTrans.commit();
    }


    public void clearAllChecked() {

        // set default image
        mImgCenterCar.setImageResource(R.mipmap.car_gray_72);

     //   nav_Menu.findItem(R.id.navigation_dashboard).setChecked(false);

    }
}
