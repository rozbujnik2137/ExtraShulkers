package me.vlob.shulkers.commands;

import com.google.common.collect.Lists;
import me.vlob.shulkers.Main;
import me.vlob.shulkers.builders.ItemBuilder;
import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.objects.ExtraShulker;
import me.vlob.shulkers.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class ExtraShulkerCommand implements CommandExecutor {

    private final Main main;

    public ExtraShulkerCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if(!sender.hasPermission("extrashulkers.command")) return StringUtils.sendMessage(sender, "&4Blad: &cNie masz permisji!");
        if(args.length == 0) {
            if(!(sender instanceof Player)) return StringUtils.sendMessage(sender, "&4Blad: &cMusisz byc graczem zeby to zrobic!");
            Player player = (Player) sender;
            ExtraShulker extraShulker = Main.getInstance().getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
            if(extraShulker == null) extraShulker = Main.getInstance().getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
            List<String> lore = Lists.newArrayList(ConfigManager.shulkerItemDescription);
            lore.add("&8" + extraShulker.getID());
            player.getInventory().addItem(new ItemBuilder(Material.SHULKER_BOX)
                    .setName(ConfigManager.shulkerItemName)
                    .setLore(lore)
                    .toItemStack());
            return StringUtils.sendMessage(player, "&2Otrzymales nowego shulkera!");
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) return StringUtils.sendMessage(sender, "&4Blad: &cNie ma takiego gracza!");
        ExtraShulker extraShulker = Main.getInstance().getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
        if(extraShulker == null) extraShulker = Main.getInstance().getExtraShulkerManager().registerShulker(null, new ItemStack[]{});
        List<String> lore = Lists.newArrayList(ConfigManager.shulkerItemDescription);
        lore.add("&8" + extraShulker.getID());
        target.getInventory().addItem(new ItemBuilder(Material.SHULKER_BOX)
                .setName(ConfigManager.shulkerItemName)
                .setLore(lore)
                .toItemStack());
        return StringUtils.sendMessage(sender, "&7Dales nowego shulkera graczowi &3" + target.getName() + "&7!");
    }
}
