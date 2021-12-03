package model.room.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import model.room.entity.Electricity;


@Dao
public interface ElectricityDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertElectricityLocation(Electricity electricity);

    @Query("DELETE FROM Electricity")
    void deleteAll();

    @Query("SELECT * FROM Electricity ORDER BY ID DESC Limit 1")
    LiveData<Electricity> getElectricity();
}
