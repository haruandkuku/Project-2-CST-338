package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;


import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;

import java.util.List;
import java.util.stream.Collectors;

public class MainActivity extends AppCompatActivity {

    UserDao mUserDao;
    UserMoneyDao mUserMoneyDao;
    ItemDao mItemDao;
    ItemStockDao mItemStockDao;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE User"
                    + " ADD COLUMN isAdmin BOOLEAN");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login_button = (Button) findViewById(R.id.add_item_button);
        Button register_button = (Button) findViewById(R.id.delete_item_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = RegisterPage.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the login_button");
                Intent intent = LoginActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });


        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();

        mUserDao = appDatabase.userDao();
        mItemDao = appDatabase.itemDao();
        mUserMoneyDao = appDatabase.userMoneyDao();
        mItemStockDao = appDatabase.itemStockDao();

//        database nuke
//        mItemDao.deleteAll();
//        mUserMoneyDao.deleteAll();
//        mUserDao.deleteAll();
//        mItemStockDao.deleteAll();

        if (mUserDao.getUserByUsername("admin2") == null) {
            mUserDao.setPredefinedUsers();
        }
        if (mUserMoneyDao.getUserMoneyByUserId(1) == null) {
            mUserMoneyDao.setPredefinedMoney();
        }
        if (mItemDao.getItemByName("Pocket Monster Ball") == null) {
            mItemDao.setPredefinedItems();
        }
    }


    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, MainActivity.class);
        return intent;
    }
}

