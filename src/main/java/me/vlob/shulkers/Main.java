package me.vlob.shulkers;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import me.vlob.shulkers.builders.ItemBuilder;
import me.vlob.shulkers.commands.ExtraShulkerCommand;
import me.vlob.shulkers.listeners.*;
import me.vlob.shulkers.managers.ExtraShulkerManager;
import me.vlob.shulkers.managers.FileManager;
import me.vlob.shulkers.managers.MysqlManager;
import me.vlob.shulkers.managers.impl.ConfigManager;
import me.vlob.shulkers.managers.impl.ExtraShulkerManagerImpl;
import me.vlob.shulkers.managers.impl.FileManagerImpl;
import me.vlob.shulkers.managers.impl.MysqlManagerImpl;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    private PluginManager pluginManager;
    private FileManager fileManager;
    private MysqlManager mysqlManager;
    private ExtraShulkerManager extraShulkerManager;
    private ProtocolManager protocolManager;

    public static Main getInstance(){
        return getPlugin(Main.class);
    }

    public void onEnable(){
        registerManagers();
        this.fileManager.checkFiles();
        this.mysqlManager.connect();
        registerRecipe();
        registerListeners(new PlayerInteractListener(this),
                new BlockPlaceListener(this),
                new InventoryCloseListener(this),
                new BlockBreakListener(this),
                new CraftItemListener(this),
                new InventoryClickListener());
        registerCommands();
    }

    public void onDisable(){
        this.extraShulkerManager.getShulkers().forEach(extraShulker -> this.mysqlManager.updateShulker(extraShulker));
        this.mysqlManager.disconnect();
    }

    private void registerManagers(){
        this.pluginManager = Bukkit.getPluginManager();
        this.fileManager = new FileManagerImpl(this);
        new ConfigManager(this);
        this.mysqlManager = new MysqlManagerImpl();
        this.extraShulkerManager = new ExtraShulkerManagerImpl(this);
        this.protocolManager = ProtocolLibrary.getProtocolManager();
    }

    private void registerCommands(){
        getCommand("extrashulkers").setExecutor(new ExtraShulkerCommand(this));
    }

    private void registerRecipe(){
        ShapedRecipe shapedRecipe = new ShapedRecipe(new NamespacedKey(this, "extrashulker"), new ItemBuilder(Material.SHULKER_BOX).setName(ConfigManager.shulkerItemName).setLore(ConfigManager.shulkerItemDescription).toItemStack());
        String[] split = ConfigManager.shulkerItemCraftingPattern.split(":");
        shapedRecipe.shape(split[0], split[1], split[2]);
        ConfigManager.shulkerItemCraftingIngredients.forEach(string -> {
            String[] split1 = string.split("=");
            shapedRecipe.setIngredient(split1[0].charAt(0), Material.matchMaterial(split1[1]));
        });
        Bukkit.addRecipe(shapedRecipe);
    }

    private void registerListeners(Listener... listeners){
        for(Listener listener : listeners){
            this.pluginManager.registerEvents(listener, this);
        }
    }

    public ExtraShulkerManager getExtraShulkerManager() {
        return extraShulkerManager;
    }

    public MysqlManager getMysqlManager() {
        return mysqlManager;
    }

    public ProtocolManager getProtocolManager() {
        return protocolManager;
    }
}
