package model.room.roomdatabase;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import model.room.dao.AccountDao;
import model.room.entity.Account;


@Database(entities = {Account.class,  },//Day.class, Schedule.class, Plant.class, Climate.class},
        version = 1, exportSchema = false)
@TypeConverters(Converters.class)
public abstract class MyRoomDatabase extends RoomDatabase {
    //public abstract DayDao accountsDao();
    //public abstract ScheduleDao saunaDao();
    //public abstract ClimateDao dataPointDao();
    //public abstract PlantDao reservationDao();
    public abstract AccountDao currentAccountDao();

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
