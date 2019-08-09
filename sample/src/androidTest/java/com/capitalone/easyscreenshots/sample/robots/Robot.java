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
package com.capitalone.easyscreenshots.sample.robots;

import android.content.Context;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import androidx.appcompat.view.menu.ListMenuItemView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;

import com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers;

import org.hamcrest.Matcher;


import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.pressKey;
import static androidx.test.espresso.matcher.ViewMatchers.hasFocus;
import static androidx.test.espresso.matcher.ViewMatchers.isAssignableFrom;
import static androidx.test.espresso.matcher.ViewMatchers.isDescendantOfA;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.capitalone.easyscreenshots.EasyScreenshots.takeScreenshot;
import static org.hamcrest.Matchers.allOf;

/**
 * Test's responsibility: WHAT am I testing? Tests use Robots, but they should not care exactly how the Robot achieves
 * the WHAT.
 * <p/>
 * Robot's responsibility: HOW am I testing the WHAT? Robots allow UI Tests to be written without the gritty knowledge
 * of HOW actions are performed.
 * <p/>
 * Guidelines: - Robots should follow the builder pattern and provide individual methods to perform the WHAT. - Robots
 * should not expose views publicly, but only as package-private.
 * <p/>
 * Link: https://realm.io/news/kau-jake-wharton-testing-robots/
 */
public abstract class Robot<T extends Robot> {

    protected final Context applicationContext;

    protected final Context testApplicationContext;

    public Robot() {
        applicationContext = InstrumentationRegistry.getTargetContext().getApplicationContext();
        testApplicationContext = InstrumentationRegistry.getContext().getApplicationContext();
    }

    /**
     * Very simple way to avoid having to cast to T and getting the unchecked warning. <br>
     * See: http://stackoverflow.com/a/34741836
     *
     * @return this
     */
    protected abstract T getThis();

    public T back() {
        takeScreenshot("pressing_back_button");
        Espresso.pressBack();
        return getThis();
    }

    public T clickMenuItem(String menuItemText) {
        EspressoHelpers.assertTextShown(menuItemText);
        takeScreenshot("clicking_menu_item_" + menuItemText);
        Matcher<View> descendantOfAMenuListItemMatcher = isDescendantOfA(isAssignableFrom(ListMenuItemView.class));
        EspressoHelpers.clickView(allOf(descendantOfAMenuListItemMatcher, withText(menuItemText)));
        return getThis();
    }

    public T pressEnterKey() {
        Matcher<View> viewInFocus = allOf(isAssignableFrom(EditText.class), hasFocus());
        takeScreenshot("pressing_enter");
        onView(viewInFocus).perform(pressKey(KeyEvent.KEYCODE_ENTER));
        return getThis();
    }
}
