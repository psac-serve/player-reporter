package tokyo.peya.plugins.peyangplayerreporter.util;

import tokyo.peya.lib.SQLModifier;
import tokyo.peya.plugins.peyangplayerreporter.Variables;

import java.sql.Connection;
import java.util.Date;
import java.util.UUID;

public class ChatLogger
{
    public static void onChat(UUID senderUUID, String content)
    {
        try(Connection connection = Variables.chatLog.getConnection())
        {
            SQLModifier.insert(connection, "CHAT_LOG",
                    senderUUID.toString(),
                    new Date().getTime(),
                    content
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static void onPrivateMessage(UUID senderUUID, UUID recipientUUID, String content)
    {
        try(Connection connection = Variables.chatLog.getConnection())
        {
            SQLModifier.insert(connection, "PRIV_MSG_LOG",
                    senderUUID.toString(),
                    recipientUUID.toString(),
                    new Date().getTime(),
                    content
            );
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
