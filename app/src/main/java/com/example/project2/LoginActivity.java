package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.LoginPageBinding;

public class LoginActivity extends AppCompatActivity {

    private UserDao mUserDao;
    EditText element_username;
    EditText element_password;
    Button login_button_two;

    Button back_button_login;

    CharSequence errorText = "Invalid username and / or password.";

    //viewbinding connects the activity_main.xml to the java file
    //also need to enable viewbinding in build.gradle module app
    LoginPageBinding mLoginPageBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.login_page);

        mLoginPageBinding = LoginPageBinding.inflate(getLayoutInflater());
        View view = mLoginPageBinding.getRoot();
        setContentView(view);

        element_username = mLoginPageBinding.username;
        element_password = mLoginPageBinding.password;
        login_button_two = mLoginPageBinding.loginButton2;
        back_button_login = mLoginPageBinding.backButtonLoginpage;

        mUserDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3)
                .allowMainThreadQueries()
                .build()
                .userDao();

        back_button_login.setOnClickListener(new View.OnClickListener() { //goes back to main activity
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        login_button_two.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("BUTTONS", "User tapped the login_button_2");
                String username = element_username.getText().toString();
                String password = element_password.getText().toString();
                User currentUser;
                Log.d("testing", "username " + username);
                currentUser = mUserDao.getUserByUsername(username);
                try {
                    currentUser = mUserDao.getUserByUsername(username);
                    Log.d("testing", "login password " + password);
                    Log.d("testing", "login uzername " + currentUser.getUsername());
                    if(password.equals(currentUser.password)){
                        Intent intent = LandingPage.getIntent(getApplicationContext());
                        intent.putExtra("currentUser", currentUser);
                        startActivity(intent);
                        Log.d("testing", "sdf " + currentUser.getUsername());
                        return;
                    }
                } catch(Exception NullPointerException) {
                    Log.d("testnousern", "no username exist");

                }
                Toast toast = Toast.makeText(getApplicationContext(), errorText, Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

    //    intent factory is used to easily get information and switch from one activity to another
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        return intent;
    }
}
