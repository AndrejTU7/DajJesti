package com.dajjesti.android.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dajjesti.android.R;
import com.dajjesti.android.activities.ProductCategoryActivity;
import com.dajjesti.android.adapters.TableAdapter;
import com.dajjesti.android.api.Api;
import com.dajjesti.android.models.Tables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class TableFragment extends Fragment {
    RecyclerView mRecyclerView;

    public TableFragment() {
        // Required empty public constructor
    }

    public static TableFragment newInstance() {
        return new TableFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_table, container, false);

        GridLayoutManager glm = new GridLayoutManager(getContext(), getResources().getInteger(R.integer.tableRow));
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(glm);

        Api.get(Api.API_TABLES, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Type listType = new TypeToken<List<Tables>>(){}.getType();
                    String jsonOutput = new String(responseBody, "UTF-8");
                    List<Tables> tableList = new Gson().fromJson(jsonOutput, listType);
                    mRecyclerView.setAdapter(new TableAdapter(getContext(),tableList, new TableAdapter.OnAdapterClickListener() {
                        @Override
                        public void onClick(Tables table) {
                            Intent intent = new Intent(getContext(), ProductCategoryActivity.class);
                            startActivity(intent);
                        }
                    }));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("FAIL", new String(responseBody));
            }
        });

        return view;
    }


}
