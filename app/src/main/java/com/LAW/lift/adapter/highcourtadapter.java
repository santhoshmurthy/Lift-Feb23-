package com.LAW.lift.adapter;

import android.app.AlertDialog;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.activity.CentralLegislation;
import com.LAW.lift.activity.MadrasHighcourt;
import com.LAW.lift.activity.MainActivity;
import com.LAW.lift.fragments.HomeFragment;
import com.LAW.lift.model.NLService;
import com.LAW.lift.model.highcourtcard;
import com.LAW.lift.model.legislationcard;
import com.LAW.lift.model.supremecard;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class highcourtadapter extends ArrayAdapter<highcourtcard> {
    private static final String TAG = "CardArrayAdapter";
    private List<highcourtcard> cardList = new ArrayList<highcourtcard>();
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    String Url, lawfull;
    Context mcontext;
    String Link;
    String bus_id_item;
    int textViewResourceId;
    Calendar c = Calendar.getInstance();
    int seconds = c.get(Calendar.SECOND);
    //Map<String, String> addToCartMap = new HashMap<>();

    File pdfFile;
    public int pos;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int id = 1;
    int counter = 0;
    private NotificationReceiver nReceiver;
    public static boolean pdfDownloaded;

    class NotificationReceiver extends BroadcastReceiver {
        @Override
        public void onReceive(Context context, Intent intent) {
            String event = intent.getExtras().getString(NLService.NOT_EVENT_KEY);
            Log.i("NotificationReceiver", "NotificationReceiver onReceive : " + event);
            if (event.trim().contentEquals(NLService.NOT_REMOVED)) {
                killTasks();
            }
        }
    }
    private void killTasks() {

        mNotifyManager.cancelAll();

    }







    public highcourtadapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
        this.mcontext = context;
    }

    @Override
    public void add(highcourtcard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public highcourtcard getItem(int index) {
        return this.cardList.get(index);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        pos=position;
        Log.d("ANDRO_ASYNC", "Central " + position);


        if (MainActivity.years==null)
        {
            MainActivity.years="2016";
        }
        pdfFile = new File(Environment.getExternalStorageDirectory(),"/MadrasHighCourt"+HomeFragment.months+MainActivity.years+position+ ".pdf");//HomeFragment.months+MainActivity.years+position+

        Log.e("highcourtadapter","pdfFile: "+pdfFile);

        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            mNotifyManager = (NotificationManager)  this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false);
            viewHolder = new CardViewHolder();
            viewHolder.line1 = (TextView) row.findViewById(R.id.highpara);
            viewHolder.line2 = (TextView) row.findViewById(R.id.contexthigh);
            viewHolder.line3 = (TextView) row.findViewById(R.id.questionhigh);
            viewHolder.line4 = (TextView) row.findViewById(R.id.answerhigh);
            viewHolder.line5 = (TextView) row.findViewById(R.id.referencehigh);
            viewHolder.line6 = (TextView) row.findViewById(R.id.texthigh);

            mProgressDialog = new ProgressDialog(mcontext);
            mProgressDialog.setMessage("Downloading file.. Wait for a While...");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);

            //viewHolder.line2 = (TextView) row.findViewById(R.id.bus_location);
            //viewHolder.line3 = (TextView) row.findViewById(R.id.bus_time);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }

        highcourtcard highcourtcard = getItem(position);
        viewHolder.line1.setText(highcourtcard.gethighpara());
        viewHolder.line2.setText(highcourtcard.getcontexthigh());
        viewHolder.line3.setText(highcourtcard.getquestionhigh());
        viewHolder.line4.setText(highcourtcard.getanswerhigh());
        viewHolder.line5.setText(highcourtcard.getreferencehigh());
        // viewHolder.line6.setText(highcourtcard.gettexthigh());

        viewHolder.line1.setSelected(true);
        viewHolder.line2.setSelected(true);
        viewHolder.line3.setSelected(true);
        viewHolder.line4.setSelected(true);
        viewHolder.line5.setSelected(true);
        viewHolder.line6.setSelected(true);


      viewHolder.line6.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {


                    Link = MadrasHighcourt.url[position];

                    startDownload(position);

                }

        });

        if (highcourtcard.gethighpara().equals("")) {
            viewHolder.line1.setText("Content not uploaded");
        } else {
            //viewHolder.line1.setText(highcourtcard.gethighpara());
        }


        if (highcourtcard.getcontexthigh().equals("")) {
            viewHolder.line2.setText("Content not uploaded");
        } else {
            //viewHolder.line2.setText(highcourtcard.getcontexthigh());
        }

        if (highcourtcard.getquestionhigh().equals("")) {
            viewHolder.line3.setText("Content not uploaded");
        } else {
           // viewHolder.line3.setText(highcourtcard.getquestionhigh());
        }

        if (highcourtcard.getanswerhigh().equals("")) {
            viewHolder.line4.setText("Content not uploaded");
        } else {
            //viewHolder.line4.setText(highcourtcard.getanswerhigh());
        }

        if (highcourtcard.getreferencehigh().equals("")) {
            viewHolder.line5.setText("Content not uploaded");
        } else {
           // viewHolder.line5.setText(highcourtcard.getreferencehigh());
        }
        if (highcourtcard.gettexthigh().equals("")|| highcourtcard.gettexthigh().equals("----")) {
            viewHolder.line6.setText("Content not uploaded");
            viewHolder.line6.setTextColor(Color.parseColor("#1c1c1c"));
            viewHolder.line6.setClickable(false);

        } else {
           // viewHolder.line6.setText(highcourtcard.gettexthigh());
        }


        if (viewHolder.line3 != null)
            viewHolder.line3.setText(Html.fromHtml(highcourtcard.getquestionhigh()).toString());


        if (viewHolder.line4 != null)
            viewHolder.line4.setText(Html.fromHtml(highcourtcard.getanswerhigh()).toString());

        return row;
    }


    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }


    static class CardViewHolder {
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;
        TextView line5;
        TextView line6;

    }

    private void startDownload(int position) {
        new DownloadFileAsync().execute(Link, position + "",pdfFile+"");

        mBuilder = new NotificationCompat.Builder(getContext());
        /*mBuilder.setContentTitle("Downloading " + "book_id.pdf"+ "...");
        mBuilder.setContentText("Download in progress");
        mBuilder.setSmallIcon(R.drawable.download);
        mBuilder.setProgress(0, 0, true);
        mNotifyManager.notify(id, mBuilder.build());
        mBuilder.setAutoCancel(true);*/
    }



    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();


        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {
                Log.e("HighCourtadapter","PdfFile: "+aurl[2]);

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);
                if (MainActivity.years==null)
                {
                    MainActivity.years="2016";
                }
                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream(aurl[2]);//"/LIFT/MadrasHighCourt"+MainActivity.years+ ".pdf");
                 //"/sdcard/" + "MadrasHighCourt"+aurl[1]+".pdf");
                 //"/sdcard/" + "MadrasHighCourt"+HomeFragment.months+MainActivity.years+aurl[1]+".pdf");



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
            //  mProgressDialog.setProgress(Integer.parseInt(progress[0]));
            int per = Integer.parseInt(progress[0]);
            //  Log.d("length", "" + len);
            if (per == 100) {
                mBuilder.setContentTitle("Done.");
                mBuilder.setContentText("Download complete");
                mBuilder.setProgress(100, per, false);
                mNotifyManager.notify(id, mBuilder.build());

            } else {

                Log.i("Counter", "Counter : " + counter + ", per : " + per);
                mBuilder.setContentText("Downloaded (" + per + "/100)");

                mBuilder.setProgress(100, per, false);
                // Displays the progress bar for the first time.
                mNotifyManager.notify(id, mBuilder.build());
                // mProgressDialog.setProgress(per);
                Log.d("ANDRO_ASYNC", progress[0] + "        " + per + "       " + counter);
            }
            // counter++;


        }

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();
            pdfDownloaded=true;
            mBuilder.setProgress(0, 0, false);
            Uri path = Uri.fromFile(pdfFile);
           // Uri path = Uri.parse("file://" + pdfFile.getPath());
            /*Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
           // objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            //mcontext.startActivity(objIntent);
            mcontext.startActivity(Intent.createChooser(objIntent, "Open File"));*/

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf" );
            mcontext.startActivity(Intent.createChooser(intent, "Open File"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
            /*Intent target = Intent.createChooser(intent, "Open File");
            try {
                mcontext.startActivity(target);
            } catch (ActivityNotFoundException e) {
                Log.e("PDFCreator", "ActivityNotFoundException:" + e);
            }*/

        }
    }


}


