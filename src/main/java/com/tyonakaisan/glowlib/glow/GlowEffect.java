package com.tyonakaisan.glowlib.glow;

import net.kyori.adventure.util.Index;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public interface GlowEffect {

    /**
     * Creates a new glowEffect.
     *
     * @param color the color
     * @return a glowEffect
     */
    static @NotNull GlowEffect create(final @NotNull Color color) {
        return new GlowEffectImpl(color);
    }

    static @NotNull GlowEffect create(final @NotNull Color color, final @NotNull Long time) {
        return new GlowEffectImpl(color, time);
    }

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
     * @return the glowEffect
     */
    @Contract("_ -> this")
    @NotNull GlowEffect color(final @NotNull Color color);

    /**
     * Gets time.
     *
     * @return the time
     */
    @NotNull Long time();

    /**
     * Sets time.
     *
     * @param time the time
     * @return the glowEffect
     */
    @Contract("_ -> this")
    @NotNull GlowEffect time(final @NotNull Long time);

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

        /**
         * The name map.
         */
        public static final Index<Integer, Color> COLORS = Index.create(Color.class, color -> color.name);
        private final int name;

        Color(final int name) {
            this.name = name;
        }
    }

}
