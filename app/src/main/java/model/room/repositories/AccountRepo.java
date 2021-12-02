package model.room.repositories;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.AccountDao;
import model.room.entity.Account;
import model.room.entity.apiDataModelClimate.Root;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {
    private MyRetrofit retrofit;
    private AccountDao currentAccountDao;


    public AccountRepo(Application application){
        retrofit = new MyRetrofit();
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        currentAccountDao = db.currentAccountDao();

    }

    public void accountInsert(Account account) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            emptyRepo();
            currentAccountDao.insertAccount(account);
        });
    }

    //delete all accounts
    public void emptyRepo(){
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            currentAccountDao.deleteAll();
        });

    }

    // return a list of all accounts to the viewmodel
    public LiveData<Account> getCurrentAccount(){
        return currentAccountDao.getCurrentAccount();
    }

}
