package com.LAW.lift.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LAW.lift.R;
import com.LAW.lift.adapter.Searchsupremeadapter;
import com.LAW.lift.adapter.supremeadapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.model.Searchlegislationcard;
import com.LAW.lift.model.Searchsupremecard;
import com.LAW.lift.model.supremecard;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SearchSupreme  extends Activity {

    ImageView back;
    TextView textsupreme;
    public static String[] get_legislation;
    public static String[] book_name;
    public static String[] book_icon;
    public static String[] year;
    public static String[] month;
    public static String[] name;
    public static String[] court;
    public static String[] title;
    public static String[] context;
    public static String[] question;
    public static String[] answer;
    public static String[] reference;
    //public static String[] url;


    String  casebookname,titlepara, contextpara, questionpara, answerpara, referencepara;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/lift/v1/search";
    ListView scaselist;
    String bookid;
    String months;
    String word;
    private Searchsupremeadapter ride;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchsupremelist);

        LiftApplication.getInstance().trackScreenView("Supreme Court");
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        textsupreme = (TextView) findViewById(R.id.textsupreme);
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


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //bus_names=extras.getStringArray("BusNames");


            word = extras.getString("Word");
            Log.d("tamil", word );


        }
       /* textsupreme.setText(months);
        if(MainActivity.Language.equals("தமிழ்")){
            urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/get_case_law.php?book_id=";
            supreme="&type=sc";
        }*/
        cd = new ConnectionDetector(getApplicationContext());
        scaselist = (ListView) findViewById(R.id.scaselist);

        ride = new Searchsupremeadapter(SearchSupreme.this, R.layout.searchsupreme);
        scaselist.setAdapter(ride);


        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(SearchSupreme.this, AlertDialog.THEME_HOLO_LIGHT);
            helpBuilder.setTitle("Connection offline");
            helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
            helpBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkIntent = new Intent(SearchSupreme.this, MainActivity.class);
                            startActivity(checkIntent);
                            finish();
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } else {

            pDialog = new ProgressDialog(SearchSupreme.this);
            pDialog.setMessage("Loading....");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue queue = MyVolley.getRequestQueue();


            StringRequest postReq = new StringRequest(Request.Method.POST,
                    urlJsonArry,
                    new Response.Listener<String>() {
                        public void onResponse(String response) {
                            //Toast.makeText(getApplicationContext(), "response " + response, Toast.LENGTH_LONG).show();
                            try {
                                JSONArray jobject = new JSONArray(response);

                                Log.e("History", "response.get_case_law" + response);




                                title = new String[jobject.length()];
                                context = new String[jobject.length()];
                                question = new String[jobject.length()];
                                answer = new String[jobject.length()];
                                reference = new String[jobject.length()];
                                book_name = new String[jobject.length()];



                                if (jobject.length()!=0){



                                    for (int i = 0; i < jobject.length(); i++) {


                                        JSONObject person = (JSONObject) jobject.get(i);


                                        //book_icon[i] = person.getString("book_icon");
                                        //year[i] = person.getString("year");
                                        //month[i] = person.getString("month");
                                        // court[i] = person.getString("court");
                                        title[i] = person.getString("title");
                                        context[i] = person.getString("context");
                                        question[i] = person.getString("question");
                                        answer[i] = person.getString("answer");
                                        reference[i] = person.getString("reference");
                                        book_name[i] = person.getString("name");
                                        // url[i] = person.getString("url");

                                        casebookname=person.getString("name");
                                        titlepara = person.getString("title");
                                        contextpara = person.getString("context");
                                        questionpara = person.getString("question");
                                        answerpara = person.getString("answer");
                                        referencepara = person.getString("reference");

                                        //texttouch = person.getString("url");



                                        Searchsupremecard searchsupremecard = new Searchsupremecard(casebookname,titlepara, contextpara, questionpara, answerpara, referencepara);
                                        ride.add(searchsupremecard);

                                    }



                                }else{

                                    AlertDialog.Builder helpBuilder = new AlertDialog.Builder(SearchSupreme.this, AlertDialog.THEME_HOLO_LIGHT);
                                    helpBuilder.setTitle("Results");
                                    helpBuilder.setMessage("No Result available");
                                    helpBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent checkIntent = new Intent(SearchSupreme.this, SearchActivity.class);
                                                    startActivity(checkIntent);
                                                    finish();
                                                }
                                            });
                                    AlertDialog helpDialog = helpBuilder.create();
                                    helpDialog.show();



                                }



                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                //mTvResult.setText(jsonResponse);

                            } catch (JSONException e) {
                                e.printStackTrace();
                                LiftApplication.getInstance().trackException(e);

                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                            }
                        }
                    }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    if (pDialog.isShowing())
                        pDialog.dismiss();
                    Log.e("Hiistory", "error  " + error);
                }

            }) {
                @Override
                protected Map<String, String> getParams() throws
                        AuthFailureError {

                    HashMap<String, String> params = new HashMap<>();
                    params.put("word", word);
                    params.put("lawtype", "caselaw");

                    return params;
                }

            };

            queue.add(postReq);
            scaselist.setAdapter(ride);


        }
    }
    @Override
    public void onBackPressed() {
       /* Intent in = new Intent(SearchLegislation.this, MainActivity.class);
        startActivity(in);*/
        finish();
    }
}