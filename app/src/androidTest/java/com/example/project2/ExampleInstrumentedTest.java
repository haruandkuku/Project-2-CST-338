package com.example.project2;

import static com.example.project2.AppDatabase.MIGRATION_2_3;
import static com.example.project2.AppDatabase.MIGRATION_4_5;

import android.content.Context;

import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

import java.io.IOException;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private UserDao mUserDao;
    private ItemDao mItemDao;
    private UserMoneyDao mUserMoneyDao;
    private AppDatabase mDb;

    @Before
    public void createDb() {
        Context context = ApplicationProvider.getApplicationContext();
        mDb = Room.inMemoryDatabaseBuilder(context, AppDatabase.class).build();
        mUserDao = mDb.userDao();
        mUserMoneyDao = mDb.userMoneyDao();
        mItemDao = mDb.itemDao();
        if (mUserDao.getUserByUsername("admin2") == null) {
            mDb.runInTransaction(() -> mUserDao.setPredefinedUsers());
        }
        if (mUserMoneyDao.getUserMoneyByUserId(1) == null) {
            mDb.runInTransaction(() -> mUserMoneyDao.setPredefinedMoney());
        }
        if (mItemDao.getItemByName("Pocket Monster Ball") == null) {
            mDb.runInTransaction(() -> mItemDao.setPredefinedItems());
        }
    }
    @After
    public void closeDb() throws IOException {
        mDb.close();
    }

    @Test
    public void retrievePredefinedUsers() throws Exception {
        User admin = mUserDao.getUserByUsername("admin2");
        assertNotNull(admin);
        assertEquals(admin.getUsername(), "admin2");
        assertEquals(admin.getPassword(), "admin2");
        assertTrue(admin.isAdmin());

        User user = mUserDao.getUserByUsername("testuser1");
        assertNotNull(user);
        assertEquals(user.getUsername(), "testuser1");
        assertEquals(user.getPassword(), "testuser1");
        assertFalse(user.isAdmin());
    }

    @Test
    public void retrievePredefinedMoney() throws Exception {
        UserMoney adminMoney = mUserMoneyDao.getUserMoneyByUserId(2);
        assertNotNull(adminMoney);
        assertEquals(adminMoney.getUserId(), 2);
        assertEquals(adminMoney.getMoneyAmount(), 0);

        UserMoney userMoney = mUserMoneyDao.getUserMoneyByUserId(1);
        assertNotNull(userMoney);
        assertEquals(userMoney.getUserId(), 1);
        assertEquals(userMoney.getMoneyAmount(), 0);
    }

    @Test
    public void retrievePredefinedItems() throws Exception {
        Item regularItem = mItemDao.getItemByName("Pocket Monster Ball");
        assertNotNull(regularItem);
        assertEquals(regularItem.getName(), "Pocket Monster Ball");
        assertEquals(regularItem.getDescription(), "It is a ball for Pocket Monsters");
        assertEquals(regularItem.getPrice(), 2);

        Item premiumItem = mItemDao.getItemByName("Great Pocket Monster Ball");
        assertNotNull(premiumItem);
        assertEquals(premiumItem.getName(), "Great Pocket Monster Ball");
        assertEquals(premiumItem.getDescription(), "It is a ball for Pocket Monsters that has a higher catch rate than the regular one");
        assertEquals(premiumItem.getPrice(), 4);
    }
}