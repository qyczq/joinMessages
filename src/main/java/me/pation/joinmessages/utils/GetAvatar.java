package me.pation.joinmessages.utils;

import me.pation.joinmessages.JoinMessages;

import java.io.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.util.List;
import java.util.UUID;

public class GetAvatar {

    private final JoinMessages plugin;

    public GetAvatar (JoinMessages plugin) {
        this.plugin = plugin;
    }

    public String processImage(UUID uuid) throws IOException {
        String PlayerMessage = "";

        URL url = new URL(this.plugin.getConfig().getString("motd.api").replace("%%uuid%%", uuid.toString()));
        BufferedImage image = ImageIO.read(url);
        List<?> r = this.plugin.getConfig().getList("motd.lines");

        for (int y = 0; y < 8; y++) {
            String allX = "";
            for (int x = 0; x < 8; x++) {
                String hex = String.format("%06x", 0xFFFFFF & image.getRGB(x, y));
                allX = allX + "<#" + hex + ">█</#" + hex + ">";
            }
            PlayerMessage = PlayerMessage + "<br>" + allX + " " + r.get(y);
        }
        PlayerMessage = PlayerMessage + "<br>";
        return PlayerMessage;
    }
}
