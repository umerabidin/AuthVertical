// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.EditText;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class ViewCitizen_ViewBinding implements Unbinder {
  private ViewCitizen target;

  @UiThread
  public ViewCitizen_ViewBinding(ViewCitizen target, View source) {
    this.target = target;

    target.edNIN = Utils.findRequiredViewAsType(source, R.id.edNIN, "field 'edNIN'", EditText.class);
    target.edFirstName = Utils.findRequiredViewAsType(source, R.id.edFirstName, "field 'edFirstName'", EditText.class);
    target.edLastName = Utils.findRequiredViewAsType(source, R.id.edLastName, "field 'edLastName'", EditText.class);
    target.edPhoneNumber = Utils.findRequiredViewAsType(source, R.id.edPhoneNumber, "field 'edPhoneNumber'", EditText.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.edAddress, "field 'edAddress'", EditText.class);
    target.btnScanFingure = Utils.findRequiredViewAsType(source, R.id.btnScanFingure, "field 'btnScanFingure'", CardView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    ViewCitizen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.edNIN = null;
    target.edFirstName = null;
    target.edLastName = null;
    target.edPhoneNumber = null;
    target.edAddress = null;
    target.btnScanFingure = null;
  }
}
