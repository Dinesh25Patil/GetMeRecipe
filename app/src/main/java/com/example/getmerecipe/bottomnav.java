package com.example.getmerecipe;

import android.content.ClipData;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.example.getmerecipe.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class bottomnav extends Fragment {

    ActivityMainBinding binding;
    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bottomnav, container, false);
        bottomNavigationView = view.findViewById(R.id.bottomNavigationView);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
//        replaceFragment(new home_Page());
        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (item.getItemId() == R.id.home){
                    Navigation.findNavController(view).navigate(R.id.action_bottomnav_to_home_Page);
                    // replaceFragment(new home_Page());
                }
                if (item.getItemId() == R.id.search) {
                    Navigation.findNavController(view).navigate(R.id.action_bottomnav_to_search2);
                    //replaceFragment(new search());
              }if (item.getItemId() == R.id.account){
                    Navigation.findNavController(view).navigate(R.id.action_bottomnav_to_account2);
                    //replaceFragment(new account());
              }
                return true;
            }
        });

        return  view;
    }

//    private void replaceFragment(Fragment fragment) {
//        FragmentManager fragmentManager = getParentFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        fragmentTransaction.replace(R.id.frame_layout,fragment);
//        fragmentTransaction.commit();
//    }
}