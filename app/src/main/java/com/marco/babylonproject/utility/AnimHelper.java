package com.marco.babylonproject.utility;

import android.animation.Animator;
import android.view.View;


public class AnimHelper {
    public static void animateInfoBanner(View infoBanner) {
        infoBanner.setVisibility(View.VISIBLE);

        infoBanner.animate().setDuration(Constants.ANIM_DURATION)
                .setStartDelay(Constants.ANIM_DELAY)
                .translationY(-infoBanner.getHeight())
                .setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                infoBanner.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }
}
