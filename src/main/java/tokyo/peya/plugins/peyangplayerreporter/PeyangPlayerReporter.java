package tokyo.peya.plugins.peyangplayerreporter;

import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public final class PeyangPlayerReporter extends JavaPlugin
{
    public static PeyangPlayerReporter instance;
    public static Logger logger;

    public PeyangPlayerReporter()
    {
        instance = this;
        logger = getLogger();
    }

    @Override
    public void onEnable()
    {
        logger.info("Initialize...");
        Init.init();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }
}
