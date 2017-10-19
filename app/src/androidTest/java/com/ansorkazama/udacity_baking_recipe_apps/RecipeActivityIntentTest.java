package com.ansorkazama.udacity_baking_recipe_apps;

import android.app.Activity;
import android.app.Instrumentation;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.ansorkazama.udacity_baking_recipe_apps.ui.activity.RecipeActivity;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.Intents.intending;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.intent.matcher.IntentMatchers.isInternal;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.ansorkazama.udacity_baking_recipe_apps.util.Constants.RECIPE_EXTRA;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class RecipeActivityIntentTest {

    private IdlingResource mIdlingResource;

    @Rule
    public IntentsTestRule<RecipeActivity> intentsTestRule = new IntentsTestRule<>(
            RecipeActivity.class);


    @Before
    public void registerIdlingResource() {

        mIdlingResource = intentsTestRule.getActivity().getIdlingResource();

        Espresso.registerIdlingResources(mIdlingResource);

       // Stub all external intents
        intending(not(isInternal())).respondWith(new Instrumentation.ActivityResult(Activity.RESULT_OK, null));

    }

    @Test
    public void clickRecyclerViewItemHasIntentWithAKey() {


        onView(withId(R.id.recipe_list_recycler)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        //Checks if the key is present
        intended(hasExtraWithKey(RECIPE_EXTRA));

    }

    @After
    public void unregisterIdlingResource() {
        if (mIdlingResource != null) {
            Espresso.unregisterIdlingResources(mIdlingResource);
        }
    }

}
