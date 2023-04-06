package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.EarnMoneyBinding;


public class EarnMoney extends AppCompatActivity {
    Button earn_money_back;
    TextView user_money_display;
    Button earn_button;
    EarnMoneyBinding mEarnMoneyBinding;
    User currentUserEarnMoney;
    UserMoneyDao mUserMoneyDao;
    UserMoney currentUserMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mEarnMoneyBinding = EarnMoneyBinding.inflate(getLayoutInflater());
        View view = mEarnMoneyBinding.getRoot();
        setContentView(view);

        earn_money_back = mEarnMoneyBinding.backButtonEarnMoney;
        user_money_display = mEarnMoneyBinding.userMoneyCount;
        earn_button = mEarnMoneyBinding.earnButton;
        currentUserEarnMoney = (User) getIntent().getSerializableExtra("currentUser");
        mUserMoneyDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build()
                .userMoneyDao();
        currentUserMoney = mUserMoneyDao.getUserMoneyByUserId(currentUserEarnMoney.id);
//        mUserMoneyDao.setPredefinedMoney();
        user_money_display.setText(Integer.toString(currentUserMoney.getMoneyAmount()));
        earn_money_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserEarnMoney);
                startActivity(intent);
            }
        });

        earn_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentUserMoney.setMoneyAmount(currentUserMoney.getMoneyAmount() + 1);
                user_money_display.setText(Integer.toString(currentUserMoney.getMoneyAmount()));
                mUserMoneyDao.updateUserMonies(currentUserMoney);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, EarnMoney.class);
        return intent;
    }
}
