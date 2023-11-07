package com.tyonakaisan.glowlib.glow;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import net.kyori.adventure.audience.Audience;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

final class GlowImpl implements Glow {

    private Entity entity;
    private Color color;

    private static final String NOT_NULL_ENTITY = "entity must not be null";
    private static final String NOT_NULL_COLOR = "color must not be null";
    private static final String NOT_NULL_RECEIVER = "receiver must not be null";

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

    @Override
    public void show(final @NotNull Audience receiver) {
        Objects.requireNonNull(receiver, NOT_NULL_RECEIVER);

        List<WrappedDataValue> dataValues = new ArrayList<>();

        WrappedDataWatcher watcher = WrappedDataWatcher.getEntityWatcher(this.entity);

        byte data = watcher.getByte(0);
        data |= 1 << 6;
        dataValues.add(new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), data));

        PacketType type = PacketType.Play.Server.ENTITY_METADATA;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);

        packet.getIntegers().write(0, this.entity.getEntityId());
        packet.getDataValueCollectionModifier().write(0, dataValues);

        ProtocolLibrary.getProtocolManager().sendServerPacket((Player) receiver, packet);
    }

    @Override
    public void hide(final @NotNull Audience receiver) {
        List<WrappedDataValue> dataValues = new ArrayList<>();

        dataValues.add(new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), 0));

        PacketType type = PacketType.Play.Server.ENTITY_METADATA;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);

        packet.getIntegers().write(0, this.entity.getEntityId());
        packet.getDataValueCollectionModifier().write(0, dataValues);

        ProtocolLibrary.getProtocolManager().sendServerPacket((Player) receiver, packet);
    }
}
