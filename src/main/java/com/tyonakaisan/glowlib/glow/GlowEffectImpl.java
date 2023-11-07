package com.tyonakaisan.glowlib.glow;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

final class GlowEffectImpl implements GlowEffect {

    private Color color;
    private Long time;

    private static final String NOT_NULL_COLOR = "color must not be null";
    private static final String NOT_NULL_TIME = "time must not be null";
    private static final String NOT_NULL_RECEIVER = "receiver must not be null";

    GlowEffectImpl(final @NotNull Color color) {
        this.color = color;
        this.time = -1L;
    }

    GlowEffectImpl(final @NotNull Color color, final @NotNull Long time) {
        this.color = color;
        this.time = time;
    }

    @Override
    public @NotNull Color color() {
        return this.color;
    }

    @Override
    public @NotNull GlowEffect color(final @NotNull Color newColor) {
        Objects.requireNonNull(newColor, NOT_NULL_COLOR);
        final Color oldColor = this.color;

        if (!Objects.equals(newColor, oldColor)) {
            this.color = newColor;
        }

        return this;
    }

    @Override
    public @NotNull Long time() {
        return this.time;
    }

    @Override
    public @NotNull GlowEffect time(final @NotNull Long newTime) {
        Objects.requireNonNull(newTime, NOT_NULL_TIME);
        final Long oldTime = this.time;

        if (!Objects.equals(newTime, oldTime)) {
            this.time = newTime;
        }

        return this;
    }
    /*

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

    public void hide(final @NotNull Audience receiver) {
        List<WrappedDataValue> dataValues = new ArrayList<>();

        dataValues.add(new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), 0));

        PacketType type = PacketType.Play.Server.ENTITY_METADATA;
        PacketContainer packet = ProtocolLibrary.getProtocolManager().createPacket(type);

        packet.getIntegers().write(0, this.entity.getEntityId());
        packet.getDataValueCollectionModifier().write(0, dataValues);

        ProtocolLibrary.getProtocolManager().sendServerPacket((Player) receiver, packet);
    }

     */
}
