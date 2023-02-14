package me.vlob.shulkers.listeners;

import me.vlob.shulkers.Main;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.PacketUtils;
import me.vlob.shulkers.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;

public class PlayerInteractListener implements Listener {

    private final Main main;

    public PlayerInteractListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event){
        Player player = event.getPlayer();
        if(!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;
        if(event.getClickedBlock() == null || event.getClickedBlock().getType().equals(Material.AIR)) return;
        Block block = event.getClickedBlock();
        if(!block.getType().equals(Material.SHULKER_BOX)) return;
        ExtraShulker extraShulker = this.main.getExtraShulkerManager().getExtraShulker(event.getClickedBlock().getLocation());
        if(extraShulker == null)
            extraShulker = this.main.getMysqlManager().getShulkerFromDatabase(event.getClickedBlock().getLocation());
        if(extraShulker == null) return;
        if(extraShulker.getViewer() !=null){
            StringUtils.sendMessage(player, "&4Blad: &cKtos juz uzywa tego shulkera!");
            return;
        }
        event.setCancelled(true);
        Inventory inventory = Bukkit.createInventory(null, 6*9, StringUtils.color("&8" + extraShulker.getID()));
        if(extraShulker.getContents() != null)inventory.setContents(extraShulker.getContents());
        extraShulker.setViewer(player);
        this.main.getExtraShulkerManager().createShulker(extraShulker);
        player.openInventory(inventory);
        PacketUtils.broadcastBlockActionsPacket( 1, block.getLocation());
    }
}
