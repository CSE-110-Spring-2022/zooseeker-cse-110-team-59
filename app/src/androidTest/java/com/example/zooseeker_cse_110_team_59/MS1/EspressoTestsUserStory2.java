package com.example.zooseeker_cse_110_team_59.MS1;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;
import androidx.test.runner.AndroidJUnit4;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Data.ZooData;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestsUserStory2 {

    //region INCLUDE THIS IN EVERY ESPRESSO TEST. Change file names to desired test files.
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms1.json", "test_node_info_ms1.json", "test_edge_info_ms1.json"});
            TestSettings.setTestClearing(true);
            TestSettings.setTestPositioning(true);
            ZooData.setZooData();
        }
    };

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");
    //endregion

    @Test
    public void testAddValidToEmptyList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Gorillas"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("Gorillas" + "\n")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("1")));
    }

    @Test
    public void testAddValidToNonEmptyList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Elephant Odyssey"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("Elephant Odyssey" + "\n")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("1")));

        materialAutoCompleteTextView.perform(replaceText("Arctic Foxes"), closeSoftKeyboard());

        materialButton.perform(click());

        appResultText1.check(matches(withText("Elephant Odyssey" + "\n" + "Arctic Foxes" + "\n")));

        appResultText2.check(matches(withText("2")));
    }

    @Test
    public void testAddValidDuplicateToList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Alligators"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("Alligators" + "\n")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("1")));

        materialAutoCompleteTextView.perform(replaceText("Alligators"), closeSoftKeyboard());

        materialButton.perform(click());

        appResultText1.check(matches(withText("Alligators" + "\n")));

        appResultText2.check(matches(withText("1")));
    }

    @Test
    public void testAddInvalidToEmptyList() {
        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("0")));

        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Entrance and Exit Gate"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        appResultText1.check(matches(withText("")));

        appResultText2.check(matches(withText("0")));
    }

    @Test
    public void testAddInvalidToNonEmptyList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Alligators"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("Alligators" + "\n")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("1")));

        materialAutoCompleteTextView.perform(replaceText("Entrance and Exit Gate"), closeSoftKeyboard());

        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        appResultText1.check(matches(withText("Alligators" + "\n")));

        appResultText2.check(matches(withText("1")));
    }

    @Test
    public void testAddDuplicateInvalidToList() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Entrance and Exit Gate"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialButton2 = onView(
                allOf(withId(android.R.id.button1), withText("Ok"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton2.perform(scrollTo(), click());

        ViewInteraction appResultText1 = onView(allOf(withId(R.id.animals_list_text_view), isDisplayed()));
        appResultText1.check(matches(withText("")));

        ViewInteraction appResultText2 = onView(allOf(withId(R.id.list_count_text_view), isDisplayed()));
        appResultText2.check(matches(withText("0")));

        materialAutoCompleteTextView.perform(replaceText("Entrance and Exit Gate"), closeSoftKeyboard());

        materialButton.perform(click());

        materialButton2.perform(scrollTo(), click());

        appResultText1.check(matches(withText("")));

        appResultText2.check(matches(withText("0")));
    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
