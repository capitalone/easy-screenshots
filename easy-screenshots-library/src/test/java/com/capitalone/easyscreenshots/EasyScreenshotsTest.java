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

import android.content.Context;
import android.os.Bundle;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import android.util.Log;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;

import java.lang.reflect.Field;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Espresso.class, Log.class, InstrumentationRegistry.class})
@SuppressStaticInitializationFor("android.support.test.espresso.Espresso")
public class EasyScreenshotsTest {

    @Before
    public void setUp() throws Exception {
        mockStatic(Espresso.class);
        mockStatic(Log.class);
        mockStatic(InstrumentationRegistry.class);

        when(InstrumentationRegistry.getTargetContext()).thenReturn(mock(Context.class));

        // Reset singleton
        Field instance = EasyScreenshots.class.getDeclaredField("_instance");
        instance.setAccessible(true);
        instance.set(null, null);
    }

    @Test
    public void testInitializer_Defaults() throws Exception {
        new EasyScreenshots.Initializer()
                .init();

        // If no instrumentation args are specified, use default
        assertThat(EasyScreenshots.getPolicy()).isEqualTo(ScreenshotPolicy.ALL);
    }

    @Test
    public void testInitializer_Override() throws Exception {
        new EasyScreenshots.Initializer()
                .policy(ScreenshotPolicy.FAILURES)
                .init();

        // Specified policy should override the default
        assertThat(EasyScreenshots.getPolicy()).isEqualTo(ScreenshotPolicy.FAILURES);
    }

    @Test
    public void testInitializer_InstrumentationArgs() throws Exception {
        Bundle bundle = mock(Bundle.class);
        when(bundle.containsKey("screenshot-policy")).thenReturn(true);
        when(bundle.getString("screenshot-policy")).thenReturn("FAILURES");
        when(InstrumentationRegistry.getArguments()).thenReturn(bundle);

        new EasyScreenshots.Initializer()
                .policy(ScreenshotPolicy.NONE)
                .init();

        // Instrumentation Args take priority, FAILURES is taken
        assertThat(EasyScreenshots.getPolicy()).isEqualTo(ScreenshotPolicy.FAILURES);
    }
}
