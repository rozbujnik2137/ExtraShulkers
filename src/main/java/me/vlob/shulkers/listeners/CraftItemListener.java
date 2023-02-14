package me.vlob.shulkers.listeners;

import com.google.common.collect.Lists;
import me.vlob.shulkers.Main;
import me.vlob.shulkers.builders.ItemBuilder;
import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class CraftItemListener implements Listener {

    private final Main main;

    public CraftItemListener(Main main) {
        this.main = main;
    }

    @EventHandler
    public void onCraft(CraftItemEvent event){
        if(!(event.getWhoClicked() instanceof Player)) return;
        ItemStack currentItem = event.getCurrentItem();
        if(Arrays.stream(event.getInventory().getMatrix()).anyMatch(itemStack -> itemStack.hasItemMeta() && itemStack.getItemMeta().getDisplayName().equals(StringUtils.color(ConfigManager.shulkerItemName)))){
            event.setCancelled(true);
            event.setCurrentItem(null);
            return;
        }
        if(!currentItem.hasItemMeta() || !currentItem.getItemMeta().hasDisplayName()) return;
        if(!currentItem.getItemMeta().getDisplayName().equals(StringUtils.color(ConfigManager.shulkerItemName))) return;
        ExtraShulker extraShulker = this.main.getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
        if(extraShulker == null) extraShulker = Main.getInstance().getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
        List<String> lore = Lists.newArrayList(ConfigManager.shulkerItemDescription);
        lore.add("&8" + extraShulker.getID());
        event.setCurrentItem(new ItemBuilder(Material.SHULKER_BOX)
                .setName(ConfigManager.shulkerItemName)
                .setLore(lore)
                .toItemStack());
    }

}
