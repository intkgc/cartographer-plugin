package com.jvmfrog.cartographer;

import com.jvmfrog.cartographer.listeners.chat.MainChatListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class Cartographer extends JavaPlugin {
    @Getter
    private static Cartographer instance;
    @Getter
    private static Logger jlogger;

    @Override
    public void onEnable() {
        instance = this;
        jlogger = super.getLogger();
        jlogger.info("Hello from шиза!");
        System.out.println("SHIZA");
        Bukkit.getPluginManager().registerEvents(new MainChatListener(), this);
    }
    @Override
    public void onDisable() {
        jlogger.info("Plugin disabled");
    }
}
