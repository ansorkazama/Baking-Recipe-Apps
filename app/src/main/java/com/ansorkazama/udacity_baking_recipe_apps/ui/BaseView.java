package com.ansorkazama.udacity_baking_recipe_apps.ui;

public interface BaseView<T> {

    void setPresenter(T presenter);

    void showMessage(String message);

    void showLoader(boolean show);
}
