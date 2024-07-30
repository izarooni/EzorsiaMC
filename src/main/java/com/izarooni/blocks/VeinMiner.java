package com.izarooni.blocks;

import com.izarooni.Ezorsia;
import com.izarooni.events.PluginEventHandler;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
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

public class VeinMiner extends PluginEventHandler {

    private static final List<Material> Ores = new ArrayList<>(25);
    private static final List<Material> Logs = new ArrayList<>(25);

    public VeinMiner(Ezorsia ezorsia) {
        super(ezorsia);
        reload(ezorsia);
    }

    public static void reload(Ezorsia ezorsia) {
        File config = new File(ezorsia.getDataFolder(), "veins.yml");
        YamlConfiguration yaml = YamlConfiguration.loadConfiguration(config);

        yaml.getStringList("ores").forEach(material -> Ores.add(Material.getMaterial(material)));
        yaml.getStringList("logs").forEach(material -> Logs.add(Material.getMaterial(material)));

        Component message = Component
                .text(String.format("VeinMiner is enabled for %d ores and %d logs", Ores.size(), Logs.size()))
                .color(TextColor.color(0x00FF00));
        ezorsia.getServer().getConsoleSender().sendMessage(message);
    }

    public void onBlockBreak(Player player, Material material, Location location) {
        if (Ores.contains(material) || Logs.contains(material)) {
            destroyVein(player, material, location, new HashSet<>(50));
        }
    }

    private void destroyVein(final Player player, final Material material, Location location, HashSet<Location> processed) {
        if (processed.contains(location)) return;

        Block targetBlock = location.getBlock();
        Material targetMaterial = targetBlock.getType();
        if (targetMaterial != material) return;

        ItemStack item = player.getInventory().getItemInMainHand();
        if (Logs.contains(material) && !isAxeMaterial(item.getType())) return;
        if (Ores.contains(material) && !isPickaxeMaterial(item.getType())) return;

        ItemMeta meta = item.getItemMeta();
        if (meta instanceof Damageable) {
            Damageable damageable = (Damageable) meta;
            int damage = damageable.getDamage();
            if (damage >= item.getType().getMaxDurability()) {
                player.sendMessage(Component.text("Your tool is broken").color(TextColor.color(0xFF0000)));
                return;
            }
            damageable.setDamage(damage + 1);
            item.setItemMeta(meta);
        }

        targetBlock.breakNaturally(item, true, true);
        processed.add(location);

        for (BlockFace face : RelativeFaces) {
            if (face == BlockFace.SELF) continue;

            Location relative = location.clone().add(face.getModX(), face.getModY(), face.getModZ());
            Block relativeBlock = relative.getBlock();
            Material relativeMaterial = relativeBlock.getType();
            if (relativeMaterial == material) {
                destroyVein(player, material, relative, processed);
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
