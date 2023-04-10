package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.project2.databinding.AdminDeleteUserBinding;

import java.util.List;
import java.util.stream.Collectors;

public class AdminDeleteUser extends AppCompatActivity {

    Button delete_button;
    Button delete_button_back;
    Spinner user_delete_list;
    TextView money_display;
    AdminDeleteUserBinding mAdminDeleteUserBinding;
    User currentUserAdminDelete;
    UserMoneyDao mUserMoneyDao;
    ItemStockDao mItemStockDao;
    ItemDao mItemDao;
    UserDao mUserDao;
    String userTerminated = "User has been successfully terminated.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminDeleteUserBinding = AdminDeleteUserBinding.inflate(getLayoutInflater());
        View view = mAdminDeleteUserBinding.getRoot();
        setContentView(view);
        delete_button = mAdminDeleteUserBinding.deleteUserButton;
        user_delete_list = mAdminDeleteUserBinding.deleteUserList;
        delete_button_back = mAdminDeleteUserBinding.backButtonDeleteUser;
        currentUserAdminDelete = (User) getIntent().getSerializableExtra("currentUser");

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase .itemDao();
        mItemStockDao = appDatabase .itemStockDao();
        mUserMoneyDao =  appDatabase .userMoneyDao();
        mUserDao =  appDatabase .userDao();

        List<Item> items = mItemDao.getAll();
        List<User> users = mUserDao.getAll();

        //setting list<item> to list<string> so we can set it as items inside the dropdown menu
//        List<String> itemsString = items.stream().map(elt -> elt.getName() + ": " + elt.getPrice()).collect(Collectors.toList());
        List<String> usersString = users.stream().map(elt -> elt.getUsername()).collect(Collectors.toList());
        // following this guide I found on internet. I believe items are displayed using array adapter.
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usersString);
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString);
        user_delete_list.setAdapter(adapter);


        delete_button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdminDelete);
                startActivity(intent);
            }
        });
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User selectedUser = mUserDao.getUserByUsername(user_delete_list.getSelectedItem().toString());
                UserMoney selectedUserMoney = mUserMoneyDao.getUserMoneyByUserId(selectedUser.getId());
//                ItemStock selectedUserItems = mItemStockDao.getItemStockByUserId(selectedUser.getId());
                List<ItemStock> selectedUserItem = mItemStockDao.getItemStockByUserId(selectedUser.getId());
                mUserDao.delete(selectedUser);
                mUserMoneyDao.delete(selectedUserMoney);
//                mItemStockDao.delete(selectedUserItem);
                Toast toast = Toast.makeText(getApplicationContext(), userTerminated, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdminDelete);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminDeleteUser.class);
        return intent;
    }
}
