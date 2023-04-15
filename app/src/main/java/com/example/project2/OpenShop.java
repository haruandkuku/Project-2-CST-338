package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.room.Room;

import com.example.project2.databinding.OpenShopBinding;

import java.util.List;
import java.util.stream.Collectors;

public class OpenShop extends AppCompatActivity {

    Button purchase_button;
    Button open_shop_back;
    Spinner itemDropDown;
    TextView money_display;
    OpenShopBinding mOpenShopBinding;
    User currentUserOpenShop;
    UserMoneyDao mUserMoneyDao;
    ItemStockDao mItemStockDao;
    ItemDao mItemDao;
    String errorMessage = "You don't have enough money.";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mOpenShopBinding = OpenShopBinding.inflate(getLayoutInflater());
        View view = mOpenShopBinding.getRoot();
        setContentView(view);
        purchase_button = mOpenShopBinding.purchaseButton;
        itemDropDown = mOpenShopBinding.items;
        open_shop_back = mOpenShopBinding.backButtonOpenshop;
        money_display = mOpenShopBinding.moneyDisplay;
        currentUserOpenShop = (User) getIntent().getSerializableExtra("currentUser");

        AppDatabase appDatabase = Room.databaseBuilder(this, AppDatabase.class, AppDatabase.DATABASE_NAME)
                .addMigrations(MIGRATION_2_3, MIGRATION_4_5)
                .allowMainThreadQueries()
                .build();


        mItemDao = appDatabase.itemDao();
        mItemStockDao = appDatabase.itemStockDao();
        mUserMoneyDao = appDatabase.userMoneyDao();

        UserMoney currentUserMoney = mUserMoneyDao.getUserMoneyByUserId(currentUserOpenShop.id);
        money_display.setText("$" + Integer.toString(currentUserMoney.getMoneyAmount())); // display money
        List<Item> items = mItemDao.getAll();

        //setting list<item> to list<string> so we can set it as items inside the dropdown menu
        List<String> itemsString = items.stream().map(elt -> elt.getName() + ": $" + elt.getPrice()).collect(Collectors.toList());
        // following this guide I found on internet. I believe items are displayed using array adapter.
        // https://stackoverflow.com/questions/13377361/how-to-create-a-drop-down-list
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, itemsString);
        itemDropDown.setAdapter(adapter);


        open_shop_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = LandingPage.getIntent(getApplicationContext());
                intent.putExtra("currentUser", currentUserOpenShop);
                startActivity(intent);
            }
        });
        LifecycleOwner x = this;
        purchase_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Item selectedItem = mItemDao.getItemByName(itemDropDown.getSelectedItem().toString().split(":", 2)[0]);

                if(currentUserMoney.getMoneyAmount() < selectedItem.getPrice()){
                    Toast toast = Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT);
                    toast.show();
                } else{
                LiveData<ItemStock> liveDataStockedItem = mItemStockDao.getItemStockByUserIdAndItemId(currentUserOpenShop.id, selectedItem.id);
                liveDataStockedItem.observe(x, new Observer<ItemStock>() {
                    @Override
                    public void onChanged(ItemStock itemStock) {
                        if (itemStock == null) {
                            ItemStock newItemStock = new ItemStock();
                            newItemStock.setUserId(currentUserOpenShop.id);
                            newItemStock.setItemId(selectedItem.id);
                            newItemStock.setQuantity(1);
                            mItemStockDao.insertAll(newItemStock);
                            Log.d("testst", "new item stock: " + newItemStock.getQuantity());

                        } else {
                            itemStock.setQuantity(itemStock.quantity + 1);
                            mItemStockDao.updateItemStock(itemStock);
                            Log.d("testnousern", "itemstock " + itemStock.getQuantity());
                        }
                        liveDataStockedItem.removeObserver(this);
                    }
                });
                currentUserMoney.setMoneyAmount(currentUserMoney.getMoneyAmount() - selectedItem.getPrice());
                mUserMoneyDao.updateUserMonies(currentUserMoney);
                money_display.setText("$" + Integer.toString(currentUserMoney.getMoneyAmount()));
                Toast toast = Toast.makeText(getApplicationContext(), selectedItem.getName() + " successfully purchased!", Toast.LENGTH_SHORT);
                toast.show();
            }
            }
        });
    }

    public static Intent getIntent(Context context) {
        Intent intent = new Intent(context, OpenShop.class);
        return intent;
    }
}
