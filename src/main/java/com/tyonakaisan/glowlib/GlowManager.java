package com.tyonakaisan.glowlib;

import com.tyonakaisan.glowlib.glow.Glow;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;

@SuppressWarnings("unused")
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

    public Stream<Glow> getGlow(final @NotNull Player player, final @NotNull Entity entity) {
        return this.getGlows()
                .stream()
                .filter(glow -> glow.containsReceiver(player))
                .filter(glow -> glow.containsEntities(entity));
    }

    public static @NotNull GlowManager getInstance() {
        return instance;
    }
}
