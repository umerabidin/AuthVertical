package com.example.authvertical.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.authvertical.R;
import com.example.authvertical.db_and_models.database.AppDataBase;
import com.example.authvertical.network.APIHelper;
import com.example.authvertical.utils.AppConstants;
import com.example.authvertical.utils.MyAppPref;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VerifyChallanDetailsSheet extends BottomSheetDialogFragment implements View.OnClickListener {
    private static final String TAG = VerifyChallanDetailsSheet.class.getSimpleName();
    @BindView(R.id.viewIssueChallanDetail)
    RecyclerView recyclerView;
    @BindView(R.id.btnConfirm)
    CardView btnConfirm;

    HashMap<String, String> adapterList;
    HashMap<String, String> requestList;

    public static VerifyChallanDetailsSheet newInstance(String adapterList, String requestList) {
        VerifyChallanDetailsSheet fragment = new VerifyChallanDetailsSheet();
        Bundle args = new Bundle();
        args.putString("adapterList", adapterList);
        args.putString("requestList", requestList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            Type type = new TypeToken<HashMap<String, String>>() {
            }.getType();
            adapterList = new Gson().fromJson(getArguments().getString("adapterList"), type);
            requestList = new Gson().fromJson(getArguments().getString("requestList"), type);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_verify_challan_detail, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        btnConfirm.setOnClickListener(this);

        VerifyChallanDetailAdapter mAdapter = new VerifyChallanDetailAdapter(getActivity(), adapterList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        APIHelper apiHelper = APIHelper.getInstance();
        AppConstants appConstants = AppConstants.getInstance();
        MyAppPref myAppPref = MyAppPref.getInstance(getActivity());
        AppDataBase appDataBase = AppDataBase.getAppDatabase(getActivity());
        appConstants.addHeader("Authorization", "Bearer " + appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, "")).getToken());

        Call<JsonElement> call = apiHelper.postRequest(appConstants.getHeaders(), appConstants.issue_ticket_by_nin,
                appConstants.createRequestBody(requestList));
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                if (response.isSuccessful()) {
                    try {
                        JSONObject body = new JSONObject(response.body().toString());
                        Log.d(TAG, "onResponse: " + body.toString());

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<JsonElement> call, Throwable t) {

            }
        });
    }

    class VerifyChallanDetailAdapter extends RecyclerView.Adapter<VerifyChallanDetailAdapter.ViewHolder> {

        Context context;
        HashMap<String, String> myMap;
        String[] keys;


        VerifyChallanDetailAdapter(Context context, HashMap<String, String> myMap) {
            this.context = context;
            this.myMap = myMap;

            keys = this.myMap.keySet().toArray(new String[0]);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.chall_detail_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
            viewHolder.tvDetails.setText(myMap.get(keys[position]));
            viewHolder.tvTitle.setText(keys[position]);
        }

        @Override
        public int getItemCount() {
            return myMap.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tvTitle, tvDetails;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                tvTitle = itemView.findViewById(R.id.tvTitle);
                tvDetails = itemView.findViewById(R.id.tvDetails);
            }

        }
    }
}
