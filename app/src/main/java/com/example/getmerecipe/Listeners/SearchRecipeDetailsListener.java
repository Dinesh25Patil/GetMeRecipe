package com.example.getmerecipe.Listeners;

import com.example.getmerecipe.Model.RandomRecipeDetailsResponse;
import com.example.getmerecipe.SearchRecipeDetails;

public interface SearchRecipeDetailsListener {
    void didFetch(RandomRecipeDetailsResponse response, String message);
    void didError(String message);
}
