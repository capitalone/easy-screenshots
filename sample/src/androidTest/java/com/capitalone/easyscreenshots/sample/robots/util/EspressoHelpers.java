/**
 * SPDX-Copyright: Copyright (c) Capital One Services, LLC
 * SPDX-License-Identifier: Apache-2.0
 * Copyright 2017 Capital One Services, LLC
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */
package com.capitalone.easyscreenshots.sample.robots.util;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.test.espresso.UiController;
import android.support.test.espresso.ViewAction;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.action.ViewActions;
import android.support.test.espresso.matcher.BoundedMatcher;
import android.support.v7.view.menu.ListMenuItemView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.capitalone.easyscreenshots.sample.robots.matchers.EspressoMatchers;
import com.capitalone.easyscreenshots.sample.robots.viewactions.BetterScrollToAction;
import com.capitalone.easyscreenshots.sample.robots.viewactions.BetterScrollToWithPercentVisibleAction;

import org.hamcrest.Description;
import org.hamcrest.Matcher;


import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.actionWithAssertions;
import static android.support.test.espresso.action.ViewActions.clearText;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.longClick;
import static android.support.test.espresso.action.ViewActions.typeText;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.isDialog;
import static android.support.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static android.support.test.espresso.matcher.ViewMatchers.isChecked;
import static android.support.test.espresso.matcher.ViewMatchers.isClickable;
import static android.support.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.isEnabled;
import static android.support.test.espresso.matcher.ViewMatchers.isNotChecked;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.startsWith;
import static org.hamcrest.core.AllOf.allOf;
import static org.hamcrest.core.Is.is;

public final class EspressoHelpers {

    public static void assertTextShown(String... expectedStrings) {
        for (String string : expectedStrings) {
            assertTextShown(string);
        }
    }

    public static void assertTextShown(@StringRes int... expectedIds) {
        for (int id : expectedIds) {
            assertTextShown(id);
        }
    }

    public static void assertMenuItemShown(String expectedText) {
        onView(allOf(isDescendantOfA(isAssignableFrom(ListMenuItemView.class)), withText(expectedText))).check(matches(isDisplayed()));
    }

    public static void assertTextShown(String expectedText) {
        assertDisplayed(withText(expectedText));
    }

    public static void assertTextShown(@StringRes int id) {
        assertDisplayed(withText(id));
    }

    public static void assertViewHidden(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(not(isDisplayed())));
    }

    public static void assertTextDoesNotExistOnVisibleScreen(String text) {
        assertDoesNotExist(allOf(withText(text), isDisplayed()));
    }

    public static void assertTextShownEndingWith(String expectedText) {
        assertDisplayed(withText(endsWith(expectedText)));
    }

    public static void assertTextNotShownEndingWith(String expectedText) {
        onView(withText(endsWith(expectedText))).check(doesNotExist());
    }

    public static void assertTextShownStartingWith(String expectedText) {
        assertDisplayed(withText(startsWith(expectedText)));
    }

    public static void assertTextShownContains(String expectedText) {
        assertDisplayed(withText(containsString(expectedText)));
    }

    @Deprecated
    public static void assertTextMatches(Matcher<View> viewMatcher, String expectedText) {
        onView(viewMatcher).check(matches(withText(expectedText)));
    }

    @Deprecated
    public static void assertTextMatches(ViewInteraction viewInteraction, String expectedText) {
        viewInteraction.check(matches(withText(expectedText)));
    }

    public static void clickButton(String buttonText) {
        clickView(onView(withText(buttonText)));
    }

    @Deprecated
    public static void clickView(ViewInteraction viewInteraction) {
        viewInteraction.perform(click());
    }

    public static void clickView(Matcher<View> viewMatcher) {
        clickView(onView(allOf(isDisplayed(), viewMatcher)));
    }

    public static void clickViewInDialog(Matcher<View> viewMatcher) {
        onView(allOf(isDisplayed(), viewMatcher)).inRoot(isDialog()).perform(click());
    }

    /**
     * Espresso does not call performClick if view is not visible at least 90%
     * To make it work we have to override the perform method
     * Solution collected from: http://stackoverflow.com/questions/28834579/click-on-not-fully-visible-imagebutton-with-espresso
     */
    public static void clickOffScreenView(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(allOf(isEnabled(), isClickable()))).perform(
                new ViewAction() {
                    @Override
                    public Matcher<View> getConstraints() {
                        return isEnabled();
                    }

                    @Override
                    public String getDescription() {
                        return "clickOffScreenView";
                    }

                    @Override
                    public void perform(UiController uiController, View view) {
                        view.performClick();
                    }
                }
        );
    }

    public static void longClickView(Matcher<View> viewMatcher) {
        onView(viewMatcher).perform(longClick());
    }

    public static void assertDisabled(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(not(isEnabled())));
    }

    public static void assertEnabled(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(isEnabled()));
    }

    public static void enterText(Matcher<View> viewMatcher, String textToEnter) {
        onView(viewMatcher).perform(clearText(), typeText(textToEnter), closeSoftKeyboard());
    }

    public static void assertImageViewHasDrawableResource(@IdRes int viewId, @DrawableRes int imageResId) {
        onView(withId(viewId)).check(matches(EspressoMatchers.withDrawable(imageResId)));
    }

    public static void assertDrawableDisplayed(@DrawableRes int imageResId) {
        assertDisplayed(onView(EspressoMatchers.withDrawable(imageResId)));
    }

    public static void assertDisplayed(@NonNull Matcher<View> viewMatcher) {
        assertDisplayed(onView(allOf(isDisplayed(), viewMatcher)));
    }

    public static void assertDisplayed(ViewInteraction viewInteraction) {
        viewInteraction.check(matches(isDisplayed()));
    }

    /**
     * In View Hierarchy, but isn't shown on the visible screen.
     */
    public static void assertNotDisplayed(ViewInteraction viewInteraction) {
        viewInteraction.check(matches(not(isDisplayed())));
    }

    public static void assertNotDisplayed(@NonNull Matcher<View> viewMatcher) {
        assertNotDisplayed(onView(viewMatcher));
    }

    public static void assertDoesNotExist(@NonNull Matcher<View> viewMatcher) {
        assertDoesNotExist(onView(viewMatcher));
    }

    public static void assertDoesNotExist(ViewInteraction viewInteraction) {
        viewInteraction.check(doesNotExist());
    }

    public static void assertChecked(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(isChecked()));
    }

    public static void assertUnchecked(Matcher<View> viewMatcher) {
        onView(viewMatcher).check(matches(isNotChecked()));
    }

    public static void assertViewWithIdShown(@IdRes int resourceId) {
        assertDisplayed(onView(withId(resourceId)));
    }

    public static void swipeUp(Matcher<View> viewMatcher) {
        onView(viewMatcher).perform(ViewActions.swipeUp());
    }

    public static void swipeDown(Matcher<View> viewMatcher) {
        onView(viewMatcher).perform(ViewActions.swipeDown());
    }

    public static void swipeLeft(Matcher<View> viewMatcher) {
        onView(viewMatcher).perform(ViewActions.swipeLeft());
    }

    public static void swipeRight(Matcher<View> viewMatcher) {
        onView(viewMatcher).perform(ViewActions.swipeRight());
    }

    public static void putAppInBackground(Activity activity) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        activity.startActivity(intent);
    }

    public static void bringAppToForeground(Activity activity, Class clazz) {
        Intent intent = new Intent(activity, clazz);
        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        activity.startActivity(intent);
    }

    public static void assertToolbarTitle(CharSequence title) {
        onView(isAssignableFrom(Toolbar.class))
                .check(matches(allOf(withToolbarTitle(is(title)), isDisplayed())));
    }

    /**
     * Used to scroll ScrollView, HorizontalScrollView, and NestedScrollView
     * Note: ListViews should use onData for scrolling, RecyclerViews should use the RecyclerViewHelpers
     * clas.
     */
    public static class Scroll {

        /**
         * Used for when the matcher is unamgibuous. If the scrollable container needs to be specified
         * to prevent ambiguity use {@link #scrollToText(String, Matcher)}
         */
        public static void scrollToText(@NonNull String text) {
            scrollTo(withText(text));
        }

        public static void scrollToTextStartingWith(@NonNull String text) {
            scrollTo(withText(startsWith(text)));
        }

        public static void scrollToTextContaining(@NonNull String text) {
            scrollTo(withText(containsString(text)));
        }

        public static void scrollToTextEndingWith(@NonNull String text) {
            scrollTo(withText(endsWith(text)));
        }

        /**
         * For when the view resides inside one of many scrollable containers (ex. within a ViewPager)
         * and would be ambiguous to match using just withText.
         */
        public static void scrollToText(@NonNull String text, Matcher<View> scrollableContainerMatcher) {
            scrollTo(withText(text), scrollableContainerMatcher);
        }


        /**
         * For when the view resides inside one of many scrollable containers (ex. within a ViewPager)
         * and would be ambiguous to match using just withText.
         */
        public static void scrollToAndClickView(Matcher<View> viewMatcher, Matcher<View> scrollableContainerMatcher) {
            scrollToAndClickView(allOf(viewMatcher, isDescendantOfA(scrollableContainerMatcher)));
        }

        /**
         * For when the view resides inside one of many scrollable containers (ex. within a ViewPager)
         * and would be ambiguous to match using just withText.
         */
        public static void scrollTo(Matcher<View> viewMatcher,
                                    Matcher<View> scrollableContainerMatcher) {
            scrollTo(allOf(viewMatcher, isDescendantOfA(scrollableContainerMatcher)));
        }

        /**
         * Used for when the matcher is unamgibuous. If the scrollable container needs to be specified
         * to prevent ambiguity use {@link #scrollToAndClickView(Matcher, Matcher)}
         */
        public static void scrollToAndClickView(Matcher<View> viewMatcher) {
            onView(viewMatcher).perform(actionWithAssertions(new BetterScrollToAction())).perform(click());
        }

        /**
         * Used for when the matcher is unamgibuous. If the scrollable container needs to be specified
         * to prevent ambiguity use {@link #scrollTo(Matcher, Matcher)}
         */
        public static void scrollTo(Matcher<View> viewMatcher) {
            onView(viewMatcher).perform(actionWithAssertions(new BetterScrollToAction())).check(matches(isDisplayed()));
        }

        public static void scrollTo(Matcher<View> viewMatcher, int percentVisible) {
            onView(viewMatcher).perform(actionWithAssertions(new BetterScrollToWithPercentVisibleAction(percentVisible))).check(matches(isDisplayed()));
        }

    }

    private static Matcher<Object> withToolbarTitle(final Matcher<CharSequence> textMatcher) {
        return new BoundedMatcher<Object, Toolbar>(Toolbar.class) {
            @Override public boolean matchesSafely(Toolbar toolbar) {
                return textMatcher.matches(toolbar.getTitle());
            }
            @Override public void describeTo(Description description) {
                description.appendText("with toolbar title: ");
                textMatcher.describeTo(description);
            }
        };
    }
}
