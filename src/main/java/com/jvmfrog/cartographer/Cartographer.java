package com.jvmfrog.cartographer;

import com.jvmfrog.cartographer.config.Config;
import com.jvmfrog.cartographer.datapack.DatapackBuilder;
import com.jvmfrog.cartographer.listeners.chat.MainChatListener;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Logger;

public final class Cartographer extends JavaPlugin {
    @Getter
    private static Cartographer instance;
    @Getter
    private static Logger jlogger;
    @Getter
    private static Config pConfig;

    private static String TEST = """
            name: Шиза
            description: КЕК
            icon: [lectern]
            box_start: [0, 10, 10]
            box_end: [10, 20, 0]
            dim: overworld
            parent: f:шиза
            """;


    @SneakyThrows
    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();
        pConfig = new Config(getDataFolder());

        DatapackBuilder.build(pConfig.getLabelsDir(), new File(getDataFolder(), "../../world/datapacks/test"));

        Bukkit.getPluginManager().registerEvents(new MainChatListener(), this);
    }

    @Override
    public void onDisable() {
    }
}
