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
package com.capitalone.easyscreenshots.sample;

import android.content.Intent;
import androidx.test.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import android.util.Log;

import com.capitalone.easyscreenshots.sample.robots.FinalScoreRobot;
import com.capitalone.easyscreenshots.sample.robots.HomeRobot;
import com.capitalone.easyscreenshots.sample.robots.QuestionRobot;
import com.linkedin.android.testbutler.TestButler;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class QuizTestES extends ScreenshotTest {

    @BeforeClass
    public static void beforeClass() {
        TestButler.setLocale("es", "ES", InstrumentationRegistry.getTargetContext());
        Log.d("TAG", "Locale ES set.");
    }

    @BeforeClass
    public static void afterClass() {
        TestButler.setLocale("en", "EN", InstrumentationRegistry.getTargetContext());
        Log.d("TAG", "Locale EN set.");
    }

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class, true, false);

    @Test
    public void activityFlowTestSpanish() {
        mActivityTestRule.launchActivity(new Intent(InstrumentationRegistry.getTargetContext(), MainActivity.class));

        new HomeRobot().assertHomeScreenShown().startQuiz();
        new QuestionRobot().typeAnswer("android").submit().pressContinue();
        new QuestionRobot().pressFalse().pressContinue();
        new QuestionRobot().typeAnswer("1000").submit().pressContinue();
        new QuestionRobot().pressTrue().pressContinue();
        new FinalScoreRobot().assertFinalScoreScreenShown();
    }
}
