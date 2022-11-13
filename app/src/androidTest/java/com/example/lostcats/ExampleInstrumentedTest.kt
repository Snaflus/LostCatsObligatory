package com.example.lostcats

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.EasyMock2Matchers.equalTo
import org.hamcrest.Matcher

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule
import kotlin.concurrent.thread

@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @get:Rule
    var activityRule: ActivityScenarioRule<MainActivity> =
        ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun useAppContext() {
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.lostcats", appContext.packageName)

        Thread.sleep(500)
        onView(withId(R.id.orderby_name_chip)).perform(click())
        Thread.sleep(300)
        onView(withId(R.id.orderby_reward_chip)).perform(click())
        Thread.sleep(300)

        Thread.sleep(300)
        onView(withId(R.id.search_bar)).perform(typeText("Musti"))
    }
}