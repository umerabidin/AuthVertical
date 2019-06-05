// Generated code from Butter Knife. Do not modify!
package com.example.authvertical.fragments;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.example.authvertical.R;
import com.jaiselrahman.hintspinner.HintSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddPoliceOfficer_ViewBinding implements Unbinder {
  private AddPoliceOfficer target;

  @UiThread
  public AddPoliceOfficer_ViewBinding(AddPoliceOfficer target, View source) {
    this.target = target;

    target.genderSpinner = Utils.findRequiredViewAsType(source, R.id.genderSpinner, "field 'genderSpinner'", HintSpinner.class);
    target.martialSpinner = Utils.findRequiredViewAsType(source, R.id.martialSpinner, "field 'martialSpinner'", HintSpinner.class);
    target.bloodGroupSpinner = Utils.findRequiredViewAsType(source, R.id.bloodGroupSpinner, "field 'bloodGroupSpinner'", HintSpinner.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddPoliceOfficer target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.genderSpinner = null;
    target.martialSpinner = null;
    target.bloodGroupSpinner = null;
  }
}
