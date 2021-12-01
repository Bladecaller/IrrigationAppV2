package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.room.entity.Humidity;
import model.room.entity.Temperature;
import model.room.repositories.HumidityRepo;
import model.room.repositories.TemperatureRepo;

public class TemperatureViewModel extends AndroidViewModel {
    private TemperatureRepo temperatureRepo;

    public TemperatureViewModel(@NonNull Application application){
        super(application);
        temperatureRepo = new TemperatureRepo(application);
    }
    public LiveData<List<Temperature>> getTemperature(String location){
        if(location!=null){
            temperatureRepo.getTemperature(location);
        }
        return temperatureRepo.getTemperatureList();
    }
}
