package com.tyonakaisan.glowlib.packet;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.tyonakaisan.glowlib.glow.Glow;
import org.bukkit.entity.Player;

public class EntityMetadata {

    public void sendGlowing(Glow glow, Player receiver) {
        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(glow.entity());

        byte data = watcher.getByte(0);
        data |= 1 << 6;
    }
}
