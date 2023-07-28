package com.example.getmerecipe;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class account extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Button account_lgout;
        FirebaseAuth fAuth;

        // Inflate the layout for this fragment
       View view  = inflater.inflate(R.layout.fragment_account, container, false);
       account_lgout = view.findViewById(R.id.account_lgout);
       fAuth = FirebaseAuth.getInstance();

       account_lgout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               fAuth.signOut();
               Navigation.findNavController(view).navigate(R.id.action_account2_to_login2);
           }
       });
       return view;
    }
}