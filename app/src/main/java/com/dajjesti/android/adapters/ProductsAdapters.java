package com.dajjesti.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dajjesti.android.R;
import com.dajjesti.android.models.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej on 14.5.2016..
 */
public class ProductsAdapters extends SingleTextAdapter {
    private Context mContext;
    private List<Products> mList = new ArrayList<>();
    private OnProductListener mListener;


    public ProductsAdapters(Context context, List<Products> list, OnProductListener listener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = listener;
    }

    @Override
    public void onBindViewHolder(Holder holder, int position) {
        final Products products = mList.get(position);
        holder.textView.setText(products.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onProductClick(products);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnProductListener {
        void onProductClick(Products products);
    }
}
