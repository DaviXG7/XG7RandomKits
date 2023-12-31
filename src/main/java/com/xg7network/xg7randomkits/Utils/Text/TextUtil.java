package com.xg7network.xg7randomkits.Utils.Text;

/*

    This class was made by DaviXG7 to make it
    easier to handle the plugin texts.

    The class is free to use if this text is
    copied into your plugin and your plugin is free.

    Thanks for reading/using my code <3

 */

import com.xg7network.xg7randomkits.Utils.PluginUtil;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

public class TextUtil {

    public static void send(String text, Player player) {
        if (text == null || text.equals("")) return;
        if (text.startsWith("ACTION: ")) sendActionBar(text, player);
        else player.sendMessage(get(text, player));

    }

    public static String get(String text, Player player) {

        text = PluginUtil.setPlaceHolders(text, player);

        text = new Color().translateHexColor(text);

        if (text.startsWith("CENTER: ")) {
            text = text.replace("CENTER: ", "");

            text = new CenterText(text,player).getCentralizedText();

        }

        return text.replace("&", "§");
    }

    public static String get(String text) {

        text = new Color().translateHexColor(text);

        if (text.startsWith("CENTER: ")) {
            text = text.replace("CENTER: ", "");

            text = new CenterText(text).getCentralizedText();

        }

        return text.replace("&", "§");

    }

    public static void sendActionBar(String text, Player player) {
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(get(text, player)));
    }

}
