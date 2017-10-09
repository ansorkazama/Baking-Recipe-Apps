package com.ansorkazama.udacity_baking_recipe_apps.networking;

public class ApiUtils {

    private ApiUtils(){}

    //Udacity Recipes
    public static final String BASE_URL_MOVIE = "http://go.udacity.com/android-baking-app-json/";

    public static UdacityApiObservable getUdacityApiObservable() {
        return RetrofitClient.getUdacityClient(BASE_URL_MOVIE).create(UdacityApiObservable.class);
    }

}
