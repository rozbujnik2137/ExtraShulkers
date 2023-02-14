package me.vlob.shulkers.managers;

import me.vlob.shulkers.objects.ExtraShulker;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.List;
import java.util.SortedSet;

public interface ExtraShulkerManager {

    List<ExtraShulker> getShulkers();

    @Nullable
    ExtraShulker registerShulker(Location location, ItemStack[] contents);

    void createShulker(ExtraShulker extraShulker);

    void removeShulker(ExtraShulker extraShulker);

    @Nullable
    ExtraShulker getExtraShulker(Location location);

    @Nullable
    ExtraShulker getExtraShulker(String id);

}
