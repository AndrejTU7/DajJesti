package com.dajjesti.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dajjesti.android.R;

/**
 * Created by Andrej on 14.5.2016..
 */
public abstract class SingleTextAdapter extends RecyclerView.Adapter<SingleTextAdapter.Holder> {
    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_single_text, parent, false);
        return new Holder(v);
    }

    public static class Holder extends RecyclerView.ViewHolder {
        public TextView textView;

        public Holder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_single_text);
        }
    }
}
