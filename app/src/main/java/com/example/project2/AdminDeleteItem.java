package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import static java.lang.Integer.parseInt;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.AdminDeleteItemBinding;

import java.util.List;
import java.util.stream.Collectors;

public class AdminDeleteItem extends AppCompatActivity {

    Button delete_button;
    Button back_button_admin_delete;
    Spinner item_delete_list;
    AdminDeleteItemBinding mAdminDeleteItemBinding;
    User currentUserAdmin;
    UserDao mUserDao;
    ItemDao mItemDao;
    ItemStockDao mItemStockDao;
    String itemDeleted = "Deleted item from database!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAdminDeleteItemBinding = AdminDeleteItemBinding.inflate(getLayoutInflater());
        View view = mAdminDeleteItemBinding.getRoot();
        setContentView(view);
        delete_button = mAdminDeleteItemBinding.deleteItemButton;
        item_delete_list = mAdminDeleteItemBinding.deleteItemAdmin;
        back_button_admin_delete = mAdminDeleteItemBinding.backButtonAdminDelete;
        currentUserAdmin = (User) getIntent().getSerializableExtra("currentUser");


        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase .itemDao();
        mItemStockDao =  appDatabase .itemStockDao();
        mUserDao = appDatabase .userDao();

        List<Item> itemList = mItemDao.getAll();
        List<String> itemsString = itemList.stream().map(elt -> elt.getName()).collect(Collectors.toList());
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString);
        item_delete_list.setAdapter(adapter);

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Item selectedItem = mItemDao.getItemByName(item_delete_list.getSelectedItem().toString());
                ItemStock selectedItemStock = mItemStockDao.getItemStockByItemID(selectedItem.id);
                mItemDao.delete(selectedItem);
                mItemStockDao.delete(selectedItemStock);
                Toast toast = Toast.makeText(getApplicationContext(), itemDeleted, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
        back_button_admin_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = Admin.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserAdmin);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context){
        Intent intent = new Intent(context, AdminDeleteItem.class);
        return intent;
    }
}
