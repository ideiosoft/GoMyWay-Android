// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenBookToRequest2$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest2 target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363113, "field 'txtMax2People'");
    target.txtMax2People = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363114, "field 'txtMedim'");
    target.txtMedim = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363054, "field 'txtBike'");
    target.txtBike = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363138, "field 'txtPets'");
    target.txtPets = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363173, "field 'txtSourceToDestination'");
    target.txtSourceToDestination = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363078, "field 'txtDateTimeSourceDestination'");
    target.txtDateTimeSourceDestination = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363182, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363128, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest2 target) {
    target.txtMax2People = null;
    target.txtMedim = null;
    target.txtBike = null;
    target.txtPets = null;
    target.txtSourceToDestination = null;
    target.txtDateTimeSourceDestination = null;
  }
}
