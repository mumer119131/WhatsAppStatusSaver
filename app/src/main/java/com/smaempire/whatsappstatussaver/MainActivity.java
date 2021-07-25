package com.smaempire.whatsappstatussaver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView bottomNavigationView=findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                new FragmentStatus()).commit();

    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    Fragment selectedFragment= null;
                    switch (item.getItemId()){
                        case R.id.nav_status:
                            selectedFragment = new FragmentStatus();
                            Toast.makeText(MainActivity.this, "Set Status", Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.nav_downloads:
                            selectedFragment=new FragmentDownloads();
                            Toast.makeText(MainActivity.this, "Set Downloads", Toast.LENGTH_SHORT).show();
                            break;
                    }
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                            selectedFragment).commit();
                    return true;
                }
            };
}