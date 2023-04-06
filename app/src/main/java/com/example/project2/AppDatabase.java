package com.example.project2;

import android.content.Context;

import androidx.room.AutoMigration;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.List;

@Database(
        version = 5,
        entities = {User.class, UserMoney.class, Item.class, ItemStock.class},
        autoMigrations = {
        @AutoMigration(from = 3, to = 4)
}
)
public abstract class AppDatabase extends RoomDatabase {
    public static final String DATABASE_NAME = "Semester1.db";
    public static final String USER_TABLE = "user";
    public static final String MONEY_TABLE = "user_money";
    public static final String ITEM_TABLE = "item";
    public static final String ITEM_STOCK_TABLE = "item_stock";
    public static volatile AppDatabase instance;
    public abstract UserDao userDao();
    public abstract UserMoneyDao userMoneyDao();
    public abstract ItemDao itemDao();
    public abstract ItemStockDao itemStockDao();
    public static final Object LOCK = new Object();
    public static AppDatabase getInstance(Context context){
        if(instance == null){
            synchronized (LOCK){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(),
                            AppDatabase.class,
                            DATABASE_NAME).build();
                }
            }
        }
        return instance;
    }
    static final Migration MIGRATION_2_3 = new Migration(2, 3) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("CREATE TABLE `user_money` (`id` INTEGER NOT NULL, "
                    + "`user_id` INTEGER NOT NULL, `money` INTEGER NOT NULL, PRIMARY KEY(`id`))");
        }
    };
    static final Migration MIGRATION_4_5 = new Migration(4, 5) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL(
        "CREATE TABLE `item` ( `id` INTEGER NOT NULL, " +
         "`name` TEXT NOT NULL," +
         "`description` TEXT NOT NULL," +
         "`price` INTEGER NOT NULL," +
         "PRIMARY KEY(`id`) );" );
            database.execSQL(
       "CREATE TABLE `item_stock` ( `id` INTEGER NOT NULL,"+
         "`user_id` INTEGER NOT NULL,"+
         "`item_id` INTEGER NOT NULL,"+
         "`quantity` INTEGER NOT NULL,"+
         " PRIMARY KEY(`id`) );"
        );
    }
    };
}
