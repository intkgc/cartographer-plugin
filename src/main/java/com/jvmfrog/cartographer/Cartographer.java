package com.jvmfrog.cartographer;

import com.jvmfrog.cartographer.config.Config;
import com.jvmfrog.cartographer.datapack.DatapackBuilder;
import com.jvmfrog.cartographer.listeners.chat.MainChatListener;
import com.jvmfrog.cartographer.ui.LabelClickListener;
import lombok.Getter;
import lombok.SneakyThrows;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public final class Cartographer extends JavaPlugin implements Listener {
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
        Bukkit.getPluginManager().registerEvents(new MainChatListener(), this);
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new LabelClickListener(), this);
        buildDatapack();
    }

    public void buildDatapack() throws IOException, InvalidConfigurationException {
        if (Config.getWorld() != null)
            DatapackBuilder.build(pConfig.getLabelsDir(), new File(getServer().getWorldContainer().getAbsoluteFile(),
                    String.format("%s/datapacks/cartographer", Config.getWorld())));
        else jlogger.warning("world folder not set");
    }

    @EventHandler
    public void openGUI(PlayerJoinEvent event) {

    }

    @Override
    public void onDisable() {
    }
}
