package com.example.oderingfood;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Bottomnavigation extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    HomeFragment homeFragment = new HomeFragment();
    TablesActivity orderFragment = new TablesActivity();
    FragmentNotification noticeFragment = new FragmentNotification();
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottomnavigation);
        bottomNavigationView=findViewById(R.id.buttom_navigation);
        //getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.home:
                            getSupportFragmentManager().beginTransaction().replace(R.id.container,homeFragment).commit();
                        return true;
                    case R.id.booking:
//                              getSupportFragmentManager().beginTransaction().replace(R.id.container,noticeFragment).commit();
                        return true;
                    case R.id.order:
                              getSupportFragmentManager().beginTransaction().replace(R.id.container,orderFragment).commit();
                        return true;
                    case R.id.employee:
                        //      getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;
                    case R.id.menu:
                        //      getSupportFragmentManager().beginTransaction().replace(R.id.container,notificationFragment).commit();
                        return true;

                }



                return false;
            }
        });
        toolbar = findViewById(R.id.bna_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbar_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.notification:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,noticeFragment).commit();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}