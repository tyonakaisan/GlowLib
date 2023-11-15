package com.tyonakaisan.glowlib;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;
import com.tyonakaisan.glowlib.glow.GlowEffect;
import com.tyonakaisan.glowlib.util.PacketHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class GlowLib {

    private @Nullable GlowEffect activeGlowEffect;

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
        // Metadata changes frequently on entities, so when it gets updated we'll add our glowing state to ensure it's not overriden by updates.
        ProtocolLibrary.getProtocolManager().addPacketListener(new PacketAdapter(plugin, ListenerPriority.NORMAL, PacketType.Play.Server.ENTITY_METADATA) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();

                if (packet.getType() == PacketType.Play.Server.ENTITY_METADATA) {
                    Player player = event.getPlayer();
                    Entity entity = event.getPacket().getEntityModifier(player.getWorld()).read(0);

                    // TODO
                    // 光らせるのは成功、ただ、リストに入っていないエンティティも光ってしまう
                    // 修正済み？要検証

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
