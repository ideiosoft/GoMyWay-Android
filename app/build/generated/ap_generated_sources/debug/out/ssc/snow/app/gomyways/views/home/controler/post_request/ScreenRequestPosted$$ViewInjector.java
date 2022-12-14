// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenRequestPosted$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.post_request.ScreenRequestPosted target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363142, "field 'txtPickupCity'");
    target.txtPickupCity = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363141, "field 'txtPickupAddress'");
    target.txtPickupAddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363090, "field 'txtDropCity'");
    target.txtDropCity = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363089, "field 'txtDropAddress'");
    target.txtDropAddress = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363077, "field 'txtDateTime'");
    target.txtDateTime = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363168, "field 'txtSeatsRequired'");
    target.txtSeatsRequired = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363082, "field 'txtDescription'");
    target.txtDescription = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362466, "field 'imgUser'");
    target.imgUser = (de.hdodenhof.circleimageview.CircleImageView) view;
    view = finder.findRequiredView(source, 2131363124, "field 'txtNameUser'");
    target.txtNameUser = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362465, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.post_request.ScreenRequestPosted target) {
    target.txtPickupCity = null;
    target.txtPickupAddress = null;
    target.txtDropCity = null;
    target.txtDropAddress = null;
    target.txtDateTime = null;
    target.txtSeatsRequired = null;
    target.txtDescription = null;
    target.imgUser = null;
    target.txtNameUser = null;
  }
}
