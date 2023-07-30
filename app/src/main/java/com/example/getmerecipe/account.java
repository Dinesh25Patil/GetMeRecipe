package com.example.getmerecipe;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class account extends Fragment {
    Button account_lgout;
    TextView account_name, account_email, account_phone;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    FirebaseUser fUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        // Inflate the layout for this fragment
       View view  = inflater.inflate(R.layout.fragment_account, container, false);

       account_lgout = view.findViewById(R.id.account_lgout);
       account_name = view.findViewById(R.id.account_name);
       account_email = view.findViewById(R.id.account_email);
       account_phone = view.findViewById(R.id.account_phone);

       fAuth = FirebaseAuth.getInstance();
       fStore = FirebaseFirestore.getInstance();
       fUser = fAuth.getCurrentUser();

       // calling method to get the data from database
       getCurretuserData();

       account_lgout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               fAuth.signOut();
               Intent i = new Intent(getActivity(), MainActivity.class);
               startActivity(i);
           }
       });
       return view;
    }

    private void getCurretuserData() {
        DocumentReference docref = fStore.collection("users").document(fUser.getUid());
        docref.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.exists()){
                    account_name.setText(documentSnapshot.getString("name"));
                    account_email.setText(documentSnapshot.getString("email"));
                    account_phone.setText(documentSnapshot.getString("phone"));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}