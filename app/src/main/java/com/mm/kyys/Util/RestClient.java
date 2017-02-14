package com.mm.kyys.Util;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by 27740 on 2016/8/2.
 */
public class RestClient {

    private static AsyncHttpClient client = new AsyncHttpClient();
    private static SyncHttpClient syncHttpClient = new SyncHttpClient();

    private static String getAbsoluteUrl(String relativeUrl) {
        return AllData.IP + relativeUrl;
    }


    public static void get(String url, RequestParams params,
                           AsyncHttpResponseHandler responseHandler) {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params,
                            AsyncHttpResponseHandler responseHandler) {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void sPost(String url, RequestParams params,
                             AsyncHttpResponseHandler responseHandler) {
        syncHttpClient.post(getAbsoluteUrl(url), params, responseHandler);
    }
}