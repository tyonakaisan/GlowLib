package com.tyonakaisan.glowlib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.tyonakaisan.glowlib.util.PacketHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;

public final class GlowLib {

    public GlowLib(
    ) {
    }

    public static void init(final Plugin plugin) {
        if (!protocolLibLoaded()) {
            plugin.getComponentLogger().error("[GlowLib] ProtocolLib could not be found! Disable " + plugin.getName() + " !");

            Bukkit.getPluginManager().disablePlugin(plugin);
        }

        registerPacketListener(plugin);
    }

    private static void registerPacketListener(final Plugin plugin) {
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
                    Player player = event.getPlayer();
                    Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                    GlowManager.getInstance().getGlowByPlayer(player).forEach(glow -> {
                        List<WrappedWatchableObject> wrappedWatchableObjects = GlowManager.getInstance().createDataWatcher(glow, entity, player).getWatchableObjects();

                        packet.getDataValueCollectionModifier().write(0, PacketHelper.watchableObjectsToDataValues(wrappedWatchableObjects));

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
