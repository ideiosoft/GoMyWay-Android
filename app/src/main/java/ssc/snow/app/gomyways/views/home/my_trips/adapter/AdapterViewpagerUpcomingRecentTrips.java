package ssc.snow.app.gomyways.views.home.my_trips.adapter;


import android.os.Parcelable;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ssc.snow.app.gomyways.views.home.my_trips.fragments.FragmentRecent;
import ssc.snow.app.gomyways.views.home.my_trips.fragments.FragmentUpcoming;

public class AdapterViewpagerUpcomingRecentTrips extends FragmentStatePagerAdapter {

    // Integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AdapterViewpagerUpcomingRecentTrips(FragmentManager fm, int tabCount) {
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
                FragmentUpcoming tab1 = new FragmentUpcoming();
                return tab1;
//            case 1:
//                FragmentReceivedRequests tab2 = new FragmentReceivedRequests();
//                return tab2;
            case 1:
                FragmentRecent tab3 = new FragmentRecent();
                return tab3;

            default:
                FragmentUpcoming tab = new FragmentUpcoming();
                return tab;
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
