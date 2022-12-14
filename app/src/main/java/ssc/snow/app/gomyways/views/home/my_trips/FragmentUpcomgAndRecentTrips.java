package ssc.snow.app.gomyways.views.home.my_trips;


import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;

import butterknife.ButterKnife;
import butterknife.InjectView;
import ssc.snow.app.gomyways.R;
import ssc.snow.app.gomyways.views.home.my_trips.adapter.AdapterViewpagerMyTripsRides;
import ssc.snow.app.gomyways.views.home.my_trips.adapter.AdapterViewpagerUpcomingRecentRides;


public class FragmentUpcomgAndRecentTrips extends Fragment implements TabLayout.OnTabSelectedListener {

    @InjectView(R.id.tabLayout_trip_ride)
    TabLayout tabLayout;

    @InjectView(R.id.pager)
    ViewPager viewPager;

    public FragmentUpcomgAndRecentTrips() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_upcomg_and_recent_trips, container, false);
        ButterKnife.inject(this, v);

        // initial implementation of views
        init();
        return v;

    }

    @SuppressLint("ClickableViewAccessibility")
    private void init() {

        // Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("My Trips"));
        tabLayout.addTab(tabLayout.newTab().setText("My Rides"));


        // Set the gravity
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        //  Creating our pager adapter
        AdapterViewpagerMyTripsRides
                adapter = new AdapterViewpagerMyTripsRides(getChildFragmentManager(), tabLayout.getTabCount());

        //  Sync with view pager
        viewPager.setAdapter(adapter);


        //  tabLayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        //  Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this);

    }


    @Override
    public void onTabSelected(@NonNull TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }

}
