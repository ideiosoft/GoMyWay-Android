package ssc.snow.app.gomyways.views.home.my_trips.adapter;


import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ssc.snow.app.gomyways.views.home.my_rides.FragmentRecentRides;
import ssc.snow.app.gomyways.views.home.my_rides.FragmentUpcomingRides;

public class AdapterViewpagerUpcomingRecentRides extends FragmentStatePagerAdapter {

    // Integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AdapterViewpagerUpcomingRecentRides(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    // Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                return new FragmentUpcomingRides();
            case 1:
                return new FragmentRecentRides();

            default:
                return new FragmentUpcomingRides();
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
//        super.restoreState(state, loader);
        try {
            super.restoreState(state, loader);
        } catch (NullPointerException e) {
            // null caught
        }
    }
}
