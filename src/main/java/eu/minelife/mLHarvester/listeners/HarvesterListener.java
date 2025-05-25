package eu.minelife.mLHarvester.listeners;

import eu.minelife.mLHarvester.utils.HarvesterHoe;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import java.util.HashMap;
import java.util.Map;

public class HarvesterListener implements Listener {

    // Map of crop materials to their seed materials
    private static final Map<Material, Material> CROP_TO_SEED = new HashMap<>();

    static {
        // Initialize the crop to seed mapping
        CROP_TO_SEED.put(Material.WHEAT, Material.WHEAT_SEEDS);
        CROP_TO_SEED.put(Material.POTATOES, Material.POTATO);
        CROP_TO_SEED.put(Material.CARROTS, Material.CARROT);
        CROP_TO_SEED.put(Material.BEETROOTS, Material.BEETROOT_SEEDS);
        CROP_TO_SEED.put(Material.NETHER_WART, Material.NETHER_WART);
        CROP_TO_SEED.put(Material.COCOA, Material.COCOA_BEANS);
        CROP_TO_SEED.put(Material.SWEET_BERRY_BUSH, Material.SWEET_BERRIES);
        CROP_TO_SEED.put(Material.MELON_STEM, Material.MELON_SEEDS);
        CROP_TO_SEED.put(Material.PUMPKIN_STEM, Material.PUMPKIN_SEEDS);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();
        Material blockType = block.getType();

        // Check if the player is using the harvester hoe
        ItemStack itemInHand = player.getInventory().getItemInMainHand();
        if (!HarvesterHoe.isHarvesterHoe(itemInHand)) {
            return;
        }

        // Check if the broken block is a crop
        if (!CROP_TO_SEED.containsKey(blockType)) {
            return;
        }

        // Check if the crop is fully grown
        if (block.getBlockData() instanceof Ageable) {
            Ageable ageable = (Ageable) block.getBlockData();
            if (ageable.getAge() != ageable.getMaximumAge()) {
                // Cancel the event if the crop is not fully grown
                event.setCancelled(true);
                return;
            }
        }

        // Get the seed type for the crop
        Material seedType = CROP_TO_SEED.get(blockType);

        // Check if the player has the seed in their inventory
        PlayerInventory inventory = player.getInventory();
        if (!inventory.contains(seedType)) {
            player.sendMessage("§cYou don't have any " + formatMaterialName(seedType) + " to replant!");
            return;
        }

        // Schedule a task to replant the crop after the block is broken
        player.getServer().getScheduler().runTaskLater(
            player.getServer().getPluginManager().getPlugin("MLHarvester"),
            () -> {
                // Remove one seed from the player's inventory
                inventory.removeItem(new ItemStack(seedType, 1));

                // Replant the crop
                block.setType(blockType);

                // Reset the age to 0 (newly planted)
                if (block.getBlockData() instanceof Ageable) {
                    Ageable ageable = (Ageable) block.getBlockData();
                    ageable.setAge(0);

                    // Special case for cocoa beans - they need to be attached to jungle logs
                    if (blockType == Material.COCOA) {
                        // We don't modify the age for cocoa, as it would lose its attachment
                        // The block data from the original block should be preserved
                    } else {
                        block.setBlockData(ageable);
                    }
                }

                //player.sendMessage("§aAutomatically replanted " + formatMaterialName(blockType) + "!");
            },
            1L // Delay of 1 tick
        );
    }

    /**
     * Formats a material name to be more readable
     * @param material The material to format
     * @return The formatted material name
     */
    private String formatMaterialName(Material material) {
        String name = material.name().toLowerCase().replace('_', ' ');
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }
}
