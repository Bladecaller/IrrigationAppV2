package viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import model.room.entity.Account;
import model.room.entity.Electricity;
import model.room.entity.Temperature;
import model.room.repositories.AccountRepo;
import model.room.repositories.ElectricityRepo;
import model.room.repositories.TemperatureRepo;

public class AccountRepoViewModel extends AndroidViewModel {
    private AccountRepo accountRepo;
    private ElectricityRepo electricityRepo;

    public AccountRepoViewModel(@NonNull Application application){
        super(application);
        accountRepo = new AccountRepo(application);
        electricityRepo = new ElectricityRepo(application);
    }
    public LiveData<Electricity> getElectricityPrice(String side){
        electricityRepo.getElectricity(side);
        return electricityRepo.getElectricityFromRoom();
    }
    public void addAccount(Account acc){
        accountRepo.accountInsert(acc);
    }

    public LiveData<Account> getCurrentAccount(){
        return accountRepo.getCurrentAccount();
    }
}
