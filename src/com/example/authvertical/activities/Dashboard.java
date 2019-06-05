package com.example.authvertical.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.authvertical.R;
import com.example.authvertical.adapter.DrawerAdapter;
import com.example.authvertical.db_and_models.login_entity.LoginEntity;
import com.example.authvertical.fragments.AddCitizen;
import com.example.authvertical.fragments.AddNewDoctor;
import com.example.authvertical.fragments.AddPatientVisitInfo;
import com.example.authvertical.fragments.AddPoliceOfficer;
import com.example.authvertical.fragments.IssueTrafficChallan;
import com.example.authvertical.fragments.ViewCitizen;
import com.example.authvertical.fragments.ViewPatientHistory;
import com.example.authvertical.fragments.WelcomeScreen;
import com.example.authvertical.utils.RoleEvent;
import com.example.authvertical.utils.drawer_items.TabsItems;
import com.google.gson.Gson;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Dashboard extends BaseActivity implements DrawerAdapter.DrawerItemClick, View.OnClickListener {
    private static final String TAG_HOME = "home";
    private static final String TAG_MAIN_MENU = "main_menu";
    private static final String TAG_PURCHACES = "purchase";
    private static final String TAG = Dashboard.class.getSimpleName();
    public static int navItemIndex = 0;
    public static String CURRENT_TAG = TAG_HOME;
    // index to identify current nav menu item
    DrawerLayout drawer;
    RecyclerView drawerList;
    ArrayList<String> drawerItems = new ArrayList<>();
    DrawerAdapter mAdapter;
    private Handler mHandler = new Handler();
    RoleEvent roleEvent;
    TextView tvLogout;

    @BindView(R.id.user_image)
    ImageView user_image;

    @BindView(R.id.tvUserName)
    TextView tvUserName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        ButterKnife.bind(this);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = findViewById(R.id.drawer_layout);
        tvLogout = findViewById(R.id.tvLogout);
        drawerList = findViewById(R.id.drawer_list);
        mAdapter = new DrawerAdapter(this, new ArrayList<>(), this);
        drawerList.setAdapter(mAdapter);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        tvLogout.setOnClickListener(this);
        LoginEntity loginEntity = appDataBase.getDao().getUser(myAppPref.getPref(appConstants.user_email, ""));
        tvUserName.setText(loginEntity.getUser().getCitizen().getFirstName() + " " + loginEntity.getUser().getCitizen().getLastName());
        Glide.with(this)
                .load(Base64.decode(loginEntity.getUser().getCitizen().getPhoto().split(",")[1], Base64.DEFAULT))
                .asBitmap()
                .placeholder(R.drawable.user_image)
                .into(user_image);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            RoleEvent roleEvent = new Gson().fromJson(extras.getString("data"), RoleEvent.class);
            if (roleEvent != null) {
                callHomeFragment(roleEvent);
            }
        }


    }

    private void callHomeFragment(RoleEvent roleEvent) {
        this.roleEvent = roleEvent;
        navItemIndex = roleEvent.getRole();
        drawerItems = new TabsItems().getTabs(navItemIndex);
        mAdapter.updateAdapter(drawerItems, roleEvent);
        CURRENT_TAG = TAG_HOME;
        loadHomeFragment();

    }

    private void loadHomeFragment() {


        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            return;
        }

        Runnable mPendingRunnable = () -> {
            // update the main content by replacing fragments
            Fragment fragment = getHomeFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
            fragmentTransaction.replace(R.id.main_container, fragment);
            fragmentTransaction.commitAllowingStateLoss();
        };


        mHandler.post(mPendingRunnable);
        drawer.closeDrawers();

    }

    private Fragment getHomeFragment() {
//        switch (navItemIndex) {
//            case 1:
//                return AddCitizen.newInstance();
//            default:
        return WelcomeScreen.newInstance();
//        }
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public void onClickItem(RoleEvent roleEvent, int position) {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }


//        roles.add(new UserRoles("1", "Civic"));
//        roles.add(new UserRoles("2", "Police System"));
//        roles.add(new UserRoles("3", "Medical System"));
//        roles.add(new UserRoles("4", "Retailer System"));
//        roles.add(new UserRoles("5", "CitizenInfo Portal"));


        if (roleEvent.getRole() == 1) {
            if (position == 1) {
                showFragment(AddCitizen.newInstance());
            } else if (position == 2) {
                showFragment(ViewCitizen.newInstance());
            } else if (position == 3) {
//                showFragment(BirthCertificate.newInstance());
            } else if (position == 4) {
//                showFragment(MarriageCertificate.newInstance());
            } else if (position == 5) {
//                showFragment(DeathCertificate.newInstance());
            }
        } else if (roleEvent.getRole() == 2) {
            if (position == 0) {
                showFragment(AddPoliceOfficer.newInstance());
            } else if (position == 1) {
                showFragment(ViewCitizen.newInstance());
            } else if (position == 2) {
                showFragment(IssueTrafficChallan.newInstance());
            }
        } else if (roleEvent.getRole() == 3) {
            if (position == 0) {
                showFragment(new AddNewDoctor());
            } else if (position == 1) {
                showFragment(AddPatientVisitInfo.newInstance());
            } else if (position == 2) {
                showFragment(ViewPatientHistory.newInstance());
            }

        }

//        if (roleEvent.getRole() == 1) {
//            if (position == 0) {
//                showFragment(new AddNewDoctor());
//            } else if (position == 1) {
//                showFragment(AddPatientVisitInfo.newInstance());
//            } else if (position == 2) {
//                showFragment(ViewPatientHistory.newInstance());
//            }
//
//        } else if (roleEvent.getRole() == 2) {
//            if (position == 0) {
//                showFragment(AddPoliceOfficer.newInstance());
//            } else if (position == 1) {
//                showFragment(IssueTrafficChallan.newInstance());
//            }
//
//        } else if (roleEvent.getRole() == 3) {
//            if (position == 0) {
//                showFragment(AddCitizen.newInstance());
//            }
//
//        }

    }


    private void showFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//            fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
//                    android.R.anim.fade_out);
        fragmentTransaction.replace(R.id.main_container, fragment);
        fragmentTransaction.commitAllowingStateLoss();

    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(Dashboard.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }
}
