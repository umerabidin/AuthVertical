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
import com.jaiselrahman.hintspinner.HintSpinner;
import java.lang.IllegalStateException;
import java.lang.Override;

public class IssueTrafficChallan_ViewBinding implements Unbinder {
  private IssueTrafficChallan target;

  @UiThread
  public IssueTrafficChallan_ViewBinding(IssueTrafficChallan target, View source) {
    this.target = target;

    target.btnIssueChallan = Utils.findRequiredViewAsType(source, R.id.btnIssueChallan, "field 'btnIssueChallan'", CardView.class);
    target.edNIN = Utils.findRequiredViewAsType(source, R.id.edNIN, "field 'edNIN'", EditText.class);
    target.edFirstName = Utils.findRequiredViewAsType(source, R.id.edFirstName, "field 'edFirstName'", EditText.class);
    target.edLastName = Utils.findRequiredViewAsType(source, R.id.edLastName, "field 'edLastName'", EditText.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.edAddress, "field 'edAddress'", EditText.class);
    target.edLicenseNumber = Utils.findRequiredViewAsType(source, R.id.edLicenseNumber, "field 'edLicenseNumber'", EditText.class);
    target.edRegistrationNumber = Utils.findRequiredViewAsType(source, R.id.edRegistrationNumber, "field 'edRegistrationNumber'", EditText.class);
    target.violation_code_spinner = Utils.findRequiredViewAsType(source, R.id.violation_code_spinner, "field 'violation_code_spinner'", HintSpinner.class);
    target.edFineImposed = Utils.findRequiredViewAsType(source, R.id.edFineImposed, "field 'edFineImposed'", EditText.class);
    target.edViolationLocation = Utils.findRequiredViewAsType(source, R.id.edViolationLocation, "field 'edViolationLocation'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    IssueTrafficChallan target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnIssueChallan = null;
    target.edNIN = null;
    target.edFirstName = null;
    target.edLastName = null;
    target.edAddress = null;
    target.edLicenseNumber = null;
    target.edRegistrationNumber = null;
    target.violation_code_spinner = null;
    target.edFineImposed = null;
    target.edViolationLocation = null;
  }
}
