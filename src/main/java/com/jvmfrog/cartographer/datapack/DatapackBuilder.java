package com.jvmfrog.cartographer.datapack;

import com.jvmfrog.cartographer.Cartographer;
import com.jvmfrog.cartographer.translator.Translator;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class DatapackBuilder {
    private static final String mcmeta = """
            {
            \t"pack": {
            \t\t"pack_format": 10,
            \t\t"description": "Cartographer plugin datapack"
            \t}
            }
            """;

    public static void build(File input, File output) throws IOException, InvalidConfigurationException {
        if (!output.exists()) output.mkdirs();
        File packMeta = new File(output, "pack.mcmeta");
        File dataDir = new File(output, "data/cartographer_plugin/advancements");
        if (!packMeta.exists()) {
            packMeta.createNewFile();
        }
        FileOutputStream os = new FileOutputStream(packMeta);
        os.write(mcmeta.getBytes(StandardCharsets.UTF_8));

        if (!dataDir.exists()) dataDir.mkdirs();
        for (File f : Objects.requireNonNull(input.listFiles())) {
            YamlConfiguration yml = new YamlConfiguration();
            yml.load(f);
            String result = Translator.translate(yml);
            String jsonName = f.getName().substring(0, f.getName().length() - 4) + ".json";
            FileOutputStream outputStream = new FileOutputStream(new File(dataDir, jsonName));
            outputStream.write(result.getBytes(StandardCharsets.UTF_8));
            outputStream.close();
        }
        Cartographer.getInstance().getServer().dispatchCommand(Bukkit.getConsoleSender(), "minecraft:reload");
    }
}