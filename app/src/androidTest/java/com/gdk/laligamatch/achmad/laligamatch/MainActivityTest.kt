package com.gdk.laligamatch.achmad.laligamatch

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.gdk.laligamatch.achmad.laligamatch.R.id.*
import com.gdk.laligamatch.achmad.laligamatch.idling.MainIdlingResource
import com.gdk.laligamatch.achmad.laligamatch.view.MainActivity
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@Suppress("DEPRECATION")
@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    var idlingResource: MainIdlingResource? = null

    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)


    @Before
    fun setup(){
        idlingResource = MainIdlingResource(3000) //mActivityRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource)
    }

    @After
    fun tearDown(){
        Espresso.unregisterIdlingResources(idlingResource)
    }

    @Test
    fun testRecyclerViewBehaviour(){
        onView(withId(recycler_view))
                .check(matches(isDisplayed()))
        onView(withId(recycler_view)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(10, click()))
    }

    @Test
    fun testAppBehaviour(){

        onView(ViewMatchers.withText("Barcelona"))
                .check(matches(isDisplayed()))
        onView(ViewMatchers.withText("Barcelona")).perform(click())
        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to Favorites"))
                .check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withId(navigation_view))
                .check(matches(isDisplayed()))
        onView(withId(next_match)).perform(click())

        onView(ViewMatchers.withText("Real Madrid"))
                .check(matches(isDisplayed()))
        onView(ViewMatchers.withText("Real Madrid")).perform(click())

        onView(withId(add_to_favorite))
                .check(matches(isDisplayed()))
        onView(withId(add_to_favorite)).perform(click())
        onView(ViewMatchers.withText("Added to Favorites"))
                .check(matches(isDisplayed()))
        Espresso.pressBack()

        onView(withId(navigation_view))
                .check(matches(isDisplayed()))
        onView(withId(favorites)).perform(click())
    }
}