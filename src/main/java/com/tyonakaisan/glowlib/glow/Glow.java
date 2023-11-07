package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Set;

public interface Glow {

    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect) {
        return new GlowImpl(glowEffect);
    }

    @NotNull GlowEffect glowEffect();

    @NotNull @UnmodifiableView Set<Entity> entities();

    @Contract("_ -> this")
    @NotNull Glow entities(Entity entity);

    @NotNull Set<Player> receivers();

    @Contract("_ -> this")
    @NotNull Glow receivers(Player receiver);

    default void show() {

    }

    default void hide() {

    }
}
