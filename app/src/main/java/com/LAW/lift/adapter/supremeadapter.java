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
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.activity.CentralLegislation;
import com.LAW.lift.activity.Supremecourt;
import com.LAW.lift.model.Card;
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


public class supremeadapter  extends ArrayAdapter<supremecard> {
    private static final String TAG = "CardArrayAdapter";
    private List<supremecard> cardList = new ArrayList<supremecard>();
    public static final int DIALOG_DOWNLOAD_PROGRESS = 0;
    private ProgressDialog mProgressDialog;
    String Url, lawfull;
    Context mcontext;
    String Link;
    String bus_id_item;
    int textViewResourceId;
    File pdfFile = new File(Environment
            .getExternalStorageDirectory(),"lift.pdf");
    //Map<String, String> addToCartMap = new HashMap<>();

    public supremeadapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
        this.mcontext=context;
    }

    @Override
    public void add(supremecard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public supremecard getItem(int index) {
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
            viewHolder.line1 = (TextView) row.findViewById(R.id.titlepara);
            viewHolder.line2 = (TextView) row.findViewById(R.id.contextpara);
            viewHolder.line3 = (TextView) row.findViewById(R.id.questionpara);
            viewHolder.line4 = (TextView) row.findViewById(R.id.answerpara);
            viewHolder.line5 = (TextView) row.findViewById(R.id.referencepara);
            viewHolder.line6 = (TextView) row.findViewById(R.id.texttouch);

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

        supremecard supremecard = getItem(position);
        viewHolder.line1.setText(supremecard.gettitlepara());
        viewHolder.line2.setText(supremecard.getcontextpara());
        viewHolder.line3.setText(supremecard.getquestionpara());
        viewHolder.line4.setText(supremecard.getanswerpara());
        viewHolder.line5.setText(supremecard.getreferencepara());
        //viewHolder.line6.setText(supremecard.gettexttouch());

        viewHolder.line1.setSelected(true);
        viewHolder.line2.setSelected(true);
        viewHolder.line3.setSelected(true);
        viewHolder.line4.setSelected(true);
        viewHolder.line5.setSelected(true);
        viewHolder.line6.setSelected(true);



        if(supremecard.gettitlepara().equals("")){
            viewHolder.line1.setText("Content not uploaded");
        }else {
            viewHolder.line1.setText(supremecard.gettitlepara());
        }
        if(supremecard.getcontextpara().equals("")){
            viewHolder.line2.setText("Content not uploaded");
        }else {
            viewHolder.line2.setText(supremecard.getcontextpara());
        }
        if(supremecard.getquestionpara().equals("")){
            viewHolder.line3.setText("Content not uploaded");
        }else {
            viewHolder.line3.setText(supremecard.getquestionpara());
        }
        if(supremecard.getanswerpara().equals("")){
            viewHolder.line4.setText("Content not uploaded");
        }else {
            viewHolder.line4.setText(supremecard.getanswerpara());
        }
        if(supremecard.getreferencepara().equals("")){
            viewHolder.line5.setText("Content not uploaded");
        }else {
            viewHolder.line5.setText(supremecard.getreferencepara());
        }

        if(supremecard.gettexttouch().equals("")){
            viewHolder.line6.setText("Content not uploaded");
        }else {
            viewHolder.line6.setText(supremecard.gettexttouch());
        }

     /*   viewHolder.line6.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Link = Supremecourt.url[position];
                startDownload();

            }
        });

*/



            if (viewHolder.line3 != null)
                viewHolder.line3.setText(Html.fromHtml(supremecard.getquestionpara()).toString());


            if (viewHolder.line4 != null)
                viewHolder.line4.setText(Html.fromHtml(supremecard.getquestionpara()).toString());

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


