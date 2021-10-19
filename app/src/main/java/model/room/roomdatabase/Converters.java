package model.room.roomdatabase;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import model.room.entity.Account;

public class Converters {
    //EXMAPLES !!!!!!
    @TypeConverter
    public static List<Account> fromString(String value) {
        Type listType = new TypeToken<List<Account>>() {}.getType();
        return new Gson().fromJson(value, listType);
    }

    @TypeConverter
    public static String fromList(List<Account> list) {
        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }
}
