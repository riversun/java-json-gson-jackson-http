package com.example.http_client.okhttp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.jackson.Model;
import com.google.gson.Gson;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * HTTPライブラリに「OkHttp3」、GSON操作ライブラリに「GSON」をつかって、
 * サーバーからJSONを「GET」するサンプル
 */
public class OkHttpGetClient {

    public static void main(String[] args) throws IOException {
        OkHttpGetClient client = new OkHttpGetClient();
        Model result = client.callWebAPI();
        System.out.println("Firstname is " + result.person.firstName);
    }

    private static final String WEB_API_ENDPOINT = "http://localhost:8080/api";
    private final Gson mGson = new Gson();

    public Model callWebAPI() throws IOException {
        final Map<String, String> httpHeaders = new LinkedHashMap<String, String>();
        final String resultStr = doGet(WEB_API_ENDPOINT, httpHeaders);
        final Model model = mGson.fromJson(resultStr, Model.class);
        return model;
    }

    public String doGet(String url, Map<String, String> headers) throws IOException {
        final Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .build();
        final OkHttpClient client = new OkHttpClient.Builder().build();
        final Response response = client.newCall(request).execute();
        final String resultStr = response.body().string();
        return resultStr;
    }

}
