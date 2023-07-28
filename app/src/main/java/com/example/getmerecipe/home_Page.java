package com.example.getmerecipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getmerecipe.Adapters.RandomRecipeAdapter;
import com.example.getmerecipe.Listeners.RandomRecipeResponseListener;
import com.example.getmerecipe.Model.RandomRecipeAPIResponse;
import com.google.firebase.auth.FirebaseAuth;


public class home_Page extends Fragment {

//    TextView textView;
//    FirebaseAuth fAuth;
    Context context;
    ProgressBar pgBar_home;
    RecyclerView recyclerView;
    RequestManager requestManager;
    RandomRecipeAdapter randomRecipeAdapter;
    View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
      view = inflater.inflate(R.layout.fragment_home__page, container, false);

      pgBar_home = view.findViewById(R.id.pgbar_home);

      requestManager = new RequestManager(context);
      requestManager.getRandomRecipe(randomRecipeResponseListener);
      pgBar_home.setVisibility(View.VISIBLE);


     // textView = view.findViewById(R.id.texview);
      //fAuth = FirebaseAuth.getInstance();

//      textView.setOnClickListener(new View.OnClickListener() {
//          @Override
//          public void onClick(View view) {
//              fAuth.signOut();
//              Navigation.findNavController(view).navigate(R.id.action_home_Page_to_login2);
//          }
//      });

      return view;
    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {
        @Override
        public void didFetch(RandomRecipeAPIResponse response, String Message) {
            pgBar_home.setVisibility(View.GONE);
            recyclerView = view.findViewById(R.id.recyler_view);
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            randomRecipeAdapter = new RandomRecipeAdapter(getActivity(), response.recipes);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String Message) {
            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();
        }
    };


}