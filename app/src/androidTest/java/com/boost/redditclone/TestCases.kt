package com.boost.redditclone

import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.rule.ActivityTestRule
import androidx.test.uiautomator.UiDevice
import kotlinx.android.synthetic.main.home_fragment.*
import org.hamcrest.BaseMatcher
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.any
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
class TestCases {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)


    @Test
    fun addTopic() {
        onView(withId(R.id.btn_add_topic)).perform(click())
        onView(withId(1)).perform(typeText("test topic"))
        onView(withText("Submit")).perform(click())
        RecyclerViewActions.scrollToPosition<TopicListAdapter.TopicListViewHolder>(11)

        Thread.sleep(3000)
    }

    @Test
    fun expandTopicAndUpvote(){
        Thread.sleep(1000)
        var itemCount = activityRule.activity.rv_home.adapter!!.itemCount-1
        onView(withId(R.id.rv_home)).perform(RecyclerViewActions.scrollToPosition<TopicListAdapter.TopicListViewHolder>(itemCount))
            .perform(RecyclerViewActions.actionOnItemAtPosition<TopicListAdapter.TopicListViewHolder>(itemCount, click()))

        onView(withId(R.id.img_up)).perform(click()).perform(doubleClick())

        Thread.sleep(1000)

        val mDevice: UiDevice = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        mDevice.pressBack()

        Thread.sleep(5000)
    }

    inner class ViewHolderBaseMatcher(view: View) :
        BaseMatcher<TopicListAdapter.TopicListViewHolder?>() {
            private var itemMatcher: Matcher<View> = any(View::class.java)

            override fun describeTo(description: Description?) {

            }

            override fun matches(item: Any?): Boolean {
                val viewHolder = item as RecyclerView.ViewHolder
                return itemMatcher.matches(viewHolder.itemView)
            }

        }


    @Suppress("UNCHECKED_CAST")
    fun <F : Fragment> AppCompatActivity.getFragment(fragmentClass: Class<F>): Fragment? {
        val navHostFragment = this.supportFragmentManager.fragments.first() as NavHostFragment

        navHostFragment.childFragmentManager.fragments.forEach {
            if (fragmentClass.isAssignableFrom(it.javaClass)) {
                return it
            }
        }

        return null
    }

    @After
    fun tearDown() {
    }


}