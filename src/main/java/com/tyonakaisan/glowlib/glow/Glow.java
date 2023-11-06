package com.tyonakaisan.glowlib.glow;

import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface Glow {

    /**
     * Creates a new glow.
     *
     * @param entity the entity
     * @return a glow
     */
    static @NotNull Glow glow(final @NotNull Entity entity) {
        return new GlowImpl(entity);
    }

    /**
     * Creates a new glow.
     *
     * @param entity the entity
     * @param color the color
     * @return a glow
     */
    static @NotNull Glow glow(final @NotNull Entity entity, final @NotNull Color color) {
        return new GlowImpl(entity, color);
    }

    /**
     * Gets entity to be glow effect.
     *
     * @return the entity
     */
    @NotNull Entity entity();

    /**
     * Sets entity to be glow effect.
     *
     * @param entity the entity
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow entity(final @NotNull Entity entity);

    /**
     * Gets glow color.
     *
     * @return the glow color
     */
    @NotNull Color color();

    /**
     * Sets glow color.
     *
     * @param color the color
     * @return the glow
     */
    @Contract("_ -> this")
    @NotNull Glow color(final @NotNull Color color);

    /**
     * Show this glow to receiver.
     *
     * @param receiver the receiver
     * @return the glow
     */
    default @NotNull Glow addReceiver(final @NotNull Audience receiver) {
        return this;
    }

    /**
     * Hide this glow to receiver.
     *
     * @param receiver the receiver
     * @return the glow
     */
    default @NotNull Glow removeReceiver(final @NotNull Audience receiver) {
        return this;
    }

    enum Color {

        /**
         * Black.
         */

        BLACK(0),

        /**
         * Dark blue.
         */

        DARK_BLUE(1),

        /**
         * Dark green.
         */

        DARK_GREEN(2),

        /**
         * Dark cyan.
         */

        DARK_CYAN(3),

        /**
         * Dark red.
         */

        DARK_RED(4),

        /**
         * Purple.
         */

        PURPLE(5),

        /**
         * Gold.
         */

        GOLD(6),

        /**
         * Gray.
         */

        GRAY(7),

        /**
         * Dark gray.
         */

        DARK_GRAY(8),

        /**
         * Blue.
         */

        BLUE(9),

        /**
         * Green.
         */

        GREEN(10),

        /**
         * Cyan.
         */

        CYAN(11),

        /**
         * Red.
         */

        RED(12),

        /**
         * Pink.
         */

        PINK(13),

        /**
         * Yellow.
         */

        YELLOW(14),

        /**
         * White.
         */

        WHITE(15);

        private final int color;

        Color(final int color) {
            this.color = color;
        }
    }

}
