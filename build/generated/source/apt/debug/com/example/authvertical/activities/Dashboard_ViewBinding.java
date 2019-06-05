// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class Dashboard_ViewBinding implements Unbinder {
  private Dashboard target;

  @UiThread
  public Dashboard_ViewBinding(Dashboard target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public Dashboard_ViewBinding(Dashboard target, View source) {
    this.target = target;

    target.user_image = Utils.findRequiredViewAsType(source, R.id.user_image, "field 'user_image'", ImageView.class);
    target.tvUserName = Utils.findRequiredViewAsType(source, R.id.tvUserName, "field 'tvUserName'", TextView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    Dashboard target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.user_image = null;
    target.tvUserName = null;
  }
}
