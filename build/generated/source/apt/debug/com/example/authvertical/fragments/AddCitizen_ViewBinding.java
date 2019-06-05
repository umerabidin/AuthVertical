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
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AddCitizen_ViewBinding implements Unbinder {
  private AddCitizen target;

  @UiThread
  public AddCitizen_ViewBinding(AddCitizen target, View source) {
    this.target = target;

    target.btnScanFingure = Utils.findRequiredViewAsType(source, R.id.btnScanFingure, "field 'btnScanFingure'", CardView.class);
    target.ivImage = Utils.findRequiredViewAsType(source, R.id.ivImage, "field 'ivImage'", CircleImageView.class);
    target.edDOB = Utils.findRequiredViewAsType(source, R.id.edDOB, "field 'edDOB'", EditText.class);
    target.btnAddCitizen = Utils.findRequiredViewAsType(source, R.id.btnAddCitizen, "field 'btnAddCitizen'", CardView.class);
    target.genderSpinner = Utils.findRequiredViewAsType(source, R.id.genderSpinner, "field 'genderSpinner'", HintSpinner.class);
    target.martialSpinner = Utils.findRequiredViewAsType(source, R.id.martialSpinner, "field 'martialSpinner'", HintSpinner.class);
    target.bloodGroupSpinner = Utils.findRequiredViewAsType(source, R.id.bloodGroupSpinner, "field 'bloodGroupSpinner'", HintSpinner.class);
    target.edFirstName = Utils.findRequiredViewAsType(source, R.id.edFirstName, "field 'edFirstName'", EditText.class);
    target.edLastName = Utils.findRequiredViewAsType(source, R.id.edLastName, "field 'edLastName'", EditText.class);
    target.edEmail = Utils.findRequiredViewAsType(source, R.id.edEmail, "field 'edEmail'", EditText.class);
    target.edPassword = Utils.findRequiredViewAsType(source, R.id.edPassword, "field 'edPassword'", EditText.class);
    target.edFatherNIN = Utils.findRequiredViewAsType(source, R.id.edFatherNIN, "field 'edFatherNIN'", EditText.class);
    target.edMotherNIN = Utils.findRequiredViewAsType(source, R.id.edMotherNIN, "field 'edMotherNIN'", EditText.class);
    target.edPhoneNumber = Utils.findRequiredViewAsType(source, R.id.edPhoneNumber, "field 'edPhoneNumber'", EditText.class);
    target.edAddress = Utils.findRequiredViewAsType(source, R.id.edAddress, "field 'edAddress'", EditText.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    AddCitizen target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.btnScanFingure = null;
    target.ivImage = null;
    target.edDOB = null;
    target.btnAddCitizen = null;
    target.genderSpinner = null;
    target.martialSpinner = null;
    target.bloodGroupSpinner = null;
    target.edFirstName = null;
    target.edLastName = null;
    target.edEmail = null;
    target.edPassword = null;
    target.edFatherNIN = null;
    target.edMotherNIN = null;
    target.edPhoneNumber = null;
    target.edAddress = null;
  }
}
