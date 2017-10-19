package com.ansorkazama.udacity_baking_recipe_apps.ui;

import android.app.Activity;

public interface BasePresenter {

    Activity getView();

    void subscribe();

    void unsubscribe();
}
