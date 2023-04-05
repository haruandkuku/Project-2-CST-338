package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface UserDao {
    @Insert
    void insertAll(User... users);

    @Delete
    void delete(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

    @Query("DELETE FROM user")
    void deleteAll();

    @Query("SELECT * FROM user WHERE username = :username")
    User getUserByUsername(String username);

    @Query("SELECT * FROM user WHERE password LIKE :password")
    User getUserByPassword(String password);
}
