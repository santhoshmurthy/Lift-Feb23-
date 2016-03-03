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
import com.LAW.lift.adapter.Searchlegislationadapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.model.Searchlegislationcard;
import com.LAW.lift.model.legislationcard;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SearchLegislation extends Activity {
    ImageView back;
    TextView tamilfeb;

    public static String[] get_legislation;
    public static String[] bookname;
    public static String[] error;
    public static String[] book_id;
    public static String[] state_id;
    public static String[] state;
    public static String[] book_icon;
    public static String[] published_month;
    public static String[] name;
    public static String[] act_no;
    public static String[] enacted_by;
    public static String[] receiver_by_president_on;
    public static String[] published_in;
    public static String[] came_in_force;
    public static String[] salient_features;
    public static String[] brief_deatils;
    public static String[] ref_url;
    String jsonResponse;
    String legbookname, tamilname, tamilactno, tamilenactedby, tamilreceived, tamilpublished, tamilcame, tamilsalient, tamilbrief, tamilfull;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/lift/v1/search";
    ListView searchlistleg;
    String word;
    String months;

    private Searchlegislationadapter rideadapter2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.searchlegislation);


        LiftApplication.getInstance().trackScreenView("tamil legislation");
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


        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //bus_names=extras.getStringArray("BusNames");


            word = extras.getString("Word");
            Log.d("tamil", word );


        }


        cd = new ConnectionDetector(getApplicationContext());
        searchlistleg = (ListView) findViewById(R.id.searchlistleg);

        rideadapter2 = new Searchlegislationadapter(SearchLegislation.this, R.layout.searchtamillegislation);
        searchlistleg.setAdapter(rideadapter2);


        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(SearchLegislation.this, AlertDialog.THEME_HOLO_LIGHT);
            helpBuilder.setTitle("Connection offline");
            helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
            helpBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkIntent = new Intent(SearchLegislation.this, MainActivity.class);
                            startActivity(checkIntent);
                            finish();
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } else {

            pDialog = new ProgressDialog(SearchLegislation.this);
            pDialog.setMessage("Loading....");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue queue = MyVolley.getRequestQueue();

                   StringRequest postReq = new StringRequest(Request.Method.POST,
                        urlJsonArry,
                        new Response.Listener<String>() {
                            public void onResponse(String response) {

                                try {
                                    JSONArray jobject = new JSONArray(response);


                                    Log.e("History", "response.get_legislation" + response);

                                    bookname = new String[jobject.length()];
                                    name = new String[jobject.length()];
                                    act_no = new String[jobject.length()];
                                    enacted_by = new String[jobject.length()];
                                    receiver_by_president_on = new String[jobject.length()];
                                    published_in = new String[jobject.length()];
                                    came_in_force = new String[jobject.length()];
                                    salient_features = new String[jobject.length()];
                                    brief_deatils = new String[jobject.length()];
                                    ref_url = new String[jobject.length()];

                                    if (jobject.length()!=0){

                                        for (int i = 0; i < jobject.length(); i++) {

                                            JSONObject person = (JSONObject) jobject.get(i);


                                            bookname[i] = person.getString("book_name");
                                            name[i] = person.getString("name");
                                            act_no[i] = person.getString("act_no");
                                            enacted_by[i] = person.getString("enacted_by");
                                            receiver_by_president_on[i] = person.getString("receiver_by_president_on");
                                            published_in[i] = person.getString("published_in");
                                            came_in_force[i] = person.getString("came_in_force");
                                            salient_features[i] = person.getString("salient_features");
                                            brief_deatils[i] = person.getString("brief_deatils");
                                            ref_url[i] = person.getString("ref_url");


                                            legbookname = person.getString("book_name");
                                            tamilname = person.getString("name");
                                            tamilactno = person.getString("act_no");
                                            tamilenactedby = person.getString("enacted_by");
                                            tamilreceived = person.getString("receiver_by_president_on");
                                            tamilpublished = person.getString("published_in");
                                            tamilcame = person.getString("came_in_force");
                                            tamilsalient = person.getString("salient_features");
                                            tamilbrief = person.getString("brief_deatils");
                                            tamilfull = person.getString("ref_url");


                                            Log.d("SearchLegislaton", "Searchlegislationadapter" + tamilname);
                                            Searchlegislationcard searchlegislationcard = new Searchlegislationcard(legbookname, tamilname, tamilactno, tamilenactedby, tamilreceived, tamilpublished, tamilcame, tamilsalient, tamilbrief, tamilfull);
                                            rideadapter2.add(searchlegislationcard);

                                        }

                                    }else{

                                        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(SearchLegislation.this, AlertDialog.THEME_HOLO_LIGHT);
                                        helpBuilder.setTitle("Results");
                                        helpBuilder.setMessage("No Result available");
                                        helpBuilder.setPositiveButton("OK",
                                                new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();
                                                        Intent checkIntent = new Intent(SearchLegislation.this, SearchActivity.class);
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

                                    }
                                catch (JSONException e) {
                                    e.printStackTrace();
                                    LiftApplication.getInstance().trackException(e);

                                    if (pDialog.isShowing())
                                        pDialog.dismiss();
                                }


                            }
                            },new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            if (pDialog.isShowing())
                                                pDialog.dismiss();
                                            Log.e("Hiistory", "error  " + error);
                                        }

                                    }){
                                        @Override
                                        protected Map<String, String> getParams ()throws
                                        AuthFailureError {

                                            HashMap<String, String> params = new HashMap<>();
                                            params.put("word", word);
                                            params.put("lawtype", "legislation");

                                            return params;
                                        }

                                    } ;

                                    queue.add(postReq);
        searchlistleg.setAdapter(rideadapter2);
    }
                            }

   /* class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

        }
        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Lenght of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());

                OutputStream output = new FileOutputStream("/sdcard/" + AppNam + ".apk");


                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;

                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    output.write(data, 0, count);
                }

                output.flush();
                output.close();
                input.close();

            } catch (Exception e) {
            }
            return null;

        }

        protected void onProgressUpdate(String... progress) {
            Log.d("ANDRO_ASYNC", progress[0]);

            mProgressDialog.setProgress(Integer.parseInt(progress[0]));
        }

*/

   /* public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }*/

                            @Override
                            public void onBackPressed() {
       /* Intent in = new Intent(SearchLegislation.this, MainActivity.class);
        startActivity(in);*/
                               finish();
                            }
                        }










