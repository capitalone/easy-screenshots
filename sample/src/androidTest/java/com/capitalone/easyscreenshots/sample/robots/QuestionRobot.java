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
import com.capitalone.easyscreenshots.sample.model.TextQuestion;
import com.capitalone.easyscreenshots.sample.model.TrueFalseQuestion;

import org.hamcrest.Matcher;


import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.capitalone.easyscreenshots.EasyScreenshots.takeScreenshot;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.assertDisplayed;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.assertTextShown;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.clickView;
import static com.capitalone.easyscreenshots.sample.robots.util.EspressoHelpers.enterText;

public class QuestionRobot extends Robot<QuestionRobot> {

    // MUST be non-static for multi-lingual tests
    private final Matcher<View> SUBMIT_BUTTON = withText(R.string.submit);

    private final Matcher<View> POSITIVE_BUTTON = withId(R.id.positive_button);

    private final Matcher<View> NEGATIVE_BUTTON = withId(R.id.negative_button);

    private final Matcher<View> CONTINUE_BUTTON = withText(R.string.text_continue);

    private final Matcher<View> ANSWER_EDIT_TEXT = withId(R.id.answer_edit_text);

    @Override
    protected QuestionRobot getThis() {
        return this;
    }

    public QuestionRobot assertQuestionShown(TrueFalseQuestion trueFalseQuestion) {
        assertTextShown(trueFalseQuestion.getQuestion());
        takeScreenshot("true_false_question_shown");
        return this;
    }

    public QuestionRobot assertQuestionShown(TextQuestion textQuestion) {
        assertTextShown(textQuestion.getQuestion());
        takeScreenshot("text_question_shown");
        return this;
    }

    public QuestionRobot pressTrue() {
        assertDisplayed(POSITIVE_BUTTON);
        takeScreenshot("clicking_true");
        clickView(POSITIVE_BUTTON);
        return this;
    }

    public QuestionRobot submit() {
        assertDisplayed(SUBMIT_BUTTON);
        takeScreenshot("clicking_submit");
        clickView(SUBMIT_BUTTON);
        return this;
    }

    public QuestionRobot pressContinue() {
        assertDisplayed(CONTINUE_BUTTON);
        takeScreenshot("clicking_continue");
        clickView(CONTINUE_BUTTON);
        return this;
    }

    public QuestionRobot pressFalse() {
        assertDisplayed(NEGATIVE_BUTTON);
        takeScreenshot("clicking_false");
        clickView(NEGATIVE_BUTTON);
        return this;
    }

    public QuestionRobot typeAnswer(String text) {
        enterText(ANSWER_EDIT_TEXT, text);
        takeScreenshot("typed_answer");
        return this;
    }
}
