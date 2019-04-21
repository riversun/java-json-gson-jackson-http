package com.example.http_client.urlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.jackson.Model;
import com.google.gson.Gson;

/**
 * HTTP通信にはJava標準の「HttpUrlConnection」、GSON操作ライブラリに「GSON」をつかって、
 * サーバーからJSONを「GET」するサンプル
 */
public class UrlConnHttpGetClient {

    public static void main(String[] args) throws IOException {
        UrlConnHttpGetClient client = new UrlConnHttpGetClient();
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

        final int TIMEOUT_MILLIS = 0;// タイムアウトミリ秒：0は無限

        final StringBuffer sb = new StringBuffer("");

        HttpURLConnection httpConn = null;
        BufferedReader br = null;
        InputStream is = null;
        InputStreamReader isr = null;

        try {
            URL urlObj = new URL(url);
            httpConn = (HttpURLConnection) urlObj.openConnection();
            httpConn.setConnectTimeout(TIMEOUT_MILLIS);// 接続にかかる時間
            httpConn.setReadTimeout(TIMEOUT_MILLIS);// データの読み込みにかかる時間
            httpConn.setRequestMethod("GET");// HTTPメソッド
            httpConn.setUseCaches(false);// キャッシュ利用
            httpConn.setDoOutput(false);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.setDoInput(true);// レスポンスのボディの受信を許可

            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                }
            }
            final int responseCode = httpConn.getResponseCode();
            String encoding = httpConn.getContentEncoding();
            if (encoding == null) {
                encoding = "UTF-8";
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
                isr = new InputStreamReader(is, encoding);
                br = new BufferedReader(isr);
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } else {
                // ステータスがHTTP_OK(200)以外の場合
                throw new IOException("responseCode is " + responseCode);
            }

        } catch (IOException e) {
            throw e;
        } finally {
            // Java1.6 Compliant
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                }
            }
            if (httpConn != null) {
                httpConn.disconnect();
            }
        }
        return sb.toString();

    }

}
