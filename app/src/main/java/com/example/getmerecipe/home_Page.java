package com.example.getmerecipe;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.core.app.ShareCompat;
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
import com.example.getmerecipe.Listeners.RandomRecipeClickListener;
import com.example.getmerecipe.Listeners.RandomRecipeResponseListener;
import com.example.getmerecipe.Model.RandomRecipeAPIResponse;
import com.example.getmerecipe.Model.SearchRecipeAPIResponse;
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
      recyclerView = view.findViewById(R.id.recyler_view);
      recyclerView.setHasFixedSize(true);
      recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
      pgBar_home.setVisibility(View.VISIBLE);

      requestManager = new RequestManager(context);
      requestManager.getRandomRecipe(randomRecipeResponseListener);


      return view;
    }
    private final RandomRecipeResponseListener randomRecipeResponseListener = new RandomRecipeResponseListener() {

        @Override
        public void didFetch(RandomRecipeAPIResponse response, String Message) {
            pgBar_home.setVisibility(View.GONE);
            randomRecipeAdapter = new RandomRecipeAdapter(getActivity(), response.recipes, listener);
            recyclerView.setAdapter(randomRecipeAdapter);
        }

        @Override
        public void didError(String Message) {
            pgBar_home.setVisibility(View.GONE);
            Toast.makeText(getActivity(), Message, Toast.LENGTH_SHORT).show();

        }
    };

    private final RandomRecipeClickListener listener = new RandomRecipeClickListener() {
        @Override
        public void onRandomRecipeClicked(String Id) {
            Intent i = new Intent(getActivity(), RandomRecipeDetails.class);
            i.putExtra("id", Id);
            startActivity(i);
        }
    };

}