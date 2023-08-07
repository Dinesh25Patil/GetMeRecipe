package com.example.getmerecipe.Listeners;

import com.example.getmerecipe.Model.RandomRecipeDetailsResponse;

public interface RandomRecipeDetailsListener {
    void didFetch(RandomRecipeDetailsResponse response, String message);
    void didError(String message);
}
