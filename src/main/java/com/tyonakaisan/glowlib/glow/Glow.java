package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Set;

public interface Glow {

    /**
     * Creates a new glow.
     *
     * @param glowEffect the glowEffect
     * @return a glow
     */
    static @NotNull Glow glowing(final @NotNull GlowEffect glowEffect) {
        return new GlowImpl(glowEffect);
    }

    /**
     * Gets glowEffect.
     *
     * @return the glowEffect
     */
    @NotNull GlowEffect glowEffect();

    /**
     * Sets glowEffect.
     *
     * @param newGlowEffect the glowEffect
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow glowEffect(final @NotNull GlowEffect newGlowEffect);

    /**
     * Gets entities.
     *
     * @return the unmodifiable entity list
     */
    @NotNull @UnmodifiableView Set<Entity> entities();

    /**
     * Adds an entities to apply the specified glow effect.
     *
     * @param entities the entities
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow entities(final @Nullable Entity... entities);

    /**
     * Adds an entities to apply the specified glow effect.
     *
     * @param entities the entities
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow entities(final @Nullable Collection<Entity> entities);

    /**
     * Gets receivers.
     *
     * @return the unmodifiable receiver list
     */
    @NotNull @UnmodifiableView Set<Player> receivers();

    default boolean hasReceiver() {
        return !this.receivers().isEmpty();
    }

    default boolean containsEntities(final @Nullable Entity entity) {
        return entity != null && this.entities().contains(entity);
    }

    default boolean containsReceiver(final @Nullable Player receiver) {
        return receiver != null && this.receivers().contains(receiver);
    }

    /**
     * Send glow to all receivers.
     *
     * @param receiver the receiver
     */
    void show(final @NotNull Player receiver);

    /**
     * Hides the glow.
     *
     * @param receiver the receiver
     */
    void hide(final @NotNull Player receiver);
}
