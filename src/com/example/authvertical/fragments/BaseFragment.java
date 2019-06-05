package com.example.authvertical.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.authvertical.db_and_models.database.AppDataBase;
import com.example.authvertical.network.APIHelper;
import com.example.authvertical.utils.AppConstants;
import com.example.authvertical.utils.DialogUtils;
import com.example.authvertical.utils.MyAppPref;
import com.kaopiz.kprogresshud.KProgressHUD;
import java.util.Objects;
import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment implements View.OnClickListener {
    DialogUtils dialogUtils;
    AppConstants appConstants;
    AppDataBase appDataBase;
    APIHelper apiHelper;
MyAppPref myAppPref;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appConstants = AppConstants.getInstance();
        apiHelper = APIHelper.getInstance();
        dialogUtils = DialogUtils.getInstance();
        appDataBase = AppDataBase.getAppDatabase(getActivity());
        myAppPref = MyAppPref.getInstance(getActivity());
    }

    public View onCreateView(@NonNull  LayoutInflater inflater, ViewGroup parent, Bundle savedInstanseState) {
        View view = provideYourFragmentView(inflater, parent, savedInstanseState);
        ButterKnife.bind(this, view);
        return view;
    }

    public abstract View provideYourFragmentView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState);
    KProgressHUD kProgressHUD;

    protected void showProgress() {
        kProgressHUD = KProgressHUD.create(Objects.requireNonNull(getActivity()))
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Please wait")
//                .setDetailsLabel("Downloading data")
                .setCancellable(true)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
    }

    protected void hideProgress() {
        if (kProgressHUD != null) {
            kProgressHUD.dismiss();
            kProgressHUD = null;
        }
    }

    protected void displayReaderNotFound() {
        android.app.AlertDialog.Builder alertDialogBuilder = new android.app.AlertDialog.Builder(getActivity());
        alertDialogBuilder.setTitle("Reader Not Found");
        alertDialogBuilder.setMessage("Plug in a reader and try again.").setCancelable(false).setPositiveButton("Ok",
                (dialog, id) -> Objects.requireNonNull(getActivity()).finish());

        android.app.AlertDialog alertDialog = alertDialogBuilder.create();
        if (!Objects.requireNonNull(getActivity()).isFinishing()) {
            alertDialog.show();
        }
    }
}
