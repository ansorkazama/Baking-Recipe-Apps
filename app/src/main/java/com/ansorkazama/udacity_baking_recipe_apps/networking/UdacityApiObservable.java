package com.ansorkazama.udacity_baking_recipe_apps.networking;

import com.ansorkazama.udacity_baking_recipe_apps.model.Recipe;
import java.util.ArrayList;
import io.reactivex.Observable;
import retrofit2.http.GET;

public interface UdacityApiObservable {
    @GET(" ")
    Observable<ArrayList<Recipe>> getUdacityRecipeResult();
}
