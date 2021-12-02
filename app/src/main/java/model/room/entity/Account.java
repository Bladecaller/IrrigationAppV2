package model.room.entity;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class Account implements Serializable {

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "UserID")
    private int UserID;

    @ColumnInfo(name = "Username")
    private String Username;

    public Account(@NonNull int UserID, String Username){
        this.UserID = UserID;
        this.Username = Username;
    }

    public int getUserID() {
        return UserID;
    }

    public void setUserID(int UserID) {
        this.UserID = UserID;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String Username) {
        this.Username = Username;
    }

}