package com.example.getmerecipe;

import android.app.DownloadManager;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.ui.CollapsingToolbarOnDestinationChangedListener;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.contentcapture.ContentCaptureCondition;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.getmerecipe.Adapters.SearchRecipeAdapter;
import com.example.getmerecipe.Listeners.SearchRecipeResponseListener;
import com.example.getmerecipe.Model.SearchRecipeAPIResponse;
import com.example.getmerecipe.databinding.ActivityMainBinding;

import retrofit2.http.Query;


public class search extends Fragment {

    ActivityMainBinding activityMainBinding;
    SearchView searchView;
    public static String searchText;
    RecyclerView recyclerView;
    Context context;
    RequestManager requestManager;
    SearchRecipeAdapter searchRecipeAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view = inflater.inflate(R.layout.fragment_search, container, false);
       searchView = view.findViewById(R.id.search_view);
       recyclerView = view.findViewById(R.id.search_recyView);

       requestManager = new RequestManager(context);
       requestManager.getsearchRecipe(searchRecipeResponseListener);

       activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());

       searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
           @Override
           public boolean onQueryTextSubmit(String s) {
               searchView.clearFocus();
               searchText = (String) searchView.getQuery();
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
            recyclerView.setHasFixedSize(true);
            recyclerView.setLayoutManager(new GridLayoutManager(context, 1));
            searchRecipeAdapter =  new SearchRecipeAdapter(getActivity(), searchRecipeAPIResponse.results);
            recyclerView.setAdapter(searchRecipeAdapter);
        }

        @Override
        public void gotError(String Msg) {
            Toast.makeText(context, Msg, Toast.LENGTH_SHORT).show();
        }
    };
}