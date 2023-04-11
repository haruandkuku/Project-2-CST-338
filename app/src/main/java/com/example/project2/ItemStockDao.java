package com.example.project2;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ItemStockDao {
    @Insert
    void insertAll(ItemStock... itemStocks);

    @Delete
    void delete(ItemStock itemStock);

    @Query("DELETE FROM item_stock")
    void deleteAll();

    @Query("SELECT * FROM item_stock WHERE user_id = :userId")
    List<ItemStock> getItemStockByUserId(int userId);

    @Query("SELECT * FROM item_stock WHERE user_id = :userId")
    ItemStock getItemStockByUserID(int userId);

    @Query("SELECT * FROM item_stock WHERE item_id = :itemId")
    ItemStock getItemStockByItemID(int itemId);

    @Query("SELECT * FROM item_stock WHERE user_id = :userId AND item_id = :itemId")
    LiveData<ItemStock> getItemStockByUserIdAndItemId(int userId, int itemId);

    @Query("SELECT * FROM item_stock WHERE user_id = :userId AND item_id = :itemId")
    List<ItemStock> getItemStockByUserIDAndItemID(int userId, int itemId);

    @Update
    void updateItemStock(ItemStock... itemStocks);
}
