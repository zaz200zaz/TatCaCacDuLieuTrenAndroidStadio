package com.example.fragmentinternactivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.viewmodel.CreationExtras;

public class MainFragment extends Fragment implements View.OnClickListener{
    private Button fragment_btn_1;
    private Button fragment_btn_2;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        fragment_btn_1 = (Button) view.findViewById(R.id.fragment_button_1);
        fragment_btn_2 = (Button) view.findViewById(R.id.fragment_button_2);
        fragment_btn_1.setOnClickListener(this);
        fragment_btn_2.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.fragment_button_1:
                Fragment fragment1 = new Fragment1();
                moveToFragment(fragment1);
                break;
            case R.id.fragment_button_2:
                Fragment fragment2 = new Fragment2();
                moveToFragment(fragment2);
                break;
        }
    }
    private void moveToFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getParentFragmentManager().beginTransaction();
//        getSupportFragmentManager().beginTransaction()
        fragmentTransaction.replace(R.id.fragment_frame, fragment, fragment.getClass().getSimpleName()).addToBackStack(null).commit();

    }

    @NonNull
    @Override
    public CreationExtras getDefaultViewModelCreationExtras() {
        return super.getDefaultViewModelCreationExtras();
    }

    private class Fragment1 extends Fragment {
    }

    private class Fragment2 extends Fragment {
    }
}
