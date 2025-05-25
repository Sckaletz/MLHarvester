package eu.minelife.mLHarvester.commands;

import eu.minelife.mLHarvester.utils.HarvesterHoe;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class GiveHarvesterCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            sender.sendMessage("§cUsage: /giveharvester <player>");
            return false;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer not found: " + args[0]);
            return false;
        }

        // Create the harvester hoe
        ItemStack harvesterHoe = HarvesterHoe.createHarvesterHoe();
        
        // Give the hoe to the player
        target.getInventory().addItem(harvesterHoe);
        
        // Send confirmation messages
        target.sendMessage("§aYou received a Harvester Hoe!");
        if (sender != target) {
            sender.sendMessage("§aGave a Harvester Hoe to " + target.getName());
        }
        
        return true;
    }
}