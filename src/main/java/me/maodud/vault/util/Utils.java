package me.maodud.vault.util;

public class Utils {


    public static String getReadableName(String name) {
        String[] strings = name.split("_");
        StringBuilder sb = new StringBuilder(100);
        for (String str : strings) {
            sb.append(str.charAt(0)).append(str.substring(1, str.length()).toLowerCase()).append(" ");
        }
        return sb.toString();
    }
}
