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
package com.capitalone.easyscreenshots.utils;

import android.app.Activity;
import androidx.test.runner.lifecycle.ActivityLifecycleMonitorRegistry;

import java.util.Collection;


import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static androidx.test.runner.lifecycle.Stage.RESUMED;
import static com.capitalone.easyscreenshots.EasyScreenshots.log;

/**
 * http://stackoverflow.com/questions/29929082/how-to-get-spoon-to-take-screenshots-for-espresso-tests
 * <p>
 * Source: https://github.com/square/spoon/issues/214#issuecomment-81979248
 */
public  class ESUtil {

    protected static final String TAG = ESUtil.class.getSimpleName();

    protected static StackTraceElement getTestMethodStackTraceElement() {
        return StackTraceElementLocator.getTestMethodStackTraceElement();
    }

    /**
     * Using the Android Test Instrumentation Registry, you can grab the current activity by finding the activity in the
     * RESUMED stage.
     */
    public static Activity getCurrentActivityInstance() {
        final Activity[] activity = new Activity[1];
        getInstrumentation().runOnMainSync(new Runnable() {
            public void run() {
                Collection resumedActivities = ActivityLifecycleMonitorRegistry.getInstance().getActivitiesInStage(
                        RESUMED);
                if (resumedActivities.iterator().hasNext()) {
                    Activity currentActivity = (Activity) resumedActivities.iterator().next();
                    activity[0] = currentActivity;
                }
            }
        });
        log(TAG, "Current Activity Instance: " + activity[0]);
        return activity[0];
    }

}
