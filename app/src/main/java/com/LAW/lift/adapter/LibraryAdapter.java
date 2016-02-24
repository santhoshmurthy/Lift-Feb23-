package com.LAW.lift.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.activity.Library;
import com.LAW.lift.activity.MainActivity;
import com.LAW.lift.app.MyVolley;
import com.LAW.lift.model.forumcard;
import com.LAW.lift.model.librarycard;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;
import java.util.List;

public class LibraryAdapter extends ArrayAdapter<librarycard> {
    private static final String TAG = "CardArrayAdapter";
    private List<librarycard> cardList = new ArrayList<librarycard>();
    librarycard librarycard;
    String bus_id_item;
    private ImageLoader mImageLoader;
    int textViewResourceId;

    //Map<String, String> addToCartMap = new HashMap<>();

    public LibraryAdapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void add(librarycard object) {
        cardList.add(object);
        super.add(object);
    }

    @Override
    public int getCount() {
        return this.cardList.size();
    }

    @Override
    public librarycard getItem(int index) {
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
            viewHolder.line1 = (NetworkImageView) row.findViewById(R.id.retimag);
            viewHolder.line2 = (TextView) row.findViewById(R.id.monthtext);


            //viewHolder.line2 = (TextView) row.findViewById(R.id.bus_location);
            //viewHolder.line3 = (TextView) row.findViewById(R.id.bus_time);
            row.setTag(viewHolder);
        } else {
            viewHolder = (CardViewHolder) row.getTag();
        }
        mImageLoader = MyVolley.getImageLoader();
       librarycard = getItem(position);
        String url = librarycard.getretimag();
        if(librarycard.getretimag().equals("url")){
            mImageLoader.get(url, ImageLoader.getImageListener(viewHolder.line1, R.drawable.bg, R.drawable.bg));
        }
        else if(url.length() > 0) {
            viewHolder.line1.setImageUrl(url, MyVolley.getImageLoader());
            mImageLoader.get(url, ImageLoader.getImageListener(viewHolder.line1, R.drawable.loadingretailer, R.drawable.loadingretailer));
            //R.drawable.loadings, R.drawable.ico_error));

        }else {
            mImageLoader.get(url, ImageLoader.getImageListener(viewHolder.line1, R.drawable.bg, R.drawable.bg));
        }



        if(librarycard.getmonthtext().equals("month")){
            viewHolder.line2.setText("");
        }else {
            viewHolder.line2.setText(librarycard.getmonthtext());
        }
        viewHolder.line1.setSelected(true);
        viewHolder.line2.setSelected(true);

        mImageLoader = MyVolley.getImageLoader();
     if(librarycard.getretimag().equals("url") || librarycard.getmonthtext().equals("month")){
       row.setClickable(false);
      }else {

     row.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Intent homeIntent = new Intent(getContext(), MainActivity.class);
            Log.i("Adapter", position + " Deal_id onClick " + Library.Id[position] + Library.month[position]);
            //Log.i("Adapter"+"MainActivity"+MainActivity.);
            homeIntent.putExtra("book_id", Library.Id[position]);
            homeIntent.putExtra("month", Library.month[position]);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getContext().startActivity(homeIntent);

        }
    });
}
        return row;
    }


    static class CardViewHolder {
        NetworkImageView line1;
        TextView line2;

    }
}



