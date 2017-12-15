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
package com.capitalone.easyscreenshots.libraryproviders;

import android.os.Build;

import com.capitalone.easyscreenshots.EasyScreenshots;

import java.io.File;

public class DefaultScreenshotLibrary implements ScreenshotLibrary {

    private static final String TAG = DefaultScreenshotLibrary.class.getSimpleName();

    /**
     * Use UiAutomation for 18+
     *
     * Use Falcon for <18
     */
    public File takeScreenshot(String tag) {
        File screenshotOutputFile;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            EasyScreenshots.log(TAG, String.format("Beginning " + getLabel()));
            EasyScreenshots
                    .log(TAG, "API Version is greater than 18, Using 'Screenshot' from Testing Support Library.");
            screenshotOutputFile = new UiAutomationScreenshotLibrary().takeScreenshot(tag);
        } else {
            EasyScreenshots.log(TAG, "API Version is less than 18, Using Falcon For The Screenshot Instead.");
            screenshotOutputFile = new FalconScreenshotLibrary().takeScreenshot(tag);
        }
        return screenshotOutputFile;
    }

    public String getLabel() {
        return getClass().getSimpleName();
    }

}
