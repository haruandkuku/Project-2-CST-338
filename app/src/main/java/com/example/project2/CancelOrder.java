package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.CancelOrderBinding;


public class CancelOrder extends AppCompatActivity {
    Button cancel_order_back;
    EditText cancel_list;
    Button cancel_button;
    CancelOrderBinding mCancelOrderBinding;
    User currentUserCancelOrder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCancelOrderBinding = CancelOrderBinding.inflate(getLayoutInflater());
        View view = mCancelOrderBinding.getRoot();
        setContentView(view);

        cancel_order_back = mCancelOrderBinding.backButtonCancelOrder;
        cancel_list = mCancelOrderBinding.cancelList;
        cancel_button = mCancelOrderBinding.cancelButton;
        currentUserCancelOrder = (User) getIntent().getSerializableExtra("currentUser");

        cancel_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserCancelOrder);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, CancelOrder.class);
        return intent;
    }
}
