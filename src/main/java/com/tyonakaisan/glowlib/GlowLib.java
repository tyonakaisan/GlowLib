package com.tyonakaisan.glowlib;

import com.tyonakaisan.glowlib.glow.Glow;
import com.tyonakaisan.glowlib.glow.GlowEffect;
import net.kyori.adventure.bossbar.BossBar;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class GlowLib {

    private final Plugin plugin;

    private @Nullable GlowEffect activeGlowEffect;

    public GlowLib(
            final Plugin plugin
    ) {
        this.plugin = plugin;

        if (!protocolLibLoaded()) {
            this.plugin.getComponentLogger().error("[GlowLib] ProtocolLib could not be found! Disable " + plugin.getName() + " !");

            Bukkit.getPluginManager().disablePlugin(this.plugin);
        }
    }

    private void test(Entity entity, Player player) {

        this.activeGlowEffect = GlowEffect.create(GlowEffect.Color.RED);
        Glow glow = Glow.glowing(this.activeGlowEffect, entity, player);

        glow.entities().add(entity);

        glow.show();

        BossBar bossBar = BossBar.bossBar(Component.empty(), 1, BossBar.Color.PURPLE, BossBar.Overlay.PROGRESS);
        bossBar.addViewer(player);
    }

    private static boolean protocolLibLoaded() {
        return Bukkit.getPluginManager().isPluginEnabled("protocolLib");
    }
}
