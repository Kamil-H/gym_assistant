package com.gymassistant.UIComponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

/**
 * Created by KamilH on 2016-06-22.
 */
public class WhiteEditText extends EditText {
    public WhiteEditText(Context context) {
        super(context);
        initEditText();
    }

    public WhiteEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initEditText();
    }

    public WhiteEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initEditText();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public WhiteEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initEditText();
    }

    private void initEditText(){
        getBackground().setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        setTextColor(Color.WHITE);
    }
}
