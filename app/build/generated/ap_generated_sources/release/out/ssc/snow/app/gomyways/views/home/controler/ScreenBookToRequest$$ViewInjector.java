// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenBookToRequest$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363067, "field 'txtCenterHeading'");
    target.txtCenterHeading = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363140, "field 'txtPickup'");
    target.txtPickup = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363073, "field 'txtDate'");
    target.txtDate = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363177, "field 'txtTime'");
    target.txtTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363091, "field 'txtDropOff'");
    target.txtDropOff = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363074, "field 'txtDateDrop'");
    target.txtDateDrop = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363178, "field 'txtTimeDrop'");
    target.txtTimeDrop = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362466, "field 'imgUser'");
    target.imgUser = (de.hdodenhof.circleimageview.CircleImageView) view;
    view = finder.findRequiredView(source, 2131363124, "field 'txtNameUser'");
    target.txtNameUser = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363082, "field 'txtDescription'");
    target.txtDescription = (android.widget.TextView) view;
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

  public static void reset(ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest target) {
    target.txtCenterHeading = null;
    target.txtPickup = null;
    target.txtDate = null;
    target.txtTime = null;
    target.txtDropOff = null;
    target.txtDateDrop = null;
    target.txtTimeDrop = null;
    target.imgUser = null;
    target.txtNameUser = null;
    target.txtDescription = null;
    target.txtSourceToDestination = null;
    target.txtDateTimeSourceDestination = null;
  }
}
