package com.example.zooseeker_cse_110_team_59.MS2;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.isRoot;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;

import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.PerformException;
import androidx.test.espresso.UiController;
import androidx.test.espresso.ViewAction;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.espresso.util.HumanReadables;
import androidx.test.espresso.util.TreeIterables;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import com.example.zooseeker_cse_110_team_59.FilesToLoad;
import com.example.zooseeker_cse_110_team_59.MainActivity;
import com.example.zooseeker_cse_110_team_59.R;
import com.example.zooseeker_cse_110_team_59.ZooData;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class EspressoTestsUserStoryMS1_8 {

    //region INCLUDE THIS IN EVERY ESPRESSO TEST. Change file names to desired test files.
    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<MainActivity>(MainActivity.class) {
        @Override
        protected void beforeActivityLaunched() {
            FilesToLoad.injectNewFiles(new String[]{"test_zoo_graph_ms2.json", "test_node_info_ms2.json", "test_edge_info_ms2.json"});
            ZooData.setZooData();
        }
    };

    /** Perform action of waiting for a specific view id. */
    public static ViewAction waitId(final int viewId, final long millis) {
        return new ViewAction() {
            @Override
            public Matcher<View> getConstraints() {
                return isRoot();
            }

            @Override
            public String getDescription() {
                return "wait for a specific view with id <" + viewId + "> during " + millis + " millis.";
            }

            @Override
            public void perform(final UiController uiController, final View view) {
                uiController.loopMainThreadUntilIdle();
                final long startTime = System.currentTimeMillis();
                final long endTime = startTime + millis;
                final Matcher<View> viewMatcher = withId(viewId);

                do {
                    for (View child : TreeIterables.breadthFirstViewTraversal(view)) {
                        // found view with required ID
                        if (viewMatcher.matches(child)) {
                            return;
                        }
                    }

                    uiController.loopMainThreadForAtLeast(50);
                }
                while (System.currentTimeMillis() < endTime);

                // timeout happens
                throw new PerformException.Builder()
                        .withActionDescription(this.getDescription())
                        .withViewDescription(HumanReadables.describe(view))
                        .withCause(new TimeoutException())
                        .build();
            }
        };
    }
    //endregion

    @Test
    public void testEnterOneExhibit() {
        ViewInteraction materialAutoCompleteTextView = onView(
                Matchers.allOf(ViewMatchers.withId(R.id.search_bar),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                0),
                        isDisplayed()));
        materialAutoCompleteTextView.perform(click());
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

        materialAutoCompleteTextView.perform(replaceText("Go Away Autocomplete"), closeSoftKeyboard());

        ViewInteraction materialButton2 = onView(
                allOf(withId(R.id.generate_plan_btn), withText("Generate Plan"),
                        isDisplayed()));
        materialButton2.perform(click());

        onView(isRoot()).perform(waitId(R.id.directions_btn, TimeUnit.SECONDS.toMillis(10)));

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_name), withText("Koi Fish"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView.check(matches(withText("Koi Fish")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.street_name), withText("Terrace Lagoon Loop"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView2.check(matches(withText("Terrace Lagoon Loop")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.cum_distance), withText("6500.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView3.check(matches(withText("6500.0 ft")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.place_name), withText("Entrance and Exit Gate"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView4.check(matches(withText("Entrance and Exit Gate")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.street_name), withText("Gate Path"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView5.check(matches(withText("Gate Path")));

        ViewInteraction textView6 = onView(
                allOf(withId(R.id.cum_distance), withText("13000.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView6.check(matches(withText("13000.0 ft")));
    }

    @Test
    public void testMultipleExhibits() {
        ViewInteraction materialAutoCompleteTextView = onView(
                allOf(withId(R.id.search_bar),
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
        materialAutoCompleteTextView2.perform(replaceText("Koi Fish"), closeSoftKeyboard());

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
        materialAutoCompleteTextView3.perform(replaceText("Bali Mynah"), closeSoftKeyboard());

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
        materialAutoCompleteTextView4.perform(replaceText("Crocodiles"), closeSoftKeyboard());

        ViewInteraction materialButton3 = onView(
                allOf(withId(R.id.search_select_btn), withText("Select"),
                        childAtPosition(
                                childAtPosition(
                                        withId(android.R.id.content),
                                        0),
                                1),
                        isDisplayed()));
        materialButton3.perform(click());

        materialAutoCompleteTextView.perform(replaceText("Go Away Autocomplete"), closeSoftKeyboard());

        ViewInteraction materialButton6 = onView(
                allOf(withId(R.id.generate_plan_btn), withText("Generate Plan"),
                        isDisplayed()));
        materialButton6.perform(click());

        ViewInteraction textView = onView(
                allOf(withId(R.id.place_name), withText("Koi Fish"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView.check(matches(withText("Koi Fish")));

        ViewInteraction textView2 = onView(
                allOf(withId(R.id.street_name), withText("Terrace Lagoon Loop"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView2.check(matches(withText("Terrace Lagoon Loop")));

        ViewInteraction textView3 = onView(
                allOf(withId(R.id.cum_distance), withText("6500.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView3.check(matches(withText("6500.0 ft")));

        ViewInteraction textView8 = onView(
                allOf(withId(R.id.place_name), withText("Crocodiles"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView8.check(matches(withText("Crocodiles")));

        ViewInteraction textView9 = onView(
                allOf(withId(R.id.street_name), withText("Hippo Trail"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView9.check(matches(withText("Hippo Trail")));

        ViewInteraction textView10 = onView(
                allOf(withId(R.id.cum_distance), withText("19300.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView10.check(matches(withText("19300.0 ft")));

        ViewInteraction textView4 = onView(
                allOf(withId(R.id.place_name), withText("Bali Mynah"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView4.check(matches(withText("Bali Mynah")));

        ViewInteraction textView5 = onView(
                allOf(withId(R.id.street_name), withText("Aviary Trail"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView5.check(matches(withText("Aviary Trail")));

        ViewInteraction textView7 = onView(
                allOf(withId(R.id.cum_distance), withText("25900.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView7.check(matches(withText("25900.0 ft")));

        ViewInteraction textView11 = onView(
                allOf(withId(R.id.place_name), withText("Entrance and Exit Gate"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView11.check(matches(withText("Entrance and Exit Gate")));

        ViewInteraction textView13 = onView(
                allOf(withId(R.id.street_name), withText("Gate Path"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView13.check(matches(withText("Gate Path")));

        ViewInteraction textView14 = onView(
                allOf(withId(R.id.cum_distance), withText("34600.0 ft"),
                        withParent(withParent(withId(R.id.route_summary))),
                        isDisplayed()));
        textView14.check(matches(withText("34600.0 ft")));
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
