package com.example.coursework2;

import android.os.Handler;

import org.junit.Test;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    private static final String SERVER_URL = "https://courseworkapi.azurewebsites.net/";

    @Test
    public void  makeRequest() throws IOException {


        Request request = new Request.Builder()
                .url(SERVER_URL + "users")
                .build();

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(request);
        Response response = call.execute();

        assertEquals(200, response.code());
    }
}