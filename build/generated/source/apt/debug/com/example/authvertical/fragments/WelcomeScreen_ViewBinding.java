// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.fragments;

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

public class WelcomeScreen_ViewBinding implements Unbinder {
  private WelcomeScreen target;

  @UiThread
  public WelcomeScreen_ViewBinding(WelcomeScreen target, View source) {
    this.target = target;

    target.tvHelloText = Utils.findRequiredViewAsType(source, R.id.tvHelloText, "field 'tvHelloText'", TextView.class);
    target.tvWelcomeText = Utils.findRequiredViewAsType(source, R.id.tvWelcomeText, "field 'tvWelcomeText'", TextView.class);
    target.ivUserImage = Utils.findRequiredViewAsType(source, R.id.ivUserImage, "field 'ivUserImage'", ImageView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    WelcomeScreen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvHelloText = null;
    target.tvWelcomeText = null;
    target.ivUserImage = null;
  }
}
