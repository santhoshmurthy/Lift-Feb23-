package com.LAW.lift.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.LAW.lift.R;
import com.LAW.lift.model.Card;
import com.LAW.lift.model.MainCard;

import java.util.ArrayList;
import java.util.List;

public  class  mainadapter extends ArrayAdapter<MainCard> {
private static final String TAG = "CardArrayAdapter";
private List<MainCard> cardList = new ArrayList<MainCard>();

        String bus_id_item;
        int textViewResourceId;

//Map<String, String> addToCartMap = new HashMap<>();

public mainadapter(Context context, int textViewResourceId) {

        super(context, textViewResourceId);
        this.textViewResourceId = textViewResourceId;
        }

@Override
public void add(MainCard object) {
        cardList.add(object);
        super.add(object);
        }

@Override
public int getCount() {
        return this.cardList.size();
        }

@Override
public MainCard getItem(int index) {
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
        viewHolder.line1 = (TextView) row.findViewById(R.id.mon);


        //viewHolder.line2 = (TextView) row.findViewById(R.id.bus_location);
        //viewHolder.line3 = (TextView) row.findViewById(R.id.bus_time);
        row.setTag(viewHolder);
        } else {
        viewHolder = (CardViewHolder) row.getTag();
        }

    MainCard MainCard = getItem(position);
        viewHolder.line1.setText(MainCard.getmonths());


        viewHolder.line1.setSelected(true);

        return row;
        }





public Bitmap decodeToBitmap(byte[] decodedByte) {
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
        }



static class CardViewHolder {
    TextView line1;

}
}

