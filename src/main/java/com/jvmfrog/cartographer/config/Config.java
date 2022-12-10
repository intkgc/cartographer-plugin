package com.jvmfrog.cartographer.config;

import com.jvmfrog.cartographer.Cartographer;
import lombok.Getter;

import java.io.File;

public class Config {
    @Getter
    private File configFile;
    @Getter
    private File labelsDir;

    public Config(File dataFolder) {
        configFile = new File(dataFolder, "config.yml");
        labelsDir = new File(dataFolder, "labels");
        if (!configFile.exists()) {
            firstLoad();
        }
    }

    public void firstLoad() {
        Cartographer.getInstance().saveDefaultConfig();
        labelsDir.mkdirs();
    }
}
