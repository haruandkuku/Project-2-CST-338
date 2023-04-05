package com.example.project2;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.Serializable;


@Entity(tableName = "user")
public class User implements Serializable {
    @PrimaryKey
    public int id;

    @ColumnInfo(name = "username")
    public String username;
    @ColumnInfo(name = "password")
    public String password;

    public boolean isAdmin;

    public boolean isAdmin() {
        return isAdmin;
    }

//    public User(String userName, String passWord, int ID){
//        username = username;
//        password = passWord;
//        id = ID;
//    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean login(String password){
        return this.password.equals(password);
    }

}
