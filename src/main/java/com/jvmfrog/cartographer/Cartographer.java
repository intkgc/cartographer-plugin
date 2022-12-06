package com.jvmfrog.cartographer;

import com.jvmfrog.cartographer.listeners.chat.MainChatListener;
import com.jvmfrog.cartographer.translator.Translator;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Cartographer extends JavaPlugin {
    @Getter
    private static Cartographer instance;
    @Getter
    private static Logger jlogger;

    private static String TEST = """
            name: Шиза
            description: КЕК
            icon: [lectern]
            box_start: [0, 10, 10]
            box_end: [10, 20, 0]
            dim: overworld
            """;

    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();
        System.out.println(12 & 23);
        YamlConfiguration x = new YamlConfiguration();
        x.loadFromString(TEST);

        System.out.println("\n" + Translator.translate(x));

        Bukkit.getPluginManager().registerEvents(new MainChatListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
