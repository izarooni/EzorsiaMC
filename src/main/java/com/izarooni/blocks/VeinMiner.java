package com.izarooni.blocks;

import com.izarooni.Ezorsia;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

public class VeinMiner {

    private final List<Material> Ores = new ArrayList<>(25);
    private final List<Material> Logs = new ArrayList<>(25);

    public VeinMiner(Ezorsia ezorsia) {
        File config = new File(ezorsia.getDataFolder(), "veins.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(config);

        yaml.getStringList("ores").forEach(material -> Ores.add(Material.getMaterial(material)));
        yaml.getStringList("logs").forEach(material -> Logs.add(Material.getMaterial(material)));
    }

    public void onBlockBreak(Player player, Material material, Location location) {
        ItemStack item = player.getInventory().getItemInMainHand();
        Material tool = item.getType();
        ItemMeta meta = item.getItemMeta();
        if (!(meta instanceof Damageable)) return;

        if (Ores.contains(material)) {
            if (isPickaxeMaterial(tool)) {
                destroyVein(player, material, location);
            }
        } else if (Logs.contains(material)) {
            if (isAxeMaterial(tool)) {
                destroyVein(player, material, location);
            }
        }
    }

    private void destroyVein(Player player, Material material, Location location) {
        destroyVein(player, material, location, new HashSet<>());
    }

    private void destroyVein(final Player player, final Material material, Location location, HashSet<Location> processed) {
        if (processed.contains(location)) return;
        processed.add(location);

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();

        Damageable damageable = (Damageable) meta;
        damageable.setDamage(damageable.getDamage() + 1);

        for (BlockFace face : RelativeFaces) {
            Location next = location.clone().add(face.getDirection());
            Block block = next.getBlock();

            Material nextMaterial = block.getType();
            if (nextMaterial == material) {
                block.breakNaturally(item);
                destroyVein(player, material, next, processed);
            }
        }
    }

    private static final BlockFace[] RelativeFaces = BlockFace.values();

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

    private static boolean isAxeMaterial(Material material) {
        return Axes.contains(material);
    }

    private static boolean isPickaxeMaterial(Material material) {
        return Pickaxes.contains(material);
    }
}
