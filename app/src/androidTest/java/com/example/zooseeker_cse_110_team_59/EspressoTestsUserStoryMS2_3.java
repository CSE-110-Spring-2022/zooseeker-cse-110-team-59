package com.example.zooseeker_cse_110_team_59;


import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.longClick;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withContentDescription;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestsUserStoryMS2_3 {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms2.json", "test_node_info_ms2.json", "test_edge_info_ms2.json"});
            TestSettings.setTestClearing(true);
            TestSettings.setTestPositioning(true);
        }
    };

    @Rule
    public GrantPermissionRule mGrantPermissionRule =
            GrantPermissionRule.grant(
                    "android.permission.ACCESS_FINE_LOCATION",
                    "android.permission.ACCESS_COARSE_LOCATION");

    @Test
    public void espressoTestsUserStoryMS2_3() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(replaceText("Koi Fish"), closeSoftKeyboard());



        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        materialAutoCompleteTextView.perform(replaceText("Crocodiles"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        materialAutoCompleteTextView.perform(replaceText("Gorillas"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("Ghk"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.generate_plan_btn), withText("Generate Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton4.perform(click());
        try {
            materialButton4.perform(click());
        }
        catch(NoMatchingViewException e){

        }

        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.directions_btn), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Mock Loc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction editText = onView(
                allOf(withText("32.73459618734685"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("32.74711745"));

        ViewInteraction editText2 = onView(
                allOf(withText("32.74711745"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));


        ViewInteraction editText3 = onView(
                allOf(withText("-117.14936"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("-117.180479"));

        ViewInteraction editText4 = onView(
                allOf(withText("-117.180479"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("YES"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_name), withText("Directions to Gorillas"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Directions to Gorillas")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.directions_text), withText("You have arrived at Gorillas.\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("You have arrived at Gorillas.\n")));
    }
    @Test
    public void previous_test() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(longClick());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("Koi Fish"), closeSoftKeyboard());



        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        materialAutoCompleteTextView3.perform(replaceText("Crocodiles"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton2.perform(click());

        ViewInteraction materialAutoCompleteTextView4 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView4.perform(replaceText("Gorillas"), closeSoftKeyboard());



        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        ViewInteraction materialAutoCompleteTextView5 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView5.perform(replaceText("Dsadasd"), closeSoftKeyboard());

        ViewInteraction materialButton4 = onView(
                allOf(withId(R.id.generate_plan_btn), withText("Generate Plan"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                7),
                        isDisplayed()));
        materialButton4.perform(click());
        try {materialButton4.perform(click());}
        catch(NoMatchingViewException e){};
        ViewInteraction materialButton5 = onView(
                allOf(withId(R.id.directions_btn), withText("Directions"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialButton5.perform(click());

        ViewInteraction overflowMenuButton = onView(
                allOf(withContentDescription("More options"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.action_bar),
                                        1),
                                0),
                        isDisplayed()));
        overflowMenuButton.perform(click());

        ViewInteraction materialTextView4 = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Mock Loc"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView4.perform(click());

        ViewInteraction editText = onView(
                allOf(withText("32.73459618734685"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText.perform(replaceText("32.74711"));

        ViewInteraction editText2 = onView(
                allOf(withText("32.74711"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                0),
                        isDisplayed()));
        editText2.perform(closeSoftKeyboard());

        ViewInteraction editText3 = onView(
                allOf(withText("-117.14936"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText3.perform(replaceText("-117.180479"));

        ViewInteraction editText4 = onView(
                allOf(withText("-117.180479"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.custom),
                                        0),
                                1),
                        isDisplayed()));
        editText4.perform(closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction materialButton7 = onView(
                allOf(withId(android.R.id.button1), withText("YES"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton7.perform(scrollTo(), click());

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.previous_button), withText("Previous: Entrance and Exit Gate, 12400.0ft"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_name), withText("Directions to Entrance and Exit Gate"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Directions to Entrance and Exit Gate")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.directions_text), withText("1. Proceed on Monkey Trail 2400.0 ft towards Hippo Trail.\n2. Proceed on Hippo Trail 4500.0 ft towards Treetops Way.\n3. Proceed on Treetops Way 4400.0 ft towards Gate Path.\n4. Proceed on Gate Path 1100.0 ft towards Entrance and Exit Gate.\n"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView2.check(matches(withText("1. Proceed on Monkey Trail 2400.0 ft towards Hippo Trail.\n2. Proceed on Hippo Trail 4500.0 ft towards Treetops Way.\n3. Proceed on Treetops Way 4400.0 ft towards Gate Path.\n4. Proceed on Gate Path 1100.0 ft towards Entrance and Exit Gate.\n")));

        ViewInteraction button = onView(
                allOf(withId(R.id.next_btn), withText("NEXT: GORILLAS, 0.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));
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
