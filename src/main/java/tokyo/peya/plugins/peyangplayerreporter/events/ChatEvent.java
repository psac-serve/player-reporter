package tokyo.peya.plugins.peyangplayerreporter.events;

import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import tokyo.peya.lib.bukkit.EntitySelector;
import tokyo.peya.lib.bukkit.exception.SelectorInvalidException;
import tokyo.peya.lib.bukkit.exception.SelectorMalformedException;
import tokyo.peya.plugins.peyangplayerreporter.util.ChatLogger;

public class ChatEvent implements Listener
{
    @EventHandler
    public void onChat(AsyncPlayerChatEvent e)
    {
        ChatLogger.onChat(e.getPlayer().getUniqueId(), e.getMessage());
    }

    @EventHandler
    public void onPrivateChat(PlayerCommandPreprocessEvent e)
    {
        String msg = e.getMessage();
        if (!msg.startsWith("/tell") && !msg.startsWith("/msg") && !msg.startsWith("/w"))
            return;

        String[] args = StringUtils.split(msg, ' ');

        if (args.length < 3)
            return;

        String selector = args[0];


        Player player;
        if ((player = Bukkit.getPlayer(selector)) != null)
        {
            ChatLogger.onPrivateMessage(e.getPlayer().getUniqueId(), player.getUniqueId(), e.getMessage());
            return;
        }

        try
        {
            EntitySelector.matchEntities(e.getPlayer(), selector, false)
                    .stream()
                    .parallel()
                    .filter(entity -> entity instanceof Player)
                    .forEach(entity -> {
                        ChatLogger.onPrivateMessage(e.getPlayer().getUniqueId(), entity.getUniqueId(), e.getMessage());
                    });
        }
        catch (SelectorInvalidException | SelectorMalformedException selectorInvalidException)
        {
            selectorInvalidException.printStackTrace();
        }

    }

}
