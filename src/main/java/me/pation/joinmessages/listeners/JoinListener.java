package me.pation.joinmessages.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.pation.joinmessages.JoinMessages;
import me.pation.joinmessages.utils.GetAvatar;

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
        String joinMsg = this.plugin.getConfig().getString("messages.join");

        String mainTitle = this.plugin.getConfig().getString("titles.main");

        String supportedVer = this.plugin.getConfig().getString("titles.supported-version");
        String oldVer = this.plugin.getConfig().getString("titles.old-version");

        Sound sound = Sound.valueOf(this.plugin.getConfig().getString("sounds.sound"));
        int volume = this.plugin.getConfig().getInt("sounds.volume");
        int pitch = this.plugin.getConfig().getInt("sounds.pitch");
        boolean global = this.plugin.getConfig().getBoolean("sounds.global");

        // Join message
        if (joinMsg != null) {
            joinMsg = PlaceholderAPI.setPlaceholders(event.getPlayer(), joinMsg);
            event.joinMessage(MiniMessage.miniMessage().deserialize(joinMsg));
        }

        boolean on = this.plugin.getConfig().getBoolean("motd.display");

        // MessageOfTheDay
        if (on) {
            UUID uuid = event.getPlayer().getUniqueId();
            GetAvatar PlayerMessage = new GetAvatar(this.plugin);
            event.getPlayer().sendMessage(MiniMessage.miniMessage().deserialize(PlaceholderAPI.setPlaceholders(event.getPlayer(), PlayerMessage.processImage(uuid))));
        }

        // Titles
        Component title = MiniMessage.miniMessage().deserialize(mainTitle);
        Component subtitle = MiniMessage.miniMessage().deserialize(supportedVer);

        // Get version from ViaVersion
        ViaAPI api = Via.getAPI();
        int version = api.getPlayerProtocolVersion(event.getPlayer()).getVersion();
        int serverVer = api.getServerVersion().highestSupportedProtocolVersion().getVersion();

        // Compare versions
        if (version < serverVer) {
            subtitle = MiniMessage.miniMessage().deserialize(oldVer);
        }

        Title wholeTitle = Title.title(title, subtitle);
        event.getPlayer().showTitle(wholeTitle);

        // Join sound
        if (global) {
            for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                player.playSound(player.getLocation(), sound, volume, pitch);
            }
            return;
        }
        event.getPlayer().playSound(event.getPlayer().getLocation(), sound, volume, pitch);
    }
}
