package me.maodud.vault.enums;

import me.maodud.vault.util.Utils;

import java.util.HashMap;
import java.util.Map;

public enum Type {
    LOGIN,
    CARD,
    IDENTITY;

    public static Map<String, String> getTypeMap() {
        Map<String, String> map = new HashMap<>();
        for (Type type : Type.values()) {
            map.put(type.toString(), Utils.getReadableName(type.name()));
        }
        return map;
    }
}
