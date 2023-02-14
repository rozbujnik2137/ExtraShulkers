package me.vlob.shulkers.listeners;

import com.google.common.collect.Lists;
import me.vlob.shulkers.Main;
import me.vlob.shulkers.builders.ItemBuilder;
import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.objects.ExtraShulker;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BlockBreakListener implements Listener {

    private final Main main;

    public BlockBreakListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onBreak(BlockBreakEvent event){
        Player player = event.getPlayer();
        if(!event.getBlock().getType().equals(Material.SHULKER_BOX)) return;
        ExtraShulker extraShulker = this.main.getExtraShulkerManager().getExtraShulker(event.getBlock().getLocation());
        if(extraShulker == null) return;
        List<String> lore = Lists.newArrayList(ConfigManager.shulkerItemDescription);
        lore.add("&8" + extraShulker.getID());
        this.main.getMysqlManager().updateShulker(extraShulker);
        this.main.getExtraShulkerManager().removeShulker(extraShulker);
        if(player.getGameMode().equals(GameMode.CREATIVE)) return;
        event.setDropItems(false);
        event.getBlock().getLocation().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemBuilder(Material.SHULKER_BOX).setName(ConfigManager.shulkerItemName).setLore(lore).toItemStack());
    }

}
