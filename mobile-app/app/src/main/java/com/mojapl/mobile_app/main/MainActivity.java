package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {

    ViewPager mviewPager;
    ViewPagerAdapter mViewPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mviewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mViewPagerAdapter);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.menu_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
}
