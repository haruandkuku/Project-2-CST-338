package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.OpenShopBinding;

import java.util.List;
import java.util.stream.Collectors;

public class OpenShop extends AppCompatActivity {

    Button purchase_button;
    Button open_shop_back;
    Spinner itemDropDown;
    OpenShopBinding mOpenShopBinding;
    User currentUserOpenShop;
    ItemDao mItemDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpenShopBinding = OpenShopBinding.inflate(getLayoutInflater());
        View view = mOpenShopBinding.getRoot();
        setContentView(view);
        purchase_button = mOpenShopBinding.purchaseButton;
        itemDropDown = mOpenShopBinding.items;
        open_shop_back = mOpenShopBinding.backButtonOpenshop;

        mItemDao = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build()
                .itemDao();

        List<Item> items = mItemDao.getAll();

        //setting list<item> to list<string> so we can set it as items inside the dropdown menu
        List<String> itemsString = items.stream().map(elt ->elt.getName()) .collect(Collectors.toList());
        // following this guide I found on internet. I believe items are displayed using array adapter.
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString );
        itemDropDown.setAdapter(adapter);

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

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, OpenShop.class);
        return intent;
    }
}
