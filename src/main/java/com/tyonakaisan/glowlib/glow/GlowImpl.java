package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class GlowImpl implements Glow {

    private Entity entity;
    private Color color;

    private static final String NOT_NULL_ENTITY = "entity must not be null";
    private static final String NOT_NULL_COLOR = "color must not be null";

    GlowImpl(final @NotNull Entity entity) {
        this.entity = Objects.requireNonNull(entity, NOT_NULL_ENTITY);
        this.color = Color.WHITE;
    }

    GlowImpl(final @NotNull Entity entity, final @NotNull Color color) {
        this.entity = Objects.requireNonNull(entity, NOT_NULL_ENTITY);
        this.color = Objects.requireNonNull(color, NOT_NULL_COLOR);
    }

    @Override
    public @NotNull Entity entity() {
        return this.entity;
    }

    @Override
    public @NotNull Glow entity(final @NotNull Entity newEntity) {
        Objects.requireNonNull(newEntity, NOT_NULL_ENTITY);
        final Entity oldEntity = this.entity;

        if (!Objects.equals(newEntity, oldEntity)) {
            this.entity = newEntity;
        }

        return this;
    }

    @Override
    public @NotNull Color color() {
        return this.color;
    }

    @Override
    public @NotNull Glow color(final @NotNull Color newColor) {
        Objects.requireNonNull(newColor, NOT_NULL_COLOR);
        final Color oldColor = this.color;

        if (!Objects.equals(newColor, oldColor)) {
            this.color = newColor;
        }

        return this;
    }
}
