package com.example.getmerecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getmerecipe.Adapters.ingridientsAdapter;
import com.example.getmerecipe.Listeners.SearchRecipeClickListener;
import com.example.getmerecipe.Listeners.SearchRecipeDetailsListener;
import com.example.getmerecipe.Model.RandomRecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class SearchRecipeDetails extends AppCompatActivity {

    int id;
    ingridientsAdapter adapter;
    RequestManager requestManager;
    TextView recipe_name, recipe_source, recipe_instructions;
    ImageView recipe_image;
    RecyclerView ingridients;
    ProgressBar pg_bar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_recipe_details);

        findviews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        requestManager = new RequestManager(this);
        requestManager.getsearch_recipe_details(listener,id);
        pg_bar.setVisibility(View.VISIBLE);

    }

    // Method to initialize the view
    private void findviews() {

        recipe_name = findViewById(R.id.search_recipe_name);
        recipe_source = findViewById(R.id.search_recipe_source);
        recipe_image = findViewById(R.id.search_recipe_image);
        recipe_instructions = findViewById(R.id.search_recipe_meal_instructions);
        ingridients = findViewById(R.id.search_ingrdients_rcv);
        pg_bar = findViewById(R.id.pgbar_searchdetails);
    }
    private final SearchRecipeDetailsListener listener = new SearchRecipeDetailsListener() {
        @Override
        public void didFetch(RandomRecipeDetailsResponse response, String message) {
            pg_bar.setVisibility(View.GONE);
            recipe_name.setText(response.title);
            recipe_source.setText(response.sourceName);
            String instructions = response.instructions;
            String processedinstructions = removeTags(instructions);
            recipe_instructions.setText(processedinstructions);
            Picasso.get().load(response.image).into(recipe_image);

            ingridients.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(SearchRecipeDetails.this, LinearLayoutManager.HORIZONTAL, false);
            ingridients.setLayoutManager(layoutManager);
            adapter = new ingridientsAdapter(SearchRecipeDetails.this, response.extendedIngredients);
            ingridients.setAdapter(adapter);
        }

        // Method to remove the unnecessary tags
        private String removeTags(String text) {
            text = text.replaceAll("<ol>", "");
            text = text.replaceAll("</ol>", "");
            text = text.replaceAll("<li>", "");
            text = text.replaceAll("</li>", "");
            return text;
        }

        @Override
        public void didError(String message) {
            Toast.makeText(SearchRecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };

}
