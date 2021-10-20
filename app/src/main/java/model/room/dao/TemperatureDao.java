package model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import model.room.entity.Climate;
import model.room.entity.Temperature;

@Dao
public interface TemperatureDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertTemperature(Temperature temperature);

    @Query("DELETE FROM Temperature")
    void deleteAll();

    @Query("SELECT * FROM Temperature")
    LiveData<List<Temperature>> getTemperatureList();
}