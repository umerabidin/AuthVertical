package com.example.authvertical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.authvertical.R;

public class SelectRoleActivity extends BaseActivity implements View.OnClickListener {

    private TextView tvDoctor;
    private TextView tvPolice;
    private TextView tvCitizen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_select_role );


        tvDoctor = findViewById ( R.id.tvDoctor );
        tvPolice = findViewById ( R.id.tvPolice );
        tvCitizen = findViewById ( R.id.tvCitizen );

        tvDoctor.setOnClickListener ( this );
        tvPolice.setOnClickListener ( this );
        tvCitizen.setOnClickListener ( this );
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.tvDoctor:
//                EventBus.getDefault ().postSticky ( new RoleEvent ( 1 ) );
                break;
            case R.id.tvPolice:
//                EventBus.getDefault ().postSticky ( new RoleEvent ( 2 ) );
                break;
            case R.id.tvCitizen:
//                EventBus.getDefault ().postSticky ( new RoleEvent ( 3 ) );
                break;
        }
        Intent intent = new Intent ( SelectRoleActivity.this , LoginActivity.class );
        startActivity ( intent );
    }
}
