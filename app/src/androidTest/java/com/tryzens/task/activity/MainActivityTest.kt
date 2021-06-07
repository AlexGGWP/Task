package com.tryzens.task.activity

import android.os.SystemClock
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.tryzens.task.R
import com.tryzens.task.base.BaseUITest
import com.tryzens.task.di.apiModule
import com.tryzens.task.di.generateTestAppComponent
import com.tryzens.task.di.networkModule
import com.tryzens.task.helpers.recyclerItemAtPosition
import com.tryzens.task.ui.MainActivity
import com.tryzens.task.ui.adapters.RepoAdapter
import org.hamcrest.CoreMatchers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import java.net.HttpURLConnection

@RunWith(AndroidJUnit4::class)
class MainActivityTest : BaseUITest() {
    @Rule
    @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mActivityTestRule = ActivityTestRule(MainActivity::class.java, true, false)

    val recyclerView = Espresso.onView(withId(R.id.repo_recycler_view))
    val progressBar = Espresso.onView(withId(R.id.progressbar))
    val testName1 = "yajl-objc"
    val testLastName = "otto"


    @Before
    fun start() {
        super.setUp()
        //Unload preloaded modules
        unloadKoinModules(
            listOf(
                networkModule,
                apiModule
            )
        )

        loadKoinModules(generateTestAppComponent(getMockWebServerUrl()).toMutableList())
    }

    @Test
    fun testMainActivityAndFragments() {
        mActivityTestRule.launchActivity(null)

        mockNetworkResponse("mock_response.json", HttpURLConnection.HTTP_OK)

        //Wait for MockWebServer to get back with response
        SystemClock.sleep(1000)

        //Is main fragment visible?
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Check for progress
        progressBar.check(ViewAssertions.matches(CoreMatchers.not(ViewMatchers.isDisplayed())))

        //Check if item at 0th position is having 0th element in json
        recyclerView
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        0,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testName1))
                    )
                )
            )

        //Scroll to last index in json
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<RepoAdapter.RepositoryViewHolder>(29)
        )

        recyclerView
            .check(
                ViewAssertions.matches(
                    recyclerItemAtPosition(
                        29,
                        ViewMatchers.hasDescendant(ViewMatchers.withText(testLastName))
                    )
                )
            )

        //Scroll back to top
        recyclerView.perform(
            RecyclerViewActions.scrollToPosition<RepoAdapter.RepositoryViewHolder>(0)
        )
        //and click first element
        recyclerView.perform(
            RecyclerViewActions.actionOnItemAtPosition<RepoAdapter.RepositoryViewHolder>(
                0,
                ViewActions.click()
            )
        )

        //Check if search icon is visible - should be NOT
        Espresso.onView(withId(R.id.search_icon))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))

        // Confirm DetailFragment is in view
        Espresso.onView(withId(R.id.fragment_details_container))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(withId(R.id.repo_node_id))
            .check(ViewAssertions.matches(ViewMatchers.withText("274562")))
        Espresso.onView(withId(R.id.repo_short_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("yajl-objc")))
        Espresso.onView(withId(R.id.repo_owner_name))
            .check(ViewAssertions.matches(ViewMatchers.withText("square")))
        Espresso.onView(withId(R.id.repo_creation_date))
            .check(ViewAssertions.matches(ViewMatchers.withText("2009-08-11T01:04:56Z")))
        Espresso.onView(withId(R.id.repo_last_update))
            .check(ViewAssertions.matches(ViewMatchers.withText("2019-09-23T22:13:41Z")))
        Espresso.onView(withId(R.id.repo_description))
            .check(ViewAssertions.matches(ViewMatchers.withText("Objective-C bindings for YAJL (Yet Another JSON Library) C library")))

        //Click on back arrow on tool bar
        Espresso.onView(withId(R.id.back_arrow_button)).perform(ViewActions.click())

        //check if click was successful
        recyclerView.check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        //Check if search icon is visible - should be VISIBLE
        Espresso.onView(withId(R.id.search_icon))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)))
        //Check if back icon is visible - should be NOT
        Espresso.onView(withId(R.id.back_arrow_button))
            .check(ViewAssertions.matches(ViewMatchers.withEffectiveVisibility(ViewMatchers.Visibility.GONE)))
    }
}