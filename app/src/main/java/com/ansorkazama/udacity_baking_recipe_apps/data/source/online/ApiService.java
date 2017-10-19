package com.ansorkazama.udacity_baking_recipe_apps.data.source.online;

import com.ansorkazama.udacity_baking_recipe_apps.data.model.Recipe;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiService {

    @GET("android-baking-app-json")
    Call<ArrayList<Recipe>> getRecipes();
}
