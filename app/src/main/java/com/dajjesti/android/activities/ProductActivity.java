package com.dajjesti.android.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.dajjesti.android.R;
import com.dajjesti.android.dialogs.ProductDialog;
import com.dajjesti.android.fragments.ProductsFragment;
import com.dajjesti.android.models.Products;

public class ProductActivity extends ChildActivity implements ProductsFragment.OnProductListListener {

    public static final String ARG_CATEGORY_ID = "arg_category_id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.mobile_container, ProductsFragment.newInstance(getIntent().getExtras().getInt(ARG_CATEGORY_ID)));
        fragmentTransaction.commit();
    }

    @Override
    public void onProductListClick(Products products) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ProductDialog newFragment = ProductDialog.newInstance(products);
        Fragment prev = getSupportFragmentManager().findFragmentByTag(ProductDialog.ARG_DIALOG_KEY);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        newFragment.show(ft, ProductDialog.ARG_DIALOG_KEY);
    }

}
