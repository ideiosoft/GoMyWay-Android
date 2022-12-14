// Generated code from Butter Knife. Do not modify!
package ssc.snow.app.gomyways.views.home.controler;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class ScreenBookToRequest6$$ViewInjector {
  public static void inject(Finder finder, final ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest6 target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131363052, "field 'txtAuthorisedAmout'");
    target.txtAuthorisedAmout = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363098, "field 'txtEnterNewCard' and method 'onViewClicked'");
    target.txtEnterNewCard = (android.widget.TextView) view;
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363154, "field 'txtRequestBookDescription'");
    target.txtRequestBookDescription = (android.widget.TextView) view;
    view = finder.findRequiredView(source, 2131363061, "field 'txtCancellationPolicyDesc'");
    target.txtCancellationPolicyDesc = (android.widget.TextView) view;
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
    view = finder.findRequiredView(source, 2131363183, "method 'onViewClicked'");
    view.setOnClickListener(
      new android.view.View.OnClickListener() {
        @Override public void onClick(
          android.view.View p0
        ) {
          target.onViewClicked(p0);
        }
      });
    view = finder.findRequiredView(source, 2131363152, "method 'onViewClicked'");
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

  public static void reset(ssc.snow.app.gomyways.views.home.controler.ScreenBookToRequest6 target) {
    target.txtAuthorisedAmout = null;
    target.txtEnterNewCard = null;
    target.txtRequestBookDescription = null;
    target.txtCancellationPolicyDesc = null;
    target.txtSourceToDestination = null;
    target.txtDateTimeSourceDestination = null;
  }
}
