package eu.minelife.mLHarvester;

import eu.minelife.mLHarvester.commands.GiveHarvesterCommand;
import eu.minelife.mLHarvester.listeners.HarvesterListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MLHarvester extends JavaPlugin {

    @Override
    public void onEnable() {
        // Register command
        getCommand("giveharvester").setExecutor(new GiveHarvesterCommand());

        // Register event listener
        getServer().getPluginManager().registerEvents(new HarvesterListener(), this);

        // Log plugin startup
        getLogger().info("MLHarvester has been enabled!");
    }

    @Override
    public void onDisable() {
        // Log plugin shutdown
        getLogger().info("MLHarvester has been disabled!");
    }
}
