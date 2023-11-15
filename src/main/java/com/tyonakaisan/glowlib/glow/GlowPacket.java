package com.tyonakaisan.glowlib.glow;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.utility.MinecraftReflection;
import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

final class GlowPacket {

    public @NotNull PacketContainer operateGlow(final @NotNull Entity entity, final boolean glowing) {
        PacketContainer metadataPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.ENTITY_METADATA);

        List<WrappedDataValue> dataValues = new ArrayList<>();
        WrappedDataWatcher dataWatcher = WrappedDataWatcher.getEntityWatcher(entity).deepClone();
        byte bitmask = dataWatcher.getByte(0);

        if (glowing) {
            bitmask |= 0x40;
        } else {
            bitmask = (byte) 0;
        }

        dataValues.add(new WrappedDataValue(0, WrappedDataWatcher.Registry.get(Byte.class), bitmask));

        metadataPacket.getDataValueCollectionModifier().write(0, dataValues);
        metadataPacket.getIntegers().write(0, entity.getEntityId());

        return metadataPacket;
    }

    public @NotNull PacketContainer operateTeam(final @NotNull Entity entity, final @NotNull GlowEffect glowEffect, final @NotNull Mode mode) {
        PacketContainer teamPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

        teamPacket.getIntegers().write(0, mode.ordinal());

        if (mode == Mode.CREATE_TEAM || mode == Mode.UPDATE_INFO_TEAM) {
            teamPacket.getOptionalStructures().read(0).ifPresent(internalStructure ->
                    internalStructure.getEnumModifier(GlowEffect.Color.class,
                                    MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                            .write(0, glowEffect.color()));
        }

        if (mode != Mode.REMOVE_TEAM) {
            var entityValue = entity instanceof Player ? entity.getName() : entity.getUniqueId().toString();
            teamPacket.getSpecificModifier(Collection.class).write(0, Collections.singletonList(entityValue));
        }

        return teamPacket;
    }

    public @NotNull PacketContainer operateTeam(final @NotNull Collection<Entity> entities, final @NotNull GlowEffect glowEffect, final @NotNull Mode mode) {
        PacketContainer teamPacket = ProtocolLibrary.getProtocolManager().createPacket(PacketType.Play.Server.SCOREBOARD_TEAM);

        teamPacket.getIntegers().write(0, mode.ordinal());

        if (mode == Mode.CREATE_TEAM || mode == Mode.UPDATE_INFO_TEAM) {
            teamPacket.getOptionalStructures().read(0).ifPresent(internalStructure ->
                    internalStructure.getEnumModifier(GlowEffect.Color.class,
                                    MinecraftReflection.getMinecraftClass("EnumChatFormat"))
                            .write(0, glowEffect.color()));
        }

        if (mode != Mode.REMOVE_TEAM) {
            teamPacket.getSpecificModifier(Collection.class).write(0, this.toEntityValueCollection(entities));
        }

        return teamPacket;
    }

    private Collection<String> toEntityValueCollection(final @NotNull Collection<Entity> entities) {
        Collection<String> entityValueCollection = new ArrayList<>();
        for (Entity entity : entities) {
            var entityValue = entity instanceof Player ? entity.getName() : entity.getUniqueId().toString();
            entityValueCollection.add(entityValue);
        }

        return entityValueCollection;
    }

    enum Mode {
        CREATE_TEAM,
        REMOVE_TEAM,
        UPDATE_INFO_TEAM,
        ADD_ENTITIES,
        REMOVE_ENTITIES;

        Mode() {
        }
    }
}
