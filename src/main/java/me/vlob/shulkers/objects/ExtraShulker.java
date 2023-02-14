package me.vlob.shulkers.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;

public interface ExtraShulker {

    String getID();

    @Nullable
    Player getViewer();

    void setViewer(Player player);

    Location getLocation();

    void setLocation(Location location);

    ItemStack[] getContents();

    void setContents(ItemStack[] contents);

}
