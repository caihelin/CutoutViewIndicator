/*
 * Copyright 2016 Philip Cohn-Cort
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuzz.indicator;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

/**
 * Plain Old Java Object representing the configuration of the child views inside
 * {@link CutoutViewIndicator}.
 *
 * @author Philip Cohn-Cort (Fuzz)
 */
public class CutoutViewLayoutParams extends LinearLayout.LayoutParams {
    @DrawableRes
    public int cellBackgroundId;
    /**
     * This is the id of the drawable currently acting as indicator. If 0, no indicator will be shown.
     */
    @DrawableRes
    public int indicatorDrawableId;



    public int internalSpacing;
    /**
     * This is the resolved dimension (in pixels)
     */
    public int cellLength;
    /**
     * This is the height or width bounding all child views when {@link #HORIZONTAL} or {@link #VERTICAL}, respectively.
     * <p/>
     * Typically equal to the height/width of the {@link CutoutViewIndicator}, minus padding.
     */
    public int perpendicularLength;

    /**
     * Utility method for setting height and width in an orientation-independent way
     *
     * @param dimension the new value for either height or width, in pixels
     * @param isHeight true if this is height, false if this is width
     */
    public void setParamDimension(int dimension, boolean isHeight) {
        if (isHeight) {
            height = dimension;
        } else {
            width = dimension;
        }
    }

    public CutoutViewLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public CutoutViewLayoutParams(@NonNull ViewGroup.LayoutParams source) {
        super(source);
        if (source instanceof CutoutViewLayoutParams) {
            CutoutViewLayoutParams cutoutSource = (CutoutViewLayoutParams) source;
            setFrom(cutoutSource);
        }
    }

    public CutoutViewLayoutParams(int width, int height) {
        super(width, height);
    }

    public void setFrom(@NonNull CutoutViewLayoutParams cutoutSource) {
        cellBackgroundId = cutoutSource.cellBackgroundId;
        indicatorDrawableId = cutoutSource.indicatorDrawableId;
        internalSpacing = cutoutSource.internalSpacing;
        cellLength = cutoutSource.cellLength;
        perpendicularLength = cutoutSource.perpendicularLength;
    }

    /**
     * Unlike calling the constructor, if the passed parameters are already of this
     * type they will be returned directly.
     *
     * @param layoutParams any layoutParams, ideally descendants of {@link CutoutViewLayoutParams}
     * @return valid CutoutViewLayoutParams
     */
    public static CutoutViewLayoutParams from(@NonNull ViewGroup.LayoutParams layoutParams) {
        if (layoutParams instanceof CutoutViewLayoutParams) {
            return (CutoutViewLayoutParams) layoutParams;
        } else {
            return new CutoutViewLayoutParams(layoutParams);
        }
    }
}