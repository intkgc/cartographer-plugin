package com.jvmfrog.cartographer.ui;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class LabelClickListener implements Listener {
    @EventHandler
    public void eventRightClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals("LABEL")) return;
        if (event.getItem().getType() != Material.PAPER) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        event.getItem().add(-1);
        ItemStack item = new ItemStack(Material.PAPER);

        Block block = event.getClickedBlock();

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();

        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.PAPER);
        meta.displayName(Component.text(String.format("X: %d, Y: %d, Z: %d", x, y, z)));

        item.setItemMeta(meta);

        event.getPlayer().getInventory().addItem(item);
    }
}
