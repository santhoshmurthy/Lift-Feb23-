package com.LAW.lift.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.adapter.CardArrayAdapter;
import com.LAW.lift.adapter.LibraryAdapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.LAW.lift.model.Card;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.model.librarycard;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;


public class Library extends Activity {
    public static String[] get_legislation;
    public static String[] Id;
    public static String[] Book_Name;
    public static String[] Url;
    public static String[] Published_Month;
    public static String[] month;
    public String[] year;
    public static String years;
    String selectedyear;
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/get_all_books.php?year=";
    String jsonPorturl = "http://www.lawinfingertips.com/webservice/Lift_Final/get_year.php?id=1";
    Spinner spinner1;
    String book;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    public ArrayAdapter<String> adapter;
    private LibraryAdapter libraryAdapter;
    GridView gridView;
    String retimag, monthtext;
    ImageView back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.librarylist);

        LiftApplication.getInstance().trackScreenView("Library");
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
                Intent open = new Intent(Library.this, MainActivity.class);
                startActivity(open);
                finish();

            }
        });

        if(MainActivity.Language.equals("தமிழ்")){
            urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/get_all_books.php?year=";
            jsonPorturl = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/get_year.php?id=1";
        }

        cd = new ConnectionDetector(getApplicationContext());
        gridView = (GridView) findViewById(R.id.gridView);


        spinner1 = (Spinner) findViewById(R.id.spinner1);


        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(Library.this, AlertDialog.THEME_HOLO_LIGHT);
            helpBuilder.setTitle("Connection offline");
            helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
            helpBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkIntent = new Intent(Library.this, MainActivity.class);
                            startActivity(checkIntent);
                            finish();
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } else {

            pDialog = new ProgressDialog(Library.this);
            pDialog.setMessage("Loading....");
            pDialog.setCancelable(false);
            pDialog.show();


            RequestQueue queue = MyVolley.getRequestQueue();
            JsonObjectRequest req = new JsonObjectRequest(jsonPorturl, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.e("History", "response.get_year" + response);

                                JSONObject jobject = response;

                                JSONArray jsonMainArr = jobject.getJSONArray("get_year");

                                year = new String[jsonMainArr.length()];


                                for (int i = 0; i < jsonMainArr.length(); i++) {

                                    JSONObject person = (JSONObject) jsonMainArr.get(i);

                                    year[i] = person.getString("year");

                                    years = person.getString("year");
                                    year[i] = years;

                                    Log.d("Library", years);
                                }


                                //mTvResult.setText(jsonResponse);
                                new PortNumber().execute();
                            } catch (JSONException e) {
                                e.printStackTrace();
                                LiftApplication.getInstance().trackException(e);


                            }

                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    Log.e("Hiistory", "error  " + error);
                    // listView.setVisibility(View.GONE);
                }


            });
            queue.add(req);

            spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    final String Url_one[]={"url","url","url","url","url","url","url","url","url","url","url","url"};
                    final String[] month_one={"month","month","month","month","month","month","month","month","month","month","month","month"};
                    years = spinner1.getSelectedItem().toString();
                    selectedyear = spinner1.getSelectedItem().toString();
                    libraryAdapter = new LibraryAdapter(Library.this, R.layout.library);
                    gridView.setAdapter(libraryAdapter);
                    libraryAdapter.clear();


                    RequestQueue queue1 = MyVolley.getRequestQueue();

                    JsonObjectRequest req1 = new JsonObjectRequest(urlJsonArry + selectedyear, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {

                                    try {
                                        Log.e("Library", "response.get_all_book" + response + selectedyear);

                                        JSONObject jobject = response;

                                        JSONArray jsonMainArr = jobject.getJSONArray("get_all_book");

                                        Id = new String[jsonMainArr.length()];
                                        Book_Name = new String[jsonMainArr.length()];
                                        Url = new String[jsonMainArr.length()];
                                        Published_Month = new String[jsonMainArr.length()];
                                        month = new String[jsonMainArr.length()];


                                        for (int i = 0; i < jsonMainArr.length(); i++) {

                                            JSONObject person = (JSONObject) jsonMainArr.get(i);

                                            Id[i] = person.getString("Id");
                                            Book_Name[i] = person.getString("Book_Name");
                                            Url[i] = person.getString("Url");
                                            Published_Month[i] = person.getString("Published_Month");
                                            month[i] = person.getString("month");



                                     /*  = Url[i];
                                        =month[i];*/


                                            retimag = person.getString("Url");
                                            monthtext = person.getString("month");


                                        }
                                        for (int i = 0; i < jsonMainArr.length(); i++) {
                                            Url_one[i] = Url[i];
                                            month_one[i] = month[i];
                                        }
                                        for (int i = 0; i < 12; i++) {
                                            librarycard librarycard = new librarycard(Url_one[i], month_one[i]);
                                            libraryAdapter.add(librarycard);
                                            if (pDialog.isShowing())
                                                pDialog.dismiss();
                                        }
                                        Log.d("liblib", "" + Url[1] + "  " + Url_one[1]);


                                        //mTvResult.setText(jsonResponse);

                                    } catch (RuntimeException e) {
                                        e.printStackTrace();
                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        LiftApplication.getInstance().trackException(e);

                                        if (pDialog.isShowing())
                                            pDialog.dismiss();
                                    }
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                }

                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            if (pDialog.isShowing())
                                pDialog.dismiss();
                            Log.e("Hiistory", "error  " + error);
                            // listView.setVisibility(View.GONE);
                        }


                    });
                    queue1.add(req1);
                    gridView.setAdapter(libraryAdapter);

                    if (pDialog.isShowing())
                        pDialog.dismiss();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {


                }


            });




        }
    }
    @Override
    public void onBackPressed() {

       /* Intent in = new Intent(Library.this, MainActivity.class);
        startActivity(in);*/
     finish();
    }


    public class PortNumber extends AsyncTask<String, Void, String[]> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            Log.e("AddServer", "pre port_name " + year);

        }

        @Override
        protected String[] doInBackground(String... params) {

            Log.e("AddServer", "return port_name " + year);
            return year;
        }




        @Override
        protected void onPostExecute(String[] s) {

            Log.e("AddServer", "port_name " + s);

            super.onPostExecute(s);
            adapter = new ArrayAdapter<String>(Library.this, R.layout.spinner_item, s);
            adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);
            spinner1.setAdapter(adapter);
        }


    }
}










