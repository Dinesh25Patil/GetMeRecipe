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

import com.example.getmerecipe.Model.Result;
import com.example.getmerecipe.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchRecipeAdapter extends RecyclerView.Adapter<SearchRecipeAdapter.SearchRecipeViewHolder> {

    public SearchRecipeAdapter(Context context, List<Result> resultList) {
        this.context = context;
        this.resultList = resultList;
    }

    Context context;
    List<Result> resultList;

    @NonNull
    @Override
    public SearchRecipeAdapter.SearchRecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.listsearchrecipe, parent, false);
        return new SearchRecipeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchRecipeAdapter.SearchRecipeViewHolder holder, int position) {
        holder.search_title.setText(resultList.get(position).title);
        holder.search_title.setSelected(true);
        Picasso.get().load(resultList.get(position).image).into(holder.search_Iv_recipe);
    }

    @Override
    public int getItemCount() {
        return resultList.size();
    }

    public static class SearchRecipeViewHolder extends RecyclerView.ViewHolder {
        CardView Cv_searchrecipe;
        TextView search_title;
        ImageView search_Iv_recipe;
        public SearchRecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            Cv_searchrecipe = itemView.findViewById(R.id.Cv_searchrecipe);
            search_title = itemView.findViewById(R.id.search_title);
            search_Iv_recipe = itemView.findViewById(R.id.search_Iv_recipe);
        }
    }
}
