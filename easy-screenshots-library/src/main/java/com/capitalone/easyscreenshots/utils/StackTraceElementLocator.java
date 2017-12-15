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

import android.os.Build;


import static com.capitalone.easyscreenshots.EasyScreenshots.log;

public abstract class StackTraceElementLocator {

    public static StackTraceElement getTestMethodStackTraceElement() {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement stackTraceElement = findTestClassTraceElement(stackTrace);
        return stackTraceElement;
    }

    static final String TEST_CASE_CLASS_JUNIT_3 = "android.test.InstrumentationTestCase";

    static final String TEST_CASE_METHOD_JUNIT_3 = "runMethod";

    static final String TEST_CASE_CLASS_JUNIT_4 = "org.junit.runners.model.FrameworkMethod$1";

    static final String TEST_CASE_METHOD_JUNIT_4 = "runReflectiveCall";

    static final String TEST_CASE_CLASS_CUCUMBER_JVM = "cucumber.runtime.model.CucumberFeature";

    static final String TEST_CASE_METHOD_CUCUMBER_JVM = "run";

    private static final int MARSHMALLOW_API_LEVEL = 23;

    /** Returns the test class element by looking at the method InstrumentationTestCase invokes. */
    static StackTraceElement findTestClassTraceElement(StackTraceElement[] trace) {
        for (int i = trace.length - 1; i >= 0; i--) {
            StackTraceElement element = trace[i];
            if (TEST_CASE_CLASS_JUNIT_3.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_3.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }

            if (TEST_CASE_CLASS_JUNIT_4.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_JUNIT_4.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }
            if (TEST_CASE_CLASS_CUCUMBER_JVM.equals(element.getClassName()) //
                    && TEST_CASE_METHOD_CUCUMBER_JVM.equals(element.getMethodName())) {
                return extractStackElement(trace, i);
            }
        }

        throw new IllegalArgumentException("Could not find test class!");
    }

    private static StackTraceElement extractStackElement(StackTraceElement[] trace, int i) {
        // Stacktrace length changed in M
        int testClassTraceIndex = Build.VERSION.SDK_INT >= MARSHMALLOW_API_LEVEL ? (i - 2) : (i - 3);
        StackTraceElement stackTraceElement = trace[testClassTraceIndex];
        log("Found Stack Trace Element: " + stackTraceElement);
        return stackTraceElement;
    }
}
