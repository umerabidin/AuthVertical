// Generated code from Butter Knife. Do not modify!
package com.example.authvertical;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.jaiselrahman.hintspinner.HintSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class CaptureFingerprintActivity_ViewBinding implements Unbinder {
  private CaptureFingerprintActivity target;

  @UiThread
  public CaptureFingerprintActivity_ViewBinding(CaptureFingerprintActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public CaptureFingerprintActivity_ViewBinding(CaptureFingerprintActivity target, View source) {
    this.target = target;

    target.hint_spinner = Utils.findRequiredViewAsType(source, R.id.hint_spinner, "field 'hint_spinner'", HintSpinner.class);
    target.cvFingurePrint = Utils.findRequiredViewAsType(source, R.id.btnCaptureImage, "field 'cvFingurePrint'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    CaptureFingerprintActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.hint_spinner = null;
    target.cvFingurePrint = null;
  }
}
