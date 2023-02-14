package me.vlob.shulkers.objects.impl;

import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.SerializationUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ExtraShulkerImpl implements ExtraShulker {

    private final String id;
    private Location location;
    private ItemStack[] contents;
    private Player viewer;

    public ExtraShulkerImpl(String id, Location location, ItemStack[] contents) {
        this.id = id;
        this.location = location;
        this.contents = contents;
    }

    public ExtraShulkerImpl(ResultSet resultSet) throws SQLException, IOException {
        this.id = resultSet.getString("id");
        this.location = (resultSet.getString("location").equals("null") ? null : SerializationUtils.getLocationFromString(resultSet.getString("location")));
        this.contents = SerializationUtils.readItemStacks(resultSet.getString("contents"));
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public Player getViewer() {
        return this.viewer;
    }

    @Override
    public void setViewer(Player player) {
        this.viewer = player;
    }

    @Override
    public Location getLocation() {
        return this.location;
    }

    @Override
    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public ItemStack[] getContents() {
        return this.contents;
    }

    @Override
    public void setContents(ItemStack[] contents) {
        this.contents = contents;
    }
}
