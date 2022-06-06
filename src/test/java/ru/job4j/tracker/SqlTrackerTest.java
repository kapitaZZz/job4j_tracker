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
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = sqlTracker.add(new Item("item"));
        assertThat(sqlTracker.findById(item.getId()), is(item));
    }

    @Test
    public void testReplace() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = sqlTracker.add(new Item("Item"));
        Item newItem = new Item("newItem");
        int id = item.getId();
        sqlTracker.replace(id, newItem);
        assertThat(sqlTracker.findByName(newItem.getName()).get(0).getName(), is((newItem.getName())));
    }

    @Test
    public void testDelete() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = sqlTracker.add(new Item("item"));
        sqlTracker.add(item);
        int id = item.getId();
        assertTrue(sqlTracker.delete(id));
        assertFalse(sqlTracker.delete(id));

    }

    @Test
    public void whenFindAllThenTrue() {
        List<Item> items = new ArrayList<>();
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item1 = sqlTracker.add(new Item("item_1"));
        Item item2 = sqlTracker.add(new Item("item_2"));
        Item item3 = sqlTracker.add(new Item("item_3"));
        Item item4 = sqlTracker.add(new Item("item_4"));
        items.add(item1);
        items.add(item2);
        items.add(item3);
        items.add(item4);
        List<Item> mainItems = sqlTracker.findAll();
        assertEquals(items, mainItems);
    }

    @Test
    public void whenFindByNameThenTrue() {
        SqlTracker sqlTracker = new SqlTracker(connection);
        Item item = sqlTracker.add(new Item("item_1"));
        Item item2 = sqlTracker.add(new Item("item_2"));
        Item item3 = sqlTracker.add(new Item("item_2"));
        Item item4 = sqlTracker.add(new Item("item_2"));
        Item item5 = sqlTracker.add(new Item("item_2"));
        Item item6 = sqlTracker.add(new Item("item_2"));
        List<Item> list = sqlTracker.findByName(item2.getName());
        assertEquals(list, List.of(item2, item3, item4, item5, item6));
    }
}
