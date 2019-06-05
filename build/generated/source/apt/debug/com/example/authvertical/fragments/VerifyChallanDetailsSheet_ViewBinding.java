// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class VerifyChallanDetailsSheet_ViewBinding implements Unbinder {
  private VerifyChallanDetailsSheet target;

  @UiThread
  public VerifyChallanDetailsSheet_ViewBinding(VerifyChallanDetailsSheet target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.viewIssueChallanDetail, "field 'recyclerView'", RecyclerView.class);
    target.btnConfirm = Utils.findRequiredViewAsType(source, R.id.btnConfirm, "field 'btnConfirm'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    VerifyChallanDetailsSheet target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
    target.btnConfirm = null;
  }
}
