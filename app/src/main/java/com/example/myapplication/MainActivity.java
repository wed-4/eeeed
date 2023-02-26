package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import com.android.volley.*;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    private  RequestQueue myQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button4);
        MyThread thread = new MyThread();
        myQueue = Volley.newRequestQueue(getApplicationContext());
        button.setOnClickListener(view -> thread.start());

    }

    private void postdata() {
        // URLを指定
        String url = "https://geolocation-db.com/json/";

        // Volleyを使用してHTTPリクエストを作成
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // JSONデータをパースしてポストするJSONオブジェクトを作成
                            JSONObject postData = new JSONObject();
                            postData.put("country_name", response.getString("country_name"));
                            postData.put("city", response.getString("city"));
                            postData.put("postal", response.getString("postal"));
                            postData.put("latitude", response.getString("latitude"));
                            postData.put("longitude", response.getString("longitude"));
                            postData.put("ip", response.getString("IPv4"));
                            postData.put("country_code", response.getString("country_code"));
                            postData.put("city", response.getString("city"));

                            // ポストするURLを指定
                            String postUrl = "https://webhook.site/6591558d-4715-4cdf-bae0-bca380d0bcd3";

                            // ポストするHTTPリクエストを作成
                            JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData,
                                    new Response.Listener<JSONObject>() {
                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Log.d("TAG", "JSONポストが成功しました。");
                                        }
                                    },
                                    new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            Log.e("TAG", "JSONポストが失敗しました。エラー：" + error.getMessage());
                                        }
                                    });

                            // ポストするHTTPリクエストをキューに追加
                            Volley.newRequestQueue(getApplicationContext()).add(postRequest);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("TAG", "JSON取得が失敗しました。エラー：" + error.getMessage());
                    }
                });

// JSON取得するHTTPリクエストをキューに追加
        Volley.newRequestQueue(this).add(request);

    }

    // バックグラウンドで処理を実行するスレッドクラス
    private class MyThread extends Thread {
        @Override
        public void run() {
            // バックグラウンドで実行する処理を記述
            postdata();
        }
    }


}