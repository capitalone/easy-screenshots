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
package com.capitalone.easyscreenshots.fileproviders;

import androidx.annotation.NonNull;

import com.capitalone.easyscreenshots.utils.StackTraceElementLocator;

import java.io.File;


import static com.capitalone.easyscreenshots.EasyScreenshots.log;

public abstract class ScreenshotFileProvider {

    private static final String TAG = ScreenshotFileProvider.class.getSimpleName();

    protected abstract File getScreenshotFile(@NonNull String tag, @NonNull StackTraceElement stackTraceElement);

    public File getScreenshotFile(@NonNull String tag) {
        // Cleanse the tag with regex so there isn't an issue writing to a file.
        tag = cleanseTag(tag);
        File file = getScreenshotFile(tag, StackTraceElementLocator.getTestMethodStackTraceElement());
        try {
            log(TAG, "Screenshot File: " + file.getCanonicalPath());
        } catch (Exception e) {
            log(TAG, e.getMessage(), e);
        }
        return file;
    }

    public abstract void clearPreviousScreenshots();

    String cleanseTag(@NonNull String tag) {
        final String originalTag = tag;
        tag = tag.toLowerCase().replaceAll(" ", "_").replaceAll("[^A-Za-z0-9_]", "");
        if (!originalTag.equals(tag)) {
            log("Cleansed Tag: [" + originalTag + "] -> [" + tag + "]");
        }
        return tag;
    }
}
