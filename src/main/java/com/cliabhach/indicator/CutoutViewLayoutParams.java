package com.cliabhach.indicator;

import android.content.Context;
import android.support.annotation.DrawableRes;
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

    public CutoutViewLayoutParams(Context c, AttributeSet attrs) {
        super(c, attrs);
    }

    public CutoutViewLayoutParams(ViewGroup.LayoutParams source) {
        super(source);
        if (source instanceof CutoutViewLayoutParams) {
            CutoutViewLayoutParams cutoutSource = (CutoutViewLayoutParams) source;
            cellBackgroundId = cutoutSource.cellBackgroundId;
            indicatorDrawableId = cutoutSource.indicatorDrawableId;
            internalSpacing = cutoutSource.internalSpacing;
            cellLength = cutoutSource.cellLength;
            perpendicularLength = cutoutSource.perpendicularLength;
        }
    }

    public CutoutViewLayoutParams(int width, int height) {
        super(width, height);
    }
}
