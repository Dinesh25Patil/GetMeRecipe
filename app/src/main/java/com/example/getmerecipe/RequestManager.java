package com.example.getmerecipe;

import android.content.Context;

import com.example.getmerecipe.Listeners.RandomRecipeResponseListener;
import com.example.getmerecipe.Listeners.SearchRecipeResponseListener;
import com.example.getmerecipe.Model.RandomRecipeAPIResponse;
import com.example.getmerecipe.Model.SearchRecipeAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RequestManager {
    Context context;
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    public RequestManager(Context context) {
        this.context = context;
    }

    public void getRandomRecipe(RandomRecipeResponseListener listener){
        random_recipe random_recipe = retrofit.create(RequestManager.random_recipe.class);
        Call<RandomRecipeAPIResponse> call = random_recipe.callRandomRecipe("8da6f12796dc4417bbca918b997d69bb" ,"20");
        call.enqueue(new Callback<RandomRecipeAPIResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeAPIResponse> call, Response<RandomRecipeAPIResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeAPIResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getsearchRecipe(SearchRecipeResponseListener listener){
        search_recipe search_recipe = retrofit.create(RequestManager.search_recipe.class);
        Call<SearchRecipeAPIResponse> call = search_recipe.callsearchRecipe("8da6f12796dc4417bbca918b997d69bb", "pizza");
        call.enqueue(new Callback<SearchRecipeAPIResponse>() {
            @Override
            public void onResponse(Call<SearchRecipeAPIResponse> call, Response<SearchRecipeAPIResponse> response) {
                if(!response.isSuccessful()){
                    listener.gotError(response.message());
                    return;
                }
                listener.gotResult(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<SearchRecipeAPIResponse> call, Throwable t) {
                listener.gotError(t.getMessage());

            }
        });
    }

    private interface random_recipe{
        @GET("recipes/random")
        Call<RandomRecipeAPIResponse> callRandomRecipe(
            @Query("apiKey") String apiKey,
            @Query("number") String number
        );
    }

    private interface search_recipe{
        @GET("recipes/complexSearch")
        Call<SearchRecipeAPIResponse> callsearchRecipe(
            @Query("apiKey") String apiKey,
            @Query("query") String query
        );
    }
}