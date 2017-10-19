package com.ansorkazama.udacity_baking_recipe_apps.data.source.local;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class IngredientContract {

    public static final int DB_VERSION = 1;
    public static final String DB_NAME = "com.ansorkazama.udacity_baking_recipe_apps.data.local.ingredient";
    public static final String TABLE = "ingredient";
    public static final String AUTH = "com.ansorkazama.udacity_baking_recipe_apps.widget";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTH + "/" + TABLE);
    public static final int LST_INGREDIENT = 1;
    public static final int ITM_INGREDIENT_ = 2;
    public static final String CT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/crevation.ingredientDB/" + TABLE;
    public static final String CT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/crevation/ingredientDB" + TABLE;

    public class Columns {

        public static final String _ID = BaseColumns._ID;
        public static final String QTY = "quantity";
        public static final String ING = "ingredient";
        public static final String MSR = "measure";

    }
}
