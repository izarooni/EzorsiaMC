package com.izarooni.events;

import com.izarooni.Ezorsia;
import com.izarooni.blocks.VeinMiner;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockHandler extends PluginEventHandler {

    private final VeinMiner veinMiner;

    public BlockHandler(Ezorsia ezorsia) {
        super(ezorsia);

        veinMiner = new VeinMiner(ezorsia);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        if (!player.isSneaking()) {
            veinMiner.onBlockBreak(player, material, location);
        }
    }
}
