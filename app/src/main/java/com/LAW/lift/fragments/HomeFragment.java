package com.LAW.lift.fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.app.Notification;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.activity.CentralLegislation;
import com.LAW.lift.activity.Forum;
import com.LAW.lift.activity.MadrasHighcourt;
import com.LAW.lift.activity.MainActivity;
import com.LAW.lift.activity.Supremecourt;
import com.LAW.lift.activity.TamilLegislation;
import com.LAW.lift.app.LiftApplication;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.common.AlertDialogManager;
import com.LAW.lift.common.ConnectionDetector;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HomeFragment extends Fragment {

  String urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final/lift_of_the_month.php?id=1";
    String months,bookid,bookname,Language;
    private Button central,Tamil,supreme,Madras,forum;
    private TextView mon;
    public static String[] get_legislation;
    public static String[] Id;
    public static String[] Book_Name;
    public static String[] Url;
    public static String[] Published_Month;
    public static String[] month;
    ProgressDialog pDialog;
    AlertDialogManager alert = new AlertDialogManager();
    ConnectionDetector cd;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.homefragment, container, false);


        central = (Button)rootView. findViewById(R.id.central);
        Tamil = (Button)rootView. findViewById (R.id.Tamil);
        supreme = (Button) rootView. findViewById(R.id.supreme);
        Madras = (Button) rootView. findViewById(R.id.Madras);
        forum = (Button) rootView. findViewById(R.id.forum);
        mon=(TextView)rootView. findViewById(R.id.mon);


        if (getArguments().getString("book_id")!= null) {


            bookid = getArguments().getString("book_id");
            months=getArguments().getString("month");
            mon.setText(months);
            Log.d("HomeFrargment", bookid + months);

        }else {
            if (MainActivity.Language.equals("தமிழ்")) {
                urlJsonArry = "http://www.lawinfingertips.com/webservice/Lift_Final_Tamil/lift_of_the_month.php?id=1";

            }
            cd = new ConnectionDetector(getContext());
            if (!cd.isConnectingToInternet()) {
                AlertDialog.Builder helpBuilder = new AlertDialog.Builder(getContext(), AlertDialog.THEME_HOLO_LIGHT);
                helpBuilder.setTitle("Connection offline");
                helpBuilder.setMessage("No Network connection! Please Check the Internet Connection");
                helpBuilder.setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Intent checkIntent = new Intent(getContext(), MainActivity.class);
                                startActivity(checkIntent);


                            }
                        });
                AlertDialog helpDialog = helpBuilder.create();
                helpDialog.show();
            } else {
                pDialog = new ProgressDialog(getContext());
                pDialog.setMessage("Loading....");
                pDialog.setCancelable(false);
                pDialog.show();



                RequestQueue queue = MyVolley.getRequestQueue();

                JsonObjectRequest req = new JsonObjectRequest(urlJsonArry, null,
                        new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    Log.e("History", "response.month_of_book" + response);

                                    JSONObject jobject = response;

                                    JSONArray jsonMainArr = jobject.getJSONArray("month_of_book");

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

                                        bookid = person.getString("Id");
                                        months = person.getString("month");
                                        mon.setText(months);
                                        Log.d("HomeFragment", bookid);
                                    }
                                    if (pDialog.isShowing())
                                        pDialog.dismiss();

                                    //mTvResult.setText(jsonResponse);

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

            }

        }

        central.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftApplication.getInstance().trackEvent("Central legislation","click","central");
                Intent openIntent = new Intent(getActivity(), CentralLegislation.class);
                openIntent.putExtra("book_id", bookid);
                openIntent.putExtra("month",months);
                startActivity(openIntent);
                // MainActivity.this.finish();


            }
        });

        Tamil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftApplication.getInstance().trackEvent("Tamil Nadu legislation","click","tamilnadu");
                Intent openIntent = new Intent(getActivity(), TamilLegislation.class);
                openIntent.putExtra("book_id", bookid);
                openIntent.putExtra("month",months);
                startActivity(openIntent);
                // MainActivity.this.finish();


            }
        });

        supreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftApplication.getInstance().trackEvent("Supreme court","click","Supreme court");
                Intent openIntent = new Intent(getActivity(), Supremecourt.class);
                openIntent.putExtra("book_id", bookid);
                openIntent.putExtra("month",months);
                startActivity(openIntent);
                //  MainActivity.this.finish();


            }
        });
        Madras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftApplication.getInstance().trackEvent("MadrasHighcourt","click","madras highcourt");
                Intent openIntent = new Intent(getActivity(), MadrasHighcourt.class);
                openIntent.putExtra("book_id", bookid);
                openIntent.putExtra("month",months);
                startActivity(openIntent);
                // MainActivity.this.finish();


            }
        });
        forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LiftApplication.getInstance().trackEvent("Forum", "click", "forum");
                Intent openIntent = new Intent(getActivity(), Forum.class);
                openIntent.putExtra("book_id", bookid);
                openIntent.putExtra("month", months);
                startActivity(openIntent);
                // MainActivity.this.finish();


            }
        });



        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        LiftApplication.getInstance().trackScreenView("Homefragment");
    }

}
