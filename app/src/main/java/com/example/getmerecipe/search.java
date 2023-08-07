package com.example.getmerecipe;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.getmerecipe.Adapters.RandomRecipeAdapter;
import com.example.getmerecipe.Adapters.searchRecipeAdapter;
import com.example.getmerecipe.Listeners.SearchRecipeClickListener;
import com.example.getmerecipe.Listeners.SearchRecipeResponseListener;
import com.example.getmerecipe.Model.SearchRecipeAPIResponse;
import com.example.getmerecipe.databinding.ActivityMain2Binding;
import com.example.getmerecipe.databinding.ActivityMainBinding;


public class search extends Fragment {

    ActivityMain2Binding activityMain2Binding;
    SearchView searchView;
    public static String searchText;
    RecyclerView recyclerView;
    Context context;
   RequestManager requestManager;
    View view;
    searchRecipeAdapter searchRecipeAdapter;
    ProgressBar pgbar_search;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       view = inflater.inflate(R.layout.fragment_search, container, false);

       searchView = view.findViewById(R.id.search_view);
       recyclerView = view.findViewById(R.id.search_recyView);
       pgbar_search = view.findViewById(R.id.pgbar_search);

       recyclerView.setHasFixedSize(true);
       recyclerView.setLayoutManager(new GridLayoutManager(context, 1));

       requestManager = new RequestManager(context);

       activityMain2Binding = ActivityMain2Binding.inflate(getLayoutInflater());

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               pgbar_search.setVisibility(View.VISIBLE);
               searchView.clearFocus();
               searchText =  searchView.getQuery().toString().trim();
               requestManager.recievedata(searchText);
               requestManager.getsearchRecipe(searchRecipeResponseListener);
               pgbar_search.setVisibility(View.GONE);
               return false;
           }
           @Override
           public boolean onQueryTextChange(String s) {

               return false;
           }
       });
      return view;
    }

    private final SearchRecipeResponseListener searchRecipeResponseListener  = new SearchRecipeResponseListener() {
        @Override
        public void gotResult(SearchRecipeAPIResponse searchRecipeAPIResponse, String Msg) {
            searchRecipeAdapter = new searchRecipeAdapter(getActivity(), searchRecipeAPIResponse.results, listener);
            recyclerView.setAdapter(searchRecipeAdapter);
        }
        @Override
        public void gotError(String Msg) {
            Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
        }
    };
    private final SearchRecipeClickListener listener = new SearchRecipeClickListener() {
        @Override
        public void onSearchRecipeClicked(String Id) {
            Intent i = new Intent(getActivity(), SearchRecipeDetails.class);
            i.putExtra("id", Id);
            startActivity(i);
        }
    };

}