package me.pation.joinmessages.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import me.pation.joinmessages.JoinMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    private final JoinMessages plugin;

    public QuitListener(JoinMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerQuit(PlayerQuitEvent event) {

        // Get from config
        String quitMsg = this.plugin.getConfig().getString("messages.quit");

        if (quitMsg != null) {
            quitMsg = PlaceholderAPI.setPlaceholders(event.getPlayer(), quitMsg);
            event.quitMessage(MiniMessage.miniMessage().deserialize(quitMsg));
        }
    }
}
