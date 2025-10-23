package me.pation.joinmessages.utils;

import me.pation.joinmessages.JoinMessages;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.title.Title;
import net.kyori.adventure.text.Component;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.ViaAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class TitleAnimation {

    private final JoinMessages plugin;

    public TitleAnimation(JoinMessages plugin) {
        this.plugin = plugin;
    }

    public void animation(Player player){

        boolean viaVer = this.plugin.getConfig().getBoolean("titles.viaver");

        Component subtitlee;

        // only when viaversion used
        if (viaVer){
            String supportedVer = this.plugin.getConfig().getString("titles.supported-version");
            String oldVer = this.plugin.getConfig().getString("titles.old-version");

            // Get version from ViaVersion
            ViaAPI api = Via.getAPI();
            int version = api.getPlayerProtocolVersion(player).getVersion();
            int serverVer = api.getServerVersion().highestSupportedProtocolVersion().getVersion();

            // Compare versions
            if (version < serverVer) {
                subtitlee = MiniMessage.miniMessage().deserialize(oldVer);
            } else{
                subtitlee = MiniMessage.miniMessage().deserialize(supportedVer);
            }

        } else{
            String subTitle = this.plugin.getConfig().getString("titles.subtitle");

            subtitlee = MiniMessage.miniMessage().deserialize(subTitle);
        }

        List<?> titles = this.plugin.getConfig().getList("titles.main");

        int delay = this.plugin.getConfig().getInt("titles.delay");


        if (titles.toArray().length == 1) {

            Component y = MiniMessage.miniMessage().deserialize((String) titles.get(0));

            Title wholeTitle = Title.title(y, subtitlee);
            player.showTitle(wholeTitle);

            return;
        }

        for (int x = 0; x < titles.toArray().length; x++) {
            // Clear title if exist
            player.clearTitle();

            Component y = MiniMessage.miniMessage().deserialize((String) titles.get(x));

            Title wholeTitle = Title.title(y, subtitlee);
            Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, () -> {
                player.showTitle(wholeTitle);
            }, (long) x * delay);
        }
    }
}
