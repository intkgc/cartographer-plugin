package com.jvmfrog.cartographer.listeners.chat;

import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class MainChatListener implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        event.joinMessage(Component.text()
                .append(Component.text("["))
                .append(Component.text("+", TextColor.fromHexString("#28ff03")))
                .append(Component.text("] "))
                .append(event.getPlayer().displayName())
                .build());
    }
}
