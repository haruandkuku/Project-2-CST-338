package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.OrderHistoryBinding;

public class OrderHistory extends AppCompatActivity {

    Button order_history_back;
    OrderHistoryBinding mOrderHistoryBinding;
    User currentUserOrderHistory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderHistoryBinding = OrderHistoryBinding.inflate(getLayoutInflater());
        View view = mOrderHistoryBinding.getRoot();
        setContentView(view);
        order_history_back = mOrderHistoryBinding.backButtonOrderHistory;
        currentUserOrderHistory = (User) getIntent().getSerializableExtra("currentUser");

        order_history_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserOrderHistory);
                startActivity(intent);
            }
        });
    }
    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, OrderHistory.class);
        return intent;
    }
}
