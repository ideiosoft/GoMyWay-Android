// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenHome$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.ScreenHome target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362668, "field 'navView'");
    target.navView = (com.google.android.material.bottomnavigation.BottomNavigationView) view;
    view = finder.findRequiredView(source, 2131362451, "field 'mImgCenterCar' and method 'onViewClicked'");
    target.mImgCenterCar = (android.widget.ImageView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362464, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362462, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.ScreenHome target) {
    target.navView = null;
    target.mImgCenterCar = null;
  }
}
