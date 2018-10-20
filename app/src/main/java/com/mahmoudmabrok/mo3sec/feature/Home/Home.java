package com.mahmoudmabrok.mo3sec.feature.Home;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.mahmoudmabrok.mo3sec.R;
import com.mahmoudmabrok.mo3sec.feature.PublicFragment.PublicFragmet;
import com.mahmoudmabrok.mo3sec.feature.SecretFragment.SecretFragment;
import com.mahmoudmabrok.mo3sec.feature.homeFragment.HomeFragment;
import com.tjeannin.apprate.AppRate;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Home extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.nav_view)
    NavigationView navView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    private FragmentManager manager;
    private FragmentTransaction transition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navView.setNavigationItemSelectedListener(this);
        manager = getSupportFragmentManager();

        openSymmtric();

        new AppRate(this)
                .setMinDaysUntilPrompt(3)
                .setMinLaunchesUntilPrompt(10)
                .setShowIfAppHasCrashed(false)
                .init();

    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.action_home:
                openHome();
                break;
            case R.id.nav_symmetric:
                openSymmtric();
                break;
         /*   case R.id.nav_asymmetric:
                openAsymmetric();
                break;*/
            case R.id.action_video:
                openVideos();
                break;
            case R.id.action_crack:
                openCrack();
                break;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openCrack() {
        transition = manager.beginTransaction();
        HomeFragment fragmet = new HomeFragment();
        transition.replace(R.id.homeContainer, fragmet).commit();


    }

    private void openVideos() {
        transition = manager.beginTransaction();
        HomeFragment fragmet = new HomeFragment();
        transition.replace(R.id.homeContainer, fragmet).commit();


    }

    private void openHome() {
        transition = manager.beginTransaction();
        HomeFragment fragmet = new HomeFragment();
        transition.replace(R.id.homeContainer, fragmet).commit();

    }

    private void openAsymmetric() {
        transition = manager.beginTransaction();
        PublicFragmet fragmet = new PublicFragmet();
        transition.replace(R.id.homeContainer, fragmet).commit();
    }

    private void openSymmtric() {
        transition = manager.beginTransaction();
        SecretFragment frament = new SecretFragment();
        transition.replace(R.id.homeContainer, frament).commit();
    }
}
