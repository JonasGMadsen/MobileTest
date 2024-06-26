package com.example.mobiletry

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun login_withEmailAndPassword_navigatesToBeerListFragment() {

        onView(withId(R.id.edittextEmail)).perform(typeText("mail@gmail.com"))

        onView(withId(R.id.passwordEditText)).perform(typeText("mail123"))

        onView(withId(R.id.loginButton)).perform(click())

        Thread.sleep(5000)

        onView(withId(R.id.beersRecyclerView)).check(matches(isDisplayed()))

    }
}