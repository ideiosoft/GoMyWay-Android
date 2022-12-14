// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler.search;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenSearchResult$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.search.ScreenSearchResult target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362970, "field 'tabLayout'");
    target.tabLayout = (com.google.android.material.tabs.TabLayout) view;
    view = finder.findRequiredView(source, 2131362710, "field 'viewPager'");
    target.viewPager = (androidx.viewpager.widget.ViewPager) view;
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362559, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.search.ScreenSearchResult target) {
    target.tabLayout = null;
    target.viewPager = null;
  }
}
