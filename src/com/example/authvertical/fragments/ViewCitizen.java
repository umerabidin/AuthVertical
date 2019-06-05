package com.example.authvertical.fragments;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewCitizen extends BaseFragment {
    private static final String TAG = ViewCitizen.class.getSimpleName();
    @BindView(R.id.edNIN)
    EditText edNIN;
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @BindView(R.id.edLastName)
    EditText edLastName;
    @BindView(R.id.edPhoneNumber)
    EditText edPhoneNumber;
    @BindView(R.id.edAddress)
    EditText edAddress;
    @BindView(R.id.btnScanFingure)
    CardView btnScanFingure;
    private static final int GENERAL_ACTIVITY_RESULT = 102;
    UsbDevice device;
    String m_deviceName = null, scanned_image_base64 = null;

    public static ViewCitizen newInstance(/*String param1, String param2*/) {
        ViewCitizen fragment = new ViewCitizen();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }


    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_view_citizen, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        btnScanFingure.setOnClickListener(this);


    }

    Reader m_reader;

    @Override
    public void onClick(View v) {
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
                        scanned_image_base64 = scannedImages.get(0);
                        getUserData(scanned_image_base64);
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
//                            if (DPFPDDUsbHost.DPFPDDUsbCheckAndRequestPermissions(applContext, mPermissionIntent, m_deviceName)) {
////                                CheckDevice();
//                            }
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

    private void getUserData(String scanned_image_base64) {
        showProgress();
        appConstants.refreshBody();
        appConstants.addElements("fingerprint", scanned_image_base64.replace("data:image/jpeg;base64,", ""));
        appConstants.addHeader("Authorization", "Bearer " + appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, "")).getToken());
        Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(),
                appConstants.get_user_by_figureprint, appConstants.createRequestBody());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                hideProgress();
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
        edPhoneNumber.setText(getCitizenEntity.getCitizen().getPhone());
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
