package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.LandingPageBinding;


public class LandingPage extends AppCompatActivity {
    LandingPageBinding mLandingPageBinding;


    Button open_shop;
    Button order_history;
    Button cancel_order;
    Button admin_button;

    Button log_out;

    User extras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLandingPageBinding = LandingPageBinding.inflate(getLayoutInflater());
        View view = mLandingPageBinding.getRoot();
        setContentView(view);
        open_shop = mLandingPageBinding.openShop;
        order_history = mLandingPageBinding.orderHistory;
        cancel_order = mLandingPageBinding.cancelOrder;
        admin_button = mLandingPageBinding.adminButton;
        log_out = mLandingPageBinding.logOut;

        extras = (User) getIntent().getSerializableExtra("currentUser");

        if(extras.isAdmin){
            admin_button.setVisibility(View.VISIBLE);
        } else {
            admin_button.setVisibility(View.GONE);
        }

        log_out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = MainActivity.getIntent(getApplicationContext());
                startActivity(intent);
            }
        });

        open_shop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OpenShop.getIntent(getApplicationContext());
                intent.putExtra("currentUser", extras);
                startActivity(intent);
            }
        });

        order_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = OrderHistory.getIntent(getApplicationContext());
                intent.putExtra("currentUser", extras);
                startActivity(intent);
            }
        });

        cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = CancelOrder.getIntent(getApplicationContext());
                intent.putExtra("currentUser", extras);
                startActivity(intent);
            }
        });

        admin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", extras);
                startActivity(intent);
                Log.d("test admin button", "yes");
            }
        });





    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, LandingPage.class);
        return intent;
    }

}



