package com.example.authvertical.utils;//package com.example.authvertical.utils;

import android.app.Activity;
import android.util.Log;

import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;

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
    public String add_citizen_key = "add_citizen";
    public String claim_fragment = "claim_fragment";
    public String pay_challan_fragment = "pay_challan_fragment";
    public String retailer_login_url = "retailsystem/rsu/login";
    public String police_login_url = "policesystem/psu/login";
    public String citizen_portal_login = "citizenportal/login";
    public String get_challans = "citizenportal/getuserticket";
    public String get_user_accounts = "citizenportal/getuseraccounts";
    public String get_account_by_organization = "banksystem/account/getAccountByOrganization";
    public String organization = "organization";
    public String pay_challan_url = "citizenportal/payticket";
    public String transfer_ammount = "citizenportal/transfertoaccount";
    public String validate_accountnumber = "banksystem/account/getAccountByNumber";
    public String medical_login_url = "";
    public String fingure_print_citizen_portal_url="citizenportal/fingerprintlogin";
    public String fingure_print_retailer_login_url="retailsystem/rsu/fingerprintlogin";
    public String fingure_print_police_login_url="policesystem/psu/fingerprintlogin";
    public String fingure_print_medical_login_url="";
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

    public RequestBody createRequestBody(JSONObject jsonObject) {
        Log.d(TAG, "createRequestBody: " + jsonObject.toString());
        return RequestBody.create(MediaType.parse("application/json; charset=utf-8"),
                (jsonObject.toString()));

    }


    public Gson getGson() {
        return new Gson();
    }


    KProgressHUD kProgressHUD;

    public void showProgress(Activity activity) {
        kProgressHUD = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    public void hideProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }
}
