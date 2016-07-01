package com.gymassistant.UIComponents;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Build;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;

import com.gymassistant.R;
import com.gymassistant.UIComponents.Dialogs.NumberDialog;

/**
 * Created by KamilH on 2016-06-22.
 */
public class NumberEditText extends WhiteEditText {
    private Context context;
    private String titleText;
    private boolean isDouble;

    public NumberEditText(Context context) {
        super(context);
        this.context = context;
    }

    public NumberEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        getTitleText(context, attrs);
    }

    public NumberEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        getTitleText(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public NumberEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.context = context;
        getTitleText(context, attrs);
    }

    private void getTitleText(Context context, AttributeSet attrs){
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyCustomElement, 0, 0);
        this.titleText = typedArray.getString(R.styleable.MyCustomElement_titleText);
        this.isDouble = typedArray.getBoolean(R.styleable.MyCustomElement_isDouble, false);
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if(focused){
            DialogFragment newFragment = NumberDialog.newInstance(titleText, isDouble, new NumberDialog.NumberSetListener() {
                @Override
                public void onNumberSet(String text) {
                    setText(text);
                }
            });
            newFragment.show(((AppCompatActivity)context).getSupportFragmentManager(), "dialog");
            clearFocus();
        }
    }
}