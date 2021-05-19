package tokyo.peya.plugins.peyangplayerreporter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tokyo.peya.lib.SQLModifier;

import java.sql.Connection;

import static tokyo.peya.plugins.peyangplayerreporter.Variables.config;
import static tokyo.peya.plugins.peyangplayerreporter.Variables.dataSource;

public class Init
{
    public static void init()
    {
        initializeConfiguration();
        initializeVariables();
    }

    private static void initializeConfiguration()
    {
        PeyangPlayerReporter.instance.saveDefaultConfig();
        config = PeyangPlayerReporter.instance.getConfig();
    }

    public static void initializeDatabases()
    {
        try(Connection connection = dataSource.getConnection())
        {
            SQLModifier.exec(connection,
                    "CREATE TABLE IF NOT EXISTS REPORT(" +
                            "id text unique," +
                            "reporter_id text," +
                            "reporter_uid text," +
                            "target_id text," +
                            "target_uid text," +
                            "reported_at integer" +
                            ");");
            SQLModifier.exec(connection,
                    "CREATE TABLE IF NOT EXISTS REPORT_REASON(" +
                            "id text unique," +
                            "reason text" +
                            ");");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }


    private static void initializeVariables()
    {
        HikariConfig databaseConfig = new HikariConfig();
        databaseConfig.setJdbcUrl(config.getString("database.jdbc"));
        databaseConfig.setDriverClassName(config.getString("database.driver"));

        dataSource = new HikariDataSource(databaseConfig);
    }
}
