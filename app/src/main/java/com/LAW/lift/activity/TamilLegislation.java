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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.LAW.lift.R;
import com.LAW.lift.adapter.CardArrayAdapter;
import com.LAW.lift.adapter.legislationadapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.LAW.lift.model.Card;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.model.legislationcard;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class TamilLegislation extends Activity {
    ImageView back;
    TextView tamilfeb;
    public static String[] get_legislation;
    public static String[] book_id;
    public static String[] state_id;
    public static String[] state;
    public static String[] book_name;
    public static String[] book_icon;
    public static String[] published_month;
    public static String[] month;
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
    String tamilname, tamilactno, tamilenactedby, tamilreceived,tamilpublished,tamilcame,tamilsalient,tamilbrief,tamilfulltext;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/get_legislation.php?book_id=";
    String tamil="&type=state&state_id=1000";
    ListView listView2;
    String bookid;
    String months;
    String success;
    private JSONObject person;
    private legislationadapter rideadapter2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tamil);
        LiftApplication.getInstance().trackScreenView("tamil legislation");
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        View v = inflator.inflate(R.layout.titleview, null);
        tamilfeb=(TextView)findViewById(R.id.tamilfeb);
        ((MyTextviewWhite) v.findViewById(R.id.title)).setText(this.getTitle());
        this.getActionBar().setCustomView(v);


        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent open = new Intent(TamilLegislation.this, MainActivity.class);
                startActivity(open);*/
                finish();

            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //bus_names=extras.getStringArray("BusNames");

            bookid = extras.getString("book_id");
            months=extras.getString("month");
            Log.d("tamil",bookid +months);
            //Toast.makeText(Booking.this,sname+"\n"+slat+"\n"+slong, Toast.LENGTH_SHORT).show();
        }
        tamilfeb.setText(months);
        if(MainActivity.Language.equals("தமிழ்")){
            urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/get_legislation.php?book_id=";
            tamil="&type=state&state_id=1000";
        }
        cd = new ConnectionDetector(getApplicationContext());
        listView2 = (ListView)findViewById(R.id.listView2);

        rideadapter2 = new legislationadapter(TamilLegislation.this, R.layout.tamillegislation);
        listView2.setAdapter(rideadapter2);


        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(TamilLegislation.this, AlertDialog.THEME_HOLO_LIGHT);
            helpBuilder.setTitle("Connection offline");
            helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
            helpBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkIntent = new Intent(TamilLegislation.this, MainActivity.class);
                            startActivity(checkIntent);
                            finish();
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } else {

            pDialog = new ProgressDialog(TamilLegislation.this);
            pDialog.setMessage("Loading....");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue queue = MyVolley.getRequestQueue();

            JsonObjectRequest req = new JsonObjectRequest(urlJsonArry+bookid+tamil,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.e("History", "response.get_legislation" + response);

                                JSONObject jobject =response;

                                JSONArray jsonMainArr = jobject.getJSONArray("get_legislation");

                                book_id = new String[jsonMainArr.length()];
                                state_id = new String[jsonMainArr.length()];
                                state = new String[jsonMainArr.length()];
                                book_name = new String[jsonMainArr.length()];
                                book_icon = new String[jsonMainArr.length()];
                                published_month = new String[jsonMainArr.length()];
                                month = new String[jsonMainArr.length()];
                                name = new String[jsonMainArr.length()];
                                act_no = new String[jsonMainArr.length()];
                                enacted_by = new String[jsonMainArr.length()];
                                receiver_by_president_on = new String[jsonMainArr.length()];
                                published_in = new String[jsonMainArr.length()];
                                came_in_force = new String[jsonMainArr.length()];
                                salient_features = new String[jsonMainArr.length()];
                                brief_deatils = new String[jsonMainArr.length()];
                                ref_url = new String[jsonMainArr.length()];

                                for (int i = 0; i < jsonMainArr.length(); i++) {


                                    person = (JSONObject) jsonMainArr.get(i);

                                    book_id[i]=person.getString("book_id");
                                    state_id[i] = person.getString("state_id");
                                    state[i] = person.getString("state");
                                    book_name[i] = person.getString("book_name");
                                    book_icon[i] = person.getString("book_icon");
                                    published_month[i]=person.getString("published_month");
                                    month[i] = person.getString("month");
                                    name[i] = person.getString("name");
                                    act_no[i] = person.getString("act_no");
                                    enacted_by[i] = person.getString("enacted_by");
                                    receiver_by_president_on[i]=person.getString("receiver_by_president_on");
                                    published_in[i] = person.getString("published_in");
                                    came_in_force[i] = person.getString("came_in_force");
                                    salient_features[i] = person.getString("salient_features");
                                    brief_deatils[i] = person.getString("brief_deatils");
                                    ref_url[i]=person.getString("ref_url");

                                    tamilname = person.getString("name");
                                    tamilactno = person.getString("act_no");
                                    tamilenactedby = person.getString("enacted_by");
                                    tamilreceived = person.getString("receiver_by_president_on");
                                    tamilpublished= person.getString("published_in");
                                    tamilcame= person.getString("came_in_force");
                                    tamilsalient=person.getString("salient_features");
                                    tamilbrief=person.getString("brief_deatils");
                                    tamilfulltext=person.getString("ref_url");


                                    months=person.getString("month");


                                    legislationcard legislationcard = new legislationcard(tamilname, tamilactno, tamilenactedby, tamilreceived,tamilpublished,tamilcame,tamilsalient,tamilbrief,tamilfulltext);
                                    rideadapter2.add(legislationcard);

                                }

                                if (pDialog.isShowing())
                                    pDialog.dismiss();
                                //mTvResult.setText(jsonResponse);

                            } catch (JSONException e) {

                                try {
                                    success = person.getString("success");
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }
                                Log.e("History", "success" + success);
                                if (success.contains("no")) {
                                    AlertDialog.Builder helpBuilder = new AlertDialog.Builder(TamilLegislation.this, AlertDialog.THEME_HOLO_LIGHT);
                                    helpBuilder.setTitle("No Content");
                                    helpBuilder.setMessage("No Content available for TamilLegislation");
                                    helpBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent checkIntent = new Intent(TamilLegislation.this, MainActivity.class);
                                                    startActivity(checkIntent);
                                                    finish();
                                                }
                                            });
                                    AlertDialog helpDialog = helpBuilder.create();
                                    helpDialog.show();
                                }

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
                    Log.e("Hiistory","error  "+error);
                    // listView.setVisibility(View.GONE);
                }


            });
            queue.add(req);
            listView2.setAdapter(rideadapter2);
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
       /* Intent in = new Intent(TamilLegislation.this, MainActivity.class);
        startActivity(in);*/
     finish();
    }
}


