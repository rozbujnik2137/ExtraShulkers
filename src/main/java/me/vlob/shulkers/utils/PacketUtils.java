package me.vlob.shulkers.utils;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.BlockPosition;
import me.vlob.shulkers.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.lang.reflect.InvocationTargetException;

public class PacketUtils {

    private static final Main main = Main.getInstance();

    public static void broadcastBlockActionsPacket(int attribute, Location blockLocation){
        PacketContainer packetContainer = main.getProtocolManager().createPacket(PacketType.Play.Server.BLOCK_ACTION);
        packetContainer.getBlockPositionModifier().write(0, new BlockPosition(blockLocation.getBlockX(), blockLocation.getBlockY(), blockLocation.getBlockZ()));
        packetContainer.getIntegers().write(0, 1);
        packetContainer.getIntegers().write(1, attribute);
        Bukkit.getOnlinePlayers().forEach(player -> {
            try {
                main.getProtocolManager().sendServerPacket(player, packetContainer);
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        });
    }

}
