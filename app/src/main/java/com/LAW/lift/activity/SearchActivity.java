package com.LAW.lift.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.LAW.lift.R;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.model.MyTextviewWhite;


public class SearchActivity extends Activity {
    ImageView back;
    AlertDialogManager alert = new AlertDialogManager();
    private ImageButton btnsearch;
    Button searchbutton;
    private CheckBox sleg;
    private CheckBox scase;
    EditText word;
    String months;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchactivity);
        sleg = (CheckBox) findViewById(R.id.sleg);
        scase = (CheckBox) findViewById(R.id.scase);
        searchbutton = (Button) findViewById(R.id.searchbutton);
        word = (EditText) findViewById(R.id.word);

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


        sleg.setOnCheckedChangeListener(listener);
        scase.setOnCheckedChangeListener(listener);

        searchbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (word.getText().toString().equalsIgnoreCase("")) {
                    word.requestFocus();
                    word.setError("Please Enter the word ?");
                } else {
                    if (sleg.isChecked()) {
                        Intent i = new Intent(SearchActivity.this, SearchLegislation.class);
                        i.putExtra("Word", word.getText().toString().trim());
                        startActivity(i);
                        SearchActivity.this.finish();
                    } else if (scase.isChecked()) {
                        Intent i = new Intent(SearchActivity.this, SearchSupreme.class);
                        i.putExtra("Word", word.getText().toString().trim());
                        startActivity(i);
                        SearchActivity.this.finish();
                    } else
                    {
                        Toast.makeText(SearchActivity.this, "Please Select Any one", Toast.LENGTH_SHORT).show();
                    }


                }




                }

            });


}

private CompoundButton.OnCheckedChangeListener listener = new CompoundButton.OnCheckedChangeListener() {

        public void onCheckedChanged(CompoundButton arg0, boolean isChecked) {
            if (isChecked) {
                switch (arg0.getId()) {
                    case R.id.sleg:
                        sleg.setChecked(true);
                        scase.setChecked(false);
                        break;
                    case R.id.scase:
                        scase.setChecked(true);
                        sleg.setChecked(false);


                }
            }


        }
    };


    @Override
    public void onBackPressed() {
        Intent in = new Intent(SearchActivity.this, MainActivity.class);
        startActivity(in);

        SearchActivity.this.finish();
    }

}
