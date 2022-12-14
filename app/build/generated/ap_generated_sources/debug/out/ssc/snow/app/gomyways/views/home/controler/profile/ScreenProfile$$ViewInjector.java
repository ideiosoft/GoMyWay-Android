// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler.profile;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenProfile$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.profile.ScreenProfile target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363067, "field 'txtCenterHeading'");
    target.txtCenterHeading = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362466, "field 'imgUser'");
    target.imgUser = (android.widget.ImageView) view;
    view = finder.findRequiredView(source, 2131363120, "field 'txtName'");
    target.txtName = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363102, "field 'txtGenderAge'");
    target.txtGenderAge = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363048, "field 'txtAbout'");
    target.txtAbout = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363109, "field 'txtJoined'");
    target.txtJoined = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363097, "field 'txtEmailVerify'");
    target.txtEmailVerify = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363116, "field 'txtMobileVerify'");
    target.txtMobileVerify = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363103, "field 'txtGovIdVerified'");
    target.txtGovIdVerified = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362796, "field 'ratingBar_'");
    target.ratingBar_ = (android.widget.RatingBar) view;
    view = finder.findRequiredView(source, 2131363159, "field 'txtReviewCount'");
    target.txtReviewCount = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131362816, "field 'recycleUpcomingRequests'");
    target.recycleUpcomingRequests = (androidx.recyclerview.widget.RecyclerView) view;
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362457, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362555, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.profile.ScreenProfile target) {
    target.txtCenterHeading = null;
    target.imgUser = null;
    target.txtName = null;
    target.txtGenderAge = null;
    target.txtAbout = null;
    target.txtJoined = null;
    target.txtEmailVerify = null;
    target.txtMobileVerify = null;
    target.txtGovIdVerified = null;
    target.ratingBar_ = null;
    target.txtReviewCount = null;
    target.recycleUpcomingRequests = null;
  }
}
