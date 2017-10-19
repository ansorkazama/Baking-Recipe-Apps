package com.ansorkazama.udacity_baking_recipe_apps.app;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.ImageView;

import com.ansorkazama.udacity_baking_recipe_apps.data.source.online.ApiService;
import com.google.gson.GsonBuilder;
import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.BASE_URL;

public class BakingApp extends Application {

    public static final String TAG = BakingApp.class.getSimpleName();
    private static Retrofit retrofit = null;
    private static BakingApp mInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
        initRetrofit();
    }

    public static synchronized BakingApp getInstance() {
        return mInstance;
    }

    /**
     * Setup retrofit and cache
     */
    void initRetrofit() {


        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)//.client(client)
                .build();

    }

    public static ApiService getApiService() {

        ApiService apiService = retrofit.create(ApiService.class);
        return apiService;
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activenet = connectivityManager.getActiveNetworkInfo();
        return activenet != null;
    }

    public static void loadImageFromResourceInto(Context c, ImageView imageView, String url) {
        if (url != null && !url.equals(""))
            Picasso.with(c).load(url).into(imageView);
    }
}
