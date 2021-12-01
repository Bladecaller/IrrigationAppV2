package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.room.entity.Climate;
import model.room.entity.Temperature;
import model.room.repositories.ClimateRepo;
import model.room.repositories.TemperatureRepo;

public class ClimateViewModel extends AndroidViewModel {
    private ClimateRepo climateRepo;

    public ClimateViewModel(@NonNull Application application){
        super(application);
        climateRepo = new ClimateRepo(application);
    }
    public LiveData<List<Climate>> getClimate(String location) throws InterruptedException {
        climateRepo.getClimate(location);
        return climateRepo.getClimateList();
    }
}
