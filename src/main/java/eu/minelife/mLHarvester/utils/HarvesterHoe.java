package eu.minelife.mLHarvester.utils;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.NamespacedKey;

import java.util.ArrayList;
import java.util.List;

public class HarvesterHoe {

    private static final String HARVESTER_HOE_NAME = ChatColor.GOLD + "Harvester Hoe";
    private static final String HARVESTER_HOE_ID = "harvester_hoe";

    /**
     * Creates a special netherite hoe that automatically replants crops
     * @return The harvester hoe item
     */
    public static ItemStack createHarvesterHoe() {
        // Create a netherite hoe
        ItemStack hoe = new ItemStack(Material.NETHERITE_HOE);
        ItemMeta meta = hoe.getItemMeta();

        // Set custom name
        meta.setDisplayName(HARVESTER_HOE_NAME);

        // Add lore
        List<String> lore = new ArrayList<>();
        lore.add(ChatColor.GRAY + "A special hoe that automatically");
        lore.add(ChatColor.GRAY + "replants crops after harvesting");
        meta.setLore(lore);

        // Add enchantment glow
        meta.addEnchant(Enchantment.UNBREAKING, 3, true);
        meta.addEnchant(Enchantment.EFFICIENCY, 5, true);
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        // Set unbreakable
        meta.setUnbreakable(true);

        hoe.setItemMeta(meta);
        return hoe;
    }

    /**
     * Checks if an item is a harvester hoe
     * @param item The item to check
     * @return True if the item is a harvester hoe, false otherwise
     */
    public static boolean isHarvesterHoe(ItemStack item) {
        if (item == null || item.getType() != Material.NETHERITE_HOE) {
            return false;
        }

        ItemMeta meta = item.getItemMeta();
        if (meta == null || !meta.hasDisplayName()) {
            return false;
        }

        return meta.getDisplayName().equals(HARVESTER_HOE_NAME);
    }
}
