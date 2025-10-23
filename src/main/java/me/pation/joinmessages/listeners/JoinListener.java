package me.pation.joinmessages.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.pation.joinmessages.JoinMessages;
import me.pation.joinmessages.utils.GetAvatar;
import me.pation.joinmessages.utils.TitleAnimation;

import java.io.IOException;
import java.util.UUID;

public class JoinListener implements Listener {

    private final JoinMessages plugin;

    public JoinListener(JoinMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) throws IOException {

        // Get from config
        boolean soundEnabled = this.plugin.getConfig().getBoolean("sounds.enabled");
        boolean motdEnabled = this.plugin.getConfig().getBoolean("motd.enabled");

        String joinMsg = this.plugin.getConfig().getString("messages.join");

        // Join message
        if (joinMsg != null) {
            joinMsg = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinMsg);
            event.joinMessage(MiniMessage.miniMessage().deserialize(joinMsg));
        }

        // Title
        TitleAnimation animation = new TitleAnimation(this.plugin);
        Bukkit.getScheduler().runTask(this.plugin, () -> {
            animation.animation(event.getPlayer());
        });

        // MessageOfTheDay
        if (motdEnabled) {
            UUID uuid = event.getPlayer().getUniqueId();
            GetAvatar PlayerMessage = new GetAvatar(this.plugin);
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                try {
                    event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(event.getPlayer(), PlayerMessage.processImage(uuid, event.getPlayer().getName()))));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
        }

        // Join sound
        if (soundEnabled){
            boolean global = this.plugin.getConfig().getBoolean("sounds.global");

            Sound sound = Sound.valueOf(this.plugin.getConfig().getString("sounds.sound"));
            int volume = this.plugin.getConfig().getInt("sounds.volume");
            int pitch = this.plugin.getConfig().getInt("sounds.pitch");

            if (global){
                for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                    player.playSound(player.getLocation(), sound, volume, pitch);
                }
                return;
            }
            event.getPlayer().playSound(event.getPlayer().getLocation(), sound, volume, pitch);
        }
    }
}
