package com.example.authvertical.fragments;

import android.graphics.Color;
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

import com.example.authvertical.R;
import com.example.authvertical.db_and_models.get_police_challan_account.GetPayChallanAccount;
import com.example.authvertical.db_and_models.get_user_accounts.GetUserAccount;
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

public class TransferBalanceFragment extends BaseFragment {
    private static final String TAG = TransferBalanceFragment.class.getSimpleName();
    String title = null, ticketId = null;
    int fine;
    CallBack callBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    private static final String TRANSFER_BALANCE = "Transfer Balance";
    private static final String PAY_CHALLAN = "Pay Challan";
    @BindView(R.id.spinnerIBAN)
    HintSpinner spinnerIBAN;
    @BindView(R.id.tvIBAN)
    EditText tvIBAN;
    @BindView(R.id.tvAmout)
    EditText tvAmout;
    @BindView(R.id.tvComments)
    EditText tvComments;
    @BindView(R.id.btnTransfer)
    CardView btnTransfer;

    public static TransferBalanceFragment newInstance(String title, int fine, String ticketId) {
        TransferBalanceFragment fragment = new TransferBalanceFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("ticketId", ticketId);
        args.putInt("fine", fine);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        if (getArguments() != null) {
            title = getArguments().getString("title");
            fine = getArguments().getInt("fine");
            ticketId = getArguments().getString("ticketId");
        }
    }

    @Override
    public View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_transfer_balance, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        ButterKnife.bind(this, view);
        if (title.equalsIgnoreCase(TRANSFER_BALANCE)) {
            tvTitle.setText(TRANSFER_BALANCE);
            tvIBAN.setOnFocusChangeListener(new View.OnFocusChangeListener() {
                @Override
                public void onFocusChange(View view, boolean hasFocus) {
                    if (hasFocus && tvIBAN.getText().toString().length() >= 10) {
                        validateAccount(tvIBAN.getText().toString());
                    }
                }
            });
        } else if (title.equalsIgnoreCase(PAY_CHALLAN)) {
            tvTitle.setText(PAY_CHALLAN);
        }
        btnTransfer.setOnClickListener(this);


        getUserAccounts();
    }

    boolean isValidate = false;

    private void validateAccount(String iban_number) {
        appConstants.showProgress(getActivity());
        appConstants.refreshBody();
        appConstants.addHeader(appConstants.authorization_key, user.getToken());
        appConstants.addElements("accountNumber", iban_number);
        Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(),
                appConstants.validate_accountnumber, appConstants.createRequestBody());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                appConstants.hideProgress();
                if (!response.isSuccessful()) {
                    tvIBAN.setError("Invalid account number!");
                    tvIBAN.requestFocus();
                    isValidate = false;
                } else {
                    isValidate = true;
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                appConstants.hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getUserAccounts() {

        appConstants.showProgress(getActivity());
        appConstants.refreshBody();
        appConstants.addHeader(appConstants.authorization_key, user.getToken());
        Call<JsonElement> call = apiHelper.getRequest(appConstants.getHeaders(), appConstants.get_user_accounts);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONArray body = new JSONArray(response.body().toString());
                        Log.d(TAG, "onResponse: " + body.toString());
                        ArrayList<GetUserAccount> accounts = new Gson().fromJson(body.toString(),
                                new TypeToken<List<GetUserAccount>>() {
                                }.getType());
                        spinnerIBAN.setAdapter(new HintSpinnerAdapter<GetUserAccount>(getActivity(),
                                accounts, "Select Account") {
                            @Override
                            public String getLabelFor(GetUserAccount accounts) {
                                return accounts.getBranch().getName() + " - " + accounts.getAccountNumber();
                            }
                        });
                        setListener();
                        if (title.equalsIgnoreCase(PAY_CHALLAN)) {
                            updateView();
                        } else {
                            appConstants.hideProgress();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                appConstants.hideProgress();
                Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void setListener() {
        spinnerIBAN.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
                try {
                    JSONObject jsonObject = new JSONObject(item);
                    from_iban = jsonObject.optString("accountNumber");
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void updateView() {
        nonEditableField(tvIBAN);
        nonEditableField(tvAmout);
        getChallanAccountDetail();
    }

    private void getChallanAccountDetail() {
        appConstants.refreshBody();
        appConstants.addHeader(appConstants.authorization_key, user.getToken());
        appConstants.addElements(appConstants.organization, "Police");
        Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(), appConstants.get_account_by_organization,
                appConstants.createRequestBody());
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                appConstants.hideProgress();
                if (response.isSuccessful()) {
                    try {
                        JSONObject body = new JSONObject(response.body().toString());
                        GetPayChallanAccount data = new Gson().fromJson(body.toString(), GetPayChallanAccount.class);
                        updateFields(data);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {
                appConstants.hideProgress();
            }
        });
    }

    String from_iban = null, to_iban = null;

    private void updateFields(GetPayChallanAccount data) {
        tvIBAN.setText(data.getBranch().getBank().getName() + " - " + data.getBranch().getName());
        tvAmout.setText(String.valueOf(fine));
        to_iban = data.getAccountNumber();
    }

    private void nonEditableField(EditText editText) {
        editText.setFocusable(false);
        editText.setFocusableInTouchMode(false);
    }

    @Override
    public void onClick(View v) {
        if (title.equalsIgnoreCase(PAY_CHALLAN)) {
            if (from_iban != null) {
                appConstants.showProgress(getActivity());
                JSONObject mainRequest = new JSONObject();
                JSONObject innerRequest = new JSONObject();
                appConstants.addHeader(appConstants.authorization_key, user.getToken());
                try {
                    innerRequest.put("from", from_iban);
                    innerRequest.put("to", to_iban);
                    innerRequest.put("amount", String.valueOf(fine));
                    mainRequest.put("transactionInfo", innerRequest);
                    mainRequest.put("ticketId", ticketId);
                    Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(), appConstants.pay_challan_url
                            , appConstants.createRequestBody(mainRequest));
                    call.enqueue(new Callback<JsonElement>() {
                        @Override
                        public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                            appConstants.hideProgress();
                            if (response.isSuccessful()) {
                                getActivity().onBackPressed();
                                callBack.challanPaid(true);
                            }
                        }

                        @Override
                        public void onFailure(Call<JsonElement> call, Throwable t) {
                            appConstants.hideProgress();
                            Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                TextView errorText = (TextView) spinnerIBAN.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please select your account!");
                errorText.requestFocus();
            }
        } else if (title.equalsIgnoreCase(TRANSFER_BALANCE)) {
            if (from_iban == null) {
                TextView errorText = (TextView) spinnerIBAN.getSelectedView();
                errorText.setError("");
                errorText.setTextColor(Color.RED);//just to highlight that this is an error
                errorText.setText("Please select your account!");
                errorText.requestFocus();
            } else if (TextUtils.isEmpty(tvIBAN.getText().toString())) {
                tvIBAN.setError("Please enter IBAN number!");
                tvIBAN.requestFocus();
            } else if (isValidate) {
                tvIBAN.setError("Please enter a valid IBAN number!");
                tvIBAN.requestFocus();
            } else if (TextUtils.isEmpty(tvAmout.getText().toString())) {
                tvAmout.setError("Please enter amount!");
                tvAmout.requestFocus();
            } else {
                sendPayment();
            }
        }
    }

    private void sendPayment() {
        appConstants.showProgress(getActivity());
        JSONObject mainRequest = new JSONObject();
        JSONObject innerRequest = new JSONObject();
        appConstants.showProgress(getActivity());
        appConstants.addHeader(appConstants.authorization_key, user.getToken());
        try {
            innerRequest.put("from", from_iban);
            innerRequest.put("to", tvIBAN.getText().toString());
            innerRequest.put("amount", String.valueOf(fine));
            mainRequest.put("transactionInfo", innerRequest);
            Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(),
                    appConstants.transfer_ammount,
                    appConstants.createRequestBody(mainRequest));
            call.enqueue(new Callback<JsonElement>() {
                @Override
                public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                    appConstants.hideProgress();
                    if (response.isSuccessful()) {
                        callBack.paymentTransferred(true);
                    }
                }

                @Override
                public void onFailure(Call<JsonElement> call, Throwable t) {
                    appConstants.hideProgress();
                    Toast.makeText(getActivity(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public interface CallBack {
        void paymentTransferred(boolean isCompleted);

        void challanPaid(boolean isPaid);
    }

    public void setListener(CallBack callBack) {
        this.callBack = callBack;
    }
}
