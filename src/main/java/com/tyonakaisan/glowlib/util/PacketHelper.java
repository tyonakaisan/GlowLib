package com.tyonakaisan.glowlib.util;

import com.comphenix.protocol.wrappers.WrappedDataValue;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import com.comphenix.protocol.wrappers.WrappedWatchableObject;

import java.util.ArrayList;
import java.util.List;

public final class PacketHelper {

    public static List<WrappedDataValue> watchableObjectsToDataValues(List<WrappedWatchableObject> watchableObjects) {
        List<WrappedDataValue> wrappedDataValueList = new ArrayList<>();
        for (WrappedWatchableObject entry : watchableObjects) {
            if (entry == null) continue;
            WrappedDataWatcher.WrappedDataWatcherObject obj = entry.getWatcherObject();
            wrappedDataValueList.add(new WrappedDataValue(
                    obj.getIndex(),
                    obj.getSerializer(),
                    entry.getRawValue()
            ));
        }
        return wrappedDataValueList;
    }
}
