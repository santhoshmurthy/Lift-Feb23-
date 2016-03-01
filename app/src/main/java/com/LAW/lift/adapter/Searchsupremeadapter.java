package com.LAW.lift.adapter;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import com.LAW.lift.activity.Supremecourt;
import com.LAW.lift.model.Searchsupremecard;
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


public class Searchsupremeadapter extends ArrayAdapter<Searchsupremecard> {
    private static final String TAG = "CardArrayAdapter";
    private List<Searchsupremecard> cardList = new ArrayList<Searchsupremecard>();
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
            .getExternalStorageDirectory(),"lift.pdf");

    //Map<String, String> addToCartMap = new HashMap<>();

    public Searchsupremeadapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
        this.mcontext=context;
    }

    @Override
    public void add(Searchsupremecard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public Searchsupremecard getItem(int index) {
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
            viewHolder.line6 = (TextView) row.findViewById(R.id.casebookname);
            viewHolder.line1 = (TextView) row.findViewById(R.id.titlepara);
            viewHolder.line2 = (TextView) row.findViewById(R.id.contextpara);
            viewHolder.line3 = (TextView) row.findViewById(R.id.questionpara);
            viewHolder.line4 = (TextView) row.findViewById(R.id.answerpara);
            viewHolder.line5 = (TextView) row.findViewById(R.id.referencepara);


           // viewHolder.line6 = (TextView) row.findViewById(R.id.texttouch);



            //viewHolder.line2 = (TextView) row.findViewById(R.id.bus_location);
            //viewHolder.line3 = (TextView) row.findViewById(R.id.bus_time);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }

        Searchsupremecard Searchsupremecard = getItem(position);
        viewHolder.line6.setText(Searchsupremecard.getcasebookname());
        viewHolder.line1.setText(Searchsupremecard.gettitlepara());
        viewHolder.line2.setText(Searchsupremecard.getcontextpara());
        viewHolder.line3.setText(Searchsupremecard.getquestionpara());
        viewHolder.line4.setText(Searchsupremecard.getanswerpara());
        viewHolder.line5.setText(Searchsupremecard.getreferencepara());
        //viewHolder.line6.setText(supremecard.gettexttouch());

        viewHolder.line1.setSelected(true);
        viewHolder.line2.setSelected(true);
        viewHolder.line3.setSelected(true);
        viewHolder.line4.setSelected(true);
        viewHolder.line5.setSelected(true);
        viewHolder.line6.setSelected(true);

       /* viewHolder.line6.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                Link = Supremecourt.url[position];
                Log.d("Supreme", "url " + Link);
                startDownload();

            }
        });

*/


       /* if(Searchsupremecard.gettitlepara().equals("")){
            viewHolder.line1.setText("Content not uploaded");
        }else {
           // viewHolder.line1.setText(supremecard.gettitlepara());
        } if(Searchsupremecard.getbookname().equals("")){
            viewHolder.line7.setText("Content not uploaded");
        }else {
            //viewHolder.line7.setText(supremecard.getbookname());
        }
        if(Searchsupremecard.getcontextpara().equals("")){
            viewHolder.line2.setText("Content not uploaded");
        }else {
            //viewHolder.line2.setText(supremecard.getcontextpara());
        }
        if(Searchsupremecard.getquestionpara().equals("")){
            viewHolder.line3.setText("Content not uploaded");
        }else {
           // viewHolder.line3.setText(supremecard.getquestionpara());
        }
        if(Searchsupremecard.getanswerpara().equals("")){
            viewHolder.line4.setText("Content not uploaded");
        }else {
           // viewHolder.line4.setText(supremecard.getanswerpara());
        }
        if(Searchsupremecard.getreferencepara().equals("")){
            viewHolder.line5.setText("Content not uploaded");
        }else {
            //viewHolder.line5.setText(supremecard.getreferencepara());
        }*/







            if (viewHolder.line3 != null)
                viewHolder.line3.setText(Html.fromHtml(Searchsupremecard.getquestionpara()).toString());


            if (viewHolder.line4 != null)
                viewHolder.line4.setText(Html.fromHtml(Searchsupremecard.getanswerpara()).toString());








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
    /*private void startDownload() {

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
                Log.e("SUPREME",""+e);
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
    }*/

}


