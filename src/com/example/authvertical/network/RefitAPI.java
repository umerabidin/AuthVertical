package com.example.authvertical.network;

import com.google.gson.JsonElement;

import java.util.HashMap;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface RefitAPI {

    @POST("{end_point}")
    Call<JsonElement> postRequest(@Path(value = "end_point", encoded = true) String url, @Body RequestBody requestBody);

    @POST("{end_point}")
    Call<JsonElement> postRequestWithHeaders(@HeaderMap Map<String, String> headers, @Path(value = "end_point", encoded = true) String add_citizen_url,
                                             @Body RequestBody requestBody);


    /* appConstants.addElements("firstName", edFirstName.getText().toString());
                appConstants.addElements("lastName", edLastName.getText().toString());
                appConstants.addElements("dob", String.valueOf(date.getTime()));
                appConstants.addElements("gender", gender);
                appConstants.addElements("fatherNin", edFatherNIN.getText().toString());
                appConstants.addElements("motherNin", edMotherNIN.getText().toString());
                appConstants.addElements("phone", edPhoneNumber.getText().toString());
                appConstants.addElements("email", edEmail.getText().toString());
                appConstants.addElements("address", edAddress.getText().toString());
                appConstants.addElements("password", edPassword.getText().toString());
                appConstants.addElements("fingerprints", scanned_image_base64);
//                appConstants.addElements("fingerprints", "scanned_image");
                appConstants.addElements("photo", userBase64Image);
//                appConstants.addElements("photo", "user_image");
                appConstants.addElements("bloodGroup", bloodGroup);
                appConstants.addElements("maritalStatus", martialStatus);*/


//    @FormUrlEncoded
//    @POST("{end_point}")
//    Call<JsonElement> addCitizen(@Path(value = "end_point", encoded = true) String url,
//                                 @HeaderMap Map<String, String> headers,
//                                 @Field("firstName") String firstName,
//                                 @Field("lastName") String lastName,
//                                 @Field("dob") String dob,
//                                 @Field("gender") String gender,
//                                 @Field("fatherNin") String fatherNIN,
//                                 @Field("motherNin") String motherNIN,
//                                 @Field("phone") String phoneNo,
//                                 @Field("email") String email_address,
//                                 @Field("address") String address,
//                                 @Field("password") String password,
//                                 @Field("fingerprints") String scanned_image_base64,
//                                 @Field("photo") String userBase64Image,
//                                 @Field("bloodGroup") String bloodGroup,
//                                 @Field("maritalStatus") String martialStatus);

    @GET("{end_point}")
    Call<JsonElement> getRequest(@HeaderMap HashMap<String, String> headers,
                                 @Path(value = "end_point", encoded = true) String url);
}
