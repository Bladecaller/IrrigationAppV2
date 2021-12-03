package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import api.MyRetrofitElectricity;
import model.room.dao.ElectricityDao;
import model.room.entity.Electricity;
import model.room.entity.Humidity;
import model.room.entity.apiDataModelClimate.Root;
import model.room.entity.apiDataModelElectricity.RootElectricity;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ElectricityRepo {
    private MyRetrofitElectricity retrofit;
    private String side;
    private double price;
    private ElectricityDao electricityDao;

    public ElectricityRepo(Application application){
        retrofit = new MyRetrofitElectricity();
        side = "";
        price = 0.0;
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        electricityDao = db.electricityDao();
    }

    public double getElectricity(String string){
        if(string == "West"){
            side = "DK1";
        }
        if(string =="East"){
            side = "DK2";
        }
        Call<RootElectricity> call = retrofit.api.getElectricity("datastore_search_sql?sql=SELECT%20%20\"SpotPriceDKK\"%20%20from%20\"elspotprices\"%20WHERE%20\"PriceArea\"%20=%20%27"+side+"%27%20%20ORDER%20BY%20\"HourDK\"%20DESC%20LIMIT%201");
        call.enqueue(new Callback<RootElectricity>(){
            @Override
            public void onResponse (Call <RootElectricity> call, Response<RootElectricity> response){
                if(response.body()!=null){
                    if(!response.body().result.records.isEmpty()){
                        price = response.body().result.records.get(0).SpotPriceDKK;
                        insert(new Electricity(9999,price));
                    }
                }
            }
            @Override
            public void onFailure(Call<RootElectricity> call, Throwable t) {
                t.printStackTrace();
                System.out.println(t.getMessage());
            }
        });
        System.out.println("RETURNING PRICE OF " + price);
                return price;
    }


    public void insert(Electricity obj) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            electricityDao.insertElectricityLocation(obj);
        });
    }

    //delete all climates
    public void empty(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            electricityDao.deleteAll();
        });

    }

    // return a list of all climate to the viewmodel
    public LiveData<Electricity> getElectricityFromRoom(){
        return electricityDao.getElectricity();
    }

}
