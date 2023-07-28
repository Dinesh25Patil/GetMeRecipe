package com.example.getmerecipe.Listeners;

import com.example.getmerecipe.Model.SearchRecipeAPIResponse;

public interface SearchRecipeResponseListener {
    void gotResult(SearchRecipeAPIResponse searchRecipeAPIResponse, String Msg);
    void gotError(String Msg);
}
