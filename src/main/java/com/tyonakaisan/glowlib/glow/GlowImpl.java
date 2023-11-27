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

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

final class GlowImpl implements Glow {

    private Color color;
    private final String teamName;
    private final Set<Entity> entities = ConcurrentHashMap.newKeySet();
    private final Set<Player> receivers = ConcurrentHashMap.newKeySet();

    private static final String NOT_NULL_COLOR = "color must not be null";
    private static final String NOT_NULL_NAME = "teamName must not be null";

    GlowImpl(final @NotNull Color color) {
        this.color = Objects.requireNonNull(color, NOT_NULL_COLOR);
        this.teamName = UUID.randomUUID().toString();
    }

    GlowImpl(final @NotNull Color color, final @NotNull String name) {
        this.color = Objects.requireNonNull(color, NOT_NULL_COLOR);
        this.teamName = Objects.requireNonNull(name, NOT_NULL_NAME);
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

    @Override
    public @NotNull @UnmodifiableView Set<Entity> entities() {
        return Collections.unmodifiableSet(this.entities);
    }

    @Override
    public @NotNull Glow addEntities(final @Nullable Entity... entities) {
        List<Entity> entityList = entities != null ? Lists.newArrayList(entities) : Collections.emptyList();
        this.operateTeamEntity(entityList, true);
        return this;
    }

    @Override
    public @NotNull Glow addEntities(final @Nullable Collection<Entity> entities) {
        List<Entity> entityList = entities != null ? Lists.newArrayList(entities) : Collections.emptyList();
        this.operateTeamEntity(entityList, true);
        return this;
    }

    @Override
    public @NotNull Glow removeEntities(@Nullable Entity... entities) {
        List<Entity> entityList = entities != null ? Lists.newArrayList(entities) : Collections.emptyList();
        this.operateTeamEntity(entityList, false);
        return this;
    }

    @Override
    public @NotNull Glow removeEntities(@Nullable Collection<Entity> entities) {
        List<Entity> entityList = entities != null ? Lists.newArrayList(entities) : Collections.emptyList();
        this.operateTeamEntity(entityList, false);
        return this;
    }

    @Override
    public @NotNull @UnmodifiableView Set<Player> receivers() {
        return Collections.unmodifiableSet(this.receivers);
    }

    @Override
    public void show(final @NotNull Player... receivers) {
        this.show(List.of(receivers));
    }

    @Override
    public void show(final @NotNull Collection<Player> receivers) {
        if (this.entities.isEmpty()) {
            throw new IllegalStateException("The entity to which the glow effect is applied is not specified");
        }

        this.operateDisplay(receivers, true);
        GlowManager.getInstance().add(this);
    }

    @Override
    public void hide(final @NotNull Player... receivers) {
        this.hide(List.of(receivers));
    }

    @Override
    public void hide(final @NotNull Collection<Player> receivers) {
        this.operateDisplay(receivers, false);
    }

    private void operateDisplay(final @NotNull Collection<Player> receivers, final boolean display) {
        for (Player receiver : receivers) {
            if (display) {
                this.receivers.add(receiver);
                continue;
            }
            this.receivers.remove(receiver);
        }

        List<PacketContainer> packets = new ArrayList<>();
        this.forEachEntity(entity -> packets.add(new GlowPacket().operateGlow(entity, display)));
        packets.add(new GlowPacket().operateTeam(this.entities, this.color, this.teamName, display ? GlowPacket.Mode.CREATE_TEAM : GlowPacket.Mode.REMOVE_TEAM));

        packets.forEach(packet -> this.sendPacket(receivers, packet));

        if (display) GlowManager.getInstance().add(this);
    }

    private void operateTeamEntity(final @NotNull Collection<Entity> entities, final boolean add) {
        List<PacketContainer> packets = new ArrayList<>();

        for (Entity entity : entities) {
            if (add) {
                this.entities.add(entity);
            } else {
                this.entities.remove(entity);
            }

            packets.add(new GlowPacket().operateGlow(entity, add));
            packets.add(new GlowPacket().operateTeam(Collections.singletonList(entity), this.color, this.teamName, add ? GlowPacket.Mode.ADD_ENTITIES : GlowPacket.Mode.REMOVE_ENTITIES));
        }

        packets.forEach(packet -> this.sendPacket(this.receivers, packet));

        if (!add && this.entities.isEmpty()) {
            GlowManager.getInstance().remove(this);
        }
    }

    private void sendPacket(final @NotNull Collection<Player> players,final @NotNull PacketContainer packet) {
        players.forEach(player -> ProtocolLibrary.getProtocolManager().sendServerPacket(player, packet));
    }

    private void forEachEntity(final @NotNull Consumer<Entity> consumer) {
        for (final Entity entity : this.entities) {
            consumer.accept(entity);
        }
    }
}
