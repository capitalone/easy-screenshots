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

import android.Manifest;
import android.os.Bundle;
import androidx.test.InstrumentationRegistry;
import androidx.test.runner.AndroidJUnitRunner;

import com.capitalone.easyscreenshots.EasyScreenshots;
import com.linkedin.android.testbutler.TestButler;

/**
 * Allows the user to pass in an instrumentation parameters to customize EasyScreenshots. Defaults are already set in
 * {@link EasyScreenshots#EasyScreenshots()}.
 */
public class CustomAndroidJUnitRunner extends AndroidJUnitRunner {

    @Override
    public void onStart() {
        TestButler.setup(InstrumentationRegistry.getTargetContext());
        TestButler.verifyAnimationsDisabled(InstrumentationRegistry.getTargetContext());
        TestButler.grantPermission(InstrumentationRegistry.getTargetContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        super.onStart();
    }

    @Override
    public void finish(int resultCode, Bundle results) {
        TestButler.teardown(InstrumentationRegistry.getTargetContext());
        super.finish(resultCode, results);
    }
}