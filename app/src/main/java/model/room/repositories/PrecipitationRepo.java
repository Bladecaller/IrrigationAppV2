package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.HumidityDao;
import model.room.dao.PrecipitationDao;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PrecipitationRepo {
    private MyRetrofit retrofit;
    private PrecipitationDao Dao;


    public PrecipitationRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        Dao = db.precipitationDao();
    }

    public void getPrecipitation(String location){
        String locationCode = "0";
        switch (location) {
            case "Horsens":
                locationCode = "06102";
                break;
            case "Aarhus":
                locationCode = "06074";
                break;
        }

        Call<Root> call3 = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=weather&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call3.enqueue(new Callback<Root>(){
            String convertedValue = "";
            double actualValue = 0.0;
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                actualValue=response.body().features.get(0).properties.value;
                if (actualValue>=100.0 && actualValue<=105.0 || actualValue>=0.0 && actualValue<=5.0){
                    convertedValue = "Dissolving Clouds";
                }
                if (actualValue>=106.0 && actualValue<=110.0 || actualValue>=6.0 && actualValue<=10.0){
                    convertedValue = "Mist";
                }
                if (actualValue>=111.0 && actualValue<=119.0 || actualValue>=11.0 && actualValue<=19.0){
                    convertedValue = "Small Precipitation";
                }
                if (actualValue>=120.0 && actualValue<=129.0 || actualValue>=20.0 && actualValue<=29.0){
                    convertedValue = "Snow";
                }
                if (actualValue>=130.0 && actualValue<=135.0 || actualValue>=30.0 && actualValue<=35.0){
                    convertedValue ="Duststorm/Sandstorm";
                }
                if (actualValue>=136.0 && actualValue<=139.0 || actualValue>=36.0 && actualValue<=39.0){
                    convertedValue = "Drifting snow";
                }
                if (actualValue>=140.0 && actualValue<=149.0 || actualValue>=40.0 && actualValue<=49.0){
                    convertedValue = "Fog";
                }
                if (actualValue>=150.0 && actualValue<=159.0 || actualValue>=50.0 && actualValue<=59.0){
                    convertedValue = "Drizzle";
                }
                if (actualValue>=160.0 && actualValue<=169.0 || actualValue>=60.0 && actualValue<=69.0){
                    convertedValue = "Rain";
                }
                if (actualValue>=170.0 && actualValue<=179.0 || actualValue>=70.0 && actualValue<=79.0){
                    convertedValue = "Solid Precipitation";
                }
                if (actualValue>=180.0 && actualValue<=193.0 || actualValue>=80.0 && actualValue<=93.0){
                    convertedValue = "Rain Shower";
                }
                if (actualValue>=194.0 && actualValue<=199.0 || actualValue>=94.0 && actualValue<=99.0){
                    convertedValue = "Thunderstorm";
                }

                //Precipitation prep = new Precipitation(0,response.body().features.get(0).properties.value);
                System.out.println("Value is :"+ convertedValue);
                Precipitation prep = new Precipitation(0,convertedValue);
                insert(prep);
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
    }

    public void insert(Precipitation obj) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.insertPrecipitation(obj);
        });
    }

    //delete all climates
    public void emptyPrecipitationRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.deleteAll();
        });

    }

    // return a list of all climate to the viewmodel
    public LiveData<List<Precipitation>> getPrecipitationList(){
        return Dao.getPrecipitationList();
    }

}
