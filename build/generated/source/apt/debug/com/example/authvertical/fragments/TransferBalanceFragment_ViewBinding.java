// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import com.jaiselrahman.hintspinner.HintSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class TransferBalanceFragment_ViewBinding implements Unbinder {
  private TransferBalanceFragment target;

  @UiThread
  public TransferBalanceFragment_ViewBinding(TransferBalanceFragment target, View source) {
    this.target = target;

    target.tvTitle = Utils.findRequiredViewAsType(source, R.id.tvTitle, "field 'tvTitle'", TextView.class);
    target.spinnerIBAN = Utils.findRequiredViewAsType(source, R.id.spinnerIBAN, "field 'spinnerIBAN'", HintSpinner.class);
    target.tvIBAN = Utils.findRequiredViewAsType(source, R.id.tvIBAN, "field 'tvIBAN'", EditText.class);
    target.tvAmout = Utils.findRequiredViewAsType(source, R.id.tvAmout, "field 'tvAmout'", EditText.class);
    target.tvComments = Utils.findRequiredViewAsType(source, R.id.tvComments, "field 'tvComments'", EditText.class);
    target.btnTransfer = Utils.findRequiredViewAsType(source, R.id.btnTransfer, "field 'btnTransfer'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    TransferBalanceFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.tvTitle = null;
    target.spinnerIBAN = null;
    target.tvIBAN = null;
    target.tvAmout = null;
    target.tvComments = null;
    target.btnTransfer = null;
  }
}
