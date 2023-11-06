package com.tyonakaisan.glowlib.glow;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

public interface GlowHandler {

    static void show(final @NotNull Glow glow, final @NotNull Audience audience) {
        new GlowHandlerImpl(glow, audience);
    }
}
