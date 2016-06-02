package com.dajjesti.android.fragments;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dajjesti.android.R;
import com.dajjesti.android.adapters.ProductsAdapters;
import com.dajjesti.android.api.Api;
import com.dajjesti.android.models.Products;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cz.msebera.android.httpclient.Header;


public class ProductsFragment extends Fragment {

    private OnProductListListener mListener;
    public static final String ARG_CATEGORY_ID = "arg_category_id";
    private int category_id;

    public ProductsFragment() {
        // Required empty public constructor
    }

    public static ProductsFragment newInstance(int category_id) {
        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, category_id);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            category_id = getArguments().getInt(ARG_CATEGORY_ID);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_products_list, container, false);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_product_list);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);

        Api.get(Api.API_PRODUCTS, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Type listType = new TypeToken<List<Products>>() {
                    }.getType();
                    String jsonOutput = new String(responseBody, "UTF-8");
                    List<Products> productsList = new Gson().fromJson(jsonOutput, listType);
                    List<Products> productsListFilter = new ArrayList<Products>();
                    for (Products products : productsList) {
                        if (products.getCategory() == category_id) {
                            productsListFilter.add(products);
                        }
                    }

                    recyclerView.setAdapter(new ProductsAdapters(getContext(), productsListFilter, new ProductsAdapters.OnProductListener() {
                        @Override
                        public void onProductClick(Products products) {
                            if (mListener != null) {
                                mListener.onProductListClick(products);
                            }
                        }
                    }));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnProductListListener) {
            mListener = (OnProductListListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnProductListListener {
        void onProductListClick(Products products);
    }
}
