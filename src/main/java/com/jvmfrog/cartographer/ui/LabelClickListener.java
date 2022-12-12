package com.jvmfrog.cartographer.ui;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
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
    public void labelRightClick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (!event.getItem().getItemMeta().getDisplayName().equals("LABEL")) return;
        if (event.getItem().getType() != Material.PAPER) return;
        if (!event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) return;

        event.getItem().add(-1);
        ItemStack item = new ItemStack(Material.MAP);

        Block block = event.getClickedBlock();

        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();


        ItemMeta meta = Bukkit.getItemFactory().getItemMeta(Material.MAP);
        meta.displayName(Component.text(String.format("Label X: %d, Y: %d, Z: %d", x, y, z)));

        item.setItemMeta(meta);

        NBTItem nbt = new NBTItem(item);
        NBTCompound cartographer = nbt.addCompound("cartographer");
        cartographer.setInteger("x", x);
        cartographer.setInteger("y", y);
        cartographer.setInteger("z", z);
        String dim = switch (block.getWorld().getEnvironment()) {
            case NORMAL -> "overworld";
            case NETHER -> "nether";
            case THE_END -> "end";
            default -> "";
        };
        cartographer.setString("dim", dim);
        item = nbt.getItem();

        event.getPlayer().getInventory().addItem(item);
    }

    @EventHandler
    public void mapCLick(PlayerInteractEvent event) {
        if (event.getItem() == null) return;
        if (event.getItem().getType() != Material.MAP) return;
        NBTItem item = new NBTItem(event.getItem());
        if (!item.hasKey("cartographer")) return;
        event.setCancelled(true);

        Action action = event.getAction();
        String name = action == Action.LEFT_CLICK_BLOCK ?
                "box_start" : action == Action.RIGHT_CLICK_BLOCK ? "box_end" : null;
        System.out.println(name);
        if (name != null)
            event.getPlayer()
                    .getInventory()
                    .setItemInMainHand(saveCoords(event.getItem(), name, event.getClickedBlock()));
    }

    private ItemStack saveCoords(ItemStack item, String name, Block block) {
        int x = block.getX();
        int y = block.getY();
        int z = block.getZ();
        NBTItem nbt = new NBTItem(item);
        NBTCompound cartographer = nbt.getCompound("cartographer");
        NBTCompound coords = cartographer.addCompound(name);
        coords.setInteger("x", x);
        coords.setInteger("y", y);
        coords.setInteger("z", z);

        return nbt.getItem();
    }
}