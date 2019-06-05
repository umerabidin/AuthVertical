package com.example.authvertical.network;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class APIHelper {
    private static final String BASE_URL = "http://142.4.198.118:19709/api/";
    private static APIHelper instance = new APIHelper();
    private RefitAPI refitService;


    private APIHelper() {
        Retrofit retrofit = createAdapter().build();
        refitService = retrofit.create(RefitAPI.class);
    }

    public synchronized static APIHelper getInstance() {
        if (instance == null)
            instance = new APIHelper();
        return instance;
    }


    private Retrofit.Builder createAdapter() {
        // Define the interceptor, add authentication headers
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC)
                .setLevel(HttpLoggingInterceptor.Level.HEADERS)
                .setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();

            Request request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .method(original.method(), original.body())
                    .build();

            return chain.proceed(request);
        });
        OkHttpClient client = httpClient.addInterceptor(interceptor)
                .readTimeout(180, TimeUnit.SECONDS)
                .connectTimeout(180, TimeUnit.SECONDS)
                .build();


        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create(gson));
    }


    public Call<JsonElement> postRequest(HashMap<String, String> headers, String url, RequestBody requestBody) {
        if (headers.size() > 0)
            return refitService.postRequestWithHeaders(headers, url, requestBody);
        else
            return refitService.postRequest(url, requestBody);
    }


//    public Call<JsonElement> addCitizen(String url, HashMap<String, String> headers, String firstName, String lastName, String dob,
//                                        String gender, String fatherNIN, String motherNIN, String phoneNo,
//                                        String email_address, String address, String password,
//                                        String scanned_image_base64, String userBase64Image, String bloodGroup, String martialStatus) {
//        return refitService.addCitizen(url, headers, firstName, lastName, dob, gender, fatherNIN, motherNIN, phoneNo, email_address,
//                address, password, scanned_image_base64, userBase64Image, bloodGroup, martialStatus);
//
//
//    }

    public Call<JsonElement> getRequest(HashMap<String, String> headers, String url) {
        return refitService.getRequest(headers, url);
    }
}
