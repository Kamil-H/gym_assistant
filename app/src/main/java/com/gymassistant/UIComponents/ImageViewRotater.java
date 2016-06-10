package com.gymassistant.UIComponents;

import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * Created by KamilH on 2016-06-09.
 */
public class ImageViewRotater {
    private static final float INITIAL_POSITION = 0.0f;
    private static final float ROTATED_POSITION = 180f;

    public static void rotateImageView(boolean isExpanded, ImageView imageView){
        RotateAnimation rotateAnimation;
        if (isExpanded) { // rotate clockwise
            imageView.setRotation(INITIAL_POSITION);
            rotateAnimation = new RotateAnimation(ROTATED_POSITION, INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        } else { // rotate counterclockwise
            imageView.setRotation(ROTATED_POSITION);
            rotateAnimation = new RotateAnimation(-1 * ROTATED_POSITION, INITIAL_POSITION,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f,
                    RotateAnimation.RELATIVE_TO_SELF, 0.5f);
        }

        rotateAnimation.setDuration(200);
        rotateAnimation.setFillAfter(true);
        imageView.startAnimation(rotateAnimation);
    }
}
