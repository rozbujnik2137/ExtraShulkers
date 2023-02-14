package me.vlob.shulkers.managers.impl;

import me.vlob.shulkers.Main;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

public class ConfigManager {

    public static String mysqlHost, mysqlPort, mysqlUsername, mysqlPassword, mysqlDatabase;
    public static String shulkerItemName;
    public static List<String> shulkerItemDescription;
    public static String shulkerItemCraftingPattern;
    public static List<String> shulkerItemCraftingIngredients;


    public ConfigManager(Main main) {
        FileConfiguration fileConfiguration = main.getConfig();
        mysqlHost = fileConfiguration.getString("mysql.host");
        mysqlPort = fileConfiguration.getString("mysql.port");
        mysqlUsername = fileConfiguration.getString("mysql.username");
        mysqlPassword = fileConfiguration.getString("mysql.password");
        mysqlDatabase = fileConfiguration.getString("mysql.database");
        shulkerItemName = fileConfiguration.getString("shulker.item.name");
        shulkerItemDescription = fileConfiguration.getStringList("shulker.item.description");
        shulkerItemCraftingPattern = fileConfiguration.getString("shulker.item.crafting.pattern");
        shulkerItemCraftingIngredients = fileConfiguration.getStringList("shulker.item.crafting.ingredients");
    }
}
