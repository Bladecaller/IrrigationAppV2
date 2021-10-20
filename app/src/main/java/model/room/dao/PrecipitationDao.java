package model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import model.room.entity.Climate;
import model.room.entity.Precipitation;

@Dao
public interface PrecipitationDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPrecipitation(Precipitation precipitation);

    @Query("DELETE FROM Precipitation")
    void deleteAll();

    @Query("SELECT * FROM Precipitation")
    LiveData<List<Precipitation>> getPrecipitationList();
}