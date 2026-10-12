package com.ibasso.music.widgets.tablayout;

import android.view.View;
import com.ibasso.music.widgets.tablayout.ViewPager;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
public final class ZoomOutPageTransformer implements ViewPager.PageTransformer {
    @NotNull
    public static final Companion Companion = new Companion(null);
    private static final float MIN_ALPHA = 0.9f;
    private static final float MIN_SCALE = 0.95f;

    public static final class Companion {
        private Companion() {
        }

        public Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    @Override
    public void transformPage(@NotNull View page, float f) {
        Intrinsics.checkNotNullParameter(page, "page");
        int width = page.getWidth();
        int height = page.getHeight();
        if (f <= -1.0f) {
            page.setAlpha(1.0f);
        } else if (f >= 1.0f) {
            page.setAlpha(1.0f);
        } else {
            float f2 = 1;
            float max = Math.max((float) MIN_SCALE, f2 - Math.abs(f));
            float f3 = f2 - max;
            float f4 = 2;
            float f5 = (height * f3) / f4;
            float f6 = (width * f3) / f4;
            if (f < 0.0f) {
                page.setTranslationX(f6 - (f5 / f4));
            } else {
                page.setTranslationX((-f6) + (f5 / f4));
            }
            page.setScaleX(max);
            page.setScaleY(max);
            page.setAlpha(Math.max((float) MIN_ALPHA, f2 - Math.abs(f)));
        }
    }
}
