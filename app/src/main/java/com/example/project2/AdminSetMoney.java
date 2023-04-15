package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.AdminSetMoneyBinding;

import java.util.List;
import java.util.stream.Collectors;

public class AdminSetMoney extends AppCompatActivity {

    Button set_money;
    Button admin_set_money_back;
    Spinner user_select_add_money;
    EditText set_money_amount;
    AdminSetMoneyBinding mAdminSetMoneyBinding;
    User currentUserAdminSetMoney;
    UserMoneyDao mUserMoneyDao;
    ItemStockDao mItemStockDao;
    ItemDao mItemDao;
    UserDao mUserDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminSetMoneyBinding = AdminSetMoneyBinding.inflate(getLayoutInflater());
        View view = mAdminSetMoneyBinding.getRoot();
        setContentView(view);
        set_money = mAdminSetMoneyBinding.setMoney;
        user_select_add_money = mAdminSetMoneyBinding.addMoneyUserList;
        admin_set_money_back = mAdminSetMoneyBinding.backButtonAdminSetMoney;
        set_money_amount = mAdminSetMoneyBinding.setMoneyAmount;
        currentUserAdminSetMoney = (User) getIntent().getSerializableExtra("currentUser");

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase .itemDao();
        mItemStockDao = appDatabase .itemStockDao();
        mUserMoneyDao =  appDatabase .userMoneyDao();
        mUserDao =  appDatabase .userDao();

        List<User> users = mUserDao.getAll();

        //setting list<item> to list<string> so we can set it as items inside the dropdown menu
        List<String> usersString = users.stream().map(elt -> elt.getUsername()).distinct().collect(Collectors.toList());
        // following this guide I found on internet. I believe items are displayed using array adapter.
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, usersString);
        user_select_add_money.setAdapter(adapter);


        admin_set_money_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdminSetMoney);
                startActivity(intent);
            }
        });
        set_money.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User selectedUser = mUserDao.getUserByUsername(user_select_add_money.getSelectedItem().toString());
                UserMoney selectedUserMoney = mUserMoneyDao.getUserMoneyByUserId(selectedUser.getId());
                String moneyAmountString = set_money_amount.getText().toString();
                int moneyAmount = Integer.parseInt(moneyAmountString);
                selectedUserMoney.setMoneyAmount(moneyAmount);
                mUserMoneyDao.updateUserMonies(selectedUserMoney);
                Toast toast = Toast.makeText(getApplicationContext(), selectedUser.getUsername() + "'s money successfully set!", Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdminSetMoney);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, AdminSetMoney.class);
        return intent;
    }
}
