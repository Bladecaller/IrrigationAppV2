package model.room.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;

import java.util.List;

import api.MyRetrofit;
import model.room.dao.AccountDao;
import model.room.entity.Account;
import model.room.roomdatabase.MyRoomDatabase;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AccountRepo {
    private MyRetrofit retrofit;
    private AccountDao currentAccountDao;


    public AccountRepo( ){
        retrofit = new MyRetrofit();
        //MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        //currentAccountDao = db.currentAccountDao();

    }

    public void getTemp(String url){

        Call<String> call = retrofit.api.getTemp(url);
        Log.d("BODY","FAKE");

        System.out.println("SUCCESS IS IN THIS BODY FAKE");
        call.enqueue(new Callback<String>(){
            @Override
            public void onResponse (Call <String> call, Response<String> response){
                Log.d("BODY","FAKE2");

                System.out.println("SUCCESS IS IN THIS BODY FAKE2");
                System.out.print(response.message());
                Log.d("ANSWER",response.message());
                Log.d("BODY",response.body());

                System.out.println("SUCCESS IS IN THIS BODY " + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                t.printStackTrace();

                Log.d("BODY","FAKE3");

                System.out.println("SUCCESS IS IN THIS BODY FAKE3");
                System.out.println("Failed at Login");
                System.out.println(t.getMessage());
            }

        });
    }

    public void accountInsert(Account account) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
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
    public LiveData<List<Account>> getCurrentAccountList(){
        return currentAccountDao.getCurrentAccountList();
    }

}
