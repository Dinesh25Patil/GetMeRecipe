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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Signup extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        TextView loginbtn;
        EditText name, email, phone, pass;
        Button signupbtn;
        ProgressBar pgbar;
        FirebaseAuth fAuth;
        FirebaseFirestore fStore;

        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        loginbtn = view.findViewById(R.id.gt_login);
        name = view.findViewById(R.id.fullname);
        email = view.findViewById(R.id.email);
        phone = view.findViewById(R.id.phone);
        pass = view.findViewById(R.id.pass);
        signupbtn = view.findViewById(R.id.signupbtn);
        pgbar = view.findViewById(R.id.pgbar);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        loginbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.action_signup2_to_login2);
            }
        });

        name.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String reg_name = name.getText().toString().trim();
                if(TextUtils.isEmpty(reg_name)){
                        name.setError("Please enter the name");
                }
            }
        });

        email.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String email_ptrn = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
                String reg_email = email.getText().toString().trim();

                if(!reg_email.matches(email_ptrn)){
                    email.setError("Please enter valid email");
                }
                if(TextUtils.isEmpty(reg_email)){
                    email.setError("Please enter email address");
                }
            }
        });

        phone.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
               String reg_phone = phone.getText().toString().toString();
               if(reg_phone.length() > 10 || reg_phone.length() < 10){
                   phone.setError("Phone no should have 10 numbers");
               }
            }
        });

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                String reg_pass = pass.getText().toString().trim();
                if ((reg_pass.length() < 6)){
                    pass.setError("Password should have <= 6 charachters");
                }
            }
        });

        signupbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register_user();
            }

            //Firebase code to register new user
            public void register_user() {

                String reg_email = email.getText().toString().trim();
                String reg_name = name.getText().toString().trim();
                String reg_pass = pass.getText().toString().trim();
                String reg_phone = phone.getText().toString().trim();

                pgbar.setVisibility(View.VISIBLE);

                fAuth.createUserWithEmailAndPassword(reg_email, reg_pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            fAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {

                                    String userId = fAuth.getCurrentUser().getUid();
                                    name.setText("");;
                                    email.setText("");;
                                    phone.setText("");;
                                    pass.setText("");;
                                    DocumentReference docref = fStore.collection("users").document(userId);
                                    Map<String, Object> users = new HashMap<>();
                                    users.put("name", reg_name);
                                    users.put("email", reg_email);
                                    users.put("phone", reg_phone);
                                    users.put("pass", reg_pass);

                                    docref.set(users).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            pgbar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), "Successfully Registered, We have sent you verification mail", Toast.LENGTH_SHORT).show();

                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            pgbar.setVisibility(View.GONE);
                                            Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();

                                        }
                                    });

                                }
                            });
                        }else{
                            Toast.makeText(getActivity(),"Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            pgbar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
        return view;
    }
}