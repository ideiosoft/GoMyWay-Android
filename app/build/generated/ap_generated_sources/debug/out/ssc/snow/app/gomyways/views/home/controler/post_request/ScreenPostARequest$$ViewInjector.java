// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler.post_request;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenPostARequest$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostARequest target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362311, "field 'edtSrc' and method 'onViewClicked'");
    target.edtSrc = (android.widget.EditText) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362292, "field 'edtDestination' and method 'onViewClicked'");
    target.edtDestination = (android.widget.EditText) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362300, "field 'edtLeaving' and method 'onViewClicked'");
    target.edtLeaving = (android.widget.EditText) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362310, "field 'edtSeatRequired'");
    target.edtSeatRequired = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131362291, "field 'edtDescription'");
    target.edtDescription = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131362447, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363145, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.home.controler.post_request.ScreenPostARequest target) {
    target.edtSrc = null;
    target.edtDestination = null;
    target.edtLeaving = null;
    target.edtSeatRequired = null;
    target.edtDescription = null;
  }
}
