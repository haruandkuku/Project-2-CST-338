package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import com.example.project2.databinding.OrderHistoryBinding;

import java.util.List;

public class OrderHistory extends AppCompatActivity {

    Button order_history_back;
    OrderHistoryBinding mOrderHistoryBinding;
    LinearLayout order_history_list;
    User currentUserOrderHistory;
    ItemStockDao mItemStockDao;
    ItemDao mItemDao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOrderHistoryBinding = OrderHistoryBinding.inflate(getLayoutInflater());
        View view = mOrderHistoryBinding.getRoot();
        setContentView(view);
        order_history_back = mOrderHistoryBinding.backButtonOrderHistory;
        order_history_list = mOrderHistoryBinding.orderList;
        currentUserOrderHistory = (User) getIntent().getSerializableExtra("currentUser");

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();

        mItemStockDao = appDatabase .itemStockDao();
        mItemDao = appDatabase .itemDao();
        List<ItemStock> listItemStock = mItemStockDao.getItemStockByUserId(currentUserOrderHistory.getId());
        listItemStock.forEach( (itemStock) -> {
            Item item = mItemDao.getItemById(itemStock.itemId); // this is to show name
            TextView textView = new TextView(this);
            textView.setTextSize(15);
            textView.setText(item.name + ":  " + "x" + itemStock.quantity);
            order_history_list.addView(textView);
        });



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
