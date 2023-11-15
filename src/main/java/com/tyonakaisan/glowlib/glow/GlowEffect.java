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
         * The id map.
         */
        public static final Index<Integer, Color> COLORS = Index.create(Color.class, color -> color.id);
        private final int id;

        Color(final int id) {
            this.id = id;
        }
    }

}
