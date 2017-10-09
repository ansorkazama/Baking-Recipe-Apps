package com.ansorkazama.udacity_baking_recipe_apps.networking;

import com.ansorkazama.udacity_baking_recipe_apps.model.Recipe;
import java.util.ArrayList;
import io.reactivex.Observable;

public class UdacityNetworkService {

    private UdacityApiObservable mUdacityApiObservable;

    public UdacityNetworkService(){
        mUdacityApiObservable = ApiUtils.getUdacityApiObservable();
    }

    public Observable<ArrayList<Recipe>> networkApiRequestRecipes() {
        Observable observer = mUdacityApiObservable.getUdacityRecipeResult();
        return observer;
    }
}
