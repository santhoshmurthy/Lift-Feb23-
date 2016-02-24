package com.LAW.lift.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
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
import java.util.List;

public class highcourtadapter extends ArrayAdapter<highcourtcard> {
    private static final String TAG = "CardArrayAdapter";
    private List<highcourtcard> cardList = new ArrayList<highcourtcard>();
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private Button startBtn;
    private ProgressDialog mProgressDialog;
    String Url, lawfull;
    Context mcontext;
    String Link;
    String bus_id_item;
    int textViewResourceId;

    //Map<String, String> addToCartMap = new HashMap<>();
    File pdfFile = new File(Environment
            .getExternalStorageDirectory(), "lift.pdf");

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
        View row = convertView;
        CardViewHolder viewHolder;
        if (row == null) {
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
            mProgressDialog.setMessage("Downloading file..");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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

  /*      viewHolder.line6.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Link = MadrasHighcourt.url[position];
                startDownload();

            }
        });*/

        if (highcourtcard.gethighpara().equals("")) {
            viewHolder.line1.setText("Content not uploaded");
        } else {
            viewHolder.line1.setText(highcourtcard.gethighpara());
        }


        if (highcourtcard.getcontexthigh().equals("")) {
            viewHolder.line2.setText("Content not uploaded");
        } else {
            viewHolder.line2.setText(highcourtcard.getcontexthigh());
        }

        if (highcourtcard.getquestionhigh().equals("")) {
            viewHolder.line3.setText("Content not uploaded");
        } else {
            viewHolder.line3.setText(highcourtcard.getquestionhigh());
        }

        if (highcourtcard.getanswerhigh().equals("")) {
            viewHolder.line4.setText("Content not uploaded");
        } else {
            viewHolder.line4.setText(highcourtcard.getanswerhigh());
        }

        if (highcourtcard.getreferencehigh().equals("")) {
            viewHolder.line5.setText("Content not uploaded");
        } else {
            viewHolder.line5.setText(highcourtcard.getreferencehigh());
        }
        if (highcourtcard.gettexthigh().equals("")) {
            viewHolder.line6.setText("Content not uploaded");
        } else {
            viewHolder.line6.setText(highcourtcard.gettexthigh());
        }


        if (viewHolder.line3 != null)
            viewHolder.line3.setText(Html.fromHtml(highcourtcard.getquestionhigh()).toString());


        if (viewHolder.line4 != null)
            viewHolder.line4.setText(Html.fromHtml(highcourtcard.getquestionhigh()).toString());

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

    private void startDownload() {

        new DownloadFileAsync().execute(Link);
    }

    class DownloadFileAsync extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressDialog.show();
            mProgressDialog.setProgress(DIALOG_DOWNLOAD_PROGRESS);
        }

        @Override
        protected String doInBackground(String... aurl) {
            int count;

            try {

                URL url = new URL(aurl[0]);
                URLConnection conexion = url.openConnection();
                conexion.connect();

                int lenghtOfFile = conexion.getContentLength();
                Log.d("ANDRO_ASYNC", "Length of file: " + lenghtOfFile);

                InputStream input = new BufferedInputStream(url.openStream());
                OutputStream output = new FileOutputStream("/sdcard/" + "lift.pdf");

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

        @Override
        protected void onPostExecute(String unused) {
            mProgressDialog.dismiss();


            Uri path = Uri.fromFile(pdfFile);
            Intent objIntent = new Intent(Intent.ACTION_VIEW);
            objIntent.setDataAndType(path, "application/pdf");
            objIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            mcontext.startActivity(objIntent);


        }
    }


}


