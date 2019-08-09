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

import android.content.Context;
import android.os.Bundle;
import androidx.test.InstrumentationRegistry;
import androidx.test.espresso.Espresso;
import android.util.Log;

import com.capitalone.easyscreenshots.ScreenshotPolicy;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.core.classloader.annotations.SuppressStaticInitializationFor;
import org.powermock.modules.junit4.PowerMockRunner;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.powermock.api.mockito.PowerMockito.mockStatic;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Espresso.class, Log.class, InstrumentationRegistry.class})
@SuppressStaticInitializationFor("android.support.test.espresso.Espresso")
public class ConfigProviderTest {

    @Before
    public void setUp() throws Exception {
        mockStatic(Espresso.class);
        mockStatic(Log.class);
        mockStatic(InstrumentationRegistry.class);

        when(InstrumentationRegistry.getTargetContext()).thenReturn(mock(Context.class));
    }

    @Test
    public void testParseInstrumentationArgs() throws Exception {
        Bundle bundle = mock(Bundle.class);
        when(bundle.containsKey("screenshot-policy")).thenReturn(true);
        when(bundle.getString("screenshot-policy")).thenReturn("NONE");
        when(InstrumentationRegistry.getArguments()).thenReturn(bundle);

        EasyScreenshotsConfig config = EasyScreenshotsConfigProvider.parse();
        assertThat(config.policy).isEqualTo(ScreenshotPolicy.NONE);
    }

    @Test(expected = RuntimeException.class)
    public void testParseInstrumentationArgsWithBlanks() throws Exception {
        Bundle bundle = mock(Bundle.class);
        when(bundle.containsKey("screenshot-policy")).thenReturn(true);
        when(bundle.getString("screenshot-policy")).thenReturn("");
        when(InstrumentationRegistry.getArguments()).thenReturn(bundle);

        //Should throw exception
        EasyScreenshotsConfigProvider.parse();
    }

}
