// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.login;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenLogin$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.login.ScreenLogin target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362325, "field 'emailEt'");
    target.emailEt = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131362719, "field 'passEt'");
    target.passEt = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131362146, "field 'checkboxRemember' and method 'onViewClicked'");
    target.checkboxRemember = (android.widget.CheckBox) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362556, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362557, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363101, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362569, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131362822, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
  }

  public static void reset(ssc.snow.app.gomyways.views.login.ScreenLogin target) {
    target.emailEt = null;
    target.passEt = null;
    target.checkboxRemember = null;
  }
}
