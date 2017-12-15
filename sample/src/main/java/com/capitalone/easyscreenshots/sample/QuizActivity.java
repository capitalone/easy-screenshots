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
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;

import com.capitalone.easyscreenshots.sample.model.Question;
import com.capitalone.easyscreenshots.sample.model.TextQuestion;
import com.capitalone.easyscreenshots.sample.model.TrueFalseQuestion;

import java.util.List;

import timber.log.Timber;

public class QuizActivity extends AppCompatActivity {

    ViewPager viewPager;

    private PagerAdapter pagerAdapter;

    private List<Question> questions = QuestionProvider.getQuestions();

    private int numCorrect = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        numCorrect = 0;

        pagerAdapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public int getCount() {
                return questions.size();
            }

            @Override
            public Fragment getItem(int position) {
                Question question = questions.get(position);
                Bundle bundle = new Bundle();
                bundle.putSerializable("question", question);
                Fragment fragment = null;
                if (question instanceof TextQuestion) {
                    fragment = new TextQuestionFragment();
                    fragment.setArguments(bundle);
                } else if (question instanceof TrueFalseQuestion) {
                    fragment = new TrueFalseQuestionFragment();
                    fragment.setArguments(bundle);
                }
                return fragment;
            }
        };

        viewPager.setAdapter(pagerAdapter);

        // Disallow left/right swiping
        viewPager.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });

        getSupportActionBar().setTitle("Quiz Activity");
    }

    public void nextQuestion(boolean wasCorrectAnswer) {
        if (wasCorrectAnswer) {
            numCorrect++;
        }
        int currentIdx = viewPager.getCurrentItem();
        int nextIdx = currentIdx + 1;
        Timber.d("NextIdx: " + nextIdx + " - " + questions.size());
        if (nextIdx == questions.size()) {
            Intent intent = new Intent(QuizActivity.this, FinalScoreActivity.class);
            Bundle extras = new Bundle();
            extras.putInt(FinalScoreActivity.CORRECT, numCorrect);
            extras.putInt(FinalScoreActivity.TOTAL, questions.size());
            intent.putExtras(extras);
            startActivity(intent);
            finish();
        } else {
            viewPager.setCurrentItem(nextIdx);
        }
    }
}
