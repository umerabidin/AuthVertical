package com.example.authvertical.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authvertical.R;
import com.example.authvertical.db_and_models.get_user_ticket.GetUserTicket;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewChallans extends BaseFragment {

    private static final String TAG = ViewChallans.class.getSimpleName();
    @BindView(R.id.viewChallanList)
    RecyclerView recyclerView;
    ViewChallansCallback viewChallansCallback;

    public void challanPaid() {
        getChallans();
    }

    ChallansListAdapter mAdapter;

    public interface ViewChallansCallback {
        void addFragment(String fragment, int fine, String ticketId);
    }

    public static ViewChallans newInstance(/*String param1, String param2*/) {
        ViewChallans fragment = new ViewChallans();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
    }

    public void setListener(ViewChallansCallback callback) {
        this.viewChallansCallback = callback;
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
        return inflater.inflate(R.layout.fragment_view_challans, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        mAdapter = new ChallansListAdapter(getActivity(), new ArrayList<GetUserTicket>());
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        getChallans();
    }

    private void getChallans() {
        appConstants.showProgress(getActivity());
        appConstants.refreshBody();
        appConstants.addHeader(appConstants.authorization_key,
                user.getToken());
        Call<JsonElement> call = apiHelper.getRequest(appConstants.getHeaders(), appConstants.get_challans);
        call.enqueue(new Callback<JsonElement>() {
            @Override
            public void onResponse(Call<JsonElement> call, Response<JsonElement> response) {
                appConstants.hideProgress();
                if (response.isSuccessful()) {
                    try {
                        JSONArray body = new JSONArray(response.body().toString());
                        ArrayList<GetUserTicket> challans = new Gson().fromJson(body.toString(),
                                new TypeToken<List<GetUserTicket>>() {
                                }.getType());
                        mAdapter.updateAdapter(challans);
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

    @Override
    public void onClick(View v) {

    }


    class ChallansListAdapter extends RecyclerView.Adapter<ChallansListAdapter.ViewHolder> {
        Activity activity;

        ArrayList<GetUserTicket> data;

        ChallansListAdapter(Activity activity, ArrayList<GetUserTicket> data) {
            this.activity = activity;
            this.data = data;
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.challan_list_item, viewGroup, false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            System.out.println(data.get(i).getDate().toString());
            viewHolder.btnClaim.setOnClickListener(v -> {
//                viewChallansCallback.addFragment(appConstants.claim_fragment, data.get(i).getViolation().getFine(),
//                        data.get(i).getId());
            });
            viewHolder.btnPayChallan.setOnClickListener(v -> {
                viewChallansCallback.addFragment(appConstants.pay_challan_fragment, data.get(i).getViolation().getFine()
                        , data.get(i).getId());
            });
            viewHolder.tvReason.setText("Violation : " + data.get(i).getViolation().getName());
            viewHolder.tvDate.setText("Date : " + data.get(i).getFormatedDate());
            viewHolder.tvFine.setText("Fine : " + data.get(i).getViolation().getFine());
            if (data.get(i).getStatus().equalsIgnoreCase("pending")) {
                viewHolder.tvStatus.setVisibility(View.GONE);
            } else {
                viewHolder.tvStatus.setText("Status : PAID");
                viewHolder.tvStatus.setVisibility(View.VISIBLE);
                viewHolder.liButtons.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        void updateAdapter(ArrayList<GetUserTicket> challans) {
            if (data != null) {
                data.clear();
                data.addAll(challans);
            }
            notifyDataSetChanged();
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            CardView btnClaim, btnPayChallan;
            private TextView tvReason;
            private TextView tvFine;
            private TextView tvDate;
            private TextView tvStatus;
            private LinearLayout liButtons;

            ViewHolder(@NonNull View itemView) {
                super(itemView);
                btnPayChallan = itemView.findViewById(R.id.btnPayChallan);
                btnClaim = itemView.findViewById(R.id.btnClaim);
                tvReason = itemView.findViewById(R.id.tvReason);
                tvFine = itemView.findViewById(R.id.tvFine);
                tvDate = itemView.findViewById(R.id.tvDate);
                tvStatus = itemView.findViewById(R.id.tvStatus);
                liButtons = itemView.findViewById(R.id.liButtons);
            }
        }
    }
}
