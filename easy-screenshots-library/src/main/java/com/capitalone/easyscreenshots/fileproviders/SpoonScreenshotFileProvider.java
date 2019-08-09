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

import android.os.Build;
import androidx.annotation.NonNull;
import androidx.test.InstrumentationRegistry;

import java.io.File;
import java.util.regex.Pattern;


import static android.content.Context.MODE_WORLD_READABLE;
import static android.os.Environment.getExternalStorageDirectory;
import static com.capitalone.easyscreenshots.EasyScreenshots.log;

public class SpoonScreenshotFileProvider extends ScreenshotFileProvider {

    private static final String TAG = SpoonScreenshotFileProvider.class.getSimpleName();

    static final String SPOON_SCREENSHOTS = "spoon-screenshots";

    static final String NAME_SEPARATOR = "_";

    private static final String EXTENSION = ".png";

    private static final Pattern TAG_VALIDATION = Pattern.compile("[a-zA-Z0-9_-]+");

    private static final int LOLLIPOP_API_LEVEL = 21;

    @Override
    public File getScreenshotFile(@NonNull String tag, @NonNull StackTraceElement stackTraceElement) {
        tag = cleanseTag(tag);

        log(TAG,
                String.format("Falcon Screenshot: %s, %s, %s", tag, stackTraceElement.getClassName(),
                        stackTraceElement.getMethodName()));

        String testClassName = stackTraceElement.getClassName();
        String testMethodName = stackTraceElement.getMethodName();
        if (!TAG_VALIDATION.matcher(tag).matches()) {
            throw new IllegalArgumentException("Tag must match " + TAG_VALIDATION.pattern() + ".");
        }
        try {
            File screenshotDirectory = filesDirectory(testClassName, testMethodName);
            String screenshotName = System.currentTimeMillis() + NAME_SEPARATOR + tag + EXTENSION;
            File screenshotFile = new File(screenshotDirectory, screenshotName);
            return screenshotFile;
        } catch (Exception e) {
            RuntimeException runtimeException = new RuntimeException("Unable to capture screenshot.  Do you have the 'android.permission.WRITE_EXTERNAL_STORAGE' permission requested and granted?", e);
            log(TAG, runtimeException.getMessage(), runtimeException);
            throw runtimeException;
        }
    }

    @Override
    public void clearPreviousScreenshots() {
        log("Clearing Previous Screenshots");
        File spoonScreenshotsDir = getSpoonScreenshotsDirectory();
        if (spoonScreenshotsDir != null && spoonScreenshotsDir.exists()) {
            // Delete Spoon screenshots from last run
            deletePath(spoonScreenshotsDir, true);
        }
        log("Previous Screenshots Cleared");
    }

    public static File getSpoonScreenshotsDirectory() {
        File directory;
        if (Build.VERSION.SDK_INT >= LOLLIPOP_API_LEVEL) {
            // Use external storage.
            directory = new File(getExternalStorageDirectory(), "app_" + SPOON_SCREENSHOTS);
            log("Using External Storage");
        } else {
            // Use internal storage.
            directory = InstrumentationRegistry.getTargetContext().getDir(SPOON_SCREENSHOTS, MODE_WORLD_READABLE);
            log("Using Internal Storage");
        }
        log(TAG, "Spoon Screenshots Directory: " + getCanonicalPath(directory));
        return directory;
    }

    public static File filesDirectory(String testClassName, String testMethodName) throws IllegalAccessException {
        File directory = getSpoonScreenshotsDirectory();
        File dirClass = new File(directory, testClassName);
        File dirMethod = new File(dirClass, testMethodName);
        createDir(dirMethod);
        log(TAG, "Directory for test method: " + getCanonicalPath(dirMethod));
        return dirMethod;
    }

    private static void createDir(File dir) throws IllegalAccessException {
        log(TAG, "Creating Directory: " + getCanonicalPath(dir));

        if (!dir.exists() && !dir.mkdirs()) {
            throw new IllegalAccessException("Unable to create output dir: " + dir.getAbsolutePath());
        }
        makeRWX(dir);
    }

    private static void makeRWX(File file) {
        log(TAG, "Making File RWX");
        file.setReadable(true, false);
        file.setWritable(true, false);
        file.setExecutable(true, false);
        log(TAG, "Finished Making File RWX");
    }

    public static void deletePath(File path, boolean inclusive) {
        log(TAG, "Clearing Path: " + getCanonicalPath(path));
        if (path.isDirectory()) {
            File[] children = path.listFiles();
            if (children != null) {
                for (File child : children) {
                    deletePath(child, true);
                }
            }
        }
        if (inclusive) {
            path.delete();
        }
        log(TAG, "Clearing Complete");
    }

    private static String getCanonicalPath(File file) {
        try {
            return file.getCanonicalPath();
        } catch (Exception e) {
            log(TAG, e.getMessage(), e);
        }
        return null;
    }

}
