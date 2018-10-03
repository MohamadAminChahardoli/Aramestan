package company.aryasoft.aramestan.ApiConnection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiServiceGenerator {

    private static Retrofit RetrofitInstance;
    private static Gson GsonInstance;
    private static OkHttpClient Client;
    private static OkHttpClient.Builder HttpClient;
    private final static String BASE_URL = "http://test.aryasoft.org/";

    public static DeceasedApis getApiService()
    {
        initializeComponents();
        return RetrofitInstance.create(DeceasedApis.class);
    }

    private static void initializeComponents()
    {
        if(HttpClient == null)
        {
            initializeHttpClient();
        }
        if (Client == null)
        {
            Client = HttpClient.build();
        }
        if (GsonInstance == null)
        {
            GsonInstance = new GsonBuilder().setLenient().create();
        }
        if (RetrofitInstance == null)
        {
            initializeRetrofit();
        }
    }

    private static void initializeHttpClient()
    {
        HttpClient = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS);
    }

    private static void initializeRetrofit()
    {
        RetrofitInstance = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(Client)
                .addConverterFactory(GsonConverterFactory.create(GsonInstance))
                .build();
    }


}
