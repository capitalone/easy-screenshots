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
package com.capitalone.easyscreenshots;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.test.espresso.Espresso;

import com.capitalone.easyscreenshots.config.EasyScreenshotsConfig;
import com.capitalone.easyscreenshots.config.EasyScreenshotsConfigProvider;
import com.capitalone.easyscreenshots.espresso.EasyScreenshotsEspressoFailureHandler;
import com.capitalone.easyscreenshots.fileproviders.ScreenshotFileProvider;
import com.capitalone.easyscreenshots.fileproviders.SpoonScreenshotFileProvider;
import com.capitalone.easyscreenshots.libraryproviders.DefaultScreenshotLibrary;
import com.capitalone.easyscreenshots.libraryproviders.ScreenshotLibrary;

import java.io.File;

public class EasyScreenshots {

    private static final String TAG = "EasyScreenshots";

    private static EasyScreenshots _instance;

    private ScreenshotPolicy policy;

    private ScreenshotLibrary screenshotLibrary;

    private ScreenshotFileProvider screenshotFileProvider;

    private boolean loggingEnabled = true;

    private EasyScreenshots() {
        this(EasyScreenshotsConfigProvider.parse(), new SpoonScreenshotFileProvider());
    }

    EasyScreenshots(EasyScreenshotsConfig config, ScreenshotFileProvider screenshotFileProvider) {
        if (_instance != null) {
            throw new RuntimeException("You cannot initialize EasyScreenshots more than once.");
        }
        _instance = this;

        screenshotLibrary = new DefaultScreenshotLibrary();

        // If instrumentation args were not set, apply defaults
        if (config == null || config.policy == null) {
            config = new EasyScreenshotsConfig();
            config.policy = ScreenshotPolicy.ALL;
        }

        this.policy = config.policy;
        this.screenshotFileProvider = screenshotFileProvider;

        log("EasyScreenshots ScreenshotLibrary: " + this.screenshotLibrary);
        log("EasyScreenshots ScreenshotPolicy: " + this.policy);
        log("EasyScreenshots ScreenshotFileProvider: " + this.screenshotFileProvider);

        clearExistingScreenshots();
        Espresso.setFailureHandler(new EasyScreenshotsEspressoFailureHandler());
    }

    void clearExistingScreenshots() {
        log("Clearing Existing Screenshots");
        screenshotFileProvider.clearPreviousScreenshots();
    }

    public static ScreenshotFileProvider getScreenshotFileProvider() {
        return getInstance().screenshotFileProvider;
    }

    public static EasyScreenshots setScreenshotFileProvider(ScreenshotFileProvider screenshotFileProvider) {
        getInstance().screenshotFileProvider = screenshotFileProvider;
        return getInstance();
    }

    public static File takeScreenshot(String tag) {
        return takeScreenshot(tag, false);
    }

    public static File takeScreenshotForcefully(String tag) {
        return takeScreenshot(tag, true);
    }

    private static File takeScreenshot(String tag, boolean force) {
        if (getInstance().policy == ScreenshotPolicy.ALL || force) {
            getInstance().screenshotLibrary.takeScreenshot(tag);
        }

        return null;
    }

    static EasyScreenshots getInstance() {
        if (_instance == null) {
            synchronized (EasyScreenshots.class) {
                if (_instance == null) {
                    _instance = new EasyScreenshots();
                }
            }
        }
        return _instance;
    }

    public static EasyScreenshots setLoggingEnabled(boolean enabled) {
        getInstance().loggingEnabled = enabled;
        return getInstance();
    }

    public static EasyScreenshots setScreenshotLibrary(@NonNull ScreenshotLibrary screenshotLibrary) {
        if (screenshotLibrary == null) {
            throw new RuntimeException("ScreenshotLibrary cannot be NULL.");
        }
        getInstance().screenshotLibrary = screenshotLibrary;
        return getInstance();
    }

    public static EasyScreenshots setPolicy(@NonNull ScreenshotPolicy policy) {
        if (policy == null) {
            throw new RuntimeException("ScreenshotPolicy cannot be NULL.");
        }
        getInstance().policy = policy;
        return getInstance();
    }

    public static EasyScreenshots setFileProvider(@NonNull ScreenshotFileProvider screenshotFileProvider) {
        if (screenshotFileProvider == null) {
            throw new RuntimeException("ScreenshotFileProvider cannot be NULL.");
        }
        getInstance().screenshotFileProvider = screenshotFileProvider;
        return getInstance();
    }

    public static ScreenshotLibrary getScreenshotLibrary() {
        return getInstance().screenshotLibrary;
    }

    public static ScreenshotPolicy getPolicy() {
        return getInstance().policy;
    }

    public static File getScreenshotFile(String tag) {
        return getInstance().screenshotFileProvider.getScreenshotFile(tag);
    }

    // Basically, use this to change the defaults -- is overridden by instrumentation args
    public static class Initializer {

        ScreenshotFileProvider _screenshotFileProvider = new SpoonScreenshotFileProvider();

        EasyScreenshotsConfig config = new EasyScreenshotsConfig();

        public Initializer() {
        }

        public Initializer screenshotFileProvider(ScreenshotFileProvider screenshotFileProvider) {
            this._screenshotFileProvider = screenshotFileProvider;
            return this;
        }

        public Initializer policy(ScreenshotPolicy policy) {
            config.policy = policy;
            return this;
        }

        public void init() {
            EasyScreenshotsConfig instrumentationArgsConfig = EasyScreenshotsConfigProvider.parse();
            if (instrumentationArgsConfig != null) {
                if (instrumentationArgsConfig.policy != null) {
                    config.policy = instrumentationArgsConfig.policy;
                }
            }

            _instance = new EasyScreenshots(config, _screenshotFileProvider);
        }
    }

    public static void log(String message) {
        log(TAG, message);
    }

    public static void log(String tag, String message) {
        if (getInstance().loggingEnabled) {
            Log.v(tag, message);
        }
    }

    public static void log(String message, Throwable ex) {
        log(EasyScreenshots.class.getSimpleName(), message, ex);
    }

    public static void log(String tag, String message, Throwable ex) {
        if (getInstance().loggingEnabled) {
            Log.e(tag, message, ex);
        }
    }
}
