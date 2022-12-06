package com.jvmfrog.cartographer.translator;

import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class Translator {
    private static final String DISPLAY_OBJ = """
            "display": {
                "icon": {
                    "item": "minecraft:%s",
                    "nbt": "%s"
                },
                "title": "%s",
                "description": "%s",
                "show_toast": %b,
                "announce_to_chat": %b,
                "hidden": %b
                }""";

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

        builder.append("\n}");
        return builder.toString();
    }

    public static String getRoot() {
        return "";
    }
}
