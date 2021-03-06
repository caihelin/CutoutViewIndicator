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
package com.fuzz.emptyhusk;

import android.os.Build;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static android.widget.LinearLayout.HORIZONTAL;
import static android.widget.LinearLayout.VERTICAL;
import static android.widget.RelativeLayout.ALIGN_TOP;
import static android.widget.RelativeLayout.BELOW;
import static android.widget.RelativeLayout.CENTER_HORIZONTAL;
import static android.widget.RelativeLayout.RIGHT_OF;
import static android.widget.RelativeLayout.TRUE;

/**
 * This ensures that orientation AND {@link RelativeLayout} alignments are
 * changed appropriately whenever the {@link MainActivity MainActivity's}
 * orientation toggle is flipped.
 *
 * @author Philip Cohn-Cort (Fuzz)
 */
class ToggleAlignmentListener implements CompoundButton.OnCheckedChangeListener {
    /**
     * Counterpart to {@link RelativeLayout#TRUE}
     */
    private static final int FALSE = 0;
    private final LinearLayout alignedLayout;
    private final View anchor;

    /**
     * It is assumed that both parameters are siblings, and that their parent
     * is of type {@link RelativeLayout}.
     * @param alignedLayout    this can be made HORIZONTAL or VERTICAL
     * @param anchor           this is either below or to the end of the alignedLayout
     */
    public ToggleAlignmentListener(LinearLayout alignedLayout, View anchor) {
        this.alignedLayout = alignedLayout;
        this.anchor = anchor;
    }

    @Override
    public void onCheckedChanged(CompoundButton view, boolean isChecked) {
        int belowSubject;
        int rightOfSubject;
        int centerGravity;

        RelativeLayout.LayoutParams anchorParams = (RelativeLayout.LayoutParams) anchor.getLayoutParams();
        RelativeLayout.LayoutParams alignedParams = (RelativeLayout.LayoutParams) alignedLayout.getLayoutParams();

        if (isChecked) {
            alignedLayout.setOrientation(VERTICAL);
            centerGravity = FALSE;
            rightOfSubject = alignedLayout.getId();
            belowSubject = 0;
        } else {
            alignedLayout.setOrientation(HORIZONTAL);
            centerGravity = TRUE;
            rightOfSubject = 0;
            belowSubject = alignedLayout.getId();
        }

        alignedParams.addRule(CENTER_HORIZONTAL, centerGravity);

        anchorParams.addRule(BELOW, belowSubject);
        anchorParams.addRule(ALIGN_TOP, rightOfSubject);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            // Setting a non-0 subject to END_OF implicitly sets RIGHT_OF. But setting 0 to
            // END_OF does not clear RIGHT_OF, triggering a rather insidious bug.
            // Explicitly removing the RIGHT_OF value ensures that only the relative direction
            // is used during resolution.
            anchorParams.removeRule(RIGHT_OF);
            anchorParams.addRule(RelativeLayout.END_OF, rightOfSubject);
        } else {
            anchorParams.addRule(RIGHT_OF, rightOfSubject);
        }
    }
}
