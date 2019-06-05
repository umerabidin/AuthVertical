package com.example.authvertical.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.authvertical.R;
import com.example.authvertical.db_and_models.login_entity.LoginEntity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WelcomeScreen extends BaseFragment {

    @BindView(R.id.tvHelloText)
    TextView tvHelloText;

    @BindView(R.id.tvWelcomeText)
    TextView tvWelcomeText;

    @BindView(R.id.ivUserImage)
    ImageView ivUserImage;

    public static WelcomeScreen newInstance(/*String param1, String param2*/) {
        WelcomeScreen fragment = new WelcomeScreen();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
        return fragment;
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
        return inflater.inflate(R.layout.fragment_welcome_screen, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        LoginEntity loginEntity = appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, ""));
        tvHelloText.setText(loginEntity.getUser().getCitizen().getFirstName() + " " + loginEntity.getUser().getCitizen().getLastName());
        tvWelcomeText.setText("Welcome to " + loginEntity.getUser_role());
        Glide.with(getActivity())
                .load(Base64.decode(loginEntity.getUser().getCitizen().getPhoto().split(",")[1], Base64.DEFAULT))
                .asBitmap()
                .placeholder(R.drawable.user_image)
                .into(ivUserImage);
    }

    @Override
    public void onClick(View v) {

    }
}
