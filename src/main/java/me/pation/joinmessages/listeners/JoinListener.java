package me.pation.joinmessages.listeners;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import me.clip.placeholderapi.PlaceholderAPI;
import me.pation.joinmessages.JoinMessages;
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

public class JoinListener implements Listener {

    private final JoinMessages plugin;

    public JoinListener(JoinMessages plugin) {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onPlayerJoin(PlayerJoinEvent event) {

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
