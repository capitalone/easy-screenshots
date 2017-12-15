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
package com.capitalone.easyscreenshots.config;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.test.InstrumentationRegistry;

import com.capitalone.easyscreenshots.ScreenshotPolicy;

import java.util.Arrays;

public class EasyScreenshotsConfigProvider {

    private static final String TAG = EasyScreenshotsConfigProvider.class.getSimpleName();

    public static final String ARG_SCREENSHOT_POLICY = "screenshot-policy";

    @Nullable
    public static EasyScreenshotsConfig parse() {
        return parse(InstrumentationRegistry.getArguments());
    }

    @Nullable
    static EasyScreenshotsConfig parse(Bundle arguments) {
        if (arguments == null) {
            return null;
        }

        EasyScreenshotsConfig args = new EasyScreenshotsConfig();

        if (arguments.containsKey(ARG_SCREENSHOT_POLICY)) {
            String policyArgValue = arguments.getString(ARG_SCREENSHOT_POLICY);
            try {
                args.policy = ScreenshotPolicy.valueOf(policyArgValue);
            } catch (IllegalArgumentException e) {
                throw new RuntimeException("Bad argument \"" + policyArgValue
                        + "\" for the screenshot-policy. Allowed values: " + Arrays.toString(ScreenshotPolicy.values()));
            }
        }

        return args;
    }

}