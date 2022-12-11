package com.jvmfrog.cartographer.config;

import com.jvmfrog.cartographer.Cartographer;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

public class Config {
    @Getter
    private File configFile;
    @Getter
    private File labelsDir;

    @Getter
    private static boolean showToast = true;
    @Getter
    private static boolean announceToChat = false;
    @Getter
    private static boolean hidden = false;
    @Getter
    private static String world = null;

    public Config(File dataFolder) {
        configFile = new File(dataFolder, "config.yml");
        labelsDir = new File(dataFolder, "labels");
        if (!labelsDir.exists())
            labelsDir.mkdirs();
        if (!configFile.exists())
            Cartographer.getInstance().saveDefaultConfig();
        FileConfiguration config = Cartographer.getInstance().getConfig();
        showToast = config.getBoolean("show_toast");
        announceToChat = config.getBoolean("announce_to_chat");
        hidden = config.getBoolean("hidden");
        world = config.getString("world");
    }
}
