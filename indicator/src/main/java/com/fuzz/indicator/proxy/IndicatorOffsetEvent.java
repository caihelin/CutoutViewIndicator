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
package com.fuzz.indicator.proxy;

import android.support.annotation.NonNull;

import com.fuzz.indicator.CutoutViewIndicator;
import com.fuzz.indicator.OffSetHint;

/**
 * An OffsetEvent that gives precise scroll information in the spirit of
 * {@link android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled(int, float, int)}.
 *
 * @author Philip Cohn-Cort (Fuzz)
 */
public class IndicatorOffsetEvent implements OffsetEvent {

    protected final int position;

    protected final float fraction;

    protected int orientation;

    @OffSetHint
    protected int offSetHints = OffSetHint.IMAGE_TRANSLATE;

    public IndicatorOffsetEvent(int position, float fraction) {
        this.position = position;
        this.fraction = fraction;
    }

    /**
     * When non-CutoutViewIndicator objects wish to call
     * {@link CutoutViewIndicator#showOffsetIndicator(int, IndicatorOffsetEvent)}, they
     * have two rudimentary options:
     * <ul>
     *     <li>call methods on {@code cvi} to rectify {@code assumedPosition} and determine what orientation to specify</li>
     *     <li>ignore CutoutViewIndicator and always return the same value</li>
     * </ul>
     * This method makes the first option more palatable - its parameters are the same as those to
     * {@link StateProxy#resendPositionInfo(ProxyReference, float)}, so it can smoothly
     * delegate for any implementations of that method.
     *
     * @param cvi                a source of information about CutoutViewIndicator's properties
     * @param assumedPosition    the return value from {@link CutoutViewIndicator#getCurrentIndicatorPosition()}
     * @return a new IndicatorOffsetEvent representing the current state of {@code cvi}.
     */
    @NonNull
    public static IndicatorOffsetEvent from(@NonNull ProxyReference cvi, float assumedPosition) {
        return new IndicatorOffsetEvent(
                cvi.fixPosition((int) assumedPosition),
                assumedPosition - (int) assumedPosition
        );
    }

    public int getPosition() {
        return position;
    }

    @Override
    public float getFraction() {
        return fraction;
    }

    /**
     * See {@link #getOrientation()} for details on the orientation field.
     *
     * @param orientation    the new (horizontal or vertical) orientation value
     */
    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    /**
     * the (vertical or horizontal) orientation in which this offset event
     * should be judged. If this object was constructed by a {@link CutoutViewIndicator},
     * then the return value can be assumed equal to that CutoutViewIndicator's
     * {@link CutoutViewIndicator#getOrientation() orientation}.
     *
     * @return either {@link CutoutViewIndicator#HORIZONTAL HORIZONTAL} or
     * {@link CutoutViewIndicator#VERTICAL VERTICAL}
     */
    public int getOrientation() {
        return orientation;
    }

    /**
     * Call this to change the {@link #offSetHints} attached to
     * this event.
     *
     * @param offSetHints    a bitwise OR of a couple
     *                       {@link OffSetHint} constants
     * @see #getOffSetHints()
     */
    public void setOffSetHints(@OffSetHint int offSetHints) {
        this.offSetHints = offSetHints;
    }

    /**
     * If no hints were set manually, this defaults to {@link OffSetHint#IMAGE_TRANSLATE}.
     *
     * @return the {@link OffSetHint OffSetHints} previously set on this IndicatorOffsetEvent.
     * @see #setOffSetHints(int)
     */
    @OffSetHint
    public int getOffSetHints() {
        return offSetHints;
    }
}
