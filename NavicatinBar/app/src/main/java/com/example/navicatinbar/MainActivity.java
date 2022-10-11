package com.example.navicatinbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayDeque;
import java.util.Deque;

public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;
    Deque<Integer> integerDeque;
    boolean flag = true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        manHinhKhiKhoiDong();
        khiAnVaoCacManHinhKhac();
    }

    private void khiAnVaoCacManHinhKhac() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();//lay id
                if (integerDeque.contains(id)){//check condition
                    if (id == R.id.dashboardId){
                        if (integerDeque.size()!=1){
                            if (flag){
                                integerDeque.addFirst(R.id.dashboardId);
                                flag = false;
                            }
                        }
                    }
                    integerDeque.remove(id);
                }
                integerDeque.push(id);
                loadFragmen(getFragment(item.getItemId()));
                return true;
            }
        });
    }

    private Fragment getFragment(int itemId) {
        switch (itemId){
            case R.id.homeId:
                bottomNavigationView.getMenu().getItem(0).setChecked(true);
                return new HomeFragment();
            case R.id.dashboardId:
                bottomNavigationView.getMenu().getItem(1).setChecked(true);
                return new DashboardFragment();
            case R.id.notificationsId:
                bottomNavigationView.getMenu().getItem(2).setChecked(true);
                return new NotiFragment();
        }
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        return new DashboardFragment();
    }

    private void manHinhKhiKhoiDong() {
        loadFragmen(new DashboardFragment());
        bottomNavigationView.setSelectedItemId(R.id.dashboardId);
    }

    private void loadFragmen(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentId,fragment,fragment.getClass()
                        .getSimpleName())
                .commit();
    }

    private void anhXa() {
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        integerDeque = new ArrayDeque<>(3);
        integerDeque.push(R.id.homeId);
    }
}