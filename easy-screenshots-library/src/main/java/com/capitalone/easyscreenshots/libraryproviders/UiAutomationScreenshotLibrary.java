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

import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.test.InstrumentationRegistry;

import com.capitalone.easyscreenshots.EasyScreenshots;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * THIS REQUIRES API 18+
 */
@RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
public class UiAutomationScreenshotLibrary implements ScreenshotLibrary {

    private static final String TAG = UiAutomationScreenshotLibrary.class.getSimpleName();

    public UiAutomationScreenshotLibrary() {
        super();
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR2) {
            throw new RuntimeException(
                    "Screenshots cannot be taken with UiAutomation on devices running less than API 18.");
        }
    }

    /**
     * Use Screenshot
     */
    public File takeScreenshot(String tag) {
        File screenshotOutputFile;
        long startTime = System.currentTimeMillis();
        EasyScreenshots.log(TAG, String.format("Beginning " + getLabel()));
        screenshotOutputFile = EasyScreenshots.getScreenshotFile(tag);
        boolean success = false;
        try {
            Bitmap bitmap = InstrumentationRegistry.getInstrumentation().getUiAutomation().takeScreenshot();
            writeBitmap(bitmap, screenshotOutputFile);
            success = true;

            double seconds = ((System.currentTimeMillis() - startTime) / 1000.0);
            EasyScreenshots.log(TAG, getLabel() + ": " + seconds + " seconds");
        } catch (Exception ex) {
            EasyScreenshots.log(TAG, "Error taking screenshot with " + getLabel(), ex);
        } finally {
            if (!success) {
                if (screenshotOutputFile.exists()) {
                    screenshotOutputFile.delete();
                }
                screenshotOutputFile = new FalconScreenshotLibrary().takeScreenshot(tag);
            }
        }
        return screenshotOutputFile;
    }

    public String getLabel() {
        return getClass().getSimpleName();
    }

    private void writeBitmap(Bitmap bitmap, File toFile) throws IOException {
        OutputStream outputStream = null;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(toFile));
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    // Do nothing
                }
            }
        }
    }

}
