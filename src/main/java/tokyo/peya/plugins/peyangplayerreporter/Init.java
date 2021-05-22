package tokyo.peya.plugins.peyangplayerreporter;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import tokyo.peya.lib.SQLModifier;

import java.sql.Connection;

import static tokyo.peya.plugins.peyangplayerreporter.Variables.*;

public class Init
{
    public static void init()
    {
        initializeConfiguration();
        initializeVariables();
        initializeDatabases();
    }

    private static void initializeConfiguration()
    {
        PeyangPlayerReporter.instance.saveDefaultConfig();
        config = PeyangPlayerReporter.instance.getConfig();
    }

    public static void initializeDatabases()
    {
        try(Connection connection = dataSource.getConnection();
            Connection chatLogConnection = chatLog.getConnection())
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
                            "id text," +
                            "type text," +
                            "reason text" +
                            ");");

            SQLModifier.exec(connection,
                    "CREATE TABLE IF NOT EXISTS CHAT_REPORT_REASON(" +
                            "id text unique," +
                            "sender_uid text," +
                            "receiver_uid text," +
                            "time integer," +
                            "content text" +
                            ");");

            SQLModifier.exec(chatLogConnection,
                    "CREATE TABLE IF NOT EXISTS CHAT_LOG(" +
                            "sender_uid text," +
                            "time integer," +
                            "content text" +
                            ");");

            SQLModifier.exec(chatLogConnection,
                    "CREATE TABLE IF NOT EXISTS PRIV_MSG_LOG(" +
                            "sender_uid text," +
                            "receiver_uid text," +
                            "time integer," +
                            "content text" +
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

        databaseConfig.setJdbcUrl("jdbc:sqlite:chat.db");

        chatLog = new HikariDataSource(databaseConfig);
    }
}
