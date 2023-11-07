package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Set;

public interface Glow {

    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect, final @NotNull Entity entity, final @NotNull Player receiver) {
        return new GlowImpl(glowEffect, entity, receiver);
    }

    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect, final @NotNull Set<Entity> entities, final @NotNull Player receiver) {
        return new GlowImpl(glowEffect, entities, receiver);
    }

    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect, final @NotNull Entity entity, final @NotNull Set<Player> receivers) {
        return new GlowImpl(glowEffect, entity, receivers);
    }

    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect, final @NotNull Set<Entity> entities, final @NotNull Set<Player> receivers) {
        return new GlowImpl(glowEffect, entities, receivers);
    }

    @NotNull GlowEffect glowEffect();

    @NotNull Set<Entity> entities();

    @Contract("_ -> this")
    @NotNull Glow entities(Entity entity);

    @NotNull Set<Player> receivers();

    default void show() {

    }

    default void hide() {

    }
}
