package com.tkmsoft.akarat.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tkmsoft.akarat.R;
import com.tkmsoft.akarat.fragment.main.home.HomeFragment;
import com.tkmsoft.akarat.fragment.main.ProfileFragment;
import com.tkmsoft.akarat.fragment.main.SubCategFragment;
import com.tkmsoft.akarat.interfaces.IOnBackPressed;
import com.tkmsoft.akarat.interfaces.MainViewCallBack;
import com.tkmsoft.akarat.fragment.main.navfragment.ContactUsFragment;
import com.tkmsoft.akarat.fragment.main.navfragment.OfferFragment;
import com.tkmsoft.akarat.fragment.main.navfragment.OrderFragment;
import com.tkmsoft.akarat.fragment.main.navfragment.WhoUsFragment;
import com.tkmsoft.akarat.util.ListSharePreference;
import com.tkmsoft.akarat.util.MoveToFragment;
import com.tkmsoft.akarat.util.MyContextWrapper;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MainViewCallBack {
    ListSharePreference.Set setSharedPreference;
    ListSharePreference.Get getSharedPreference;
    Boolean mIsLogged;
    Locale locale;
    String mId, mName, mEmail, mMobile, mImage;
    CircleImageView mNavCircleImageView;
    TextView mNavNameTextView, mNavEmailTextView;
    TextView textView;
    ImageButton filter;
    NavigationView navigationView;
    AlertDialog.Builder alertDialogBuilder;
    String api_token;
    DrawerLayout drawer;
    MoveToFragment moveFragment ;

    private IOnBackPressed onBackPressedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setLayoutLanguage();
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mIsLogged = getSharedPreference.getIsLogged();
        textView = findViewById(R.id.toolbar_title);
        filter = findViewById(R.id.toolbar_filter_button);
        moveFragment = new MoveToFragment(this);
        initUi(toolbar);
        getUserInfo();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    private void getUserInfo() {

        mId = getSharedPreference.getUId();
        mName = getSharedPreference.getname();
        mEmail = getSharedPreference.getemail();
        mMobile = getSharedPreference.getmobil();
        mImage = getSharedPreference.getimage();

        mNavNameTextView.setText(mName);
        mNavEmailTextView.setText(mEmail);
        Picasso.get()
                .load(mImage)
                .error(R.drawable.profile_placeholder)
                .placeholder(R.drawable.profile_placeholder)
                .into(mNavCircleImageView);
        Intent intent = getIntent();
       int key = intent.getIntExtra("key", 0);
        if (key == 1) {
            Bundle bundle = new Bundle();
            bundle.putString("type", intent.getStringExtra("type"));
            bundle.putString("city", intent.getStringExtra("city"));
            bundle.putString("district", intent.getStringExtra("district"));
            bundle.putString("bathRoom", intent.getStringExtra("bathRoom"));
            bundle.putString("rooms", intent.getStringExtra("rooms"));
            bundle.putString("garage", intent.getStringExtra("garage"));

            bundle.putBoolean("filterOn", true);
            SubCategFragment detailsFragment = new SubCategFragment();
            detailsFragment.setArguments(bundle);
            moveFragment.moveInMain(detailsFragment);

        } else {
            moveFragment.moveInMain(new HomeFragment());
        }
    }

    private void initUi(Toolbar toolbar) {
        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View header = navigationView.getHeaderView(0);
        mNavCircleImageView = header.findViewById(R.id.nav_header_imageView);
        mNavNameTextView = header.findViewById(R.id.nav_header_name);
        mNavEmailTextView = header.findViewById(R.id.nav_header_email);


    }

    private void setLayoutLanguage() {
        if (getLang().equals("ar"))
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        else
            getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        setSharedPreference = new ListSharePreference.Set(newBase);
        getSharedPreference = new ListSharePreference.Get(newBase);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, getLang()));
    }

    private String getLang() {
        return getSharedPreference.getLanguage();
    }

    @Override
    public void onBackPressed() {
        if (onBackPressedListener != null)
            onBackPressedListener.onBackPressed();
        else
            super.onBackPressed();
    }

    public void setOnBackPressedListener(IOnBackPressed onBackPressedListener) {
        this.onBackPressedListener = onBackPressedListener;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            getFragmentManager().beginTransaction().replace(R.id.main_frame, new ProfileFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_request) {
            getFragmentManager().beginTransaction().replace(R.id.main_frame, new OrderFragment()).addToBackStack(null).commit();
        } else if (id == R.id.nav_home) {
            getFragmentManager().beginTransaction().replace(R.id.main_frame, new HomeFragment()).commit();
        }
        else if (id == R.id.nav_offer) {
            try {
                getFragmentManager().beginTransaction().replace(R.id.main_frame, new OfferFragment()).addToBackStack(null).commit();
            } catch (Exception ignore) {}
        }
        else if (id == R.id.nav_whoUs) {
            getFragmentManager().beginTransaction().replace(R.id.main_frame, new WhoUsFragment()).addToBackStack(null).commit();
        }
        else if (id == R.id.nav_contact_us) {

            getFragmentManager().beginTransaction().replace(R.id.main_frame, new ContactUsFragment()).addToBackStack(null).commit();
        } else if (id == R.id.logout_nav) {
            if (mIsLogged) {
                // setLogout();
                setSharedPreference.setIsLogged(false);
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            } else {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    @Override
    public void setCallBackTitle(String title) {
        textView.setText(title);
    }

    @Override
    public void setFilterBtn(boolean isVisible) {
        if (isVisible) {
            filter.setVisibility(View.VISIBLE);
        } else {
            filter.setVisibility(View.GONE);
        }
    }

}
