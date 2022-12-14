package ssc.snow.app.gomyways.views.home.controler.search.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import ssc.snow.app.gomyways.views.home.controler.search.fragments.FragmentAll;
import ssc.snow.app.gomyways.views.home.controler.search.fragments.FragmentRequests;
import ssc.snow.app.gomyways.views.home.controler.search.fragments.FragmentTrips;

public class AdapterViewpagerSearchResults extends FragmentStatePagerAdapter {

    //integer to count number of tabs
    int tabCount;

    //Constructor to the class
    public AdapterViewpagerSearchResults(FragmentManager fm, int tabCount) {
        super(fm);
        //Initializing tab count
        this.tabCount = tabCount;
    }

    //Overriding method getItem
    @Override
    public Fragment getItem(int position) {
        //Returning the current tabs
        switch (position) {
            case 0:
                FragmentAll tab1 = new FragmentAll();
                return tab1;
            case 1:
                FragmentTrips tab2 = new FragmentTrips();
                return tab2;
            case 2:
                FragmentRequests tab3 = new FragmentRequests();
                return tab3;
            default:
                FragmentAll tab = new FragmentAll();
                return tab;
        }
    }

    //Overriden method getCount to get the number of tabs
    @Override
    public int getCount() {
        return tabCount;
    }
}
