package ru.job4j.tracker;


import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SqlTrackerTest {

    private static Connection connection;

    @BeforeClass
    public static void initConnection() {
        try (InputStream in = SqlTrackerTest.class.getClassLoader().getResourceAsStream("test.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            connection = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")

            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @AfterClass
    public static void closeConnection() throws SQLException {
        connection.close();
    }

    @After
    public void wipeTable() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("delete from items")) {
            statement.execute();
        }
    }

    @Test
    public void whenSaveItemAndFindByGeneratedIdThenMustBeTheSame() {
        SqlTracker tracker = new SqlTracker(connection);
        Item item = new Item("item");
        tracker.add(item);
        assertThat(tracker.findById(item.getId()), is(item));
    }

    @Test
    public void testReplace() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = new Item("Item");
        Item newItem = new Item("newItem");
        sqlTracker.add(item);
        int id = item.getId();
        sqlTracker.replace(id, newItem);
        assertThat(sqlTracker.findById(newItem.getId()), is((newItem)));
    }

    @Test
    public void testDelete() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = new Item("item");
        sqlTracker.add(item);
        int id = item.getId();
        assertTrue(sqlTracker.delete(id));
        assertFalse(sqlTracker.delete(id));
    }

    @Test
    public void testFindAll() {
        List<Item> items = new ArrayList<>();
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item1 = new Item("item_1");
        Item item2 = new Item("item_2");
        Item item3 = new Item("item_3");
        Item item4 = new Item("item_4");
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        List<Item> mainItems = sqlTracker.findAll();
        assertEquals(items, mainItems);
    }

    @Test
    public void testFindByName() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = new Item("item_1");
        Item item2 = new Item("item_2");
        sqlTracker.add(item);
        sqlTracker.add(item2);
        List<Item> list = sqlTracker.findByName(item2.getName());
        assertEquals(list.get(0), item2);
    }
}
