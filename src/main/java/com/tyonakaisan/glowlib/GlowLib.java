package com.tyonakaisan.glowlib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.tyonakaisan.glowlib.glow.Glow;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public final class GlowLib {

    public GlowLib(
    ) {
    }

    public static void init(final @NotNull Plugin plugin) {
        if (!protocolLibLoaded()) {
            plugin.getComponentLogger().error("[GlowLib] ProtocolLib could not be found! Disable " + plugin.getName() + " !");

            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        registerPacketListener(plugin);
    }

    private static void registerPacketListener(final @NotNull Plugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
                    Player player = event.getPlayer();
                    Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                    GlowManager.getInstance().getGlow(player, entity).forEach(glow -> {
                        List<WrappedWatchableObject> watchableObjects = createDataWatcher(glow, entity, player).getWatchableObjects();

                        packet.getDataValueCollectionModifier().write(0, toDataValues(watchableObjects));
                        event.setPacket(packet);
                    });
                }
            }
        });
    }

    private static List<WrappedDataValue> toDataValues(final @NotNull List<WrappedWatchableObject> watchableObjects) {
        List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();

        for (WrappedWatchableObject entry : watchableObjects) {
            if (entry == null) continue;

            WrappedDataWatcher.WrappedDataWatcherObject obj = entry.getWatcherObject();
            wrappedDataValueList.add(new WrappedDataValue(obj.getIndex(), obj.getSerializer(), entry.getRawValue()));
        }

        return wrappedDataValueList;
    }

    private static WrappedDataWatcher createDataWatcher(final @NotNull Glow glow, final @NotNull Entity entity, final @NotNull Player receiver) {
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        byte bitmask = dataWatcher.getByte(0);

        if (glow.containsReceiver(receiver) && glow.containsEntities(entity)) {
            bitmask |= 0x40;
        }

        dataWatcher.setEntity(entity);
        dataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), bitmask);

        return dataWatcher;
    }

    private static boolean protocolLibLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("protocolLib");
    }
}
