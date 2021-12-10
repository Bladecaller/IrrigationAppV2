package api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class MyRetrofitRecommendations {

    public InterfaceAPI api;
    public Gson gson;


    public MyRetrofitRecommendations() {
        gson = new GsonBuilder()
                .setLenient()
                //.registerTypeAdapterFactory(typeFactory)
                .create();

        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://bprapi20211113155922.azurewebsites.net/api/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        this.api = retrofit.create(InterfaceAPI.class);

    }
}