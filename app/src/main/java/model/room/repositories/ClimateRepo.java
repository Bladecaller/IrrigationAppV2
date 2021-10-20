package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.AccountDao;
import model.room.dao.ClimateDao;
import model.room.entity.Account;
import model.room.entity.Climate;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ClimateRepo {
    private MyRetrofit retrofit;
    private ClimateDao climateDao;


    public ClimateRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
    }

    public void getClimate(String location) throws InterruptedException {
        emptyClimateRepo();
        Climate climate = new Climate(0,null,null,null);
        String locationCode = "0";
        if(location == "Horsens"){
            locationCode = "06102";
        }
        //Temperature----------------------------------------------------------------------------------------------------------------------

        Call<Root> call = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=temp_dry&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                System.out.println("Value is :"+ response.body().features.get(0).properties.value);
                climate.setTemperature(response.body().features.get(0).properties.value);
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
        //Humidity-----------------------------------------------------------------------------------------------------------------------------
        Call<Root> call2 = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=humidity_past1h&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call2.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                System.out.println("Value is :"+ response.body().features.get(0).properties.value);
                climate.setHumidity(response.body().features.get(0).properties.value);
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
        //Storm ?----------------------------------------------------------------------------------------------------------------------------------
        Call<Root> call3 = retrofit.api.getClimate("metObs/collections/observation/items?stationId="+locationCode+"&parameterId=weather&period=latest&api-key=14cc5e73-90a5-463b-a221-68e503b2a396");
        call3.enqueue(new Callback<Root>(){
            @Override
            public void onResponse (Call <Root> call, Response<Root> response){
                String convertedValue = "";
                if (response.body().features.get(0).properties.value== 100.0){
                    convertedValue = "Clouds not observed";
                }
                System.out.println("Value is :"+ convertedValue);
                climate.setPrecipitation(convertedValue);
            }
            @Override
            public void onFailure(Call<Root> call, Throwable t) {
                t.printStackTrace();
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }
        });
        Thread.sleep(20000);
        climateInsert(climate);
    }

    public void climateInsert(Climate climate) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            climateDao.insertClimate(climate);
        });
    }

    //delete all climates
    public void emptyClimateRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            climateDao.deleteAll();
        });

    }

    // return a list of all climate to the viewmodel
    public LiveData<List<Climate>> getClimateList(){
        return climateDao.getClimateList();
    }

}
