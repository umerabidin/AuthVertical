package com.example.authvertical.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbHost;
import com.example.authvertical.CaptureFingerprintActivity;
import com.example.authvertical.GetReaderActivity;
import com.example.authvertical.Globals;
import com.example.authvertical.R;
import com.example.authvertical.db_and_models.get_citizen.GetCitizenEntity;
import com.example.authvertical.db_and_models.violations_list.Violation;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IssueTrafficChallan extends BaseFragment {

    private static final String TAG = IssueTrafficChallan.class.getSimpleName();
    UsbDevice device;
    String m_deviceName;
    @BindView(R.id.btnIssueChallan)
    CardView btnIssueChallan;
    @BindView(R.id.edNIN)
    EditText edNIN;
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @BindView(R.id.edLastName)
    EditText edLastName;
    @BindView(R.id.edAddress)
    EditText edAddress;
    @BindView(R.id.edLicenseNumber)
    EditText edLicenseNumber;
    @BindView(R.id.edRegistrationNumber)
    EditText edRegistrationNumber;
    @BindView(R.id.violation_code_spinner)
    HintSpinner violation_code_spinner;
    @BindView(R.id.edFineImposed)
    EditText edFineImposed;
    @BindView(R.id.edViolationLocation)
    EditText edViolationLocation;


    private static final int GENERAL_ACTIVITY_RESULT = 102;

    public static IssueTrafficChallan newInstance(/*String param1 , String param2*/) {
        IssueTrafficChallan fragment = new IssueTrafficChallan();
//        Bundle args = new Bundle ();
//        args.putString ( ARG_PARAM1 , param1 );
//        args.putString ( ARG_PARAM2 , param2 );
//        fragment.setArguments ( args );
        return fragment;
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_issue_traffic_challan, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);

        if (m_reader == null) {
            Intent i = new Intent(getActivity(), GetReaderActivity.class);
            i.putExtra("device_name", m_deviceName);
            startActivityForResult(i, GENERAL_ACTIVITY_RESULT);

        } else {
            Intent i = new Intent(getActivity(),
                    CaptureFingerprintActivity.class);
            i.putExtra("add_citizen", true);
            i.putExtra("device_name", m_deviceName);
            startActivityForResult(i, GENERAL_ACTIVITY_RESULT);
        }
        btnIssueChallan.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnIssueChallan:
                if (TextUtils.isEmpty(edNIN.getText().toString())) {
                    edNIN.setError("Please enter required information!");
                    edNIN.requestFocus();
                } else if (TextUtils.isEmpty(edFirstName.getText().toString())) {
                    edFirstName.setError("Please enter required information!");
                    edFirstName.requestFocus();
                } else if (TextUtils.isEmpty(edLastName.getText().toString())) {
                    edLastName.setError("Please enter required information!");
                    edLastName.requestFocus();
                } else if (TextUtils.isEmpty(edAddress.getText().toString())) {
                    edAddress.setError("Please enter required information!");
                    edAddress.requestFocus();
                } else if (TextUtils.isEmpty(edLicenseNumber.getText().toString())) {
                    edLicenseNumber.setError("Please enter required information!");
                    edLicenseNumber.requestFocus();
                } else if (TextUtils.isEmpty(edRegistrationNumber.getText().toString())) {
                    edRegistrationNumber.setError("Please enter required information!");
                    edRegistrationNumber.requestFocus();
                } else if (TextUtils.isEmpty(edFineImposed.getText().toString())) {
                    TextView errorText = (TextView) violation_code_spinner.getSelectedView();
                    errorText.setError("");
                    errorText.setTextColor(Color.RED);//just to highlight that this is an error
                    errorText.setText("Please fill the required field!");
                    errorText.requestFocus();
                } else if (TextUtils.isEmpty(edViolationLocation.getText().toString())) {
                    edViolationLocation.setError("Please enter required information!");
                    edViolationLocation.requestFocus();
                } else {
                    appConstants.refreshBody();
                    appConstants.addElements("NIN", edNIN.getText().toString());
                    appConstants.addElements("Name", edFirstName.getText().toString() + " " + edLastName.getText().toString());
                    appConstants.addElements("Address", edAddress.getText().toString());
                    appConstants.addElements("Driver License Number", edLicenseNumber.getText().toString());
                    appConstants.addElements("Vehicle Registration Number", edRegistrationNumber.getText().toString());
                    appConstants.addElements("Violation", violation_type);
                    appConstants.addElements("Fine Imposed", edFineImposed.getText().toString());
                    appConstants.addElements("Violation Location", edViolationLocation.getText().toString());
                    String adapterList = new Gson().toJson(appConstants.getApiParams());
                    //json request
                    appConstants.refreshBody();
                    appConstants.addElements("nin", edNIN.getText().toString());
                    appConstants.addElements("violation", violation_id);
                    appConstants.addElements("fine", edFineImposed.getText().toString());
                    appConstants.addElements("violationLocation", edViolationLocation.getText().toString());
                    appConstants.addElements("vehicleRegistrationNumber", edRegistrationNumber.getText().toString());
                    String requestList = new Gson().toJson(appConstants.getApiParams());
                    VerifyChallanDetailsSheet verifyChallanDetailsSheet =
                            VerifyChallanDetailsSheet.newInstance(adapterList,requestList);
                    verifyChallanDetailsSheet.show(getChildFragmentManager(),
                            "add_photo_dialog_fragment");
                }
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GENERAL_ACTIVITY_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                Globals.ClearLastBitmap();
                m_deviceName = data.getExtras().getString("device_name");

                if ((m_deviceName != null) && !m_deviceName.isEmpty()) {
                    ArrayList<String> scannedImages = new Gson().fromJson(data.getExtras().getString(appConstants.scan_image_string),
                            new TypeToken<List<String>>() {
                            }.getType());
                    if (scannedImages != null && scannedImages.size() > 0) {
                        String scanned_image_base64 = scannedImages.get(0);
                        getUserInfo(scanned_image_base64);
                    }
                    try {
                        Context applContext = getActivity().getApplicationContext();
                        m_reader = Globals.getInstance().getReader(m_deviceName, applContext);

                        {
                            PendingIntent mPermissionIntent;
                            mPermissionIntent = PendingIntent.getBroadcast(applContext, 0, new Intent(ACTION_USB_PERMISSION), 0);
                            IntentFilter filter = new IntentFilter(ACTION_USB_PERMISSION);
                            applContext.registerReceiver(mUsbReceiver, filter);
                            DPFPDDUsbHost.DPFPDDUsbCheckAndRequestPermissions(applContext, mPermissionIntent, m_deviceName);

                        }
                    } catch (UareUException e1) {
                        displayReaderNotFound();
                    } catch (DPFPDDUsbException e) {
                        displayReaderNotFound();
                    }

                } else {
                    displayReaderNotFound();

                }
            } else if (resultCode == Activity.RESULT_CANCELED) {

                if (data != null) {
                    String error_string = data.getStringExtra(appConstants.error_string);
                    if (error_string == null || error_string.equalsIgnoreCase("NullPointerException")) {
                        displayReaderNotFound();
                    } else if (error_string.equalsIgnoreCase("The reader is not working properly.")) {
                        Intent i = new Intent(getActivity(), GetReaderActivity.class);
                        i.putExtra("device_name", m_deviceName);
                        startActivityForResult(i, GENERAL_ACTIVITY_RESULT);
                    }
                } else {
                    displayReaderNotFound();
                    return;

                }
            }

        }


    }

    private void getUserInfo(String scanned_image_base64) {
        showProgress();
        appConstants.refreshBody();
        appConstants.addElements("fingerprint", scanned_image_base64.replace("data:image/jpeg;base64,", ""));
        appConstants.addHeader("Authorization", "Bearer " + appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, "")).getToken());
        Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(),
                appConstants.get_user_by_figureprint, appConstants.createRequestBody());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject body = new JSONObject(response.body().toString());
                        GetCitizenEntity getCitizenEntity = new Gson().fromJson(body.toString(), GetCitizenEntity.class);

                        viewInfo(getCitizenEntity);

                        Log.d(TAG, "onResponse: " + body.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void viewInfo(GetCitizenEntity getCitizenEntity) {
        edFirstName.setText(getCitizenEntity.getCitizen().getFirstName());
        edLastName.setText(getCitizenEntity.getCitizen().getLastName());
        edNIN.setText(getCitizenEntity.getCitizen().getId());
        edAddress.setText(getCitizenEntity.getCitizen().getCurrentAddress());
        edLicenseNumber.setText(getCitizenEntity.getType().getId());

        appConstants.refreshBody();
        appConstants.addHeader(appConstants.authorization_key, "Bearer " + appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, "")).getToken());
        Call<JsonElement> call = apiHelper.getRequest(appConstants.getHeaders(), appConstants.get_violation_list);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideProgress();
                if (response.isSuccessful()) {
                    try {
                        JSONArray body = new JSONArray(response.body().toString());
                        List<Violation> violations = new Gson().fromJson(body.toString(), new TypeToken<List<Violation>>() {
                        }.getType());
                        violation_code_spinner.setAdapter(new HintSpinnerAdapter<Violation>(getActivity(),
                                violations, "Select Violation Code") {
                            @Override
                            public String getLabelFor(Violation violation) {
                                return violation.getName();
                            }
                        });
                        setListener();
                        Log.d(TAG, "onResponse: " + violations.toString());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                hideProgress();
            }
        });
    }

    String violation_type, violation_id;

    private void setListener() {
        violation_code_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                try {
                    JSONObject body = new JSONObject(item);
                    String fine = body.optString("fine");
                    violation_type = body.optString("name");
                    violation_id = body.optString("_id");
                    edFineImposed.setText("" + fine);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    Reader m_reader;

    protected void CheckDevice() {
        try {
            m_reader.Open(Reader.Priority.EXCLUSIVE);
            Reader.Capabilities cap = m_reader.GetCapabilities();
            m_reader.Close();
        } catch (UareUException e1) {
            displayReaderNotFound();
        }
    }

    private static final String ACTION_USB_PERMISSION = "com.digitalpersona.uareu.dpfpddusbhost.USB_PERMISSION";

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (ACTION_USB_PERMISSION.equals(action)) {
                synchronized (this) {
                    device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        if (device != null) {
                            //call method to set up device communication
                            Intent i = new Intent(getActivity(),
                                    CaptureFingerprintActivity.class);
                            i.putExtra("device_name", m_deviceName);
                            i.putExtra("add_citizen", true);
                            startActivityForResult(i, GENERAL_ACTIVITY_RESULT);
                        }
                    } else {
                        Toast.makeText(context, "Error Reciever", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    };
}
