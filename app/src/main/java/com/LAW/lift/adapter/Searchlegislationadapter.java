package com.LAW.lift.adapter;

import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import com.LAW.lift.activity.MainActivity;
import com.LAW.lift.activity.SearchLegislation;
import com.LAW.lift.activity.TamilLegislation;
import com.LAW.lift.fragments.HomeFragment;
import com.LAW.lift.model.NLService;
import com.LAW.lift.model.Searchlegislationcard;
import com.LAW.lift.model.legislationcard;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


public class Searchlegislationadapter extends ArrayAdapter<Searchlegislationcard> {
    private static final String TAG = "CardArrayAdapter";
    private List<Searchlegislationcard> cardList = new ArrayList<Searchlegislationcard>();

    String bus_id_item;
    int textViewResourceId;
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private Button startBtn;
    private ProgressDialog mProgressDialog;

    Context mcontext;
    String Link;
    //Map<String, String> addToCartMap = new HashMap<>();
    public int pos;
    public static boolean pdfDownloaded;
    //Map<String, String> addToCartMap = new HashMap<>();
    File pdfFile;
    NotificationCompat.Builder mBuilder;
    NotificationManager mNotifyManager;
    int id = 1;
    int counter = 0;
    private NotificationReceiver nReceiver;
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

    public Searchlegislationadapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
        this.mcontext=context;
    }

    @Override
    public void add(Searchlegislationcard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Searchlegislationcard getItem(int index) {
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
        pdfFile = new File(Environment.getExternalStorageDirectory(),"/SearchLegislation"+ HomeFragment.months+ MainActivity.years+position+".pdf");
        Log.e("highcourtadapter", "pdfFile: " + pdfFile);


        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
            mNotifyManager = (NotificationManager)  this.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
            LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(textViewResourceId, parent, false);
            viewHolder = new CardViewHolder();

            viewHolder.line10 = (TextView) row.findViewById(R.id.legbookname);
            viewHolder.line1 = (TextView) row.findViewById(R.id.tamilname);
            viewHolder.line2 = (TextView) row.findViewById(R.id.tamilactno);
            viewHolder.line3 = (TextView) row.findViewById(R.id.tamilenactedby);
            viewHolder.line4 = (TextView) row.findViewById(R.id.tamilreceived);
            viewHolder.line5 = (TextView) row.findViewById(R.id.tamilpublished);
            viewHolder.line6 = (TextView) row.findViewById(R.id.tamilcame);
            viewHolder.line7 = (TextView) row.findViewById(R.id.tamilsalient);
            viewHolder.line8 = (TextView) row.findViewById(R.id.tamilbrief);
             viewHolder.line9 = (TextView) row.findViewById(R.id.tamilfull);


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

        Searchlegislationcard Searchlegislationcard = getItem(position);

        viewHolder.line10.setText(Searchlegislationcard.getlegbookname());
        viewHolder.line1.setText(Searchlegislationcard.gettamilname());
        viewHolder.line2.setText(Searchlegislationcard.gettamilactno());
        viewHolder.line3.setText(Searchlegislationcard.gettamilenactedby());
        viewHolder.line4.setText(Searchlegislationcard.gettamilreceived());
        viewHolder.line5.setText(Searchlegislationcard.gettamilpublished());
        viewHolder.line6.setText(Searchlegislationcard.gettamilcame());
        viewHolder.line7.setText(Searchlegislationcard.gettamilsalient());
        viewHolder.line8.setText(Searchlegislationcard.gettamilbrief());
       // viewHolder.line9.setText(Searchlegislationcard.gettamilfulltext());



        viewHolder.line10.setSelected(true);
        viewHolder.line1.setSelected(true);
        viewHolder.line2.setSelected(true);
        viewHolder.line3.setSelected(true);
        viewHolder.line4.setSelected(true);
        viewHolder.line5.setSelected(true);
        viewHolder.line6.setSelected(true);
        viewHolder.line7.setSelected(true);
        viewHolder.line8.setSelected(true);
        viewHolder.line9.setSelected(true);












        if (viewHolder.line1!= null)
            viewHolder.line1.setText(Html.fromHtml(Searchlegislationcard.gettamilname()).toString());


        if (viewHolder.line7!= null)
            viewHolder.line7.setText(Html.fromHtml(Searchlegislationcard.gettamilsalient()).toString());

        if (viewHolder.line8!= null)
            viewHolder.line8.setText(Html.fromHtml(Searchlegislationcard.gettamilbrief()).toString());







       /* if(Searchlegislationcard.gettamilname().equals("")){
            viewHolder.line1.setText("Content not uploaded");
        }else {
            viewHolder.line1.setText(Searchlegislationcard.gettamilname());
        }

        if(Searchlegislationcard.gettamilname().equals("")){
            viewHolder.line10.setText("Content not uploaded");
        }else {
            viewHolder.line10.setText(Searchlegislationcard.gettamilname());
        }

        if(Searchlegislationcard.gettamilactno().equals("")){
            viewHolder.line2.setText("Content not uploaded");
        }else {
            viewHolder.line2.setText(Searchlegislationcard.gettamilactno());
        }

        if(Searchlegislationcard.gettamilenactedby().equals("")){
            viewHolder.line3.setText("Content not uploaded");
        }else {
            viewHolder.line3.setText(Searchlegislationcard.gettamilenactedby());
        }


        if(Searchlegislationcard.gettamilreceived().equals("")){
            viewHolder.line4.setText("Content not uploaded");
        }else {
            viewHolder.line4.setText(Searchlegislationcard.gettamilreceived());
        }

        if(Searchlegislationcard.gettamilpublished().equals("")){
            viewHolder.line5.setText("Content not uploaded");
        }else {
            viewHolder.line5.setText(Searchlegislationcard.gettamilpublished());
        }

        if(Searchlegislationcard.gettamilcame().equals("")){
            viewHolder.line6.setText("Content not uploaded");
        }else {
            viewHolder.line6.setText(Searchlegislationcard.gettamilcame());
        }

        if(Searchlegislationcard.gettamilsalient().equals("")){
            viewHolder.line7.setText("Content not uploaded");
        }else {
            viewHolder.line7.setText(Searchlegislationcard.gettamilsalient());
        }

        if(Searchlegislationcard.gettamilbrief().equals("")){
            viewHolder.line8.setText("Content not uploaded");
        }else {
            viewHolder.line8.setText(Searchlegislationcard.gettamilbrief());
        }

*/
       /* if(legislationcard.gettamilfulltext().equals("")){
            viewHolder.line9.setText("Content not uploaded");
        }else {
            viewHolder.line9.setText(legislationcard.gettamilfulltext());
        }*/




       /* if (row != null) {
            TextView line7 = (TextView) row.findViewById(R.id.tamilsalient);

            if (line7 != null)
                line7.setText(Html.fromHtml(Searchlegislationcard.gettamilsalient()).toString());



        }


        if (row != null) {
            TextView line8 = (TextView) row.findViewById(R.id.tamilbrief);

            if (line8 != null)
                line8.setText(Html.fromHtml(Searchlegislationcard.gettamilsalient()).toString());
        }
          if (row != null) {
            TextView line1 = (TextView) row.findViewById(R.id.tamilname);

            if (line1 != null)
                line1.setText(Html.fromHtml(Searchlegislationcard.gettamilsalient()).toString());
        }
      if (row != null) {
            TextView line2 = (TextView) row.findViewById(R.id.legbookname);

            if (line2 != null)
                line2.setText(Html.fromHtml(Searchlegislationcard.gettamilsalient()).toString());
        }
*/

        viewHolder.line9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Link = SearchLegislation.ref_url[position];
                startDownload(position);
            }
        });

        return row;
    }


    public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }



    static class CardViewHolder {
       TextView line10;
        TextView line1;
        TextView line2;
        TextView line3;
        TextView line4;
        TextView line5;
        TextView line6;
        TextView line7;
        TextView line8;
        TextView line9;


    }

    private void startDownload(int position) {

        new DownloadFileAsync().execute(Link, position + "",pdfFile+"");

        mBuilder = new NotificationCompat.Builder(getContext());
        /*mBuilder.setContentTitle("Downloading " + "book_id.pdf"+ "...");
        mBuilder.setContentText("Download in progress");
        mBuilder.setSmallIcon(R.drawable.menu);
        mBuilder.setProgress(0, 0, true);
        mNotifyManager.notify(id, mBuilder.build());
        mBuilder.setAutoCancel(true);*/
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
            //  mProgressDialog.setProgress(DIALOG_DOWNLOAD_PROGRESS);
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
                OutputStream output = new FileOutputStream(aurl[2]);

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


            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setDataAndType(path, "application/pdf" );
            mcontext.startActivity(Intent.createChooser(intent, "Open File"));
            intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        }
    }
}



