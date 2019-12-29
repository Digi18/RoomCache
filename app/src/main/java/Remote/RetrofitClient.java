package Remote;

import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    private static Retrofit retrofit = null;
    private static final String URL = "https://bookbudiapp.herokuapp.com/";

    public static Retrofit getInstance(){

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                                  .connectTimeout(20, TimeUnit.SECONDS)
                                  .readTimeout(20,TimeUnit.SECONDS)
                                  .writeTimeout(20,TimeUnit.SECONDS)
                                  .build();

        if(retrofit == null){

            retrofit = new Retrofit.Builder()
                       .baseUrl(URL)
                       .addConverterFactory(GsonConverterFactory.create(new GsonBuilder().setLenient().create()))
                       .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                       .client(okHttpClient)
                       .build();
        }

        return retrofit;
    }

    private RetrofitClient(){

    }
}
