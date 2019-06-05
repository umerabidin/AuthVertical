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
import com.example.authvertical.utils.DropDownItems;
import com.example.authvertical.utils.Gender;
import com.jaiselrahman.hintspinner.HintSpinner;
import com.jaiselrahman.hintspinner.HintSpinnerAdapter;

public class AddNewDoctor extends Fragment implements AdapterView.OnItemSelectedListener {

    private static final String TAG = AddNewDoctor.class.getSimpleName();
    HintSpinner genderSpinner, fieldDomainSpinner;

    public static AddNewDoctor newInstance(/*String param1 , String param2*/) {
        AddNewDoctor fragment = new AddNewDoctor();
//        Bundle args = new Bundle ();
//        args.putString ( ARG_PARAM1 , param1 );
//        args.putString ( ARG_PARAM2 , param2 );
//        fragment.setArguments ( args );
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments () != null) {
//            mParam1 = getArguments ().getString ( ARG_PARAM1 );
//            mParam2 = getArguments ().getString ( ARG_PARAM2 );
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_add_new_doctor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        genderSpinner = view.findViewById(R.id.genderSpinner);
        fieldDomainSpinner = view.findViewById(R.id.fieldDomainSpinner);
        genderSpinner.setAdapter(new HintSpinnerAdapter<Gender>(getActivity(),
                DropDownItems.getInstance().getGenders(), "Select Gender") {
            @Override
            public String getLabelFor(Gender roles) {
                return roles.getName();
            }
        });
        fieldDomainSpinner.setAdapter(new HintSpinnerAdapter<Gender>(getActivity(),
                DropDownItems.getInstance().getGenders(), "Select Gender") {
            @Override
            public String getLabelFor(Gender roles) {
                return roles.getName();
            }
        });
        genderSpinner.setOnItemSelectedListener(this);
        fieldDomainSpinner.setOnItemSelectedListener(this);

    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.d(TAG, "onItemClick: " + parent.getItemAtPosition(position).toString());
        switch (view.getId()) {
            case R.id.genderSpinner:
//        EventBus.getDefault().postSticky(new RoleEvent(Integer.parseInt(parent.getItemAtPosition(position).toString())));

                break;
            case R.id.fieldDomainSpinner:
//        EventBus.getDefault().postSticky(new RoleEvent(Integer.parseInt(parent.getItemAtPosition(position).toString())));

                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
