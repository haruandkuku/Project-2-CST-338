package com.example.project2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project2.databinding.OpenShopBinding;

public class OpenShop extends AppCompatActivity {

    Button purchase_button;
    EditText item_list_shop;
    Button open_shop_back;
    OpenShopBinding mOpenShopBinding;
    User currentUserOpenShop;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpenShopBinding = OpenShopBinding.inflate(getLayoutInflater());
        View view = mOpenShopBinding.getRoot();
        setContentView(view);
        purchase_button = mOpenShopBinding.purchaseButton;
        item_list_shop = mOpenShopBinding.itemList;
        open_shop_back = mOpenShopBinding.backButtonOpenshop;
        currentUserOpenShop = (User) getIntent().getSerializableExtra("currentUser");

        open_shop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserOpenShop);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, OpenShop.class);
        return intent;
    }
}
