package com.example.authvertical.fragments;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.CircularProgressDrawable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.digitalpersona.uareu.Reader;
import com.digitalpersona.uareu.UareUException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbException;
import com.digitalpersona.uareu.dpfpddusbhost.DPFPDDUsbHost;
import com.example.authvertical.CaptureFingerprintActivity;
import com.example.authvertical.GetReaderActivity;
import com.example.authvertical.Globals;
import com.example.authvertical.R;
import com.example.authvertical.activities.Dashboard;
import com.example.authvertical.utils.BloodGroup;
import com.example.authvertical.utils.DropDownItems;
import com.example.authvertical.utils.Gender;
import com.example.authvertical.utils.ImagePickerActivity;
import com.example.authvertical.utils.ImageUtil;
import com.example.authvertical.utils.Martial;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddCitizen extends BaseFragment implements AdapterView.OnItemSelectedListener {
    private static final String TAG = AddCitizen.class.getSimpleName();
    UsbDevice device;
    String m_deviceName = null, scanned_image_base64 = null, userBase64Image = null;
    public static final int REQUEST_IMAGE = 100;
    private static final int DATE_PICKER_REQUEST_CODE = 101;
    private static final int GENERAL_ACTIVITY_RESULT = 102;

    @BindView(R.id.btnScanFingure)
    CardView btnScanFingure;
    @BindView(R.id.ivImage)
    CircleImageView ivImage;
    Dashboard activity;
    @BindView(R.id.edDOB)
    EditText edDOB;
    @BindView(R.id.btnAddCitizen)
    CardView btnAddCitizen;
    @BindView(R.id.genderSpinner)
    HintSpinner genderSpinner;
    @BindView(R.id.martialSpinner)
    HintSpinner martialSpinner;
    @BindView(R.id.bloodGroupSpinner)
    HintSpinner bloodGroupSpinner;
    @BindView(R.id.edFirstName)
    EditText edFirstName;
    @BindView(R.id.edLastName)
    EditText edLastName;
    @BindView(R.id.edEmail)
    EditText edEmail;
    @BindView(R.id.edPassword)
    EditText edPassword;
    @BindView(R.id.edFatherNIN)
    EditText edFatherNIN;
    @BindView(R.id.edMotherNIN)
    EditText edMotherNIN;
    @BindView(R.id.edPhoneNumber)
    EditText edPhoneNumber;
    @BindView(R.id.edAddress)
    EditText edAddress;


    public static AddCitizen newInstance(/*String param1 , String param2*/) {
        AddCitizen fragment = new AddCitizen();
//        Bundle args = new Bundle ();
//        args.putString ( ARG_PARAM1 , param1 );
//        args.putString ( ARG_PARAM2 , param2 );
//        fragment.setArguments ( args );
        return fragment;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Dashboard) getActivity();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_add_citizen, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        btnAddCitizen.setOnClickListener(this);
        ivImage.setOnClickListener(this);
        edDOB.setOnClickListener(this);
        btnScanFingure.setOnClickListener(this);
        martialSpinner.setAdapter(new HintSpinnerAdapter<Martial>(getActivity(),
                DropDownItems.getInstance().getMartialStatuses(), "Married Status") {
            @Override
            public String getLabelFor(Martial roles) {
                return roles.getName();
            }
        });
        genderSpinner.setAdapter(new HintSpinnerAdapter<Gender>(getActivity(),
                DropDownItems.getInstance().getGenders(), "Select Gender") {
            @Override
            public String getLabelFor(Gender roles) {
                return roles.getName();
            }
        });
        bloodGroupSpinner.setAdapter(new HintSpinnerAdapter<BloodGroup>(getActivity(),
                DropDownItems.getInstance().getBloodGroups(), "Choose your Blood Group") {
            @Override
            public String getLabelFor(BloodGroup roles) {
                return roles.getName();
            }
        });

        genderSpinner.setOnItemSelectedListener(this);
        martialSpinner.setOnItemSelectedListener(this);
        bloodGroupSpinner.setOnItemSelectedListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnScanFingure:
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

                break;
            case R.id.btnAddCitizen:
                requestAddCitizen();
                break;
            case R.id.edDOB:
                final FragmentManager fm = getActivity().getSupportFragmentManager();

                AppCompatDialogFragment newFragment = new DatePickerFragment();
                // set the targetFragment to receive the results, specifying the request code
                newFragment.setTargetFragment(AddCitizen.this, DATE_PICKER_REQUEST_CODE);
                // show the datePicker
                newFragment.show(fm, "datePicker");


                break;
            case R.id.ivImage:

                onProfileImageClick();
                break;
        }


    }

    private void requestAddCitizen() {
        if (TextUtils.isEmpty(edFirstName.getText().toString())) {
            edFirstName.setError("Please fill the required field!");
            edFirstName.requestFocus();
        } else if (TextUtils.isEmpty(edLastName.getText().toString())) {
            edLastName.setError("Please fill the required field!");
            edLastName.requestFocus();
        } else if (TextUtils.isEmpty(edDOB.getText().toString())) {
            edDOB.setError("Please fill the required field!");
            edDOB.requestFocus();
        } else if (gender == null) {
            TextView errorText = (TextView) genderSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please fill the required field!");
            errorText.requestFocus();
        } else if (martialStatus == null) {
            TextView errorText = (TextView) martialSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please fill the required field!");
            errorText.requestFocus();
        } else if (bloodGroup == null) {
            TextView errorText = (TextView) bloodGroupSpinner.getSelectedView();
            errorText.setError("");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("Please fill the required field!");
            errorText.requestFocus();
        } else if (TextUtils.isEmpty(edEmail.getText().toString())) {
            edEmail.setError("Please fill the required field!");
            edEmail.requestFocus();
        } else if (TextUtils.isEmpty(edPassword.getText().toString())) {
            edPassword.setError("Please fill the required field!");
            edPassword.requestFocus();
        } else if (TextUtils.isEmpty(edFatherNIN.getText().toString())) {
            edFatherNIN.setError("Please fill the required field!");
            edFatherNIN.requestFocus();
        } else if (TextUtils.isEmpty(edMotherNIN.getText().toString())) {
            edMotherNIN.setError("Please fill the required field!");
            edMotherNIN.requestFocus();
        } else if (TextUtils.isEmpty(edPhoneNumber.getText().toString())) {
            edPhoneNumber.setError("Please fill the required field!");
            edPhoneNumber.requestFocus();
        } else if (TextUtils.isEmpty(edAddress.getText().toString())) {
            edAddress.setError("Please fill the required field!");
            edAddress.requestFocus();
        } else if (userBase64Image == null) {
            Toast.makeText(activity, "Please select your image!", Toast.LENGTH_SHORT).show();
        } else {
            showProgress();
            DateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");
            try {
                Date date = formatter.parse(edDOB.getText().toString());


                appConstants.refreshBody();
                appConstants.addElements("firstName", edFirstName.getText().toString());
                appConstants.addElements("lastName", edLastName.getText().toString());
                appConstants.addElements("dob", String.valueOf(date.getTime()));
                appConstants.addElements("gender", gender);
                appConstants.addElements("fatherNin", edFatherNIN.getText().toString());
                appConstants.addElements("motherNin", edMotherNIN.getText().toString());
                appConstants.addElements("phone", edPhoneNumber.getText().toString());
                appConstants.addElements("email", edEmail.getText().toString());
                appConstants.addElements("address", edAddress.getText().toString());
                appConstants.addElements("password", edPassword.getText().toString());
                appConstants.addElements("fingerprints", scanned_image_base64);
//                appConstants.addElements("fingerprints", "scanned_image");
                appConstants.addElements("photo", userBase64Image);
//                appConstants.addElements("photo", "user_image");
                appConstants.addElements("bloodGroup", bloodGroup);
                appConstants.addElements("maritalStatus", martialStatus);
                HashMap<String, String> headers = new HashMap<>();
                headers.put("Authorization",appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, "")).getToken());

//                Call<JsonElement> call = apiHelper.addCitizen(appConstants.add_citizen_url, headers,
//                        edFirstName.getText().toString(),
//                        edLastName.getText().toString(),
//                        String.valueOf(date.getTime()),
//                        gender,
//                        edFatherNIN.getText().toString(),
//                        edMotherNIN.getText().toString(),
//                        edPhoneNumber.getText().toString(),
//                        edEmail.getText().toString(),
//                        edAddress.getText().toString(),
//                        edPassword.getText().toString(),
//                        scanned_image_base64,
//                        userBase64Image,
//                        bloodGroup,
//                        martialStatus);


                Call<JsonElement> call = apiHelper.postRequest(headers, appConstants.add_citizen_url, appConstants.createRequestBody());
                call.enqueue(new Callback<JsonElement>() {
                    @Override
                    public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                        hideProgress();
                        if (response.isSuccessful()) {
                            try {
                                JSONObject jsonObject = new JSONObject(response.body().toString());

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
            } catch (Exception e) {
                hideProgress();
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            }


        }


    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == DATE_PICKER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            // set the value of the editText
            edDOB.setText(data.getStringExtra("selectedDate"));
        } else if (requestCode == REQUEST_IMAGE && resultCode == Activity.RESULT_OK) {
            Uri uri = data.getParcelableExtra("path");
            try {
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(uri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);


                // You can update this bitmap to your server
                userBase64Image = ImageUtil.convert(selectedImage);
                // loading profile image from local cache
                loadProfile(uri.toString());
            } catch (IOException e) {
                e.printStackTrace();

            }
        } else if (requestCode == GENERAL_ACTIVITY_RESULT) {
            if (resultCode == Activity.RESULT_OK) {
                Globals.ClearLastBitmap();
                m_deviceName = data.getExtras().getString("device_name");

                if ((m_deviceName != null) && !m_deviceName.isEmpty()) {
                    ArrayList<String> scannedImages = new Gson().fromJson(data.getExtras().getString(appConstants.scan_image_string),
                            new TypeToken<List<String>>() {
                            }.getType());
                    if (scannedImages != null && scannedImages.size() > 0) {
                        scanned_image_base64 = scannedImages.get(0);
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

    private void loadProfile(String url) {
        Log.d(TAG, "Image cache path: " + url);
        CircularProgressDrawable circularProgressDrawable = new CircularProgressDrawable(getActivity());
        circularProgressDrawable.setStrokeWidth(5f);
        circularProgressDrawable.setCenterRadius(30f);
        circularProgressDrawable.start();
        Glide.with(this)
                .load(url)
//                .placeholder(circularProgressDrawable)
                .thumbnail(0.5f)
                .fitCenter()
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(ivImage);
    }

    String gender, martialStatus, bloodGroup;

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String itemData = parent.getItemAtPosition(position).toString();
        switch (parent.getId()) {
            case R.id.genderSpinner:
                try {
                    JSONObject roles = new JSONObject(itemData);
                    gender = roles.optString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.martialSpinner:
                try {
                    JSONObject roles = new JSONObject(itemData);
                    martialStatus = roles.optString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
            case R.id.bloodGroupSpinner:
                try {
                    JSONObject roles = new JSONObject(itemData);
                    bloodGroup = roles.optString("name");

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }


    void onProfileImageClick() {
        Dexter.withActivity(getActivity())
                .withPermissions(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                        if (report.areAllPermissionsGranted()) {
                            showImagePickerOptions();
                        }

                        if (report.isAnyPermissionPermanentlyDenied()) {
                            showSettingsDialog();
                        }
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }

    private void showImagePickerOptions() {
        ImagePickerActivity.showImagePickerOptions(getActivity(), new ImagePickerActivity.PickerOptionListener() {
            @Override
            public void onTakeCameraSelected() {
                launchCameraIntent();
            }

            @Override
            public void onChooseGallerySelected() {
                launchGalleryIntent();
            }
        });
    }


    /**
     * Showing Alert Dialog with Settings option
     * Navigates user to app settings
     * NOTE: Keep proper title and message depending on your app
     */
    private void showSettingsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.dialog_permission_title));
        builder.setMessage(getString(R.string.dialog_permission_message));
        builder.setPositiveButton(getString(R.string.go_to_settings), (dialog, which) -> {
            dialog.cancel();
            openSettings();
        });
        builder.setNegativeButton(getString(android.R.string.cancel), (dialog, which) -> dialog.cancel());
        builder.show();

    }

    // navigating user to app settings
    private void openSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
        intent.setData(uri);
        startActivityForResult(intent, 101);
    }

    private void launchCameraIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_IMAGE_CAPTURE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);

        // setting maximum bitmap width and height
        intent.putExtra(ImagePickerActivity.INTENT_SET_BITMAP_MAX_WIDTH_HEIGHT, true);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_WIDTH, 1000);
        intent.putExtra(ImagePickerActivity.INTENT_BITMAP_MAX_HEIGHT, 1000);

        startActivityForResult(intent, REQUEST_IMAGE);
    }

    private void launchGalleryIntent() {
        Intent intent = new Intent(getActivity(), ImagePickerActivity.class);
        intent.putExtra(ImagePickerActivity.INTENT_IMAGE_PICKER_OPTION, ImagePickerActivity.REQUEST_GALLERY_IMAGE);

        // setting aspect ratio
        intent.putExtra(ImagePickerActivity.INTENT_LOCK_ASPECT_RATIO, true);
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_X, 1); // 16x9, 1x1, 3:4, 3:2
        intent.putExtra(ImagePickerActivity.INTENT_ASPECT_RATIO_Y, 1);
        startActivityForResult(intent, REQUEST_IMAGE);
    }


}
