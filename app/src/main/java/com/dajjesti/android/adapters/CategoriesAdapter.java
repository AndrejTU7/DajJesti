package com.dajjesti.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dajjesti.android.R;
import com.dajjesti.android.models.Categories;
import com.dajjesti.android.models.Products;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrej on 14.5.2016..
 */
public class CategoriesAdapter extends SingleTextAdapter {
    private List<Categories> mList = new ArrayList<>();
    private Context mContext;
    private OnCategoryListener mListener;

    public CategoriesAdapter(Context context, List<Categories> list, OnCategoryListener listener) {
        this.mList = list;
        this.mContext = context;
        this.mListener = listener;

    }

    @Override
    public void onBindViewHolder(Holder holder, final int position) {
        final Categories categories = mList.get(position);
        holder.textView.setText(categories.getName());
        holder.textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onCategoryClick(categories);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public interface OnCategoryListener {
        void onCategoryClick(Categories category);
    }


}
