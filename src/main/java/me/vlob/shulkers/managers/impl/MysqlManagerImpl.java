package me.vlob.shulkers.managers.impl;

import com.google.common.collect.Lists;
import me.vlob.shulkers.databases.MysqlDatabase;
import me.vlob.shulkers.managers.MysqlManager;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.objects.impl.ExtraShulkerImpl;
import me.vlob.shulkers.utils.SerializationUtils;
import me.vlob.shulkers.utils.StatementUtils;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MysqlManagerImpl implements MysqlManager {

    private final MysqlDatabase mysqlDatabase;

    private Statement statement;

    public MysqlManagerImpl(){
        this.mysqlDatabase = new MysqlDatabase(ConfigManager.mysqlHost,
                ConfigManager.mysqlPort,
                ConfigManager.mysqlDatabase,
                ConfigManager.mysqlUsername,
                ConfigManager.mysqlPassword);
    }

    @Override
    public void connect() {
        try {
            this.mysqlDatabase.openConnection();
            createStatement();
            checkTables();
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disconnect() {
        try {
            this.mysqlDatabase.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createStatement() {
        try {
            this.statement = this.mysqlDatabase.getConnection().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void checkTables() {
        updateStatement("CREATE TABLE IF NOT EXISTS `shulkers` (id varchar(255), location varchar(255), contents text, primary key(id))");
    }

    @Override
    public void insertShulker(ExtraShulker extraShulker) {
        updateStatement(StatementUtils.insertObject("shulkers",
                Lists.newArrayList(extraShulker.getID(), "null", SerializationUtils.writeItemStacks(extraShulker.getContents()))));
    }

    @Override
    public void updateShulker(ExtraShulker extraShulker) {
        updateStatement(StatementUtils.updateObject("shulkers", extraShulker.getID(),
                Lists.newArrayList("id", "location", "contents"),
                Lists.newArrayList(extraShulker.getID(), SerializationUtils.getStringFromLocation(extraShulker.getLocation()), SerializationUtils.writeItemStacks(extraShulker.getContents()))));
    }

    @Nullable
    @Override
    public ExtraShulker getShulkerFromDatabase(String id) {
        try {
            ResultSet resultSet = this.queryStatement("SELECT * FROM `shulkers` WHERE id ='" + id + "';");
            if(!resultSet.next()) return null;
            return new ExtraShulkerImpl(resultSet);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Nullable
    @Override
    public ExtraShulker getShulkerFromDatabase(Location location) {
        try {
            ResultSet resultSet = this.queryStatement("SELECT * FROM `shulkers` WHERE location='" + SerializationUtils.getStringFromLocation(location) + "';");
            if(!resultSet.next()) return null;
            return new ExtraShulkerImpl(resultSet);
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Statement getStatement() {
        return this.statement;
    }

    @Override
    public void updateStatement(String statement){
        try {
            this.statement.executeUpdate(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public ResultSet queryStatement(String statement){
        try {
            return this.statement.executeQuery(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
