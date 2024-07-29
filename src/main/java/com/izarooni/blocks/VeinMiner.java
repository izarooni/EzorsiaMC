package com.izarooni.blocks;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VeinMiner {
    public static void onBlockBreak(Player player, Material material, Location location) {
        ItemStack item = player.getInventory().getItemInMainHand();
        Material tool = item.getType();
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable)) return;

        if (isOreMaterial(material)) {
            if (isPickaxeMaterial(tool)) {
                destroyVein(player, material, location);
            }
        } else if (isLogMaterial(material)) {
            if (isAxeMaterial(tool)) {
                destroyVein(player, material, location);
            }
        }
    }

    private static void destroyVein(Player player, Material material, Location location) {
        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage() + 1);

        for (BlockFace face : RelativeFaces) {
            Location next = location.clone().add(face.getDirection());
            Material nextMaterial = next.getBlock().getType();
            if (nextMaterial == material) {
                next.getBlock().breakNaturally(item);
                destroyVein(player, material, next);
            }
        }
    }

    private static final BlockFace[] RelativeFaces = new BlockFace[]{
            BlockFace.UP,
            BlockFace.DOWN,

            BlockFace.NORTH,
            BlockFace.NORTH_EAST,
            BlockFace.NORTH_WEST,
            BlockFace.NORTH_NORTH_EAST,
            BlockFace.NORTH_NORTH_WEST,

            BlockFace.EAST,
            BlockFace.EAST_NORTH_EAST,
            BlockFace.EAST_SOUTH_EAST,

            BlockFace.SOUTH,
            BlockFace.SOUTH_EAST,
            BlockFace.SOUTH_WEST,
            BlockFace.SOUTH_SOUTH_EAST,
            BlockFace.SOUTH_SOUTH_WEST,

            BlockFace.WEST,
            BlockFace.WEST_NORTH_WEST,
            BlockFace.WEST_SOUTH_WEST,
    };

    private static final List<Material> Ores = new ArrayList<>(Arrays.asList(
            Material.COAL_ORE,
            Material.IRON_ORE,
            Material.GOLD_ORE,
            Material.REDSTONE_ORE,
            Material.LAPIS_ORE,
            Material.DIAMOND_ORE,
            Material.EMERALD_ORE,
            Material.NETHER_QUARTZ_ORE,
            Material.ANCIENT_DEBRIS,
            Material.COPPER_ORE,

            Material.DEEPSLATE_COAL_ORE,
            Material.DEEPSLATE_IRON_ORE,
            Material.DEEPSLATE_GOLD_ORE,
            Material.DEEPSLATE_REDSTONE_ORE,
            Material.DEEPSLATE_LAPIS_ORE,
            Material.DEEPSLATE_DIAMOND_ORE,
            Material.DEEPSLATE_EMERALD_ORE,
            Material.DEEPSLATE_COPPER_ORE
    ));

    private static final List<Material> Logs = new ArrayList<>(Arrays.asList(
            Material.OAK_LOG,
            Material.SPRUCE_LOG,
            Material.BIRCH_LOG,
            Material.JUNGLE_LOG,
            Material.ACACIA_LOG,
            Material.DARK_OAK_LOG,
            Material.CHERRY_LOG,
            Material.MANGROVE_LOG,

            // stripped
            Material.STRIPPED_OAK_LOG,
            Material.STRIPPED_SPRUCE_LOG,
            Material.STRIPPED_BIRCH_LOG,
            Material.STRIPPED_JUNGLE_LOG,
            Material.STRIPPED_ACACIA_LOG,
            Material.STRIPPED_DARK_OAK_LOG,
            Material.STRIPPED_CHERRY_LOG,
            Material.STRIPPED_MANGROVE_LOG
    ));

    private static final List<Material> Axes = new ArrayList<>(Arrays.asList(
            Material.WOODEN_AXE,
            Material.STONE_AXE,
            Material.IRON_AXE,
            Material.GOLDEN_AXE,
            Material.DIAMOND_AXE,
            Material.NETHERITE_AXE
    ));

    private static final List<Material> Pickaxes = new ArrayList<>(Arrays.asList(
            Material.WOODEN_PICKAXE,
            Material.STONE_PICKAXE,
            Material.IRON_PICKAXE,
            Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE,
            Material.NETHERITE_PICKAXE
    ));

    private static boolean isOreMaterial(Material material) {
        return Ores.contains(material);
    }

    private static boolean isLogMaterial(Material material) {
        return Logs.contains(material);
    }

    private static boolean isAxeMaterial(Material material) {
        return Axes.contains(material);
    }

    private static boolean isPickaxeMaterial(Material material) {
        return Pickaxes.contains(material);
    }
}
