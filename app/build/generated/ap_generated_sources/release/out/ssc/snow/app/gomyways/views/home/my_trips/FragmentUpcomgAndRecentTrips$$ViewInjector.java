// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.my_trips;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class FragmentUpcomgAndRecentTrips$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.my_trips.FragmentUpcomgAndRecentTrips target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362971, "field 'tabLayout'");
    target.tabLayout = (com.google.android.material.tabs.TabLayout) view;
    view = finder.findRequiredView(source, 2131362710, "field 'viewPager'");
    target.viewPager = (androidx.viewpager.widget.ViewPager) view;
  }

  public static void reset(ssc.snow.app.gomyways.views.home.my_trips.FragmentUpcomgAndRecentTrips target) {
    target.tabLayout = null;
    target.viewPager = null;
  }
}
