package com.tyonakaisan.glowlib.glow;

import net.kyori.adventure.audience.Audience;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class GlowHandlerImpl implements GlowHandler{

    private Glow glow;
    private Audience receiver;

    private static final String NOT_NULL_GLOW = "glow must not be null";
    private static final String NOT_NULL_RECEIVER = "receiver must not be null";

    GlowHandlerImpl(final @NotNull Glow glow, final @NotNull Audience receiver) {
        this.glow = Objects.requireNonNull(glow, NOT_NULL_GLOW);
        this.receiver = Objects.requireNonNull(receiver, NOT_NULL_RECEIVER);
    }
}
