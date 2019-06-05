package com.example.authvertical.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.example.authvertical.R;
import com.example.authvertical.utils.BloodGroup;
import com.example.authvertical.utils.DropDownItems;
import com.example.authvertical.utils.Gender;
import com.example.authvertical.utils.Martial;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;


public class AddPoliceOfficer extends Fragment implements  AdapterView.OnItemSelectedListener {

    private static final String TAG = AddPoliceOfficer.class.getSimpleName();
    @BindView(R.id.genderSpinner)
    HintSpinner genderSpinner;
    @BindView(R.id.martialSpinner)
    HintSpinner martialSpinner;
    @BindView(R.id.bloodGroupSpinner)
    HintSpinner bloodGroupSpinner;

    public static AddPoliceOfficer newInstance(/*String param1 , String param2*/) {
        AddPoliceOfficer fragment = new AddPoliceOfficer();
//        Bundle args = new Bundle ();
//        args.putString ( ARG_PARAM1 , param1 );
//        args.putString ( ARG_PARAM2 , param2 );
//        fragment.setArguments ( args );
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
//        if (getArguments () != null) {
//            mParam1 = getArguments ().getString ( ARG_PARAM1 );
//            mParam2 = getArguments ().getString ( ARG_PARAM2 );
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container ,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate ( R.layout.fragment_add_police_officer , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        ButterKnife.bind(this, view);

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
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + parent.getItemAtPosition(position).toString());
        switch (view.getId()) {
            case R.id.genderSpinner:
//        EventBus.getDefault().postSticky(new RoleEvent(Integer.parseInt(parent.getItemAtPosition(position).toString())));

                break;
            case R.id.martialSpinner:
//        EventBus.getDefault().postSticky(new RoleEvent(Integer.parseInt(parent.getItemAtPosition(position).toString())));

                break;
            case R.id.bloodGroupSpinner:
//        EventBus.getDefault().postSticky(new RoleEvent(Integer.parseInt(parent.getItemAtPosition(position).toString())));

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}

