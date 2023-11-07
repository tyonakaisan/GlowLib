package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collections;
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
        this.entities = Collections.emptySet();
        this.receivers = Collections.emptySet();
    }

    @Override
    public @NotNull GlowEffect glowEffect() {
        return this.glowEffect;
    }

    @Override
    public @NotNull @UnmodifiableView Set<Entity> entities() {
        return Collections.unmodifiableSet(this.entities);
    }

    @Override
    public @NotNull Glow entities(Entity entity) {
        this.entities.add(entity);
        return this;
    }

    @Override
    public @NotNull @UnmodifiableView Set<Player> receivers() {
        return Collections.unmodifiableSet(this.receivers);
    }

    @Override
    public @NotNull Glow receivers(Player receiver) {
        this.receivers.add(receiver);
        return this;
    }
}
