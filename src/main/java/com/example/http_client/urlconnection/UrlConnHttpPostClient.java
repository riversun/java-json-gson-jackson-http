package com.example.http_client.urlconnection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import com.example.http_client.okhttp.OkHttpPostClient;

/**
 * HTTP通信にはJava標準の「HttpUrlConnection」、GSON操作ライブラリに「GSON」をつかって、
 * サーバーからJSONを「POST」するサンプル
 */
public class UrlConnHttpPostClient {

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

    private String doPost(String url, String encoding, Map<String, String> headers, String jsonString) throws IOException {

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
            httpConn.setRequestMethod("POST");// HTTPメソッド
            httpConn.setUseCaches(false);// キャッシュ利用
            httpConn.setDoOutput(true);// リクエストのボディの送信を許可(GETのときはfalse,POSTのときはtrueにする)
            httpConn.setDoInput(true);// レスポンスのボディの受信を許可

            if (headers != null) {
                for (String key : headers.keySet()) {
                    httpConn.setRequestProperty(key, headers.get(key));// HTTPヘッダをセット
                }
            }

            final OutputStream os = httpConn.getOutputStream();
            final boolean autoFlash = true;
            final PrintStream ps = new PrintStream(os, autoFlash, encoding);
            ps.print(jsonString);
            ps.close();

            final int responseCode = httpConn.getResponseCode();

            String _responseEncoding = httpConn.getContentEncoding();
            if (_responseEncoding == null) {
                _responseEncoding = "UTF-8";
            }

            if (responseCode == HttpURLConnection.HTTP_OK) {
                is = httpConn.getInputStream();
                isr = new InputStreamReader(is, _responseEncoding);
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
