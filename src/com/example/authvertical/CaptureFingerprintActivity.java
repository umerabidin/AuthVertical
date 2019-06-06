/*
 * File: 		CaptureFingerprintActivity.java
 * Created:		2013/05/03
 *
 * copyright (c) 2013 DigitalPersona Inc.
 */

package com.example.authvertical;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.digitalpersona.uareu.Fid;
import com.digitalpersona.uareu.Quality;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.Reader.Priority;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.jni.DpfjQuality;
import com.example.authvertical.activities.BaseActivity;
import com.example.authvertical.activities.Dashboard;
import com.example.authvertical.utils.ImageUtil;
import com.example.authvertical.utils.RoleEvent;
import com.example.authvertical.utils.UserRoles;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CaptureFingerprintActivity extends BaseActivity implements OnItemSelectedListener {
    private static final String TAG = CaptureFingerprintActivity.class.getSimpleName();
    private Button m_back;
    private String m_deviceName = "";

    private Reader m_reader = null;
    private int m_DPI = 0;
    private Bitmap m_bitmap = null;
    private ImageView m_imgView;
    private TextView m_selectedDevice;
    private TextView m_title;
    private CheckBox m_spoof_enable;
    private boolean m_reset = false;
    private TextView m_text_conclusion;
    private String m_text_conclusionString;
    private Reader.CaptureResult cap_result = null;
    ArrayList<String> base64Image = new ArrayList<>();
    private Spinner m_spinner = null;
    private HashMap<String, Reader.ImageProcessing> m_imgProcMap = null;
    private boolean m_PADEnabled = false;
    private boolean bFirstTime = true;
    String error_string = null;
    String name = null;
    @BindView(R.id.hint_spinner)
    HintSpinner hint_spinner;
    String role = null;
    int roleId = 0;
    @BindView(R.id.btnCaptureImage)
    CardView cvFingurePrint;
    boolean add_citizen = false;
    String request_api = null;
    private void initializeActivity() {
        //getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        m_title = (TextView) findViewById(R.id.title);
        m_title.setText("Capture");
        m_selectedDevice = (TextView) findViewById(R.id.selected_device);
        m_deviceName = getIntent().getExtras().getString("device_name");

        m_selectedDevice.setText("Device: " + m_deviceName);

        m_bitmap = Globals.GetLastBitmap();
        if (m_bitmap == null)
            m_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
        m_imgView = (ImageView) findViewById(R.id.bitmap_image);
        m_imgView.setImageBitmap(m_bitmap);

        m_spoof_enable = (CheckBox) findViewById(R.id.checkBox);
        m_text_conclusion = (TextView) findViewById(R.id.text_conclusion);
        m_back = (Button) findViewById(R.id.back);

        m_back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onBackPressed();
            }
        });

        m_spinner = (Spinner) findViewById(R.id.imgproc);
        m_spinner.setOnItemSelectedListener(this);

        m_imgProcMap = new HashMap<String, Reader.ImageProcessing>();
        m_imgProcMap.put("DEFAULT", Reader.ImageProcessing.IMG_PROC_DEFAULT);
        m_imgProcMap.put("PIV", Reader.ImageProcessing.IMG_PROC_PIV);
        m_imgProcMap.put("ENHANCED", Reader.ImageProcessing.IMG_PROC_ENHANCED);
        m_imgProcMap.put("ENHANCED_2", Reader.ImageProcessing.IMG_PROC_ENHANCED_2);

        Globals.DefaultImageProcessing = Reader.ImageProcessing.IMG_PROC_DEFAULT;
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
                        request_api = appConstants.fingure_print_login_url;
                    } else if (roleId == 2) {
                        request_api = appConstants.fingure_print_police_login_url;
                    } else if (roleId == 3) {
                        request_api = appConstants.fingure_print_medical_login_url;
                    } else if (roleId == 4) {
                        request_api = appConstants.fingure_print_retailer_login_url;
                    } else if (roleId == 5) {
                        request_api = appConstants.fingure_print_citizen_portal_url;
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
        boolean fromLogin = getIntent() != null && getIntent().getBooleanExtra(appConstants.from_login, false);
        if (!fromLogin) {
            hint_spinner.setVisibility(View.GONE);
            TextView btnText = findViewById(R.id.btnText);
            btnText.setText("Save Image");
        }

    }

    private void populateSpinner() {
        if (m_reader != null) {

            /*

                VID			PID			DEVICE			IMAGE PROCESSING
                ---			----		------			----------------
                0x05ba		0x000a		4500			DEFAULT
                0x05ba		0x000b		51XX,4500UID	DEFAULT, PIV, ENHANCED
                0x05ba		0x000c		5301			DEFAULT
                0x05ba		0x000d		5200			DEFAULT, PIV, ENHANCED, ENHANCED2
                0x05ba		0x000e		5300			DEFAULT, PIV, ENHANCED, ENHANCED2
                0x080b		0x010b		Drax30			DEFAULT, PIV, ENHANCED
                0x080b		0x0109		Pocket30		DEFAULT, PIV, ENHANCED
                0x05ba		0x7340		Pocket30-prot	DEFAULT, PIV, ENHANCED
             */

            List<String> options = new ArrayList<String>();

            int vid = m_reader.GetDescription().id.vendor_id;
            int pid = m_reader.GetDescription().id.product_id;

            if (vid == 0x05ba && pid == 0x000a) {
                // device: 4500
                options.add("DEFAULT");
            } else if (vid == 0x05ba && pid == 0x000b) {
                // device: 51XX,4500UID
                options.add("DEFAULT");
                options.add("PIV");
                options.add("ENHANCED");
            } else if (vid == 0x05ba && pid == 0x000c) {
                // device: 5301
                options.add("DEFAULT");
            } else if (vid == 0x05ba && pid == 0x000d) {
                // device: 5200
                options.add("DEFAULT");
                options.add("PIV");
                options.add("ENHANCED");
                options.add("ENHANCED_2");
            } else if (vid == 0x05ba && pid == 0x000e) {
                // device: 5300
                options.add("DEFAULT");
                options.add("PIV");
                options.add("ENHANCED");
                options.add("ENHANCED_2");
            } else if ((vid == 0x080b && (pid == 0x010b || pid == 0x0109)) || (vid == 0x05ba && pid == 0x7340)) {
                // device: Drax30 or Pocket30
                options.add("DEFAULT");
                options.add("PIV");
                options.add("ENHANCED");
            } else {
                options.add("DEFAULT");
            }

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, options);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
            m_spinner.setAdapter(adapter);
        }
    }

    public void onItemSelected(AdapterView<?> parent, View view,
                               int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        // parent.getItemAtPosition(pos)

        // get the current selection from the spinner
        String selection = (String) parent.getItemAtPosition(pos);

        // lookup the image processing mode and set
        Globals.DefaultImageProcessing = m_imgProcMap.get(selection);

        try {
            // abort the current capture (if any)
            if (!bFirstTime) {
                m_reader.CancelCapture();
            }
            bFirstTime = false;
        } catch (UareUException ex) {
            // ignore all exceptions
        }

        // show a Toast to notify the user of the switch
        Toast.makeText(parent.getContext(),
                "OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
                Toast.LENGTH_SHORT).show();
    }

    public void onNothingSelected(AdapterView<?> parent) {
        // Another interface callback
    }

    private void displayDialog(String msg, UareUException e) {
        String str = String.format("%s \nReturned DP error %d \n%s", msg, (e.getCode() & 0xffff), e.toString());
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Error")
                .setMessage(str)
                .setPositiveButton("OK", null)
                .create();
        if (!isFinishing()) dialog.show();
    }

    private void checkPADEnable() {
        try {
            if (m_reader == null) {
                return;
            }
//            m_spoof_enable.setVisibility(View.VISIBLE);
            m_spoof_enable.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    byte[] params = new byte[1];
                    boolean checked = m_spoof_enable.isChecked();
                    try {
                        if (checked) {
                            if (!m_PADEnabled) {
                                params[0] = (byte) 1;
                                m_reader.SetParameter(Reader.ParamId.DPFPDD_PARMID_PAD_ENABLE, params);
                                m_PADEnabled = true;
                            }
                        } else {
                            params[0] = (byte) 0;
                            m_reader.SetParameter(Reader.ParamId.DPFPDD_PARMID_PAD_ENABLE, params);
                            m_PADEnabled = false;
                        }
                    } catch (UareUException e) {
                        Log.w("UareUSampleJava", "error during SetParameter: " + e.toString());
                        displayDialog("Set PAD parameter fail!", e);
                        m_spoof_enable.setChecked(m_PADEnabled = false); //Exception: uncheck PAD enable
                    }
                }
            });
            m_spoof_enable.setChecked(m_PADEnabled); //General: check/uncheck PAD enable
            //}
        } catch (Exception e) {
            if (!m_reset) {
                Log.w("UareUSampleJava", "error during capture: " + e.toString());
                m_deviceName = "";
                onBackPressed();
            }
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_capture_stream);
        ButterKnife.bind(this);
        // ...
        initializeActivity();

        // initialize dp sdk
        try {
            Context applContext = getApplicationContext();
            m_reader = Globals.getInstance().getReader(m_deviceName, applContext);
            m_reader.Open(Priority.EXCLUSIVE);
            m_DPI = Globals.GetFirstDPI(m_reader);

            byte[] result = m_reader.GetParameter(Reader.ParamId.DPFPDD_PARMID_PAD_ENABLE);
            //Log.i("-->app GetParameter", "PAD_buffer[0]=" + String.format("%s", result[0]));
            if (m_spoof_enable.isChecked() && !m_PADEnabled) {
                byte[] params = {1};
                m_reader.SetParameter(Reader.ParamId.DPFPDD_PARMID_PAD_ENABLE, params);
                m_PADEnabled = true;
            }

        } catch (Exception e) {
            Log.w("UareUSampleJava", "error during init of reader");


            if (e.toString().contains("The reader is not working properly.")) {
                error_string = "The reader is not working properly.";
                //java.lang.NullPointerException: Attempt to invoke interface method 'void com.digitalpersona.uareu.Reader.Open(com.digitalpersona.uareu.Reader$Priority)' on a null object reference
            } else if (e.toString().contains("NullPointerException")) {
                error_string = "NullPointerException";
            }
            onBackPressed();
            return;
        }

        // Check PAD enable button
        checkPADEnable();
        // populate the spinner widget
        populateSpinner();

        // loop capture on a separate thread to avoid freezing the UI
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    m_reset = false;
                    while (!m_reset) {
                        // capture the image (synchronous)
                        if (true) {
                            cap_result = m_reader.Capture(Fid.Format.ANSI_381_2004, Globals.DefaultImageProcessing, m_DPI, -1);
                        }

                        // capture the image (asynchronous)
                        if (false) {
                            final Object captureComplete = new Object();

                            m_reader.CaptureAsync(Fid.Format.ANSI_381_2004, Globals.DefaultImageProcessing, m_DPI, -1, new Reader.CaptureCallback() {
                                public void CaptureResultEvent(Reader.CaptureResult result) {
                                    synchronized (captureComplete) {
                                        cap_result = result;
                                        captureComplete.notify();
                                    }
                                }
                            });

                            // note: may need to place a time limit on the wait
                            synchronized (captureComplete) {
                                captureComplete.wait();
                            }
                        }


                        // an error occurred
                        if (cap_result == null) continue;

                        if (cap_result.image != null) {
                            // save bitmap image locally
                            m_bitmap = Globals.GetBitmapFromRaw(cap_result.image.getViews()[0].getImageData(), cap_result.image.getViews()[0].getWidth(), cap_result.image.getViews()[0].getHeight());

                            // calculate nfiq score
                            DpfjQuality quality = new DpfjQuality();
                            int nfiqScore = quality.nfiq_raw(
                                    cap_result.image.getViews()[0].getImageData(),    // raw image data
                                    cap_result.image.getViews()[0].getWidth(),        // image width
                                    cap_result.image.getViews()[0].getHeight(),        // image height
                                    m_DPI,                                            // device DPI
                                    cap_result.image.getBpp(),                        // image bpp
                                    Quality.QualityAlgorithm.QUALITY_NFIQ_NIST        // qual. algo.
                            );

                            // log NFIQ score
                            Log.i("UareUSampleJava", "capture result nfiq score: " + nfiqScore);

                            // update ui string
                            m_text_conclusionString = Globals.QualityToString(cap_result);
                            m_text_conclusionString = String.format("%s%s", m_text_conclusionString, " (NFIQ score: " + nfiqScore + ")");
                        } else {
                            m_bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.black);
                            // update ui string
                            m_text_conclusionString = Globals.QualityToString(cap_result);
                        }

                        runOnUiThread(() -> {
                            UpdateGUI();
                            if (cap_result.image != null)
                                base64Image.add(ImageUtil.convert(cap_result.image.getData()));
                        });
                    }
                } catch (Exception e) {
                    if (!m_reset) {
                        Log.w("UareUSampleJava", "error during capture: " + e.toString());
                        m_deviceName = "";
                        onBackPressed();
                    }
                }
            }
        }).start();
    }

    public void UpdateGUI() {
        m_imgView.setImageBitmap(m_bitmap);
        m_imgView.invalidate();
        m_text_conclusion.setText(m_text_conclusionString);
    }

    @Override
    public void onBackPressed() {
        try {
            m_reset = true;
            try {
                m_reader.CancelCapture();
            } catch (Exception e) {
                e.printStackTrace();
            }
            m_reader.Close();

        } catch (Exception e) {
            Log.w("UareUSampleJava", "error during reader shutdown");
        }

        Intent i = new Intent();
        i.putExtra(appConstants.device_name, m_deviceName);
        i.putExtra(appConstants.error_string, error_string);
        setResult(Activity.RESULT_CANCELED, i);
        finish();
    }

    // called when orientation has changed to manually destroy and recreate activity
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        setContentView(R.layout.activity_capture_stream);
        initializeActivity();
        // Check PAD enbale button
        checkPADEnable();
        // populate the spinner widget
        populateSpinner();
    }

    @Override
    public void onClick(View v) {
        if (base64Image.size() > 0) {

            if (getIntent() != null && getIntent().getBooleanExtra(appConstants.from_login, false)) {
                showProgress();
                appConstants.refreshBody();
                appConstants.addElements("fingerprint", base64Image.get(0));
                Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(),
                        request_api,
                        appConstants.createRequestBody());
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());
                                try {

                                    Intent intent = new Intent(CaptureFingerprintActivity.this, Dashboard.class);
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
                        Toast.makeText(CaptureFingerprintActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            } else {
                Intent i = new Intent();
                i.putExtra(appConstants.device_name, m_deviceName);
                i.putExtra(appConstants.scan_image_string, new Gson().toJson(base64Image));
                setResult(Activity.RESULT_OK, i);
                finish();

            }

        } else {
            Toast.makeText(this, "Please scan your image first!", Toast.LENGTH_SHORT).show();
        }
    }
}
