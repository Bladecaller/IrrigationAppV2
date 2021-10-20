package model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import model.room.entity.Account;
import model.room.entity.Climate;

@Dao
public interface ClimateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertClimate(Climate climate);

    @Query("DELETE FROM Climate")
    void deleteAll();

    @Query("SELECT * FROM Climate")
    LiveData<List<Climate>> getClimateList();
}
