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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.CancelOrderBinding;

import java.util.List;
import java.util.stream.Collectors;


public class CancelOrder extends AppCompatActivity {
    Button cancel_order_back;
    Spinner cancel_list;
    Button cancel_button;
    CancelOrderBinding mCancelOrderBinding;
    User currentUserCancelOrder;
    UserMoneyDao mUserMoneyDao;
    ItemStockDao mItemStockDao;
    ItemDao mItemDao;
    String itemCancelled = "Item has been cancelled!";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCancelOrderBinding = CancelOrderBinding.inflate(getLayoutInflater());
        View view = mCancelOrderBinding.getRoot();
        setContentView(view);

        cancel_order_back = mCancelOrderBinding.backButtonCancelOrder;
        cancel_list = mCancelOrderBinding.cancelItem;
        cancel_button = mCancelOrderBinding.cancelButton;
        currentUserCancelOrder = (User) getIntent().getSerializableExtra("currentUser");
        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase.itemDao();
        mItemStockDao = appDatabase.itemStockDao();
        mUserMoneyDao = appDatabase.userMoneyDao();

        List<ItemStock> items = mItemStockDao.getItemStockByUserId(currentUserCancelOrder.getId());
        items.forEach((itemStock) -> {
            Item item = mItemDao.getItemById(itemStock.itemId); // this is to show name
            List<String> itemsString = items.stream().map(elt -> Integer.toString(elt.getQuantity()) + " " + item.getName()).collect(Collectors.toList());
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString);
            cancel_list.setAdapter(adapter);
            }
        );


        ItemStock selectedItem = mItemStockDao.getItemStockByUserID(currentUserCancelOrder.getId());
        cancel_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemStockDao.delete(selectedItem);
                Toast toast = Toast.makeText(getApplicationContext(), itemCancelled, Toast.LENGTH_SHORT);
                toast.show();
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserCancelOrder);
                startActivity(intent);
            }
        });

        cancel_order_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserCancelOrder);
                startActivity(intent);
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, CancelOrder.class);
        return intent;
    }
}
