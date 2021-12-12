package model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import model.room.entity.Humidity;

@Dao
public interface HumidityDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertHumidity(Humidity humidity);

    @Query("DELETE FROM Humidity")
    void deleteAll();

    @Query("SELECT * FROM Humidity")
    LiveData<List<Humidity>> getHumidityList();
}