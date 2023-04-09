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


        mItemDao = appDatabase .itemDao();
        mItemStockDao = appDatabase .itemStockDao();
        mUserMoneyDao =  appDatabase .userMoneyDao();

//        List<ItemStock> items = mItemStockDao.getItemStockByUserIdAndItemId(currentUserCancelOrder.getId(), 1));
//        List<String> itemsString = items.stream().map(elt -> elt.getName() + ": " + elt.getPrice()).collect(Collectors.toList());
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString);
//        cancel_list.setAdapter(adapter);


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
