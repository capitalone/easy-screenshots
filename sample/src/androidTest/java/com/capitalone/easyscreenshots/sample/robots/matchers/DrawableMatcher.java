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
package com.capitalone.easyscreenshots.sample.robots.matchers;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import androidx.annotation.DrawableRes;
import androidx.appcompat.view.menu.ActionMenuItemView;
import android.view.View;
import android.widget.ImageView;

import org.hamcrest.Description;
import org.hamcrest.TypeSafeMatcher;

public class DrawableMatcher extends TypeSafeMatcher<View> {

    private final int expectedId;

    String resourceName;

    public DrawableMatcher(@DrawableRes int expectedId) {
        super(View.class);
        this.expectedId = expectedId;
    }

    @Override
    protected boolean matchesSafely(View target) {
        if (target instanceof ImageView || target instanceof ActionMenuItemView) {
            Resources resources = target.getContext().getResources();
            resourceName = resources.getResourceEntryName(expectedId);
            Drawable expectedDrawable = resources.getDrawable(expectedId);

            if (expectedDrawable == null) {
                return false;
            }

            Bitmap expectedBitmap = ((BitmapDrawable) expectedDrawable).getBitmap();

            return target instanceof ImageView ? matchesImageView((ImageView) target, expectedBitmap)
                    : matchesActionMenuItemView((ActionMenuItemView) target, expectedBitmap);
        }

        return false;
    }

    @Override
    public void describeTo(Description description) {
        description.appendText("with drawable from resource id: ");
        description.appendValue(expectedId);
        if (resourceName != null) {
            description.appendText("[");
            description.appendText(resourceName);
            description.appendText("]");
        }
    }

    private boolean matchesImageView(ImageView imageView, Bitmap expectedBitmap) {
        if (expectedId < 0) {
            return imageView.getDrawable() == null;
        }

        Drawable imageViewDrawable = imageView.getDrawable();
        if (imageViewDrawable == null) {
            return false;
        }

        if (imageViewDrawable instanceof BitmapDrawable) {
            Bitmap bitmap = ((BitmapDrawable) imageViewDrawable).getBitmap();
            return bitmap.sameAs(expectedBitmap);
        } else {
            return false;
        }

    }

    // Menu items are not descendents of ImageView, so must be matched differently
    private boolean matchesActionMenuItemView(ActionMenuItemView actionMenuItemView, Bitmap expectedBitmap) {
        Drawable[] drawables = actionMenuItemView.getCompoundDrawables();

        if (drawables.length == 0) {
            return false;
        }

        for (Drawable curr : drawables) {
            if (curr != null && curr instanceof BitmapDrawable) {
                Bitmap currBitmap = ((BitmapDrawable) curr).getBitmap();
                if (currBitmap.sameAs(expectedBitmap)) {
                    return true;
                }
            }
        }
        return false;
    }
}