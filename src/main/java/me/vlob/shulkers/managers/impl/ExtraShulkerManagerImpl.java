package me.vlob.shulkers.managers.impl;

import me.vlob.shulkers.Main;
import me.vlob.shulkers.managers.ExtraShulkerManager;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.objects.impl.ExtraShulkerImpl;
import org.bukkit.Location;
import org.bukkit.inventory.ItemStack;

import javax.annotation.Nullable;
import java.util.*;

public class ExtraShulkerManagerImpl implements ExtraShulkerManager {

    private final Main main;

    private final Map<String, ExtraShulker> extraShulkerMap = new HashMap<>();

    public ExtraShulkerManagerImpl(Main main) {
        this.main = main;
    }

    @Override
    public List<ExtraShulker> getShulkers() {
        return new ArrayList<>(this.extraShulkerMap.values());
    }

    private String getNewID(){
        return UUID.randomUUID().toString();
    }

    @Nullable
    @Override
    public ExtraShulker registerShulker(Location location, ItemStack[] contents) {
        String id = getNewID();
        if(this.extraShulkerMap.containsKey(id)) return null;
        ExtraShulker extraShulker = new ExtraShulkerImpl(id, location, contents);
        this.main.getMysqlManager().insertShulker(extraShulker);
        return extraShulker;
    }

    @Override
    public void createShulker(ExtraShulker extraShulker) {
        if(!this.extraShulkerMap.containsKey(extraShulker.getID())) this.extraShulkerMap.put(extraShulker.getID(), extraShulker);
    }

    @Nullable
    @Override
    public ExtraShulker getExtraShulker(String id) {
        return this.extraShulkerMap.get(id);
    }

    @Override
    public void removeShulker(ExtraShulker extraShulker) {
        this.extraShulkerMap.remove(extraShulker.getID());
    }

    @Nullable
    @Override
    public ExtraShulker getExtraShulker(Location location) {
        return this.extraShulkerMap.values()
                .stream()
                .filter(extraShulker -> extraShulker.getLocation().equals(location))
                .findFirst()
                .orElse(null);
    }
}
