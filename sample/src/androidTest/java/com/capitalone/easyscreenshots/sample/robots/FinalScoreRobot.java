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

import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static com.capitalone.easyscreenshots.EasyScreenshots.takeScreenshot;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.assertDisplayed;
import static org.hamcrest.core.AllOf.allOf;

public class FinalScoreRobot extends Robot<FinalScoreRobot> {

    // MUST be non-static for multi-lingual tests
    private final Matcher<View> FINAL_SCORE_TITLE = allOf(withId(R.id.score_title), withText(R.string.final_score));

    @Override
    protected FinalScoreRobot getThis() {
        return this;
    }

    public FinalScoreRobot assertFinalScoreScreenShown() {
        assertDisplayed(FINAL_SCORE_TITLE);
        takeScreenshot("final_score_shown");
        return this;
    }
}
