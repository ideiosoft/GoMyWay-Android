// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenPostOrRequestTrip$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostOrRequestTrip target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363083, "field 'txtDescriptionPostTrip'");
    target.txtDescriptionPostTrip = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363084, "field 'txtDescriptionRequestTrip'");
    target.txtDescriptionRequestTrip = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362123, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362124, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362102, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362101, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostOrRequestTrip target) {
    target.txtDescriptionPostTrip = null;
    target.txtDescriptionRequestTrip = null;
  }
}
