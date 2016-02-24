package com.LAW.lift.model;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextviewWhite extends TextView {

    public MyTextviewWhite(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public MyTextviewWhite(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextviewWhite(Context context) {
        super(context);
        init();
    }

    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Regular.otf");
        setTypeface(tf);
        setTextColor(Color.WHITE);
        //setTextColor(Color.parseColor("#FFFFFF"));
        //setLineSpacing(10f, 1f);
    }
}

