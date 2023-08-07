package com.example.getmerecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.getmerecipe.Adapters.ingridientsAdapter;
import com.example.getmerecipe.Listeners.RandomRecipeClickListener;
import com.example.getmerecipe.Listeners.RandomRecipeDetailsListener;
import com.example.getmerecipe.Model.RandomRecipeDetailsResponse;
import com.squareup.picasso.Picasso;

public class RandomRecipeDetails extends AppCompatActivity {

    int id;
    ingridientsAdapter adapter;
    TextView recipe_name, recipe_source, recipe_meal_summary;
    ImageView recipe_image;
    RecyclerView ingrdients_rcv;
    RequestManager requestManager;
    ProgressBar pgbar_details;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_random_recipe_details);

        findViews();

        id = Integer.parseInt(getIntent().getStringExtra("id"));
        requestManager = new RequestManager(this);
        requestManager.getRecipeDetails(randomRecipeDetailsListener, id);
        pgbar_details.setVisibility(View.VISIBLE);
    }

    private void findViews() {
        pgbar_details = findViewById(R.id.pgbar_details);
        recipe_name = findViewById(R.id.recipe_name);
        recipe_image = findViewById(R.id.recipe_image);
        recipe_source = findViewById(R.id.recipe_source);
        recipe_meal_summary = findViewById(R.id.recipe_meal_summary);
        ingrdients_rcv = findViewById(R.id.ingrdients_rcv);
    }

    private final RandomRecipeDetailsListener randomRecipeDetailsListener = new RandomRecipeDetailsListener() {
        @Override
        public void didFetch(RandomRecipeDetailsResponse response, String message) {
            pgbar_details.setVisibility(View.GONE);
            recipe_name.setText(response.title);
            recipe_source.setText(response.sourceName);
            String instructions = response.instructions;
            String processedinstructions = removeTags(instructions);
            recipe_meal_summary.setText(processedinstructions);
            Picasso.get().load(response.image).into(recipe_image);

            ingrdients_rcv.setHasFixedSize(true);
            LinearLayoutManager manager = new LinearLayoutManager(RandomRecipeDetails.this, LinearLayoutManager.HORIZONTAL, false);
            ingrdients_rcv.setLayoutManager(manager);
            adapter = new ingridientsAdapter(RandomRecipeDetails.this, response.extendedIngredients);
            ingrdients_rcv.setAdapter(adapter);

        }

        private String removeTags(String text) {
            text = text.replaceAll("<ol>", "");
            text = text.replaceAll("</ol>", "");
            text = text.replaceAll("<li>", "");
            text = text.replaceAll("</li>", "");
            return text;
        }

        @Override
        public void didError(String message) {
            Toast.makeText(RandomRecipeDetails.this, message, Toast.LENGTH_SHORT).show();
        }
    };
}