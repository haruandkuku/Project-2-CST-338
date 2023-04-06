package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserMoneyDao {
    @Insert
    void insertAll(UserMoney... userMonies);

    @Delete
    void delete(UserMoney userMoney);

    @Query("DELETE FROM user_money")
    void deleteAll();

    @Query("SELECT * FROM user_money WHERE user_id = :userId")
    UserMoney getUserMoneyByUserId(int userId);

    @Query("INSERT INTO user_money (user_id, money)\n" +
            "VALUES (1, 0), (2, 0)")
    void setPredefinedMoney();

    @Update
    void updateUserMonies(UserMoney... userMonies);
}
