package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import model.room.entity.Precipitation;
import model.room.entity.Temperature;
import model.room.repositories.PrecipitationRepo;
import model.room.repositories.TemperatureRepo;

public class PrecipitationViewModel extends AndroidViewModel {
    private PrecipitationRepo precipitationRepo;

    public PrecipitationViewModel(@NonNull Application application){
        super(application);
        precipitationRepo = new PrecipitationRepo(application);
    }
    public LiveData<List<Precipitation>> getPrecipitation(String location){
        if(location!=null){
            precipitationRepo.getPrecipitation(location);
        }
        return precipitationRepo.getPrecipitationList();
    }
}
