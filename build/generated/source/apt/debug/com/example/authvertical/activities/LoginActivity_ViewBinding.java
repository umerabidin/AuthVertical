// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.activities;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import com.jaiselrahman.hintspinner.HintSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_ViewBinding implements Unbinder {
  private LoginActivity target;

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_ViewBinding(LoginActivity target, View source) {
    this.target = target;

    target.hint_spinner = Utils.findRequiredViewAsType(source, R.id.hint_spinner, "field 'hint_spinner'", HintSpinner.class);
    target.edEmail = Utils.findRequiredViewAsType(source, R.id.edEmail, "field 'edEmail'", EditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.edPassword, "field 'edPassword'", EditText.class);
    target.cvFingurePrint = Utils.findRequiredViewAsType(source, R.id.cvFingurePrint, "field 'cvFingurePrint'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.hint_spinner = null;
    target.edEmail = null;
    target.edPassword = null;
    target.cvFingurePrint = null;
  }
}
