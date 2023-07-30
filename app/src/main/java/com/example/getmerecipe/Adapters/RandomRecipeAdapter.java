package com.example.getmerecipe.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.getmerecipe.Model.Recipe;
import com.example.getmerecipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class RandomRecipeAdapter extends RecyclerView.Adapter<RandomRecipeAdapter.RandomRecipeViewholder> {

    Context context;
    List<Recipe> list;

    public RandomRecipeAdapter(Context context, List<Recipe> list) {
        this.context = context;
        this.list = list;
    }



    @NonNull
    @Override
    public RandomRecipeAdapter.RandomRecipeViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View  v = LayoutInflater.from(context).inflate(R.layout.listrandomrecipe, parent, false);
        return new RandomRecipeViewholder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RandomRecipeViewholder holder, int position) {
        holder.title.setText(list.get(position).title);
        holder.title.setSelected(true);
        Picasso.get().load(list.get(position).image).into(holder.Iv_recipe);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

public static class RandomRecipeViewholder extends RecyclerView.ViewHolder{
        CardView Cv_random;
        TextView title;
        ImageView Iv_recipe;


    public RandomRecipeViewholder(@NonNull View itemView) {
        super(itemView);
        Cv_random = itemView.findViewById(R.id.Cv_randomrecipe);
        title = itemView.findViewById(R.id.tv_title);
        Iv_recipe = itemView.findViewById(R.id.Iv_recipe);
    }
}
}
