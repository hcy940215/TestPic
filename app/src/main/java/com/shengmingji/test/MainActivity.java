package com.shengmingji.test;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements Callback<UpdateBean> {
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;
    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    private InternetService service;
    private static final String BASE_URL = "http://218.94.117.26:12721/";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new TabClassFragment()).commit();
        // getSupportFragmentManager().beginTransaction().replace(R.id.fl_container, new HomeFragment()).commit();


        OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();
        httpClientBuilder.connectTimeout(3, TimeUnit.SECONDS);
        httpClientBuilder.readTimeout(2, TimeUnit.SECONDS);
        httpClientBuilder.writeTimeout(2, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(httpClientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        service = retrofit.create(InternetService.class);

    }

    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        //创建拍照存储的图片文件
        String path = Environment.getExternalStorageDirectory()
                + File.separator + "image";
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdir();
        }
        tempFile = new File(path, PHOTO_FILE_NAME);
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {
            case REQUEST_CAPTURE:
                if (resultCode == RESULT_OK) {
                    Uri uri = Uri.fromFile(tempFile);
                    String filePath = FileUtil.getRealFilePathFromUri(getApplicationContext(), uri);
                    Log.i("TAG", "onActivityResult: "+filePath);
                    File file = new File(filePath);
                    HashMap<String, RequestBody> map = new HashMap<>();
                    String content = requestUpdateAvater().toString();
                    RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), content);
                    map.put("args",requestBody);

                    RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                    Log.i("TAG", "onActivityResult: "+requestFile);

                    MultipartBody.Part body =
                            MultipartBody.Part.createFormData("file", file.getName(), requestFile);

                    service.updateMultiTypes(map,body).enqueue(this);
                }
                break;

            default:
                break;
        }
    }

    public void openPhoto(View view) {
        gotoCamera();
    }

    public JSONObject requestUpdateAvater() {
        JSONObject object = new JSONObject();
        JSONObject head = new JSONObject();
        try {
            head.put("tokenId", "edd336a2-6d58-4888-a7c5-3e82cb5a1127");
            object.put("_header_", head);
            object.put("x", 0);
            object.put("y", 0);
            object.put("size", 128);
            return object;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onResponse(Call<UpdateBean> call, Response<UpdateBean> response) {
        String avatarId = response.body().getAvatarId();
    }

    @Override
    public void onFailure(Call<UpdateBean> call, Throwable t) {
        Log.i("TAG", "onFailure: "+t.getMessage());
    }
}
