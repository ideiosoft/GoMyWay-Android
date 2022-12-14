package ssc.snow.app.gomyways.views.home.my_trips.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.TabLayoutOnPageChangeListener
import kotlinx.android.synthetic.main.fragment_my_trips.*
import kotlinx.android.synthetic.main.fragment_upcomg_and_recent_trips.pager
import ssc.snow.app.gomyways.R
import ssc.snow.app.gomyways.views.home.my_trips.adapter.AdapterViewpagerUpcomingRecentTrips

/**
 * A simple [Fragment] subclass.
 */
class MyTrips : Fragment(), TabLayout.OnTabSelectedListener {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.fragment_my_trips, container, false)
        return v

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    private fun init() {

        // Adding the tabs using addTab() method
        tabLayout.addTab(tabLayout.newTab().setText("Upcoming"))
      //  tabLayout.addTab(tabLayout.newTab().setText("Requests"))
        tabLayout.addTab(tabLayout.newTab().setText("Recent"))

        tabLayout?.tabGravity = TabLayout.GRAVITY_FILL

        //  Creating our pager adapter
        val adapter = AdapterViewpagerUpcomingRecentTrips(childFragmentManager, tabLayout.tabCount)

        //  Sync with view pager
        pager.adapter = adapter
        //  tabLayout.setupWithViewPager(viewPager);
        pager.addOnPageChangeListener(TabLayoutOnPageChangeListener(tabLayout))
        //  Adding onTabSelectedListener to swipe views
        tabLayout.addOnTabSelectedListener(this)

    }


    override fun onTabSelected(tab: TabLayout.Tab) {
        pager.currentItem = tab.position
    }


    override fun onTabReselected(p0: TabLayout.Tab?) {

    }

    override fun onTabUnselected(p0: TabLayout.Tab?) {

    }


}
