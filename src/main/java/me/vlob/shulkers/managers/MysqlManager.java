package me.vlob.shulkers.managers;

import me.vlob.shulkers.objects.ExtraShulker;
import org.bukkit.Location;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.sql.Statement;

public interface MysqlManager {

    void connect();

    void disconnect();

    void createStatement();

    void checkTables();

    void insertShulker(ExtraShulker extraShulker);

    void updateShulker(ExtraShulker extraShulker);

    @Nullable
    ExtraShulker getShulkerFromDatabase(String id);

    @Nullable
    ExtraShulker getShulkerFromDatabase(Location location);

    Statement getStatement();

    ResultSet queryStatement(String statement);

    void updateStatement(String statement);

}
