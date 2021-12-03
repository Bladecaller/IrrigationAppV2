package model.room.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.room.dao.AccountDao;
import model.room.dao.ClimateDao;
import model.room.dao.ElectricityDao;
import model.room.dao.HumidityDao;
import model.room.dao.PrecipitationDao;
import model.room.dao.TemperatureDao;
import model.room.entity.Account;
import model.room.entity.Climate;
import model.room.entity.Electricity;
import model.room.entity.Humidity;
import model.room.entity.Precipitation;
import model.room.entity.Temperature;


@Database(entities = {Electricity.class, Account.class, Temperature.class, Humidity.class, Precipitation.class},//Day.class, Schedule.class, Plant.class, },
        version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyRoomDatabase extends RoomDatabase {

    public abstract TemperatureDao temperatureDao();
    public abstract HumidityDao humidityDao();
    public abstract PrecipitationDao precipitationDao();
    public abstract AccountDao currentAccountDao();
    public abstract ElectricityDao electricityDao();

    private static volatile MyRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 12;
    public static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static MyRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (MyRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            MyRoomDatabase.class, "my_room_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }


}
