package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.AdminAddItemBinding;
import com.example.project2.databinding.AdminPageBinding;

public class Admin extends AppCompatActivity {

    Button add_item_button;
    Button delete_user_button;
    Button back_button_admin;
    Button set_money_button;
    AdminPageBinding mAdminPageBinding;
    User currentUserAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminPageBinding = AdminPageBinding.inflate(getLayoutInflater());
        View view = mAdminPageBinding.getRoot();
        setContentView(view);

        add_item_button = mAdminPageBinding.addItemButton;
        delete_user_button = mAdminPageBinding.deleteUserButton;
        back_button_admin = mAdminPageBinding.backButtonAdmin;
        set_money_button = mAdminPageBinding.setUserMoneyButton;
        currentUserAdmin = (User) getIntent().getSerializableExtra("currentUser");

        delete_user_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminDeleteUser.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminAddItem.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
        set_money_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = AdminSetMoney.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
        back_button_admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, Admin.class);
        return intent;
    }
}
