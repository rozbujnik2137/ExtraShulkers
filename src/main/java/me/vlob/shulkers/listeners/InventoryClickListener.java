package me.vlob.shulkers.listeners;

import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;

import java.util.Arrays;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onClick(InventoryClickEvent event) {
        if(!(event.getWhoClicked() instanceof Player)) return;
        if(!(event.getClickedInventory() instanceof AnvilInventory)) return;
        AnvilInventory anvilInventory = (AnvilInventory) event.getInventory();
        if(Arrays.stream(anvilInventory.getContents()).noneMatch(itemStack -> itemStack !=null && itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().equals(StringUtils.color(ConfigManager.shulkerItemName)))) return;
        if(event.getSlot() !=2) return;
        Player player = (Player) event.getWhoClicked();
        event.setCancelled(true);
        player.updateInventory();
    }

}
