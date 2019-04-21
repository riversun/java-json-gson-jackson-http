package com.example.http_client.okhttp;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * HTTPライブラリに「OkHttp3」、GSON操作ライブラリに「GSON」をつかって、
 * サーバーにJSONを「POST」するサンプル
 */
public class OkHttpPostClient {

    public static void main(String[] args) throws IOException {
        OkHttpPostClient client = new OkHttpPostClient();
        String result = client.callWebAPI();
        System.out.println(result);
    }

    private static final String WEB_API_ENDPOINT = "http://localhost:8080/api";

    public String callWebAPI() throws IOException {

        final String postJson = "{" +
                "    \"person\": {" +
                "        \"firstName\": \"John\"," +
                "        \"lastName\": \"Doe\"," +
                "        \"address\": \"NewYork\"," +
                "        \"pets\": [" +
                "            {\"type\": \"Dog\", \"name\": \"Jolly\"}," +
                "            {\"type\": \"Cat\", \"name\": \"Grizabella\"}," +
                "            {\"type\": \"Fish\", \"name\": \"Nimo\"}" +
                "        ]" +
                "    }" +
                "}";

        final Map<String, String> httpHeaders = new LinkedHashMap<String, String>();
        final String resultStr = doPost(WEB_API_ENDPOINT, "UTF-8", httpHeaders, postJson);

        return resultStr;
    }

    public String doPost(String url, String encoding, Map<String, String> headers, String jsonString) throws IOException {
        final okhttp3.MediaType mediaTypeJson = okhttp3.MediaType.parse("application/json; charset=" + encoding);

        final RequestBody requestBody = RequestBody.create(mediaTypeJson, jsonString);

        final Request request = new Request.Builder()
                .url(url)
                .headers(Headers.of(headers))
                .post(requestBody)
                .build();

        final OkHttpClient client = new OkHttpClient.Builder()
                .build();
        final Response response = client.newCall(request).execute();// 同期呼び出し
        final String resultStr = response.body().string();
        return resultStr;
    }
}
