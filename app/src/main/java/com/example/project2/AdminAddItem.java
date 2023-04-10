package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.AdminAddItemBinding;

public class AdminAddItem extends AppCompatActivity {

    Button add_item_button;
    Button back_button_admin_add;
    AdminAddItemBinding mAdminAddItemBinding;
    EditText item_name;
    EditText item_description;
    EditText item_price;
    User currentUserAdmin;
    ItemDao mItemDao;
    ItemStockDao mItemStockDao;
    String itemAdded = "Item added!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminAddItemBinding = AdminAddItemBinding.inflate(getLayoutInflater());
        View view = mAdminAddItemBinding.getRoot();
        setContentView(view);

        add_item_button = mAdminAddItemBinding.addItemButton;
        back_button_admin_add = mAdminAddItemBinding.backButtonAdminAdd;
        item_name = mAdminAddItemBinding.itemName;
        item_description = mAdminAddItemBinding.itemDescription;
        item_price = mAdminAddItemBinding.itemPrice;
        currentUserAdmin = (User) getIntent().getSerializableExtra("currentUser");

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase .itemDao();
        mItemStockDao =  appDatabase .itemStockDao();

        add_item_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String itemName = item_name.getText().toString();
                String itemDescription = item_description.getText().toString();
                int itemPrice = Integer.parseInt(item_price.getText().toString());
                Item newItem = new Item();
                newItem.setName(itemName);
                newItem.setDescription(itemDescription);
                newItem.setPrice(itemPrice);
                mItemDao.insertAll(newItem);
                Toast toast = Toast.makeText(getApplicationContext(), itemAdded, Toast.LENGTH_SHORT);
                toast.show();
            }
        });

        back_button_admin_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AdminAddItem.class);
        return intent;
    }
}
