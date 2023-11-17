package com.tyonakaisan.glowlib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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

                    GlowManager.getInstance().getGlowByPlayer(player, entity).forEach(glow -> {

                        List<WrappedDataValue> dataValues = new ArrayList<>();
                        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();
                        byte bitmask = dataWatcher.getByte(0);

                        if (glow.containsReceiver(player) && glow.containsEntities(entity)) {
                            bitmask |= 0x40;
                        } else {
                            bitmask = (byte) 0;
                        }

                        dataValues.add(new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), bitmask));

                        packet.getDataValueCollectionModifier().write(0, dataValues);
                        event.setPacket(packet);
                    });
                }
            }
        });
    }

    private static boolean protocolLibLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("protocolLib");
    }
}
