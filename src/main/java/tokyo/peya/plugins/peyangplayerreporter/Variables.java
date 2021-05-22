package tokyo.peya.plugins.peyangplayerreporter;

import com.zaxxer.hikari.HikariDataSource;
import org.bukkit.configuration.file.FileConfiguration;

public class Variables
{
    public static FileConfiguration config;
    public static HikariDataSource dataSource;
    public static HikariDataSource chatLog;
}
