package com.example.authvertical.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
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
import com.example.authvertical.db_and_models.login_entity.LoginEntity;
import com.example.authvertical.utils.RoleEvent;
import com.example.authvertical.utils.UserRoles;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity {

    private static final String TAG = LoginActivity.class.getSimpleName();
    @BindView(R.id.hint_spinner)
    HintSpinner hint_spinner;
    String role = null;
    int roleId = 0;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.cvFingurePrint)
    CardView cvFingurePrint;
    private final int GENERAL_ACTIVITY_RESULT = 1;
    UsbDevice device;
    String m_deviceName = null, name = null, request_api = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        ArrayList<UserRoles> roles = new ArrayList<>();
        roles.add(new UserRoles("1", "Civic"));
        roles.add(new UserRoles("2", "Police System"));
        roles.add(new UserRoles("3", "Medical System"));
        roles.add(new UserRoles("4", "Retailer System"));
        roles.add(new UserRoles("5", "CitizenInfo Portal"));
        hint_spinner.setAdapter(new HintSpinnerAdapter<UserRoles>(this,
                roles, "Select System") {
            @Override
            public String getLabelFor(UserRoles roles) {
                return roles.getName();
            }
        });
        hint_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                role = parent.getItemAtPosition(position).toString();
                try {
                    JSONObject roles = new JSONObject(role);
                    roleId = roles.optInt("roleId");
                    name = roles.optString("name");
                    if (roleId == 1) {
                        request_api = appConstants.login_url;
                    } else if (roleId == 2) {
                        request_api = appConstants.police_login_url;
                    } else if (roleId == 3) {
                        request_api = appConstants.medical_login_url;
                    } else if (roleId == 4) {
                        request_api = appConstants.retailer_login_url;
                    } else if (roleId == 5) {
                        request_api = appConstants.citizen_portal_login;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        cvFingurePrint.setOnClickListener(this);
    }


    public void login(View view) {
        if (role == null) {
            Toast.makeText(this, "Please select your role first!", Toast.LENGTH_SHORT).show();
        } else if (roleId == 3 ) {
            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(edEmail.getText().toString())) {
            edEmail.setError("Please enter your email!");
            edEmail.requestFocus();
        } else if (TextUtils.isEmpty(edPassword.getText().toString())) {
            edPassword.setError("Please enter your password");
            edPassword.requestFocus();
        } else {
            showProgress();
            appConstants.refreshBody();
            appConstants.addElements("id", edEmail.getText().toString());
            appConstants.addElements("password", edPassword.getText().toString());
            Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(), request_api, appConstants.createRequestBody());
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    hideProgress();
                    if (response.isSuccessful()) {
                        try {
                            JSONObject jsonObject = new JSONObject(response.body().toString());
                            try {
                                LoginEntity loginEntity = new Gson().fromJson(jsonObject.toString(), LoginEntity.class);
                                if (appDataBase.getDao().getUser(edEmail.getText().toString()) != null) {
                                    appDataBase.getDao().deleteUser(edEmail.getText().toString());
                                }

                                loginEntity.setEmail_address(edEmail.getText().toString());
                                loginEntity.setUser_role(name);
                                loginEntity.setToken("Bearer " + loginEntity.getToken());
                                appDataBase.getDao().storeUser(loginEntity);

                                myAppPref.saveString(appConstants.user_email, edEmail.getText().toString());

                                Intent intent = new Intent(LoginActivity.this, Dashboard.class);
                                intent.putExtra("data", new Gson().toJson(new RoleEvent(roleId, name)));
                                startActivity(intent);
                                finish();
                            } catch (Exception e) {
                                Log.d(TAG, "Exception: " + e.getMessage());
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    hideProgress();
                    Toast.makeText(LoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });


        }

    }

    @Override
    public void onClick(View v) {
        if (m_reader == null) {
            Intent i = new Intent(LoginActivity.this, GetReaderActivity.class);
            i.putExtra(appConstants.device_name, m_deviceName);
            startActivityForResult(i, GENERAL_ACTIVITY_RESULT);

        } else {
            Intent i = new Intent(LoginActivity.this,
                    CaptureFingerprintActivity.class);
            i.putExtra(appConstants.device_name, m_deviceName);
            i.putExtra(appConstants.from_login, true);
            startActivityForResult(i, GENERAL_ACTIVITY_RESULT);
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    }
                    try {
                        Context applContext = getApplicationContext();
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
                        Intent i = new Intent(this, GetReaderActivity.class);
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

    private void displayReaderNotFound() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Reader Not Found");
        alertDialogBuilder.setMessage("Plug in a reader and try again.").setCancelable(false).setPositiveButton("Ok",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        if (!isFinishing()) {
            alertDialog.show();
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
                            Intent i = new Intent(LoginActivity.this,
                                    CaptureFingerprintActivity.class);
                            i.putExtra(appConstants.device_name, m_deviceName);
                            i.putExtra(appConstants.from_login, true);
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
