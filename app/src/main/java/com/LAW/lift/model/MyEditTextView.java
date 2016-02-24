package com.LAW.lift.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.EditText;

public class MyEditTextView extends EditText {

    public MyEditTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyEditTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyEditTextView(Context context) {
        super(context);
        init();
    }

    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Regular.otf");
        setTypeface(tf);

        //setTextColor(Color.parseColor("#FFFFFF"));
        //setLineSpacing(10f, 1f);
    }
}

