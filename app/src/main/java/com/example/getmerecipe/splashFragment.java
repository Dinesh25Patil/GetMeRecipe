package com.example.getmerecipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firestore.v1.FirestoreGrpc;

public class splashFragment extends Fragment {
    View view;
    FirebaseAuth fAuth;
    FirebaseUser fUser;
    ProgressBar splash_pgbar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_splash, container, false);
        fAuth = FirebaseAuth.getInstance();
        fUser = fAuth.getCurrentUser();
        splash_pgbar = view.findViewById(R.id.splash_pgbar);
        splash_pgbar.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (fUser != null){
                    splash_pgbar.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_bottomnav);
                }else {
                    splash_pgbar.setVisibility(View.GONE);
                    Navigation.findNavController(view).navigate(R.id.action_splashFragment_to_login2);
                }
            }
        }, 3500);
       return view;
    }
}