package com.example.http_client.okhttp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.gson.Person;
import com.example.gson.Pet;
import com.example.gson.Model;
import com.google.gson.Gson;

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
    private final Gson mGson = new Gson();

    public String callWebAPI() throws IOException {

        Model model = new Model();
        model.person = new Person();
        model.person.firstName = "ジョン";
        model.person.lastName = "ドゥ";
        model.person.address = "ニューヨーク";
        model.person.pets = new ArrayList<Pet>();
        Pet pet1 = new Pet();
        pet1.type = "犬";
        pet1.name = "ジョリー";
        model.person.pets.add(pet1);
        Pet pet2 = new Pet();
        pet2.type = "猫";
        pet2.name = "グリザベラ";
        model.person.pets.add(pet2);
        Pet pet3 = new Pet();
        pet3.type = "魚";
        pet3.name = "ニモ";
        model.person.pets.add(pet3);

        final String postJson = mGson.toJson(model);

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
