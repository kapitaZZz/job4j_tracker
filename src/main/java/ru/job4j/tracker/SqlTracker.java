package ru.job4j.tracker;

import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class SqlTracker implements Store, AutoCloseable {

    private Connection cn;

    public SqlTracker(Connection cn) {
        this.cn = cn;
    }

    public SqlTracker() {
    }

    public void init() {
        try (InputStream in = SqlTracker.class.getClassLoader().getResourceAsStream("app.properties")) {
            Properties config = new Properties();
            config.load(in);
            Class.forName(config.getProperty("driver-class-name"));
            cn = DriverManager.getConnection(
                    config.getProperty("url"),
                    config.getProperty("username"),
                    config.getProperty("password")
            );
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void close() throws Exception {
        if (cn != null) {
            cn.close();
        }
    }

    @Override
    public Item add(Item item) {
        String sql = "insert into items (name, created) values (?, ?)";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                item.setId(resultSet.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public boolean replace(int id, Item item) {
        boolean result = false;
        String sql = "update items set name = ? created = ? where id = ?";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            preparedStatement.setString(1, item.getName());
            preparedStatement.setTimestamp(2, Timestamp.valueOf(item.getCreated()));
            preparedStatement.setInt(3, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean delete(int id) {
        boolean result = false;
        String sql = "delete from items where id = ?";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public List<Item> findAll() {
        List<Item> items = new ArrayList<>();
        String sql = "select * from items";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            items.add(parseResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return items;
    }

    @Override
    public List<Item> findByName(String key) {
        List<Item> item = new ArrayList<>();
        String sql = "select * from items where name = ?";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            preparedStatement.setString(1, key);
            ResultSet resultSet = preparedStatement.executeQuery();
            item.add(parseResultSet(resultSet));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    @Override
    public Item findById(int id) {
        Item item = null;
        String sql = "select * from items where id = ?";
        try (PreparedStatement preparedStatement = cn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            item = parseResultSet(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    private Item parseResultSet(ResultSet resultSet) throws SQLException {
        Item item = new Item();
        while (resultSet.next()) {
            item.setId(resultSet.getInt("id"));
            item.setName(resultSet.getString("name"));
            item.setCreated(resultSet.getTimestamp("created").toLocalDateTime());
        }
        return item;
    }
}
