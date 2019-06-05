package com.example.authvertical.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.authvertical.db_and_models.database.AppDataBase;
import com.example.authvertical.network.APIHelper;
import com.example.authvertical.utils.AppConstants;
import com.example.authvertical.utils.MyAppPref;
import com.kaopiz.kprogresshud.KProgressHUD;


public abstract class BaseActivity extends AppCompatActivity implements View.OnClickListener {
   protected MyAppPref myAppPref;
    protected APIHelper apiHelper;
    protected AppConstants appConstants;
    protected AppDataBase appDataBase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        appDataBase = AppDataBase.getAppDatabase(this);
        apiHelper = APIHelper.getInstance();
        appConstants = AppConstants.getInstance();

        myAppPref = MyAppPref.getInstance ( this );
    }


    KProgressHUD kProgressHUD;

    protected void showProgress() {
        kProgressHUD = KProgressHUD.create(BaseActivity.this)
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
}
