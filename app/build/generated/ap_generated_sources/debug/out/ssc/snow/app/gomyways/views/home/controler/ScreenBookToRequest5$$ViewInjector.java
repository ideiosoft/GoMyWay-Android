// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenBookToRequest5$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest5 target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131362308, "field 'edtResponse'");
    target.edtResponse = (android.widget.EditText) view;
    view = finder.findRequiredView(source, 2131363082, "field 'txtDescription'");
    target.txtDescription = (android.widget.TextView) view;
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

  public static void reset(ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest5 target) {
    target.edtResponse = null;
    target.txtDescription = null;
    target.txtDateTimeSourceDestination = null;
  }
}
