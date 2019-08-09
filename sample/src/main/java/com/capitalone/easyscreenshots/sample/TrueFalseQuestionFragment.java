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

import android.os.Bundle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capitalone.easyscreenshots.sample.model.TrueFalseQuestion;

import timber.log.Timber;

public class TrueFalseQuestionFragment extends QuestionFragment {

    private TrueFalseQuestion question;

    private AppCompatButton positiveButton;

    private AppCompatButton negativeButton;

    private AppCompatTextView questionText;

    public TrueFalseQuestionFragment() {
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        question = (TrueFalseQuestion) getQuestion();

        View rootView = inflater.inflate(R.layout.true_false_question, container, false);

        questionText = (AppCompatTextView) rootView.findViewById(R.id.question_text);
        positiveButton = (AppCompatButton) rootView.findViewById(R.id.positive_button);
        negativeButton = (AppCompatButton) rootView.findViewById(R.id.negative_button);

        questionText.setText(question.getQuestion());

        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("TRUE clicked, Answer: " + question.getBooleanAnswer());
                if (question.getBooleanAnswer() == true) {
                    correct();
                } else {
                    incorrect();
                }
            }
        });

        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Timber.d("FALSE clicked, Answer: " + question.getBooleanAnswer());
                if (question.getBooleanAnswer() == false) {
                    correct();
                } else {
                    incorrect();
                }
            }
        });

        return rootView;
    }

}
