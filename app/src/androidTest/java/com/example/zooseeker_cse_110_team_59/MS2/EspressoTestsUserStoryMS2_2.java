package com.example.zooseeker_cse_110_team_59.MS2;


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
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.espresso.NoMatchingViewException;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;
import androidx.test.rule.GrantPermissionRule;

import com.example.zooseeker_cse_110_team_59.Data.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.Utilities.TestSettings;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestsUserStoryMS2_2 {

    //region INCLUDE THIS IN EVERY ESPRESSO TEST. Change file names to desired test files.
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
    //endregion

    @Test
    public void testPositionSameDestinationChange() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(click());

        ViewInteraction materialAutoCompleteTextView2 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView2.perform(replaceText("Siamangs"), closeSoftKeyboard());

        ViewInteraction materialButton = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton.perform(click());

        ViewInteraction materialAutoCompleteTextView3 = onView(
                allOf(withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView3.perform(replaceText("Emerald Dove"), closeSoftKeyboard());

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
        materialAutoCompleteTextView4.perform(replaceText("Bali Mynah"), closeSoftKeyboard());

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
        materialAutoCompleteTextView5.perform(replaceText("Sdgsdgsdgs"), closeSoftKeyboard());

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
        } catch (NoMatchingViewException e) {
            // For some reason this works at moving the test along, as it would normally get stuck here.
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

        ViewInteraction materialTextView = onView(
                allOf(withId(androidx.appcompat.R.id.title), withText("Mock Location"),
                        childAtPosition(
                                childAtPosition(
                                        withId(androidx.appcompat.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialTextView.perform(click());

        ViewInteraction materialButton6 = onView(
                allOf(withId(android.R.id.button1), withText("Submit"),
                        childAtPosition(
                                childAtPosition(
                                        withClassName(is("android.widget.ScrollView")),
                                        0),
                                3)));
        materialButton6.perform(scrollTo(), click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_name), withText("Directions to Siamangs"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView.check(matches(withText("Directions to Siamangs")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.directions_text),
                        isDisplayed()));
        textView2.check(matches(withText("1. Proceed on Gate Path 1100.0 ft towards Treetops Way.\n" +
                "2. Proceed on Treetops Way 2500.0 ft towards Orangutan Trail.\n" +
                "3. Proceed on Orangutan Trail 1200.0 ft towards Siamangs.\n")));

        ViewInteraction button = onView(
                allOf(withId(R.id.previous_button), withText("PREVIOUS: ENTRANCE AND EXIT GATE, 0.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button.check(matches(isDisplayed()));

        ViewInteraction button2 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT: EMERALD DOVE, 8700.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button2.check(matches(isDisplayed()));

        ViewInteraction materialButton7 = onView(
                allOf(withId(R.id.next_btn), withText("Next: Emerald Dove, 8700.0ft"),
                        isDisplayed()));
        materialButton7.perform(click());

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.place_name), withText("Directions to Emerald Dove"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView3.check(matches(withText("Directions to Emerald Dove")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.directions_text),
                        isDisplayed()));
        textView4.check(matches(withText("1. Proceed on Gate Path 1100.0 ft towards Treetops Way.\n" +
                "2. Proceed on Treetops Way 2500.0 ft towards Orangutan Trail.\n" +
                "3. Proceed on Orangutan Trail 3800.0 ft towards Aviary Trail.\n" +
                "4. Proceed on Aviary Trail 1300.0 ft towards Owens Aviary.\n")));

        ViewInteraction button3 = onView(
                allOf(withId(R.id.previous_button), withText("PREVIOUS: SIAMANGS, 4800.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button3.check(matches(isDisplayed()));

        ViewInteraction button4 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT: BALI MYNAH, 8700.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button4.check(matches(isDisplayed()));

        ViewInteraction materialButton8 = onView(
                allOf(withId(R.id.next_btn), withText("Next: Bali Mynah, 8700.0ft"),
                        isDisplayed()));
        materialButton8.perform(click());

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.place_name), withText("Directions to Bali Mynah"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView5.check(matches(withText("Directions to Bali Mynah")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.directions_text),
                        isDisplayed()));
        textView6.check(matches(withText("1. Proceed on Gate Path 1100.0 ft towards Treetops Way.\n" +
                "2. Proceed on Treetops Way 2500.0 ft towards Orangutan Trail.\n" +
                "3. Proceed on Orangutan Trail 3800.0 ft towards Aviary Trail.\n" +
                "4. Proceed on Aviary Trail 1300.0 ft towards Owens Aviary.\n")));

        ViewInteraction button5 = onView(
                allOf(withId(R.id.previous_button), withText("PREVIOUS: EMERALD DOVE, 8700.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button5.check(matches(isDisplayed()));

        ViewInteraction button6 = onView(
                allOf(withId(R.id.next_btn), withText("NEXT: ENTRANCE AND EXIT GATE, 0.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button6.check(matches(isDisplayed()));

        ViewInteraction materialButton9 = onView(
                allOf(withId(R.id.next_btn), withText("Next: Entrance and Exit Gate, 0.0ft"),
                        isDisplayed()));
        materialButton9.perform(click());

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.place_name), withText("Directions to Entrance and Exit Gate"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        textView7.check(matches(withText("Directions to Entrance and Exit Gate")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.directions_text),
                        isDisplayed()));
        textView8.check(matches(withText("You have arrived at Entrance and Exit Gate.\n")));

        ViewInteraction button7 = onView(
                allOf(withId(R.id.previous_button), withText("PREVIOUS: BALI MYNAH, 8700.0FT"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button7.check(matches(isDisplayed()));

        ViewInteraction button8 = onView(
                allOf(withId(R.id.finish_btn), withText("FINISH"),
                        withParent(withParent(withId(android.R.id.content))),
                        isDisplayed()));
        button8.check(matches(isDisplayed()));
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
