package com.dajjesti.android.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dajjesti.android.R;
import com.dajjesti.android.adapters.CategoriesAdapter;
import com.dajjesti.android.api.Api;
import com.dajjesti.android.models.Categories;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ProductCategoryFragment extends Fragment {

    private OnProductCategoryListener mListener;

    public ProductCategoryFragment() {
        // Required empty public constructor
    }


    public static ProductCategoryFragment newInstance() {
        ProductCategoryFragment fragment = new ProductCategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_product_category, container, false);
        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_product_category);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(llm);
        Api.get(Api.API_CATEGORIES, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    Type listType = new TypeToken<List<Categories>>() {
                    }.getType();
                    String jsonOutput = new String(responseBody, "UTF-8");
                    List<Categories> categoriesList = new Gson().fromJson(jsonOutput, listType);
                    recyclerView.setAdapter(new CategoriesAdapter(getContext(), categoriesList, new CategoriesAdapter.OnCategoryListener() {
                        @Override
                        public void onCategoryClick(Categories categories) {
                            if (mListener != null) {
                                mListener.onProductCategoryClick(categories);
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
        if (context instanceof OnProductCategoryListener) {
            mListener = (OnProductCategoryListener) context;
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

    public interface OnProductCategoryListener {
        void onProductCategoryClick(Categories categories);
    }
}
