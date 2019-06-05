package com.example.authvertical.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.authvertical.R;

import butterknife.ButterKnife;

public class AddPatientVisitInfo extends Fragment {

    public static AddPatientVisitInfo newInstance(/*String param1 , String param2*/) {
        AddPatientVisitInfo fragment = new AddPatientVisitInfo();
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
        return inflater.inflate ( R.layout.fragment_add_patient_visit_info , container , false );
    }

    @Override
    public void onViewCreated(@NonNull View view , @Nullable Bundle savedInstanceState) {
        super.onViewCreated ( view , savedInstanceState );
        ButterKnife.bind ( this , view );
    }
}
