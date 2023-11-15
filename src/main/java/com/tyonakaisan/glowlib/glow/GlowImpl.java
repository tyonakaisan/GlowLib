package com.tyonakaisan.glowlib.glow;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.google.common.collect.Lists;
import com.tyonakaisan.glowlib.GlowManager;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.UnmodifiableView;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

final class GlowImpl implements Glow {

    private GlowEffect glowEffect;
    private PacketContainer teamPacket;
    private final Set<Entity> entities = ConcurrentHashMap.newKeySet();
    private final Set<Player> receivers = ConcurrentHashMap.newKeySet();

    private static final String NOT_NULL_GLOW_EFFECT = "glowEffect must not be null";

    GlowImpl(final @NotNull GlowEffect glowEffect) {
        this.glowEffect = Objects.requireNonNull(glowEffect, NOT_NULL_GLOW_EFFECT);
    }

    @Override
    public @NotNull GlowEffect glowEffect() {
        return this.glowEffect;
    }

    @Override
    public @NotNull Glow glowEffect(@NotNull GlowEffect newGlowEffect) {
        Objects.requireNonNull(newGlowEffect, NOT_NULL_GLOW_EFFECT);
        final GlowEffect oldGlowEffect = this.glowEffect;

        if (!Objects.equals(newGlowEffect, oldGlowEffect)) {
            this.glowEffect = newGlowEffect;
        }
        return this;
    }

    @Override
    public @NotNull @UnmodifiableView Set<Entity> entities() {
        return Collections.unmodifiableSet(this.entities);
    }

    @Override
    public @NotNull Glow entities(final @Nullable Entity... entities) {
        this.entities.addAll(entities != null ? Lists.newArrayList(entities) : Collections.emptyList());
        return this;
    }

    @Override
    public @NotNull Glow entities(@Nullable Collection<Entity> entities) {
        this.entities.addAll(entities != null ? Lists.newArrayList(entities) : Collections.emptyList());
        return this;
    }

    @Override
    public @NotNull @UnmodifiableView Set<Player> receivers() {
        return Collections.unmodifiableSet(this.receivers);
    }

    @Override
    public void show(final @NotNull Player receiver) {
        this.receivers.add(receiver);

        if (this.entities.isEmpty()) {
            throw new IllegalStateException("The entity to which the glow effect is applied is not specified");
        }

        this.sendGlowingAll(receiver);
        GlowManager.getInstance().add(this);
    }

    @Override
    public void hide(final @NotNull Player receiver) {
        GlowManager.getInstance().remove(this);
        this.receivers.removeIf(playerValue -> playerValue.equals(receiver));
        this.stopGlowingAll(receiver);
    }

    private void sendGlowingAll(final @NotNull Player receiver) {
        GlowPacket.Mode mode = GlowPacket.Mode.CREATE_TEAM;

        this.teamPacket = new GlowPacket().operateTeam(this.entities, this.glowEffect, mode);

        this.forEachEntity(entity -> {
            PacketContainer metadataPacket = new GlowPacket().operateGlow(entity, true);
            ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, metadataPacket);
        });

        ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.teamPacket);
    }

    private void stopGlowingAll(final @NotNull Player receiver) {
        this.forEachEntity(entity -> this.stopGlowing(entity, receiver));
    }

    private void stopGlowing(final @NotNull Entity entity, final @NotNull Player receiver) {
        PacketContainer packet = new GlowPacket().operateGlow(entity, false);

        this.teamPacket = new GlowPacket().operateTeam(entity, this.glowEffect, GlowPacket.Mode.REMOVE_ENTITIES);
        ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, packet);
        ProtocolLibrary.getProtocolManager().sendServerPacket(receiver, this.teamPacket);
    }

    private void forEachReceiver(final @NotNull Consumer<Player> consumer) {
        for (final Player receiver : this.receivers) {
            consumer.accept(receiver);
        }
    }

    private void forEachEntity(final @NotNull Consumer<Entity> consumer) {
        for (final Entity entity : this.entities) {
            consumer.accept(entity);
        }
    }
}
