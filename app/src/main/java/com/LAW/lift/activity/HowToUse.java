package com.LAW.lift.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.LAW.lift.R;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.model.MyTextviewWhite;


public class HowToUse extends Activity {
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.howtouse);


        LiftApplication.getInstance().trackScreenView("How to Use");
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        ((MyTextviewWhite) v.findViewById(R.id.title)).setText(this.getTitle());
        this.getActionBar().setCustomView(v);


        back = (ImageView) findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent open = new Intent(CentralLegislation.this,MainActivity.class);
                startActivity(open);*/
                finish();

            }
        });

    }

    @Override
    public void onBackPressed() {
       /* Intent in = new Intent(CentralLegislation.this, MainActivity.class);
        startActivity(in);*/

        finish();
    }

}






