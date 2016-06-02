package com.dajjesti.android.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.dajjesti.android.R;
import com.dajjesti.android.adapters.PriceListAdapter;
import com.dajjesti.android.api.Api;
import com.dajjesti.android.models.OrderResponse;
import com.dajjesti.android.models.Products;
import com.dajjesti.android.models.Tables;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.loopj.android.http.AsyncHttpResponseHandler;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;
import cz.msebera.android.httpclient.message.BasicHeader;
import cz.msebera.android.httpclient.protocol.HTTP;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Created by Andrej on 14.5.2016..
 */
public class ProductDialog extends DialogFragment {
    public static final String ARG_DIALOG_KEY = "arg_product_dialog";
    private Products mProduct;
    private static final String ARG_PRODUCT = "arg_product";
    private int amount = 1;


    public static ProductDialog newInstance(Products product) {
        ProductDialog fragment = new ProductDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_PRODUCT, product);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mProduct = getArguments().getParcelable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_product, container, false);

        TextView productDescription = (TextView) view.findViewById(R.id.tv_description);
        TextView productIngredients = (TextView) view.findViewById(R.id.tv_ingredients);
        final TextView tv_amount = (TextView) view.findViewById(R.id.tv_amount);
        tv_amount.setText(String.valueOf(amount));
        ImageButton bt_minus = (ImageButton) view.findViewById(R.id.bt_minus);
        ImageButton bt_plus = (ImageButton) view.findViewById(R.id.bt_plus);
        final Spinner spinner = (Spinner) view.findViewById(R.id.spinner);
        spinner.setAdapter(new PriceListAdapter(getContext(), mProduct.getPricelist()));

        EditText et_hint = (EditText) view.findViewById(R.id.et_hint);

        Button bt_cancel = (Button) view.findViewById(R.id.bt_cancel);
        final Button bt_order = (Button) view.findViewById(R.id.bt_order);
        bt_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HashMap<String, Object> paramMap = new HashMap<>();
                paramMap.put("id_product", mProduct.getId());
                paramMap.put("pricelist", spinner.getSelectedItem());
                paramMap.put("amount", amount);
                //paramMap.put("id_table", );
                String params = new Gson().toJson(paramMap);

                StringEntity data = null;
                try {
                    data = new StringEntity(params.toString());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
                data.setContentType(new BasicHeader(HTTP.CONTENT_TYPE, "application/json"));
                Api.postJson(getContext(), Api.API_ORDER, data, new AsyncHttpResponseHandler() {

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                        Type listType = new TypeToken<OrderResponse>() {
                        }.getType();
                        if (statusCode == 200) {
                            try {
                                String jsonOutput = new String(responseBody, "UTF-8");
                                OrderResponse orderStatus = new Gson().fromJson(jsonOutput, listType);
                                Toast.makeText(getContext(), orderStatus.getStatus(), Toast.LENGTH_SHORT).show();

                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            dismiss();
                        }

                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                    }
                });
            }
        });
        getDialog().setTitle(mProduct.getName());
        productDescription.setText(mProduct.getDescription());

        String ingridients = StringUtils.join(mProduct.getIngredients(), ',');
        productIngredients.setText(ingridients);

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        bt_minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (amount > 1) {
                    amount--;
                    tv_amount.setText(String.valueOf(amount));
                }
            }
        });
        bt_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                amount++;
                tv_amount.setText(String.valueOf(amount));
            }
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }
}
