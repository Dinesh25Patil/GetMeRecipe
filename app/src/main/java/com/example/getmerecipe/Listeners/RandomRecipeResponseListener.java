package com.example.getmerecipe.Listeners;

import com.example.getmerecipe.Model.RandomRecipeAPIResponse;

public interface RandomRecipeResponseListener {
    void  didFetch(RandomRecipeAPIResponse response, String Message);
    void  didError(String Message);
}
