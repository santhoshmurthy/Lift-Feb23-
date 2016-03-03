package com.LAW.lift.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.LAW.lift.adapter.SupremeCardArrayAdapter;
import com.LAW.lift.adapter.supremeadapter;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.LAW.lift.model.Card;
import com.LAW.lift.model.MyTextviewWhite;
import com.LAW.lift.model.SupremecourtCard;
import com.LAW.lift.model.supremecard;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



public class Supremecourt extends Activity {
    ImageView back;
    TextView textsupreme;

    public static String[] get_legislation;
    public static String[] succes;
    public static String[] error;
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
    public static String[] url;
    String jsonResponse;
    String success;
    String titlepara, contextpara, questionpara, answerpara, referencepara, texttouch;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/get_case_law.php?book_id=";
    String supreme="&type=sc";
    ListView listView;
    String bookid;
    String bookname;
    String months;
    private JSONObject person;
    private SupremeCardArrayAdapter ride;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.supremelist);
        LiftApplication.getInstance().trackScreenView("Supreme Court");
        this.getActionBar().setDisplayShowCustomEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);
        LayoutInflater inflator = LayoutInflater.from(this);
        textsupreme=(TextView)findViewById(R.id.textsupreme);
        View v = inflator.inflate(R.layout.titleview, null);
        ((MyTextviewWhite) v.findViewById(R.id.title)).setText(this.getTitle());
        this.getActionBar().setCustomView(v);
        back=(ImageView)findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* Intent open = new Intent(Supremecourt.this, MainActivity.class);
                startActivity(open);*/
                finish();

            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            //bus_names=extras.getStringArray("BusNames");

            bookid = extras.getString("book_id");
            months=extras.getString("month");


            Log.d("supreme",bookid +months+bookname);
            //Toast.makeText(Booking.this,sname+"\n"+slat+"\n"+slong, Toast.LENGTH_SHORT).show();
        }
        textsupreme.setText(months);
        if(MainActivity.Language.equals("தமிழ்")){
            urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/get_case_law.php?book_id=";
            supreme="&type=sc";
        }
        cd = new ConnectionDetector(getApplicationContext());
        listView = (ListView)findViewById(R.id.listView3);

        ride = new SupremeCardArrayAdapter(Supremecourt.this, R.layout.supreme);
        listView.setAdapter(ride);


        if (!cd.isConnectingToInternet()) {
            AlertDialog.Builder helpBuilder = new AlertDialog.Builder(Supremecourt.this, AlertDialog.THEME_HOLO_LIGHT);
            helpBuilder.setTitle("Connection offline");
            helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
            helpBuilder.setPositiveButton("OK",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Intent checkIntent = new Intent(Supremecourt.this, MainActivity.class);
                            startActivity(checkIntent);
                            finish();
                        }
                    });
            AlertDialog helpDialog = helpBuilder.create();
            helpDialog.show();
        } else {

            pDialog = new ProgressDialog(Supremecourt.this);
            pDialog.setMessage("Loading....");
            pDialog.setCancelable(false);
            pDialog.show();

            RequestQueue queue = MyVolley.getRequestQueue();

            JsonObjectRequest req = new JsonObjectRequest(urlJsonArry+bookid+supreme,null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                Log.e("History", "response.get_case_law" + response);

                                JSONObject jobject =response;

                                JSONArray jsonMainArr = jobject.getJSONArray("get_case_law");

                                book_name = new String[jsonMainArr.length()];
                                book_icon = new String[jsonMainArr.length()];
                                year = new String[jsonMainArr.length()];
                                month = new String[jsonMainArr.length()];
                                court = new String[jsonMainArr.length()];
                                title = new String[jsonMainArr.length()];
                                context = new String[jsonMainArr.length()];
                                question = new String[jsonMainArr.length()];
                                answer = new String[jsonMainArr.length()];
                                reference = new String[jsonMainArr.length()];
                                url = new String[jsonMainArr.length()];
                                succes= new String[jsonMainArr.length()];

                                Log.e("Hiistory"," succes "+ succes);
                                if(success=="no"){
                                    Toast.makeText(Supremecourt.this, "The law content for the specified Supreme Court is not available.", Toast.LENGTH_SHORT).show();
                                }else {
                                    for (int i = 0; i < jsonMainArr.length(); i++) {


                                        person = (JSONObject) jsonMainArr.get(i);

                                        book_name[i] = person.getString("book_name");
                                        book_icon[i] = person.getString("book_icon");
                                        year[i] = person.getString("year");
                                        month[i] = person.getString("month");
                                        court[i] = person.getString("court");
                                        title[i] = person.getString("title");
                                        context[i] = person.getString("context");
                                        question[i] = person.getString("question");
                                        answer[i] = person.getString("answer");
                                        reference[i] = person.getString("reference");
                                        url[i] = person.getString("url");


                                        titlepara = person.getString("title");
                                        contextpara = person.getString("context");
                                        questionpara = person.getString("question");
                                        answerpara = person.getString("answer");
                                        referencepara = person.getString("reference");
                                        texttouch = person.getString("url");
                                        months = person.getString("month");


                                        SupremecourtCard SupremecourtCard = new SupremecourtCard(titlepara, contextpara, questionpara, answerpara, referencepara, texttouch);
                                        ride.add(SupremecourtCard);

                                    }
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
                                    AlertDialog.Builder helpBuilder = new AlertDialog.Builder(Supremecourt.this, AlertDialog.THEME_HOLO_LIGHT);
                                    helpBuilder.setTitle("No Content");
                                    helpBuilder.setMessage("No Content available for Supremecourt");
                                    helpBuilder.setPositiveButton("OK",
                                            new DialogInterface.OnClickListener() {
                                                public void onClick(DialogInterface dialog, int which) {
                                                    dialog.dismiss();
                                                    Intent checkIntent = new Intent(Supremecourt.this, MainActivity.class);
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
            listView.setAdapter(ride);
        }
    }
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
       /* Intent in = new Intent(Supremecourt.this, MainActivity.class);
        startActivity(in);*/
        finish();
    }
}
