package com.example.authvertical.utils;//package com.example.authvertical.utils;

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.HashMap;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class AppConstants {
    private static final String TAG = AppConstants.class.getSimpleName();
    public static AppConstants instance = null;
    public String login_url = "citizensystem/csu/login";
    public String fingure_print_login_url = "citizensystem/csu/fingerprintlogin";
    public String add_citizen_url = "citizensystem/citizen/addcitizen";
    public String user_email = "email_address";
    public String scan_image_string = "scan_image_string";
    public String device_name = "device_name";
    public String from_login = "from_login";
    public String get_user_by_figureprint = "policesystem/psu/getdriverinfobyfingerprint";
    public String error_string = "error_string";
    public String authorization_key = "Authorization";
    public String get_violation_list = "policesystem/violation/listallviolations";
    public String issue_ticket_by_nin = "policesystem/ticket/issueticketByNin";
    HashMap<String, String> apiParams;
    HashMap<String, String> headers;

    public synchronized static AppConstants getInstance() {
        if (instance == null)
            instance = new AppConstants();
        return instance;
    }


    public void refreshBody() {
        apiParams = new HashMap<>();
        headers = new HashMap<>();
    }

    public void addElements(String key, String value) {
        apiParams.put(key, value);
    }

    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    public HashMap<String, String> getApiParams() {
        return apiParams;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public RequestBody createRequestBody() {
        Log.d(TAG, "createRequestBody: " + new JSONObject(apiParams).toString());
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(apiParams).toString()));

    }
    public RequestBody createRequestBody(HashMap<String ,String> adpterList) {
        Log.d(TAG, "createRequestBody: " + new JSONObject(adpterList).toString());
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                (new JSONObject(adpterList).toString()));

    }


    public Gson getGson() {
        return new Gson();
    }
}
