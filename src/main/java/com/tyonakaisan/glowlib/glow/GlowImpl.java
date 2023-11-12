package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Set;

final class GlowImpl implements Glow {

    private final GlowEffect glowEffect;
    private Set<Entity> entities;
    private Set<Player> receivers;

    private static final String NOT_NULL_GLOW_EFFECT = "glowEffect must not be null";
    private static final String NOT_NULL_ENTITIES = "entities must not be null";
    private static final String NOT_NULL_RECEIVER = "receivers must not be null";

    GlowImpl(final @NotNull GlowEffect glowEffect) {
        this.glowEffect = Objects.requireNonNull(glowEffect, NOT_NULL_GLOW_EFFECT);
        this.entities = Set.of();
        this.receivers = Set.of();
    }

    @Override
    public @NotNull GlowEffect glowEffect() {
        return this.glowEffect;
    }

    @Override
    public @NotNull Set<Entity> entities() {
        return Collections.unmodifiableSet(this.entities);
    }

    @Override
    public @NotNull Glow entities(final @NotNull Entity... entities) {
        this.entities.addAll(List.of(entities));
        return this;
    }

    @Override
    public @NotNull Set<Player> receivers() {
        return Collections.unmodifiableSet(this.receivers);
    }

    @Override
    public @NotNull Glow receivers(final @NotNull Player... receivers) {
        this.receivers.addAll(List.of(receivers));
        return this;
    }
}
