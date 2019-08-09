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

import android.content.DialogInterface;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;

import com.capitalone.easyscreenshots.sample.model.Question;

public abstract class QuestionFragment extends Fragment {

    public QuestionFragment() {
        super();
    }

    protected void correct() {
        showResultDialog(true);
    }

    protected void incorrect() {
        showResultDialog(false);
    }

    private void showResultDialog(final boolean correct) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        if (correct) {
            builder.setMessage(R.string.correct);
        } else {
            builder.setMessage(R.string.incorrect);

        }
        builder.setCancelable(false).setPositiveButton(R.string.text_continue, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                nextQuestion(correct);
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    protected void nextQuestion(boolean wasCorrectAnswer) {
        ((QuizActivity) getActivity()).nextQuestion(wasCorrectAnswer);
    }

    public Question getQuestion() {
        return (Question) getArguments().getSerializable("question");
    }
}
