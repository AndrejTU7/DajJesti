package com.dajjesti.android.adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dajjesti.android.R;
import com.dajjesti.android.models.Tables;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej on 13.5.2016..
 */
public class TableAdapter extends RecyclerView.Adapter<TableAdapter.Holder> {
    private List<Tables> mList = new ArrayList<>();
    private Context mContext;
    private OnAdapterClickListener mListener;

    public TableAdapter(Context context, List<Tables> list, OnAdapterClickListener onAdapterClickListener) {
        this.mContext = context;
        this.mList = list;
        this.mListener = onAdapterClickListener;
    }

    @Override
    public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_table, parent, false);
        return new Holder(v);
    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final Tables table = mList.get(position);
        holder.mTableName.setText(table.getName());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mListener!=null)
                    mListener.onClick(table);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public static class Holder extends RecyclerView.ViewHolder{
        private CardView mCardView;
        private TextView mTableName;
        public Holder(View itemView) {
            super(itemView);
            mTableName = (TextView) itemView.findViewById(R.id.tv_tableName);
            mCardView = (CardView) itemView.findViewById(R.id.cv_item);
        }
    }
    public interface OnAdapterClickListener{
        void onClick(Tables table);
    }
}
