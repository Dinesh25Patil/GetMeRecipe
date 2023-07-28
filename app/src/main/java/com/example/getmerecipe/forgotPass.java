package com.example.getmerecipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;


public class forgotPass extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        EditText forg_Email;
        Button next;
        FirebaseAuth fAuth;
        ProgressBar pgbar_forg;

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_forgot_pass, container, false);

        forg_Email = view.findViewById(R.id.forg_email);
        next = view.findViewById(R.id.next);
        pgbar_forg = view.findViewById(R.id.pgbar_forg);
        fAuth = FirebaseAuth.getInstance();

        forg_Email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = forg_Email.getText().toString().trim();
                String email_ptrn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!email.matches(email_ptrn)){
                    forg_Email.setError("Please enter valid email");
                }
                if(TextUtils.isEmpty(email)){
                    forg_Email.setError("Please enter email");
                }
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pgbar_forg.setVisibility(View.VISIBLE);
                forget_pass();
            }

            // Firebase code for forgot password
            public void forget_pass() {
                String email = forg_Email.getText().toString().trim();
                fAuth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        pgbar_forg.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Password reset mail has been sent on " + email, Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pgbar_forg.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        return  view;
    }
}