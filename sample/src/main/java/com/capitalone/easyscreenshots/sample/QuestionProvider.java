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

import com.capitalone.easyscreenshots.sample.model.Question;
import com.capitalone.easyscreenshots.sample.model.TextQuestion;
import com.capitalone.easyscreenshots.sample.model.TrueFalseQuestion;

import java.util.ArrayList;
import java.util.List;

class QuestionProvider {
    private static List<Question> questions = new ArrayList<>();

    public static List<Question> getQuestions() {
        questions.clear();
        questions.add(new TextQuestion("What is the best mobile OS?", "Android"));
        questions.add(new TrueFalseQuestion("Is Banana Cream Pie an Android OS Version?", false));
        questions.add(new TextQuestion("How many words is a screenshot worth?", "1000"));
        questions.add(new TrueFalseQuestion("Is Donut an Android OS Version?", true));
        return questions;
    }
}
