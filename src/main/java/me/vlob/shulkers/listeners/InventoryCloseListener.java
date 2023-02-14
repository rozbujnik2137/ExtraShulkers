package me.vlob.shulkers.listeners;

import me.vlob.shulkers.Main;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.PacketUtils;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.InventoryView;

public class InventoryCloseListener implements Listener {

    private final Main main;

    public InventoryCloseListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        InventoryView inventoryView = event.getView();
        String id = ChatColor.stripColor(inventoryView.getTitle());
        ExtraShulker extraShulker = this.main.getExtraShulkerManager().getExtraShulker(id);
        if(extraShulker == null) return;
        extraShulker.setContents(event.getInventory().getContents());
        this.main.getMysqlManager().updateShulker(extraShulker);
        extraShulker.setViewer(null);
        Player player = (Player) event.getPlayer();
        PacketUtils.broadcastBlockActionsPacket(0, extraShulker.getLocation());
    }
}
