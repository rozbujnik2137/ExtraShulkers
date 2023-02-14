package me.vlob.shulkers.listeners;

import me.vlob.shulkers.Main;
import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;

public class BlockPlaceListener implements Listener {

    private final Main main;

    public BlockPlaceListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event){
        Player player = event.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if(!itemStack.getType().equals(Material.SHULKER_BOX)) itemStack = player.getInventory().getItemInOffHand();
        if(!itemStack.getType().equals(Material.SHULKER_BOX)) return;
        if(!itemStack.hasItemMeta() && !itemStack.getItemMeta().getDisplayName().equals(StringUtils.color(ConfigManager.shulkerItemName))) return;
        if(itemStack.getItemMeta().getLore().isEmpty()) return;
        String id = ChatColor.stripColor(itemStack.getItemMeta().getLore().get(itemStack.getItemMeta().getLore().size() - 1));
        ExtraShulker extraShulker = this.main.getExtraShulkerManager().getExtraShulker(id);
        if(extraShulker == null){
            extraShulker = this.main.getMysqlManager().getShulkerFromDatabase(id);
        }
        extraShulker.setLocation(event.getBlockPlaced().getLocation());
        this.main.getExtraShulkerManager().createShulker(extraShulker);
    }
}
