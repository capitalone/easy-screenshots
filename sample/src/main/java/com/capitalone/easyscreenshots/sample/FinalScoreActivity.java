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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.view.View;

public class FinalScoreActivity extends AppCompatActivity {

    private AppCompatTextView finalScore;

    private AppCompatButton doneButton;

    public static final String CORRECT = "CORRECT";

    public static final String TOTAL = "TOTAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_final_score);
        getSupportActionBar().setTitle("Your Score");

        finalScore = (AppCompatTextView) findViewById(R.id.score_value);

        int correct = getIntent().getExtras().getInt(CORRECT);
        int total = getIntent().getExtras().getInt(TOTAL);

        finalScore.setText(String.format("%s out of %s", correct, total));

        doneButton = (AppCompatButton) findViewById(R.id.done_button);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(FinalScoreActivity.this, MainActivity.class));
                finish();
            }
        });

    }
}
