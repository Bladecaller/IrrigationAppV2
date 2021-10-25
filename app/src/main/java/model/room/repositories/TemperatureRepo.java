package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.TemperatureDao;
import model.room.entity.Humidity;
import model.room.entity.Temperature;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TemperatureRepo {
    private MyRetrofit retrofit;
    private TemperatureDao Dao;


    public TemperatureRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        Dao = db.temperatureDao();
    }

    public void getTemperature(String location){
        String locationCode = "0";
       switch (location){
           case "Horsens": locationCode="06102";
           break;
           case "Aarhus" : locationCode="06074";
           break;
        }
        //Temperature----------------------------------------------------------------------------------------------------------------------

        Call<Root> call = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=temp_dry&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                System.out.println("Value is :"+ response.body().features.get(0).properties.value);
                insert(new Temperature(0,response.body().features.get(0).properties.value));
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
    }

    public void insert(Temperature obj) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.insertTemperature(obj);
        });
    }

    //delete all Temperature
    public void emptyTemperatureRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            Dao.deleteAll();
        });

    }

    // return a list of all Temperature to the viewmodel
    public LiveData<List<Temperature>> getTemperatureList(){
        return Dao.getTemperatureList();
    }

}
