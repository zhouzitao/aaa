package com.bwei.zhouzitao.zhouzitao1123.utils;

import android.os.Handler;
import android.os.Message;

import com.google.common.io.CharStreams;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class HttpUtils {
    //get请求
    public HttpUtils doGet(String urlPath) {
        doHttp(urlPath);
        return this;
    }

    //网络请求
    private void doHttp(final String urlPath) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    URL url = new URL(urlPath);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(5000);
                    connection.connect();

                    int code = connection.getResponseCode();
                    Message message = Message.obtain();
                    if (code == HttpURLConnection.HTTP_OK) {
                        message.what = 2000;
                        String data = CharStreams.toString(new InputStreamReader(connection.getInputStream(), "utf-8"));
                        message.obj = data;
                    }
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            switch (what) {
                case 2000:
                    String data = (String) msg.obj;
                    listener.success(data);
                    break;
            }
        }
    };

    public interface HttpListener {
        void success(String data);
    }

    private HttpListener listener;

    public void result(HttpListener listener) {
        this.listener = listener;
    }
}
