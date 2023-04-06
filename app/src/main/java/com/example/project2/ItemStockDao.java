package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemStockDao {
    @Insert
    void insertAll(ItemStock... itemStocks);

    @Delete
    void delete(ItemStock itemStock);

    @Query("DELETE FROM item_stock")
    void deleteAll();
}
