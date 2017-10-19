package com.ansorkazama.udacity_baking_recipe_apps.util;

import android.view.View;

public interface OnItemClickListener<MODEL> {

        void onClick(MODEL model, View view);
}
