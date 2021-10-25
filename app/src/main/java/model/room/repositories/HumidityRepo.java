package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.ClimateDao;
import model.room.dao.HumidityDao;
import model.room.entity.Climate;
import model.room.entity.Humidity;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HumidityRepo {
    private MyRetrofit retrofit;
    private HumidityDao Dao;


    public HumidityRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        Dao = db.humidityDao();
    }

    public void getHumidity(String location){
        String locationCode = "0";
        switch (location) {
            case "Horsens":
                locationCode = "06102";
                break;
            case "Aarhus":
                locationCode = "06074";
                break;
        }
        Call<Root> call = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=humidity_past1h&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                Humidity hum = new Humidity(0,response.body().features.get(0).properties.value);
                insert(hum);
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
    }

    public void insert(Humidity obj) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.insertHumidity(obj);
        });
    }

    //delete all climates
    public void emptyHumidityRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.deleteAll();
        });

    }

    // return a list of all climate to the viewmodel
    public LiveData<List<Humidity>> getHumidityList(){
        return Dao.getHumidityList();
    }

}
