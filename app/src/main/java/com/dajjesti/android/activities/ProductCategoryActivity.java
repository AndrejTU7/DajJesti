package com.dajjesti.android.activities;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.widget.FrameLayout;

import com.dajjesti.android.R;
import com.dajjesti.android.dialogs.ProductDialog;
import com.dajjesti.android.fragments.ProductCategoryFragment;
import com.dajjesti.android.fragments.ProductsFragment;
import com.dajjesti.android.models.Categories;
import com.dajjesti.android.models.Products;

public class ProductCategoryActivity extends ChildActivity implements ProductsFragment.OnProductListListener, ProductCategoryFragment.OnProductCategoryListener {
    private FrameLayout mMobileLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_category);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mMobileLayout = (FrameLayout) findViewById(R.id.mobile_container);

        if (mMobileLayout != null) {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.mobile_container, ProductCategoryFragment.newInstance());
            fragmentTransaction.commit();
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.tablet_container_category, ProductCategoryFragment.newInstance());
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onProductCategoryClick(Categories category) {
        if (mMobileLayout != null) {
            Intent intent = new Intent(ProductCategoryActivity.this, ProductActivity.class);
            intent.putExtra(ProductActivity.ARG_CATEGORY_ID, category.getId());
            startActivity(intent);
        } else {
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.tablet_container_products, ProductsFragment.newInstance(category.getId()));
            fragmentTransaction.commit();
        }
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
