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

import com.example.getmerecipe.Model.ExtendedIngredient;
import com.example.getmerecipe.R;
import com.squareup.picasso.Picasso;

import java.util.ConcurrentModificationException;
import java.util.List;

public class ingridientsAdapter extends RecyclerView.Adapter<ingridientsAdapter.IngridentsViewHolder> {

    Context context;
    List<ExtendedIngredient> list;

    public ingridientsAdapter(Context context, List<ExtendedIngredient> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ingridientsAdapter.IngridentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(context).inflate(R.layout.listrecipeingrdients, parent, false);
        return new IngridentsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ingridientsAdapter.IngridentsViewHolder holder, int position) {
        holder.textView_ingridents.setText(list.get(position).name);
        holder.textView_ingridents.setSelected(true);
        holder.textView_ingridents_quantity.setText(list.get(position).original);
        holder.textView_ingridents_quantity.setSelected(true);
        Picasso.get().load("https://spoonacular.com/cdn/ingredients_100x100/"+list.get(position).image).into(holder.imageView_ingridents);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class IngridentsViewHolder extends RecyclerView.ViewHolder {
        TextView textView_ingridents_quantity, textView_ingridents;
        ImageView imageView_ingridents;
        CardView cv_ingridents;

        public IngridentsViewHolder(@NonNull View itemView) {
            super(itemView);

            textView_ingridents_quantity = itemView.findViewById(R.id.textView_ingridents_quantity);
            textView_ingridents = itemView.findViewById(R.id.textView_ingridents);
            imageView_ingridents = itemView.findViewById(R.id.imageView_ingridents);
            cv_ingridents = itemView.findViewById(R.id.cv_ingridents);
        }
    }
}
