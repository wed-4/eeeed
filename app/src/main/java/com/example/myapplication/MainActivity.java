package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.button4);
        // マルチスレッドで処理を実行
        Volley.newRequestQueue(getApplicationContext());
        button.setOnClickListener(view -> PostData());

    }

    private void PostData() {
        // URLを指定
        String url = "https://geolocation-db.com/json/";

// Volleyを使用してHTTPリクエストを作成
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                response -> {
                    try {
                        // JSONデータをパースしてポストするJSONオブジェクトを作成
                        JSONObject postData = new JSONObject();
                        postData.put("ip", response.getString("IPv4"));
                        postData.put("country_code", response.getString("country_code"));
                        postData.put("city", response.getString("city"));

                        // ポストするURLを指定
                        String postUrl = "https://webhook.site/6591558d-4715-4cdf-bae0-bca380d0bcd3";

                        // ポストするHTTPリクエストを作成
                        JsonObjectRequest postRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData,
                                response1 -> Log.d("TAG", "JSONポストが成功しました。"),
                                error -> Log.e("TAG", "JSONポストが失敗しました。エラー：" + error.getMessage()));

                        // ポストするHTTPリクエストをキューに追加
                        Volley.newRequestQueue(getApplicationContext()).add(postRequest);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                },
                error -> Log.e("TAG", "JSON取得が失敗しました。エラー：" + error.getMessage()));

// JSON取得するHTTPリクエストをキューに追加
        Volley.newRequestQueue(this).add(request);

    }

    
}