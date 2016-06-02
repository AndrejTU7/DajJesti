package com.dajjesti.android.adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import com.dajjesti.android.R;
import com.dajjesti.android.models.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej on 14.5.2016..
 */
public class PriceListAdapter implements SpinnerAdapter {

    private List<Products.Price> mList = new ArrayList<>();
    private Context mContext;

    public PriceListAdapter(Context context, List<Products.Price> list) {
        this.mList = list;
        this.mContext = context;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver observer) {

    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Products.Price getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    @NonNull
    private View getSpinnerView(int position, View convertView, ViewGroup parent) {
        Products.Price price = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        Holder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new Holder();
            LayoutInflater inflater = LayoutInflater.from(mContext);
            convertView = inflater.inflate(R.layout.item_price, parent, false);
            viewHolder.name = (TextView) convertView.findViewById(R.id.tv_name);
            viewHolder.price = (TextView) convertView.findViewById(R.id.tv_price);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (Holder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        viewHolder.name.setText(price.getType());
        viewHolder.price.setText(String.format("%s kn", String.valueOf(price.getPrice())));
        // Return the completed view to render on screen
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return 1;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public boolean isEmpty() {
        return mList.size() == 0 ? true : false;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getSpinnerView(position, convertView, parent);
    }

    public static class Holder {
        private TextView name, price;
    }
}
