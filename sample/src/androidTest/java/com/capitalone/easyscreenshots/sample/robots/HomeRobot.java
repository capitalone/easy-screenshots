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

import android.view.View;

import com.capitalone.easyscreenshots.sample.R;

import org.hamcrest.Matcher;


import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.capitalone.easyscreenshots.EasyScreenshots.takeScreenshot;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.assertTextShown;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.clickView;

public class HomeRobot extends Robot<HomeRobot> {

    // MUST be non-static for multi-lingual tests
    private final Matcher<View> START_QUIZ_BUTTON = withText(R.string.start_quiz);

    @Override
    protected HomeRobot getThis() {
        return this;
    }

    public HomeRobot assertHomeScreenShown() {
        assertTextShown("Quiz");
        takeScreenshot("main_activity_shown");
        return this;
    }

    public HomeRobot startQuiz() {
        takeScreenshot("clicking_start_quiz");
        clickView(START_QUIZ_BUTTON);
        return this;
    }
}
