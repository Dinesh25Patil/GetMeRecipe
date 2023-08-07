package com.example.getmerecipe;

import android.content.Context;
import android.widget.Toast;

import com.example.getmerecipe.Listeners.RandomRecipeDetailsListener;
import com.example.getmerecipe.Listeners.RandomRecipeResponseListener;
import com.example.getmerecipe.Listeners.SearchRecipeDetailsListener;
import com.example.getmerecipe.Listeners.SearchRecipeResponseListener;
import com.example.getmerecipe.Model.RandomRecipeAPIResponse;
import com.example.getmerecipe.Model.RandomRecipeDetailsResponse;
import com.example.getmerecipe.Model.SearchRecipeAPIResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public class RequestManager {
    search search;
    String data_from;
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

    public void getRecipeDetails(RandomRecipeDetailsListener listener, int id){
        recipe_detail recipe_detail = retrofit.create(RequestManager.recipe_detail.class);
        Call<RandomRecipeDetailsResponse> call = recipe_detail.callrecipedetails(id, "8da6f12796dc4417bbca918b997d69bb");
        call.enqueue(new Callback<RandomRecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeDetailsResponse> call, Response<RandomRecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void getsearchRecipe(SearchRecipeResponseListener listener){
        search_recipe search_recipe = retrofit.create(RequestManager.search_recipe.class);
        Call<SearchRecipeAPIResponse> call = search_recipe.callsearchRecipe("8da6f12796dc4417bbca918b997d69bb", data_from);
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

    public void getsearch_recipe_details(SearchRecipeDetailsListener listener, int id){
        search_recipe_details search_recipe_details = retrofit.create(RequestManager.search_recipe_details.class);
        Call<RandomRecipeDetailsResponse> call = search_recipe_details.callSearchrecipeDetails(id, "8da6f12796dc4417bbca918b997d69bb");
        call.enqueue(new Callback<RandomRecipeDetailsResponse>() {
            @Override
            public void onResponse(Call<RandomRecipeDetailsResponse> call, Response<RandomRecipeDetailsResponse> response) {
                if(!response.isSuccessful()){
                    listener.didError(response.message());
                    return;
                }
                listener.didFetch(response.body(), response.message());
            }

            @Override
            public void onFailure(Call<RandomRecipeDetailsResponse> call, Throwable t) {
                listener.didError(t.getMessage());
            }
        });
    }

    public void recievedata(String data){
        data_from = data;
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

    private interface recipe_detail{
        @GET("recipes/{id}/information")
        Call<RandomRecipeDetailsResponse> callrecipedetails(
              @Path("id") int id,
              @Query("apiKey") String apiKey
        );

    }

    private interface search_recipe_details{
        @GET("recipes/{id}/information")
        Call<RandomRecipeDetailsResponse> callSearchrecipeDetails(
                @Path("id") int id,
                @Query("apiKey") String apiKey
        );
    }
}
