package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;


import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;
import com.example.project2.databinding.RegisterPageBinding;


public class RegisterPage extends AppCompatActivity {
    EditText element_register_username;
    EditText element_register_password;
    Button register_button;
    Button back_button_register;
    RegisterPageBinding mRegisterPageBinding;
    UserDao mUserDao;
    UserMoneyDao mUserMoneyDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_page);

        mRegisterPageBinding = RegisterPageBinding.inflate(getLayoutInflater());
        View view = mRegisterPageBinding.getRoot();
        setContentView(view);

        element_register_username = mRegisterPageBinding.usernameRegister;
        element_register_password = mRegisterPageBinding.passwordRegister;
        register_button = mRegisterPageBinding.registerButton;
        back_button_register = mRegisterPageBinding.backButtonRegisterpage;

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mUserDao = appDatabase .userDao();
        mUserMoneyDao =  appDatabase .userMoneyDao();
        back_button_register.setOnClickListener(new View.OnClickListener() { //goes back to main activity
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //process of adding user
                String username = element_register_username.getText().toString();
                String password = element_register_password.getText().toString();
                if (mUserDao.getUserByUsername(username) != null) {
                    // Show a toast message indicating that the username already exists
                    Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new User and UserMoney entities and insert them into the database
                    UserMoney newUserMoney = new UserMoney();
                    User newUser = new User();
                    newUser.setUsername(username);
                    newUser.setPassword(password);
                    newUser.setAdmin(false);
                    mUserDao.insertAll(newUser);
                    newUserMoney.setUserId(mUserDao.getUserByUsername(username).getId());
                    newUserMoney.setMoneyAmount(0);
                    mUserMoneyDao.insertAll(newUserMoney);
                    Intent intent = LoginActivity.getIntent(getApplicationContext());
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), "Username successfully created!", Toast.LENGTH_SHORT).show();
                }
//                try {
//                    UserMoney newUserMoney = new UserMoney();
//                    User newUser = new User();
//                    newUser.setUsername(username);
//                    newUser.setPassword(password);
//                    newUser.setAdmin(false);
//                    mUserDao.insertAll(newUser);
//                    newUserMoney.setUserId(mUserDao.getUserByUsername(username).getId());
//                    newUserMoney.setMoneyAmount(0);
//                    mUserMoneyDao.insertAll(newUserMoney);
//                    Intent intent = LoginActivity.getIntent(getApplicationContext());
//                    startActivity(intent);
//                } catch (SQLiteConstraintException e) {
//                    // Show a toast message indicating that the username already exists
//                    Toast.makeText(getApplicationContext(), "Username already exists!", Toast.LENGTH_SHORT).show();
//                }
            }
        });

    }


    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, RegisterPage.class);
        return intent;
    }
}