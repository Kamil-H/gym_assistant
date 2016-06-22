package com.gymassistant.UIComponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.Spinner;

/**
 * Created by KamilH on 2016-06-22.
 */
public class WhiteSpinner extends Spinner {
    public WhiteSpinner(Context context) {
        super(context);
        initEditText();
    }

    public WhiteSpinner(Context context, int mode) {
        super(context, mode);
        initEditText();
    }

    public WhiteSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }

    public WhiteSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText();
    }

    public WhiteSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
        initEditText();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WhiteSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode) {
        super(context, attrs, defStyleAttr, defStyleRes, mode);
        initEditText();
    }

    @TargetApi(Build.VERSION_CODES.M)
    public WhiteSpinner(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, defStyleRes, mode, popupTheme);
        initEditText();
    }

    private void initEditText(){
        getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
    }
}
