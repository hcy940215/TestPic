package com.shengmingji.test;


import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public interface InternetService {

    @POST("ecp/contact/avatar/update")
    Call<UpdateBean> updateMultiType(@Body RequestBody body);

    @Multipart
    @POST("ecp/openapi/ecp/contact/avatar/update")
    Call<UpdateBean> updateMultiTypes(@PartMap() Map<String, RequestBody> partMap,
                                      @Part MultipartBody.Part file);

    @Multipart
    @POST("ecp/contact/avatar/update")
    Call<UpdateBean> updateMultiTypess(@Part MultipartBody.Part args,
                                       @Part MultipartBody.Part parts);

    @Multipart
    @POST("http://218.94.117.26:12721/ecp/openapi/ecp/contact/avatar/update")
    Call<UpdateBean> updateMultiTypesss(@Part MultipartBody.Part parts);

}
