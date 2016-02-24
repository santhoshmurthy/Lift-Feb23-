package com.LAW.lift.model;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class ProximaNovaRegularTextView extends TextView {

    public ProximaNovaRegularTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public ProximaNovaRegularTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ProximaNovaRegularTextView(Context context) {
        super(context);
        init();
    }

    public void init() {

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/ProximaNova-Regular.otf");
        setTypeface(tf);
        //setTextColor(Color.MAGENTA);
        //setTextColor(Color.parseColor("#FB0246"));
        //setLineSpacing(10f, 1f);
    }


}
