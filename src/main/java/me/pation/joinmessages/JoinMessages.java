package me.pation.joinmessages;

import me.pation.joinmessages.commands.MainCommand;
import me.pation.joinmessages.listeners.JoinListener;
import me.pation.joinmessages.listeners.QuitListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class JoinMessages extends JavaPlugin {

    @Override
    public void onEnable() {

        // Plugin startup logic
        getLogger().info("JoinMessages started!");
        getServer().getPluginManager().registerEvents(new JoinListener(this), this);
        getServer().getPluginManager().registerEvents(new QuitListener(this), this);

        getCommand("joinMessages").setExecutor(new MainCommand(this));

        saveDefaultConfig();
    }

    @Override
    public void onDisable() {

        // Plugin shutdown logic
        getLogger().info("JoinMessages stopped!");
    }
}
