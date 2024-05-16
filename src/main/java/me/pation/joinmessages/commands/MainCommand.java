package me.pation.joinmessages.commands;

import me.pation.joinmessages.JoinMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor, TabExecutor {

    private final JoinMessages plugin;

    public MainCommand(JoinMessages plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        String reloadMsg = this.plugin.getConfig().getString("messages.reload");

        if (args.length == 1 && args[0].equals("reload")) {
            sender.sendMessage(MiniMessage.miniMessage().deserialize(reloadMsg));
            this.plugin.reloadConfig();
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 1) return List.of("reload");

        return null;
    }
}
