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
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatTextView;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.capitalone.easyscreenshots.sample.model.TextQuestion;

public class TextQuestionFragment extends QuestionFragment {

    private TextQuestion question;

    private AppCompatTextView questionText;

    private AppCompatEditText answerEditText;

    private AppCompatButton submitButton;

    public TextQuestionFragment() {
        super();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        question = (TextQuestion) getQuestion();

        View rootView = inflater.inflate(R.layout.text_question, container, false);

        questionText = (AppCompatTextView) rootView.findViewById(R.id.question_text);
        answerEditText = (AppCompatEditText) rootView.findViewById(R.id.answer_edit_text);
        submitButton = (AppCompatButton) rootView.findViewById(R.id.submit_button);

        answerEditText.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    submit();
                    return true;
                }
                return false;
            }
        });

        questionText.setText(question.getQuestion());
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submit();
            }
        });

        return rootView;
    }

    private void submit() {
        if (question.getAnswer().equalsIgnoreCase(answerEditText.getText().toString())) {
            correct();
        } else {
            incorrect();
        }
    }
}
