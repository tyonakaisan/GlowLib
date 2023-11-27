package com.tyonakaisan.glowlib.glow;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Set;

@SuppressWarnings("unused")
public interface Glow {

    /**
     * Creates a new glow.
     *
     * @param color the color
     * @return a glow
     */
    static @NotNull Glow glowing(final @NotNull Color color) {
        return new GlowImpl(color);
    }

    /**
     * Creates a new glow.
     *
     * @param color the glowEffect
     * @param teamName the teamName
     * @return a glow
     */
    static @NotNull Glow glowing(final @NotNull Color color, final @NotNull String teamName) {
        return new GlowImpl(color, teamName);
    }

    /**
     * Gets color.
     *
     * @return the color.
     */
    @NotNull Color color();

    /**
     * Sets color.
     *
     * @param newColor the new color
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow color(final @NotNull Color newColor);

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
    @NotNull Glow addEntities(final @Nullable Entity... entities);

    /**
     * Adds an entities to apply the specified glow effect.
     *
     * @param entities the entities
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow addEntities(final @Nullable Collection<Entity> entities);

    /**
     * Removes an entities to apply the specified glow effect.
     *
     * @param entities the entities
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow removeEntities(final @Nullable Entity... entities);

    /**
     * Removes an entities to apply the specified glow effect.
     *
     * @param entities the entities
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow removeEntities(final @Nullable Collection<Entity> entities);

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
     * @param receivers the receivers
     */
    void show(final @NotNull Player... receivers);

    /**
     * Send glow to all receivers.
     *
     * @param receivers the receivers
     */
    void show(final @NotNull Collection<Player> receivers);

    /**
     * Hides the glow.
     *
     * @param receivers the receivers
     */
    void hide(final @NotNull Player... receivers);

    /**
     * Hides the glow.
     *
     * @param receivers the receivers
     */
    void hide(final @NotNull Collection<Player> receivers);

    enum Color {

        /**
         * Black.
         */

        BLACK(0x000000),

        /**
         * Dark blue.
         */

        DARK_BLUE(0x0000aa),

        /**
         * Dark green.
         */

        DARK_GREEN(0x00aa00),

        /**
         * Dark aqua.
         */

        DARK_AQUA(0x00aaaa),

        /**
         * Dark red.
         */

        DARK_RED(0xaa0000),

        /**
         * Dark purple.
         */

        DARK_PURPLE(0xaa00aa),

        /**
         * Gold.
         */

        GOLD(0xffaa00),

        /**
         * Gray.
         */

        GRAY(0xaaaaaa),

        /**
         * Dark gray.
         */

        DARK_GRAY(0x555555),

        /**
         * Blue.
         */

        BLUE(0x5555ff),

        /**
         * Green.
         */

        GREEN(0x55ff55),

        /**
         * Aqua.
         */

        AQUA(0x55ffff),

        /**
         * Red.
         */

        RED(0xff5555),

        /**
         * Light purple.
         */

        LIGHT_PURPLE(0xff55ff),

        /**
         * Yellow.
         */

        YELLOW(0xffff55),

        /**
         * White.
         */

        WHITE(0xffffff);

        /**
         * The color id.
         */
        private final int id;

        Color(final int id) {
            this.id = id;
        }

        public int getId() {
            return this.id;
        }
    }
}
