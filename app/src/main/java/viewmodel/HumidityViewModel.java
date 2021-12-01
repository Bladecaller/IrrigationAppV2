package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.room.entity.Humidity;
import model.room.repositories.HumidityRepo;

public class HumidityViewModel extends AndroidViewModel {
    private HumidityRepo humidityRepo;

    public HumidityViewModel(@NonNull Application application){
        super(application);
        humidityRepo = new HumidityRepo(application);
    }
    public LiveData<List<Humidity>> getHumidity(String location){
        if(location!=null){
            humidityRepo.getHumidity(location);
        }
        return humidityRepo.getHumidityList();
    }
}
