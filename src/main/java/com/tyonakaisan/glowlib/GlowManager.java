package com.tyonakaisan.glowlib;

import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.tyonakaisan.glowlib.glow.Glow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

public final class GlowManager {

    @NotNull static GlowManager instance = new GlowManager();

    private final Set<Glow> glows = ConcurrentHashMap.newKeySet();

    public void add(final @NotNull Glow glow) {
        this.glows.add(glow);
    }

    public void remove(final @NotNull Glow glow) {
        this.glows.remove(glow);
    }

    public @NotNull @UnmodifiableView Set<Glow> getGlows() {
        return Collections.unmodifiableSet(this.glows);
    }

    public Stream<Glow> getGlowByPlayer(final @NotNull Player player, final @NotNull Entity entity) {
        return this.getGlows()
                .stream()
                .filter(glow -> glow.containsReceiver(player))
                .filter(glow -> glow.containsEntities(entity));
    }

    public WrappedDataWatcher createDataWatcher(final @NotNull Glow glow, final @NotNull Entity entity, final @NotNull Player receiver) {
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();

        byte bitmask = dataWatcher.getByte(0);

        if (glow.containsReceiver(receiver) && glow.containsEntities(entity)) {
            bitmask |= 0x40;
        }

        dataWatcher.setEntity(entity);
        dataWatcher.setObject(0, WrappedDataWatcher.Registry.get(Byte.class), bitmask);

        return dataWatcher;
    }

    public static @NotNull GlowManager getInstance() {
        return instance;
    }
}
