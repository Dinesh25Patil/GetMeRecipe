package com.example.getmerecipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class login extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView register, forgotpass;
        FirebaseAuth fAuth;
        EditText lgin_email, lgin_pass;
        Button login;
        ProgressBar pgbar;


        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        register = view.findViewById(R.id.registerbtn);
        lgin_pass = view.findViewById(R.id.lgin_pass);
        lgin_email = view.findViewById(R.id.lgin_email);
        login = view.findViewById(R.id.login_btn);
        forgotpass = view.findViewById(R.id.forgotpass);
        pgbar = view.findViewById(R.id.pgbar_lgin);

        fAuth = FirebaseAuth.getInstance();

        lgin_email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email = lgin_email.getText().toString().trim();
                String email_ptrn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                if(!email.matches(email_ptrn)){
                    lgin_email.setError("Please enter valid email");
                }
                if (TextUtils.isEmpty(email)){
                    lgin_email.setError("Email is required");
                }
            }
        });

        lgin_pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String pass = lgin_pass.getText().toString().trim();
                if (pass.isEmpty()){
                    lgin_pass.setError("Password should be <= 6");
                }
            }
        });

        // Navigate to forget password fragment
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_login2_to_forgotPass);
            }
        });

        // login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user_login();
            }

            // Firebase code to check email and password also to check that the user is email verified or not
            public void user_login() {
                String email  = lgin_email.getText().toString().trim();
                String pass  = lgin_pass.getText().toString().trim();

                pgbar.setVisibility(View.VISIBLE);
                fAuth.signInWithEmailAndPassword(email ,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        pgbar.setVisibility(View.GONE);
                        if (fAuth.getCurrentUser().isEmailVerified()){
                            pgbar.setVisibility(View.GONE);
                            Navigation.findNavController(view).navigate(R.id.action_login2_to_bottomnav);
                        }
                        else{
                            pgbar.setVisibility(View.GONE);
                            Toast.makeText(getActivity(), "Please verfiy email address", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        pgbar.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), "Error " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        // Go back to sign up page
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_login2_to_signup2);
            }
        });
        return view;
    }
}