package com.dajjesti.android.api;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import cz.msebera.android.httpclient.HttpEntity;

/**
 * Created by Andrej on 13.5.2016..
 */
public class Api {
    private static final String BASE_URL = "https://www.dajjesti.sklempej.com/api/";

    public static final String API_TABLES = "tables";
    public static final String API_PRODUCTS = "products";
    public static final String API_CATEGORIES = "categories";
    public static final String API_ORDER = "order"; // test api


    private static AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void postJson(Context context, String url, HttpEntity data, AsyncHttpResponseHandler responseHandler) {
        client.post(context, getAbsoluteUrl(url), data, "application/json", responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl) {
        return BASE_URL + relativeUrl;
    }
}
