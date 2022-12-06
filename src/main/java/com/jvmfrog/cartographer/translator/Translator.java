package com.jvmfrog.cartographer.translator;

import com.jvmfrog.cartographer.exception.BoxException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Translator {
    private static final String DISPLAY_OBJ = """
            \t"display": {
            \t\t"icon": {
            \t\t\t"item": "minecraft:%s",
            \t\t\t"nbt": "%s"
            \t\t},
            \t\t"title": "%s",
            \t\t"description": "%s",
            \t\t"show_toast": %b,
            \t\t"announce_to_chat": %b,
            \t\t"hidden": %b
            \t},
            """;
    private static final String BOX_TEMPLATE = """
            \t"criteria": {
            \t\t"requirement": {
            \t\t\t"trigger": "minecraft:location",
            \t\t\t"conditions": {
            \t\t\t\t"location": {
            \t\t\t\t\t"position": {
            %s
            \t\t\t\t\t\t"dimension": "minecraft:%s"
            \t\t\t\t\t}
            \t\t\t\t}
            \t\t\t}
            \t\t}
            \t}""";
    private static final String POSITION_TEMPLATE = """
            \t\t\t\t\t\t"%c": {
            \t\t\t\t\t\t\t"min": %d
            \t\t\t\t\t\t\t"max": %d
            \t\t\t\t\t\t}""";
    private static final String TICK_CRITERIA = """
            \t"criteria": {
            \t\t"trigger": {
            \t\t\t"trigger": "minecraft:tick"
            \t\t}
            \t}""";
    private static boolean SHOW_TOAST = true;
    private static boolean ANNOUNCE_TO_CHAT = false;
    private static boolean HIDDEN = false;

    public static String translate(YamlConfiguration label) {
        StringBuilder builder = new StringBuilder();
        builder.append("{\n");

        List<String> icon = label.getStringList("icon");

        builder.append(
                String.format(DISPLAY_OBJ,
                        icon.get(0),
                        icon.size() > 1 ? icon.get(1) : "",
                        label.getString("name"),
                        label.getString("description"),
                        label.getBoolean("show_toast", SHOW_TOAST),
                        label.getBoolean("announce_to_chat", ANNOUNCE_TO_CHAT),
                        label.getBoolean("hidden", HIDDEN)
                ));

        List<Integer> boxStart = label.getIntegerList("box_start");
        List<Integer> boxEnd = label.getIntegerList("box_end");
        if (boxStart.size() == boxEnd.size()) {
            StringBuilder position = new StringBuilder();
            if (boxStart.size() == 3) {
                position.append(formatPositionString('x', boxStart.get(0), boxEnd.get(0)))
                        .append(",\n");
                position.append(formatPositionString('y', boxStart.get(1), boxEnd.get(1)))
                        .append(",\n");
                position.append(formatPositionString('z', boxStart.get(2), boxEnd.get(2)))
                        .append(",");
                builder.append(String.format(BOX_TEMPLATE, position, label.getString("dim")));
            } else if (boxStart.size() != 0)
                throw new BoxException("Array must contain 3 elements");
        } else throw new BoxException("Array sizes do not match");
        if (boxStart.size() == 0) {
            builder.append(TICK_CRITERIA);
        }
        if (label.getString("parent") != null) {
            builder.append(String.format("""
                    "parent":"%s",""", label.getString("parent")));
        }

        builder.append("\n}");
        return builder.toString();
    }

    private static String formatPositionString(char coordinate, int start, int end) {
        return String.format(POSITION_TEMPLATE,
                coordinate,
                Math.min(start, end),
                Math.max(start, end));
    }
}
