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
package com.capitalone.easyscreenshots.sample.robots.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AndroidTestPreferenceHelpers {

    private final Context applicationContext;

    public AndroidTestPreferenceHelpers(Context applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void clearAllPreferences() {
        clearAllPreferences(false);
    }

    public void clearAllPreferences(boolean verboseLogging) {
        List<String> preferenceBundleNames = getPreferenceBundleNames();

        if (verboseLogging) {
            printAllPreferences();
        }

        for (String bundleName : preferenceBundleNames) {
            clearPreferenceBundle(bundleName);
        }

        if (verboseLogging) {
            printAllPreferences();
        }
    }

    public void printAllPreferences() {
        List<String> list = getPreferenceBundleNames();
        for (String bundleName : list) {
            printPreferenceBundle(bundleName);
        }
    }

    public List<String> getPreferenceBundleNames() {
        File sharedPrefsDir = new File(applicationContext.getFilesDir(), "../shared_prefs");
        File[] preferenceFiles = sharedPrefsDir.listFiles();
        List preferenceBundleNames = new ArrayList<>();
        for (File preferenceFile : preferenceFiles) {
            String preferenceName = preferenceFile.getName().replace(".xml", "");
            preferenceBundleNames.add(preferenceName);
        }
        return preferenceBundleNames;
    }

    public void printPreferenceBundle(String bundleName) {
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(bundleName, Context.MODE_PRIVATE);
        for (String preferenceKey : sharedPreferences.getAll().keySet()) {
            Log.v(getClass().getSimpleName(),
                    "-- " + preferenceKey + "=" + sharedPreferences.getAll().get(preferenceKey));
        }
    }

    public void clearPreferenceBundle(String bundleName) {
        SharedPreferences sharedPreferences = applicationContext.getSharedPreferences(bundleName, Context.MODE_PRIVATE);
        sharedPreferences.edit().clear().commit();
    }
}