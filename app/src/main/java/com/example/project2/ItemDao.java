package com.example.project2;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ItemDao {
    @Insert
    void insertAll(Item... items);

    @Delete
    void delete(Item item);

    @Query("DELETE FROM item")
    void deleteAll();

    @Query("SELECT * FROM item")
    List<Item> getAll();

    @Query("SELECT * FROM item WHERE name = :itemName")
    Item getItemByName(String itemName);

    @Query("SELECT * FROM item WHERE id = :itemId")
    Item getItemById(int itemId);

    @Query("SELECT * FROM item WHERE name = :price")
    Item getItemByPrice(int price);

    @Query("INSERT INTO item (name, description, price)\n" +
            "VALUES ('Pocket Monster Ball', 'It is a ball for Pocket Monsters', 2) , ('Great Pocket Monster Ball', 'It is a ball for Pocket Monsters that has a higher catch rate than the regular one', 4)")
    void setPredefinedItems();
}
