package com.mojapl.mobile_app.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.mojapl.mobile_app.R;
import com.mojapl.mobile_app.main.adapters.ViewPagerAdapter;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class MainActivity extends AppCompatActivity {

    ViewPager mviewPager;
    ViewPagerAdapter mViewPagerAdapter;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initRealmDb();
        instance = this;
        setContentView(R.layout.activity_main);
        mviewPager = (ViewPager) findViewById(R.id.pager);
        mViewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        mviewPager.setAdapter(mViewPagerAdapter);
    }

    private void initRealmDb() {
        Realm.init(this);
        RealmConfiguration config = new RealmConfiguration.Builder().schemaVersion(2).build();
        Realm.setDefaultConfiguration(config);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.menu_settings) {
            intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
        } else if (id == R.id.menu_logout) {
            SharedPreferences preferences = this.getSharedPreferences("LoginData", MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear().commit();
            intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            this.finish();
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
