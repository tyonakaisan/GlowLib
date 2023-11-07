package com.tyonakaisan.glowlib.packet;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.tyonakaisan.glowlib.glow.GlowEffect;
import org.bukkit.entity.Player;

public class EntityMetadata {

    public void sendGlowing(GlowEffect glowEffect, Player receiver) {
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(glowEffect.entity());

        byte data = watcher.getByte(0);
        data |= 1 << 6;
    }
}
